# Phigros 4.0.1 credits relocation: compiled evidence

Read-only analysis through IDA MCP, GUI database libil2cpp_arm64.so.i64, image base zero. Pseudocode exports retain native instruction addresses and resolve StringLiteral identifiers using il2cpp/stringliteral.json.

## Actual Chapter 9 ending

1. `MessageLevelEffects.Update`, RVA `0x1D469EC`: when the chart time exceeds endTime and its audio is no longer playing, capture the result and call `SceneManager.LoadScene("Deduction")` at `0x1D46C30`.
2. `C9DeductionControl.<FinishAndWhiteOut>d__76.MoveNext`, RVA `0x1CF2440`: after white-out, call `LoadScene("AfterDeduction")` at `0x1CF24F4`.
3. `C9AfterDeductionControl.<Start>d__12.MoveNext`, RVA `0x1D3FFB0`: upon first execution, before preparing/playing the video and rolling credits, write the flags listed below and call `PlayerPrefs.Save()` at `0x1D401B0`.
4. `C9AfterDeductionControl.Update`, RVA `0x1D3FD8C`: advances the credits transform by elapsed time; after configured `time + 6` seconds (or permitted hold-to-skip) calls Back.
5. `C9AfterDeductionControl.Back`, RVA `0x1D3FC00`: `GameInformation.nextScene = "ChapterSelector"`; starts transition to `"BootScene"`. It never calls old `AboutUs.Back`.

The writes in step 3 are:

```text
Chapter9Phase2Passed = true       // call 0x1D400BC
C9HateUnlocked = true            // call 0x1D400D4
C9DesultorySignalsUnlocked = true // call 0x1D400EC
C9MessageUnlocked = true         // call 0x1D40104
C9HappyEndingUnlocked = true     // call 0x1D4011C
delete C9PendingMessageAfterQuit // call 0x1D40130
delete C9PendingMessageLevel     // call 0x1D40144
GameCompleted = "4.0"           // call 0x1D40164
```

The new ending has no write to `chapter8Passed` or `C8DistortedFateUnlocked`.

## Old credits and misleading residual code

`AboutUs.Back`, RVA `0x1CB21C8`, still contains the old completion writes, conditional on `LoadBool("chapter8Passed", false)` being false: `chapter8Passed=true` at `0x1CB2540`, `C8DistortedFateUnlocked=true` at `0x1CB2558`. The earlier report incorrectly inferred that the DF story flow reached this function.

`AboutUs.StartPlay`, RVA `0x1CB27F0`, still sets `GameCompleted="4.0"` if it differs, but this is another old credits controller method, not evidence of a call from Chapter 8 or Chapter 9's current ending.

`"AboutUs"` string address `0x42A5F68` has code references only in `Setting.AboutUs`, RVA `0x1D967C4`, plus a metadata data reference. This confirms the current directly named scene entry is the settings path; it does not establish practical accessibility before game completion.

The settings entry is itself gated: `Setting.Start` (`0x1D93C18`) reads `GameCompleted` at `0x1D94C7C`; if it differs from `"4.0"` at `0x1D94C88`, it hides `aboutUsText`'s parent GameObject with `SetActive(false)` at `0x1D94CC4`. Thus a fresh save cannot naturally use the settings credits to obtain the old C8 flags before completing the current ending. `0x1D93C18_excerpt.c` contains the relevant paginated decompilation (starting at pseudocode line 590; unrelated string literals remain unresolved).

`Chapter9Phase2Progress.CompleteChapterFromAboutUs`, RVA `0x1D1C200`, is particularly misleading by name: compiled body merely checks current step==4, session active, stage==7 (Credits), and logs `"Tried to complete Chapter 9 outside the valid credits state."` on mismatch. There are **no save writes** in its body and xrefs show only method metadata at `0x4230440`, no native call site. Do not treat this method as the implementation of current final unlocks.

## Scope

This report covers native ending transitions and state writes. The DF-to-QDEvent return and Challenge Mode gates are being investigated in separate reports. No on-device runtime test or binary modification was performed.
