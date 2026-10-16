# Settings UI investigation — 2026-10-16

## Observations

Android arm64, Phigros 4.0.1, Frida 16.7.19. The native settings entry renders
and its click reaches `show()`. Panel construction fails on `EditText.setText`:
`argument types do not match ... (CharSequence, TextView$BufferType)`.
Earlier TextView and CheckBox calls succeed with the same Java String conversion.

## Hypotheses

1. **Root hypothesis:** Frida resolves the EditText override and hides inherited
   one-argument overloads. Supports: error lists only the two-argument override.
   Conflicts: none. Test: enumerate EditText/TextView overloads; invoke the
   TextView one-argument overload explicitly on a disposable EditText.
2. Java String does not coerce to CharSequence. Supports: argument mismatch.
   Conflicts: preceding TextView/CheckBox calls work. Test: same String with
   the explicit TextView overload.
3. Activity/thread state is invalid. Supports: injected dialog on Unity Activity.
   Conflicts: prior widget constructors succeed on the Android main thread.
   Test: construct and set a disposable EditText on that same main thread.

## Experiment

The runtime lists only `(CharSequence, BufferType)` on EditText, but lists
`(CharSequence)` on TextView. Calling the explicit TextView overload succeeds.
A second probe found that `String(edit.getText())` and its direct `toString()`
produce `[object Object]`; casting the result to CharSequence returns `probe`.

## Root cause and fix

Frida's wrappers expose EditText overrides and Editable return objects differently
from their inherited TextView/CharSequence interfaces. Call TextView's explicit
one-argument overload and cast Editable to CharSequence before extracting text.
Regression verification: open the panel through the real settings button,
decode the QR from its screenshot, then save/reopen the URL through the form.

## Unload crash — testing stopped

The dialog subsequently rendered after also binding the static Bitmap overload
with `.call(UI.Bitmap, ...)`. With the dialog open, `run.py` received `quit`,
called `phiDispose()` (which schedules asynchronous UI dismissal), then detached.
A following attach/create_script failed with `TransportError: connection is closed`.
ADB confirmed the game process exited. Device log timestamp 21:41:26 +0800:
SIGSEGV, fault 0x4, Android main thread, ART InvokeVirtualOrInterfaceWithVarArgs
and JNI CallObjectMethod, followed by an anonymous frame.

Hypotheses for the next investigation:

1. Pending Java main-thread dismissal/callback races script unload. Supports:
   dispose schedules dismissal but returns before it runs; prior reloads without
   an open dialog succeeded. Conflicts: no symbolized anonymous frame yet.
   Test: close dialog and await dismissal before unload; compare open-dialog unload.
2. Registered Java listener survives its script callback. Supports: dialog has
   click/dismiss listeners registered through Frida. Conflicts: failure appears
   during teardown, not ordinary clicks. Test: clear listeners on UI thread and
   await cleanup before unloading.
3. IL2CPP thread attachment lifetime across repeated script unloads. Supports:
   runtime initializes using thread_attach without matching detach. Conflicts:
   crash is Android UI thread/JNI, not a Unity/IL2CPP frame. Test separately with
   judgment-only attach/unload and no UI construction.

No hypothesis has been proven and no speculative teardown fix was applied.
Dynamic testing stopped as requested; no Frida 17 fallback. The independent
four-grade fixture had not executed when this happened.

## Resumed investigation

User reopened the game and authorized continued panel debugging. PID 15157.
First experiment: instrument the existing closeSettings call and main-thread
completion without altering its implementation. Invoke dispose, wait for an
explicit second main-thread turn, then unload. This varies unload ordering only.
If disposal returns before the UI turn, the ordering defect is established;
if awaiting completion avoids failure, use that evidence for a cleanup barrier.

Experiment result: `close requested` -> `dispose returned` -> `UI close turn
completed`. Awaiting a second main-thread turn and then unloading succeeded;
the same PID 15157 remained attachable. This proves premature dispose completion.
It supports (but does not fully symbolize) the original JNI teardown crash.

Implemented: await UI cleanup and a subsequent UI turn, clear JS-backed listeners,
resolve off the Java callback stack, then remove the native entry on Unity's
thread before detaching hooks. The runner awaits this RPC; cleanup errors keep
the interactive script loaded for retry. Old-panel listeners are disconnected
before opening a replacement panel. Regression tests follow below.

## Regression results

`tests/settings_regression.py` drives the real Android views through the test-only
`settings_driver.js`. Final suite passed on Frida 16.7.19 / PID 15157:

- URL validation failures do not change active config.
- Save applies URL, four filters, disabled state and custom Chinese QR text.
- Close/reopen and replacement-dialog callbacks work.
- Android Back closes the panel.
- Three consecutive open-dialog disposal, unload and reinjection cycles preserve
  PID and restore saved configuration. A second dispose is harmless.
- Initial configuration restored; zero script error events.

OpenCV independently decoded default and Unicode QR screenshots. The initially
captured screenshots caught Android entry/checkmark animations; waiting 500 ms
before screenshot capture produces a stable opaque panel and settled checkmarks.
The final layout uses a compact adaptive scale and a fixed Save/Close footer.
The device's floating keyboard opens from the URL field and dismisses with Back,
leaving the panel open. Final evidence is under `evidence/`.

The original crash's premature-completion path is fixed and its orderly-unload
regression passes. Abrupt host termination/CLI automatic reload does not await
the cleanup contract; use the runner or await `phiDispose()` before unloading.
