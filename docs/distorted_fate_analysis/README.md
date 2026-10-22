# Distorted Fate unlock conditions — Phigros 4.0.1

[中文研究索引与验证状态](INDEX.md)

Investigated the ARM64 `libil2cpp_arm64.so` through ida-pro-mcp. Addresses below are RVAs (IDA image base 0). Serialized configuration was read from the supplied APK to resolve values that are stored in assets rather than native code.

## Playable unlock route

**Correction after tracing the actual 4.0.1 scene transitions:** this is a route to the Distorted Fate *story chart*, not a complete permanent song unlock route. The previous version of this report incorrectly connected QDEvent to AboutUs. The compiled QDEvent ending returns to ChapterSelector. See [the follow-up investigation](FOLLOWUP.md).

1. Reach Chapter 8's second phase. The DESTRUCTION 3,2,1 story ending writes `chapter8UnlockSecondPhase = true`.
2. Play **Luminescence** in the Chapter 8 story selection. Its special ending requires both of these checks:
   - No **Bad or Miss** while the chart clock is between **86.087 and 96.521 seconds**, inclusive. Good judgments are allowed.
   - At the first update at/after **130.434 seconds**, the internal score must be at least **684796.5 (EZ)**, **677965.5 (HD)**, or **784842.8125 (IN)**. These are `0.92f ×` the serialized difficulty-specific target score. For a whole-number target, aim for at least **684797 / 677966 / 784843** respectively.
3. Continue the special ending into **Retribution**. The game preserves Luminescence's selected difficulty and adds `LevelMod/RetributionEffectSecondPhase`.
4. Survive Retribution's HP challenge from approximately **40.421 to 101.684 seconds**. Reaching zero HP causes failure. After the challenge locks and the track finishes, the game loads **`DFUnlock`**.
5. Play **Distorted Fate** and continue through the story. The actual sequence is `DFLevelEffect -> QDEvent -> ChapterSelector`; it does not show credits or write `chapter8Passed` / `C8DistortedFateUnlocked`. The old `AboutUs.Back` still contains those writes, but this route does not call it. A Distorted Fate chart record >= 880000 independently satisfies Chapter 9's chapter-selection unlock condition.

The difficulty selected for Retribution is also used for the Distorted Fate story chart.

## AT

The song's serialized AT configuration has `unlockType = 2` (all keys required):

```text
chapter8Passed == true
AND DistortedFateINSGrade == true
```

`DistortedFateINSGrade` is set when the current Distorted Fate **IN** result has an integer score **>= 920000** (S or higher). This is handled both by the special story ending and by normal result processing. Obtaining this flag during the story does not also set the missing `chapter8Passed` or permanent song unlock flag.

The Chapter 8 song entry separately requires `C8DistortedFateUnlocked`. EZ/HD/IN chart entries use the game's normal difficulty unlocking rules; the special all-keys condition above applies to AT.

## Native evidence

| RVA | Function | Relevant behavior |
|---|---|---|
| `0x1CD0338` | `Destruction321LevelEffect.<DestructAnimation>d__59.MoveNext` | Writes Chapter 8 second-phase flag. |
| `0x1D350D4` | `Chapter8SelectMusicControl.Play` | Adds Luminescence's story effect before Chapter 8 completion. |
| `0x1D2B8C8` | `LuminescenceLevelEffect.OnEnable` | Subscribes Bad/Miss handlers; sets target score to configured value times `0.92f`. |
| `0x1D2C864`, `0x1D2C878` | Luminescence judgment callbacks | Bad/Miss during the event window sets its failure flag. |
| `0x1D2C41C` | `LuminescenceLevelEffect.Update` | Checks time window and score; starts special ending only if neither failure flag is set. |
| `0x1D2CEE0` | `LuminescenceLevelEffect.<UnlockRetribution>d__54.MoveNext` | Starts Retribution with the second-phase level modifier and same difficulty. |
| `0x1D2ECF0` | `RetributionEffectSecondPhase.Update` | Checks HP, locks challenge after its end, and loads `DFUnlock` when audio finishes. |
| `0x1CD3EF8` | `DFUnlockControl.<Start>d__13.MoveNext` | Selects `DistortedFate.Sakuzyo.0`, preserving difficulty. |
| `0x1CD0FB0` | `DFLevelEffect.Update` | Saves the IN S flag at `>= 920000`; continues story regardless of score. |
| `0x1CB21C8` | `AboutUs.Back` | Contains old Chapter 8 completion/permanent song writes; not reached by the current DF story ending. |
| `0x1D7EBDC` | `LevelOverControl...Handle92wRequirement` | Normal results also set the IN S flag at `>= 920000`. |
| `0x1CBAE10` | `GameInformation.CheckSongUnlock` | Chart unlock type 2 checks every required save key. |

## Asset evidence

- `level0`, GameInformation object path ID `50`: Chapter 8 entry and embedded song configuration. Distorted Fate's song record starts at byte offset `35904` in the exported object; [song_unlock_rules.json](song_unlock_rules.json) contains the decoded record.
- Addressable `LevelMod/LuminescenceLevelEffect`: `CAB-c3dd343576d14de5ce1fb583353b1925`, object path ID `3058949334611908021`. Its exported JSON contains the timing and target-score values quoted above.
- The `.c` files alongside this report contain IDA pseudocode with metadata string literals resolved. They are analysis exports, not compilable source.

These findings are from static native-code and asset analysis; the unlock sequence was not played through on a device.
