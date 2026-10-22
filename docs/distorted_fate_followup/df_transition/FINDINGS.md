# Distorted Fate story end and Chapter 9 entry in 4.0.1

Verified against the live IDA GUI database for `work/il2cpp/libil2cpp_arm64.so.i64` using IDA MCP, plus the actual APK GameInformation serialized object. Image base is zero; addresses below are RVAs. No binary or IDB modifications were performed.

## Story end does not reach credits

`DFLevelEffect.Update` (`0x1CD0FB0`) waits until the audio stops after the ending UI sequence. It then:

1. Gets the current chart record and current result (`0x1CD1230` / `0x1CD124C`).
2. Updates the record, writes it back, and updates Ranking Score (`0x1CD1264`, `0x1CD12A4`, `0x1CD12D8`).
3. If the current difficulty is `IN` and current result score is at least 920000, writes `DistortedFateINSGrade = true` (`0x1CD1338`).
4. Saves `LastChapterBackgroundCode = IdBuilder.BuildBackgroundImageId("MainStory8_2BlurS")` (`0x1CD1454`).
5. Calls `BlackScreen.Show("QDEvent", 4.0)` (`0x1CD1474`).

It does not write `chapter8Passed`, `C8DistortedFateUnlocked`, or `GameCompleted`.

The end-of-song save and transition have **no minimum-score requirement** in this function. The 920000 check controls only `DistortedFateINSGrade`; the 880000 check below controls Chapter 9 selection independently. Finishing the story sequence alone must not be equated with meeting Chapter 9's score threshold.

`QDEventControl.<Start>d__5.MoveNext` (`0x1CD5FE0`) prepares and plays the video, captions, and the `O Fortuna velut luna statu variabilis……` message. Its final case 7 calls `BlackScreen.Show("ChapterSelector", 0.5)` at `0x1CD68A4`. It contains no persistent unlock writes and never requests the credits scene. This is the exact point the earlier analysis wrongly assumed led to AboutUs.

## Why Chapter 9 can nevertheless open

The actual `MainStory9` chapter object starts at raw byte offset **10940** in `docs/distorted_fate_analysis/level0_50.bin`. Parsed configuration is in `chapter9_config.json`; `parse_chapter9.py` reproduces extraction according to the field definitions from `dump.cs`:

```json
{"unlockMode":1,"unlockFlag":"DistortedFate.Sakuzyo.0","unlockTip":"","fakeCoverFlag":"","fakeCoverCode":"","fakeCoverBlurCode":""}
```

`ChapterSelector.Start` (`0x1D535F0`) handles unlockMode 1 by calling `IsSpecifiedSongReachScore(unlockFlag, 880000)` at `0x1D5543C`. The helper name is misleading: `0x1D55AD8` actually returns `records.All(t => t.s < score)`, with the `<` comparison explicitly visible in its predicate `0x1D56CF0`. The caller keeps the chapter locked when that result is true. Therefore the condition is:

```text
Chapter 9 selectable iff any Distorted Fate chart's saved score >= 880000.
```

`GameInformation.GetSongAllRecord` (`0x1CB98A4`) obtains records for every chart listed in the song definition. This test is independent of `chapter8Passed`, `C8DistortedFateUnlocked`, and `GameCompleted`. The story ending already saved its chart result before returning to ChapterSelector, so a sufficiently high story score satisfies Chapter 9's selector requirement even though the permanent Distorted Fate song flag and credits flag remain absent.

The native flow is consequently:

```text
Distorted Fate story finish
  -> save chart score / optional IN S flag
  -> QDEvent
  -> ChapterSelector
  -> MainStory9 unlocked if any DF record >= 880000
```

This condition should not be described as a mandatory `Chapter9UnlockBegin` write at Distorted Fate completion; there is no such write in the traced functions.

## Evidence files

`0x1CD0FB0.c`, `0x1CD5FE0.c`, `0x1D535F0_1460.c`, `0x1D55AD8.c`, and `0x1D56CF0.c` are IDA pseudocode exports with string literals resolved by matching reference addresses against `il2cpp/stringliteral.json`. Raw MCP responses are preserved in `exports_full_raw.json`. Exports of ChapterSelector.Start are selected pages; no claim is made that their concatenation is the full function.

The entire investigation is static analysis. Community behavior described by the user agrees with this control flow. No device playthrough was performed.
