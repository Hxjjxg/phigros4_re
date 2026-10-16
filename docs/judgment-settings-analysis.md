# Phigros 4.0.1: judgment and settings integration

Analysis date: 2026-10-16. IDA input:
`E:\phigros4_re\work\il2cpp\libil2cpp_arm64.so`, image base 0.
Used ida-pro-mcp decompilation/xrefs plus `il2cpp/dump.cs` and live Frida 16.7.19
metadata discovery. The older `EN_Judge_Analysis.md` supplied functional names
only; its addresses were not used.

## Judgment callbacks

| Function | Current arm64 RVA | Managed arguments |
|---|---:|---|
| ScoreControl.Miss | 0x1D874AC | float noteCode |
| ScoreControl.Bad | 0x1D8758C | float noteCode, float judgeTime |
| ScoreControl.Good | 0x1D87680 | float noteCode, float judgeTime, Vector3 position, bool isHold |
| ScoreControl.Perfect | 0x1D87864 | same as Good |

These RVAs were independently resolved by runtime method names and match IDA.
Implementation uses runtime discovery instead of fixed RVAs or field offsets.

All four append to `_noteCodes`, update counters, and optionally invoke their
`OnMiss/OnBad/OnGood/OnPerfect` Action. Observing the concrete methods preserves
existing callbacks. Perfect increments combo; Good increments combo and early/
late; Bad/Miss reset combo. Good clears AP; Bad/Miss clear AP and FC.

Xrefs confirm Click/Drag/Flick/Hold call Miss and Perfect, Click/Hold call Good,
and only Click calls Bad. This version additionally has
`HoldControl.ForceCompleteByBlock` calling Good/Perfect. The prototype includes
these score settlements. Hold head visuals do not necessarily call ScoreControl;
hooking head effects as well would risk premature/double reporting.

AAPCS64 places noteCode/judgeTime and the Vector3 HFA in floating-point registers;
the Good/Perfect `isHold` argument is in x1. The prototype intentionally emits
grade/counters without pretending general-purpose args contain the floats.

Counter offsets observed in dump: combo 0x4C, perfect 0x58, good 0x5C, bad 0x60,
miss 0x64. Actual code resolves offsets by field name.

## Settings construction and layout

| Function | RVA | Observed behavior |
|---|---:|---|
| Setting.Start | 0x1D93C18 | Creates sliders/toggles through settingItemsManager; ends with RefreshItems |
| Setting.Update | 0x1D95438 | Existing Unity update suitable for one-time main-thread insertion after Start |
| SettingItemsManager.AddButton | 0x1D98540 | Instantiates buttonPrefab under itemsHolder, gets ButtonSettingItem, sets title and adds supplied listeners |
| SettingItemsManager.AddToggle | 0x1D9890C | Toggle creation helper; initial-value overload at 0x1D98A6C |
| SettingItemsManager.AddSlider | 0x1D98688 | Slider creation helper; value/range overloads also present |
| SettingItemsManager.RefreshItems | 0x1D98AD0 | Walks active SettingItem descendants, calls RefreshPosition, sums heights, updates scroll dimensions |
| SettingItemsManager.RefreshPosition | 0x1D98D40 | Updates position for slanted scrolling |
| SettingItem.RefreshPosition | 0x1D9EFA4 | Shared item positioning |
| ButtonSettingItem.Start | 0x1D9EEF0 | Installs prefab button forwarding callback |
| ButtonSettingItem.<Start>b__3_0 | 0x1D9EF90 | Invokes this item's OnClick event |
| Setting.ChangeTab | 0x1D95EE0 | Indexes frame/tab animator and scroll-content arrays, updates selection and slider |
| SettingLayoutAdjuster.Refresh | 0x1D98EC8 | Layout entry point; not needed by this implementation |

`SettingItemsManager` has viewport/content/itemsHolder plus button/slider/toggle
prefabs. `AddButton` requires an array; a null listener array throws. An empty
`UnityAction[]` works. The prototype invokes this helper on the Unity thread,
names the object `PhigrosJudgmentLink`, moves it to the first sibling position,
then calls `RefreshItems`. Clicking is observed only for that specific item.

The localized title class is
`Phigros2.Utils.MultiLanguageText.MultiLanguageTextString`. Its `set_NewString`
method (0x1CE01CC) **appends** to each locale; using a newly constructed instance
with empty fields makes this suitable for a shared title. Strings, arrays and
title are temporarily rooted with GC handles while construction runs.

Adding a new tab is more involved than incrementing an index: frameAnimators,
tabAnimators and tabScrollContents must stay aligned, with animator state and
slider geometry. Reusing the existing list is the implemented approach.

## QR and settings panel

The shipped prototype uses an Android dialog attached to UnityPlayer.currentActivity,
opened from the game's native button. It supplies editable URL/custom QR text,
enable and grade filters, save/connect, and status display. It uses independent
SharedPreferences rather than modifying GameInformation or game save files.

For a future fully Unity-rendered panel, create/clone a panel under the current
Canvas, add InputField/Toggle/Button components with correctly bound UnityAction
delegates, and display generated QR pixels through Texture2D + RawImage. Reserve
space through a SettingItem if the QR is inside the slanted scroll list; arbitrary
children without SettingItem won't contribute to RefreshItems height. Texture
creation, updates and destruction must execute on Unity's main thread. Use point
filtering, an opaque white background, a four-module quiet zone, and square
integer sizing. Keep its modal input/Back behavior separate from chart input.

No AssetRipper inspection was necessary: the existing serialized prefabs and
creation helpers were accessible directly. No asset bundle was modified.

## Initial crash and completed panel debugging

Screenshots (workspace artifacts, ignored by git):

- `work/judgment/entry.png`: native Judgment link entry in Gameplay settings.
- `work/judgment/panel.png`: original prototype dialog.
- `hooks/judgment/evidence/settings-panel.png`: final verified layout.
- `hooks/judgment/evidence/custom-qr.png`: verified Unicode QR.

At device time **2026-10-16 21:41:26 +0800**, PID 28956 crashed when the runner
quit while its dialog was open and a new injection was being prepared. Logcat
reports SIGSEGV at address 0x4 on the Android main thread, in
`art::InvokeVirtualOrInterfaceWithVarArgs`, `JNI::CallObjectMethod`, then an
anonymous frame. A pending dialog-dismiss callback racing script unload is a
hypothesis, not a proven root cause. No gameplay was running at that point.

Testing initially stopped, then resumed after the user reopened the game and
requested completion of panel debugging. Instrumentation confirmed that dispose
returned before the UI close turn completed. Waiting for UI completion allowed
unload without a process exit. The implementation now removes JS-backed view
listeners, dismisses on Android's main thread, drains a subsequent UI turn,
resolves off the callback stack, removes the native entry on Unity's thread,
and only then detaches hooks. Cleanup is awaited and idempotent.

Final regression tests on PID 15157 passed three consecutive open-dialog
dispose/unload/reinject cycles without changing PID. Real widget Save, disabled
state, grade filters, URL validation, persistence across injection, Close,
Android Back and replacement dialogs pass. The initial settings were restored.
OpenCV decoded screenshots to the expected default URL and custom Chinese text.
The final panel keeps Save/Close fixed and adapts sizing for landscape height.

Frida 17 was never used. Abrupt host termination or
CLI auto-reload bypassing the awaited cleanup contract is not covered; use
`run.py` and `quit`, or await `phiDispose()` before unloading in the CLI.

## Actual gameplay verification

The user played **Igallta AT Lv.17**. The result screenshot shows Perfect 224,
Good 188, Bad 21 and Miss 681. All four hook observation counters exactly match.
The server received **890** events: Good 188, Bad 21, Miss 681, Perfect 0.
This is the intended default Perfect filtering behavior.

Checked every event: unique `(session, seq)`, strictly increasing sequence,
one increment for the reported non-Perfect grade, no increments for other
non-Perfect grades, and sequence gaps matching intervening Perfect counts.
No counter resets or synthetic events appeared. 18 Good messages identify holds.
The final message counters match the result screenshot. The host reports zero
dropped events and zero hook errors, and connection OPEN at verification.

The receiver ran at PC loopback port 18765, forwarded through adb reverse;
the game used the existing TDS WebSocket transport. Session: `mvb1uyva`.
Snapshots are under `hooks/judgment/evidence/gameplay-*`.
This establishes correct behavior for the tested run, not a latency measurement
or a reconnect/delivery guarantee. The isolated fixture was unnecessary for this
run and has not been executed.
