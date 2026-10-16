# Judgment hooks and settings prototype

**Settings panel and actual gameplay judgment delivery verified with Frida 16.7.19.**
Open-dialog orderly unload, repeated injection, persistence, validation and QR
decoding pass. One actual Igallta AT run matched all four result counters and
delivered all 890 non-Perfect judgments without duplicates. See
[`DEBUG.md`](DEBUG.md) and [`../../docs/judgment-settings-analysis.md`](../../docs/judgment-settings-analysis.md).

## Implemented

- Resolve `ScoreControl.Perfect/Good/Bad/Miss` through IL2CPP metadata, by name.
  No hook addresses are copied from the older judgment analysis.
- Observe original execution with `Interceptor.attach`; read grade/combo counters
  on return. All grades are observed, but only Good/Bad/Miss are forwarded by default.
- Reuse `network/ws/phigros_ws.js`. Defer transport calls off the judgment callback.
  The bounded queue holds at most 256 pending events; disconnected events are
  dropped and counted. No replay, acknowledgement, or guaranteed delivery.
- Add a native Unity `ButtonSettingItem` to the Gameplay settings list.
- Open an Android dialog inside the Unity Activity for URL, enable/grade filters,
  connection status, and QR text. Settings use a separate SharedPreferences file.
- Generate QR locally with vendored MIT-licensed qrcode-generator, UTF-8, level M,
  four-module quiet zone, integer scaling. Blank QR text uses the server URL.

The dialog is an Android overlay launched from a real Unity settings button;
it is not a new Unity tab. It needs no APK/bundle modification.

## Build and attach

Only use Frida **16** (tested attachment: **16.7.19**, arm64 Phigros **4.0.1**).

```powershell
& 'F:\frida16\Scripts\python.exe' hooks\judgment\build.py
adb forward tcp:27042 tcp:27042
# Only when the receiving server is on the PC:
adb reverse tcp:8765 tcp:8765
& 'F:\frida16\Scripts\python.exe' hooks\judgment\run.py
```

Build reads the existing transport and source files, then writes the standalone
`phigros_judgment.js`. Do not load the original transport a second time.
No frida-il2cpp-bridge dependency is needed: this build exports the C APIs.

For a server on the same Android device, loopback means that device; for a LAN
server use its LAN address. A loopback URL in a QR will also mean the scanning
device's own loopback. Set custom QR text to whatever the companion app expects.
The companion app's actual protocol has not yet been specified.

Runner commands: `status`, `settings`, `close`, `configure {JSON}`, `quit`.
`quit` awaits UI and Unity cleanup before detaching. The Gameplay settings page
also has a **Judgment link** button; Save and Close stay visible at the bottom.

For the original Frida CLI workflow, load `phigros_judgment.js` with Frida 16.
The JavaScript REPL commands are:

```javascript
phiStatus()
phiSettings()
await phiCloseSettings()
phiConfigure({url: 'ws://127.0.0.1:8765', enabled: true})
phiConfigure({perfect: true}) // diagnostics only
```

Before unloading/reloading in the CLI, run `await phiDispose()` and wait for
completion. The tested runner handles this automatically. Forced termination or
automatic file reload bypasses this lifecycle and is not covered by the tests.
If Unity settings updates are paused, cleanup returns an error and the runner's
interactive `quit` keeps the script loaded: return to Gameplay settings and retry.

## Message contract

```json
{
  "type": "judgment",
  "version": 1,
  "source": "phigros",
  "session": "injection-session-id",
  "seq": 2,
  "timestamp": 1790000000000,
  "grade": "good",
  "isHold": true,
  "combo": 2,
  "counts": {"perfect": 1, "good": 1, "bad": 0, "miss": 0}
}
```

`timestamp` is host-process wall-clock milliseconds captured at callback entry.
`session` identifies script injection, not a chart. `seq` increments for all
observed grades, so filtered events leave gaps. `counts`/`combo` are read from
the receiving ScoreControl after settlement. `isHold` is available for
Good/Perfect, and null for Bad/Miss (their functions have no hold argument).
No noteCode/judgeTime is claimed: on arm64 those arguments are in SIMD registers,
not `args[1]`/`args[2]`. Holds report final settlement, not the initial head effect.

## Verification status

Confirmed on device:

- Frida 16.7.19 attachment and 241 exported IL2CPP APIs.
- Four runtime-resolved RVAs match the current IDA database.
- Native settings entry renders at the top of Gameplay and opens the dialog.
- Final responsive dialog and full QR quiet zone render correctly; fields scroll
  when needed and Save/Close remain fixed at the bottom.
- Real Save button applies URL, all grade filters, enable/disable and custom QR.
- Invalid scheme, missing host, zero/oversized port and fragment are rejected
  without changing the active configuration.
- Close button, Android Back and replacement of an already open panel work.
- Three consecutive open-dialog dispose/unload/reinject cycles keep the same
  game PID; settings persist, native entry is removed/recreated and disposal is
  idempotent. The full suite was run again after the final UI changes.
- Default QR decodes to `ws://127.0.0.1:8765`; custom Unicode QR decodes to
  `phigros://pair?name=测试&token=local-test` from device screenshots.
- Tapping the native entry opens the final panel; focusing URL opens the device
  keyboard and Back dismisses the keyboard without closing the panel.

Evidence: [`evidence/settings-panel.png`](evidence/settings-panel.png),
[`evidence/custom-qr.png`](evidence/custom-qr.png), and
[`evidence/settings-regression.json`](evidence/settings-regression.json).

Reproduce panel tests with no other judgment script attached, Gameplay settings
visible, and Frida port forwarded:

```powershell
& 'F:\frida16\Scripts\python.exe' hooks\judgment\tests\settings_regression.py
```

This exercises actual Android widgets, restores the initial configuration, and
unloads cleanly. QR screenshot decoding was performed separately using OpenCV;
no Frida 17 library was imported or used.

Actual gameplay verification (Igallta AT Lv.17, session `mvb1uyva`):

| Grade | Game result | Hook observed | Server received |
|---|---:|---:|---:|
| Perfect | 224 | 224 | 0 (filtered as intended) |
| Good | 188 | 188 | 188 |
| Bad | 21 | 21 | 21 |
| Miss | 681 | 681 | 681 |

890 received events; no duplicate `(session, seq)` pairs, no gaps in successive
non-Perfect counters, zero dropped/error counts. 18 Good events have `isHold=true`.
Sequence gaps exactly account for Perfect increments. No synthetic events were
used. The connection remained OPEN when verified. This verifies one actual run;
it does not measure network latency or guarantee delivery through disconnects.

Evidence: [`gameplay-verification.json`](evidence/gameplay-verification.json),
[`gameplay-events.jsonl`](evidence/gameplay-events.jsonl), and
[`gameplay-result-counts.png`](evidence/gameplay-result-counts.png).

The optional isolated fixture remains unexecuted; actual gameplay covered all
four callbacks instead. It is excluded from production builds, adds
`synthetic: true`, and does not call scene/save methods. `tests/capture_server.py`
was used as the local receiver at port 18765 with `adb reverse tcp:18765 tcp:18765`.
It appends events to `work/judgment/received.jsonl`.
