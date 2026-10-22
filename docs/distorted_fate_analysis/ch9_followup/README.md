# Chapter 9 depends on Challenge Mode in 4.0.1

Read-only IDA MCP analysis of libil2cpp_arm64.so.i64, image base zero. Addresses below are RVAs. Saved pseudocode contains instruction addresses. Most earlier exports resolve literals from il2cpp/stringliteral.json by metadata reference address; the additional password/C9S6 exports retain raw literal symbols.

## Concrete progression dependency

`Chapter9Phase1Control.OpenInputPassword`, `0x1CFCA28`, permits opening the password UI only when `LoadBool("C9ExoplanetaryMirageUnlocked", false)` is true (call `0x1CFCAA4`), in addition to ordinary animation/play/focus guards. `StringLiteral_2573` is the pointer at `0x42A7428`.

On a correct password, `Chapter9InputPasswordControl.<Submit>b__1`, `0x1D49C38`, saves the password and schedules b__3 at `0x1D49DD8`. That method starts FadeToBlackThenStartC9S6. Its MoveNext at `0x1D4A144` calls `C9S6Unlock.StartC9S6` at `0x1D4A190`; StartC9S6 is `0x1CF3380` and prepares the secret C9S6 level. Thus Mirage unlock is a direct gate to the password route into subsequent story progression, rather than merely a song-library preference.

`C9S6Unlock.Complete`, `0x1CEED34`, writes `C9S6Unlocked=true` at `0x1CEED9C`. Callers include C9StoryControl.ReturnToChapterSelector MoveNext `0x1D12458` and C9DeductionControl.Awake `0x1CEEC50`. LaunchControl.ToChapter9 `0x1D0B28C` chooses phase 2 only when C9S6Unlocked and Chapter9LastSelectMusicIsPhase2 are both true; otherwise phase 1.

## How Mirage is actually unlocked

`ExoplanetaryMirageUnlock.IsProcessActive`, `0x1D0A4FC`, requires:

- Chapter9UnlockBegin true;
- C9ExoplanetaryMirageUnlocked false;
- collection poyidomejiu1 read.

`IsValidUnlockSelection`, `0x1D0A7F4`, requires exactly two ChallengeSongItems, of equal levelIndex/difficulty, in this order:

1. Evanescent.LeaF.0
2. AboutTheUniverse.SOTUIMIssionary.0

`GetPendingHiddenThirdSongId`, `0x1D0A790`, returns ExoplanetaryMirage.かめりあ.0 when those tests pass. The challenge selector wrapper at `0x1CE1CFC` delegates to it.

`ChallengeSongSelectorControl.Play`, `0x1CE1544`, appends this hidden third song, initializes a challenge run and transitions to the actual ChallengeMode scene at `0x1CE17B0`. The relevant logic belongs to Challenge Mode selection/play/result, rather than the Chapter 9 map's ordinary song results.

`ChallengeModeOverControl.Start`, `0x1D2107C`, invokes UnlockC9HiddenSongKeys at `0x1D216B8` when secretChallengeLifeFailed is false. This call precedes the numeric total-score ranking thresholds; do not infer a minimum challenge total from this unlock block alone.

`UnlockC9HiddenSongKeys`, `0x1D219A4`, requires Chapter9UnlockBegin and tries both the normal third challenge item and hiddenChallengeSongItem. Helper `0x1D21D38` maps its song ID to a save key and writes true. The dictionary initializer `0x1D22538` contains exactly:

```text
ExoplanetaryMirage.かめりあ.0 -> C9ExoplanetaryMirageUnlocked
EntrancetotheChaos.打打だいずvssiromaru.0 -> C9EntranceToTheChaosUnlocked
```

Therefore a save unable to enter Challenge Mode cannot obtain the Mirage flag via this normal progression route, and cannot open the password route to C9S6.

## Distinguish chapter visibility from progression

Chapter9UnlockBegin is written by Chapter9Phase1Control.Start `0x1CF87D0` on first entry (save call `0x1CF8AF8`), not by the Distorted Fate story completion. Chapter 9's selector visibility has a separate asset-defined Distorted Fate score condition, investigated in the parent report.

Scope: static compiled-code analysis, no save or binary modifications and no on-device tests. This report demonstrates the normal password-route dependency, not the impossibility of every conceivable abnormal scene entry or edited-save bypass.
