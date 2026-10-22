# Phigros 4.0.1 Chapter 8 completion investigation

## Observations

- User reports the established Luminescence -> Retribution -> Distorted Fate story route opens Chapter 9, but does not permanently unlock Distorted Fate or Challenge Mode, and does not show credits.
- Original report incorrectly assumed QDEvent continued to AboutUs. A function containing unlock writes is not proof that the story reaches that function.
- Static analysis only; no new device playthrough performed.
- Native image: work/il2cpp/libil2cpp_arm64.so, IDA image base zero. Metadata: il2cpp/dump.cs and stringliteral.json.
- IDA GUI/Hex-Rays health is good. Session IDs shared with subagents returned Session not found; each agent independently adopted the existing GUI and successfully retrieved pseudocode. Some requests were interrupted by user messages, which is not a server error.

## Hypotheses

### H1: The credits relocation bypassed the old completion writer (root hypothesis)
- Supports: AboutUs.Back contains chapter8Passed and C8DistortedFateUnlocked writes, but DF -> QDEvent returns to ChapterSelector in the compiled image.
- Conflicts: Need determine whether another reachable writer replaces the old one.
- Test: Follow scene transitions and inspect current Chapter 9 ending, Challenge entry gate, and Chapter 9 admission condition.

### H2: Completion is written but later reset or overridden
- Supports: Could explain successful story with unavailable song and mode.
- Conflicts: No write on the traced DF/QDEvent path so far.
- Test: Examine save writers/readers and any reset/migration paths relevant to the flags.

### H3: Distorted Fate completion depends on an additional score/difficulty check
- Supports: Special story has an IN S-grade save key.
- Conflicts: DF story transition itself is unconditional on score; only the separate AT-related grade key is conditional.
- Test: Compare conditional score write with unconditional scene transition and serialized song unlock key.

## Experiments

Read-only native decompilation, cross-references, and serialized asset checks are used instead of code modifications.

- DFLevelEffect.Update (0x1CD0FB0) -> QDEvent; QDEventControl.<Start>d__5.MoveNext (0x1CD5FE0), final state -> ChapterSelector. This confirms the old AboutUs writer is bypassed on this route.
- C9AfterDeductionControl.<Start>d__12.MoveNext (0x1D3FFB0) writes Chapter 9 completion keys and GameCompleted=4.0. No old Chapter 8 completion keys in this function. Verified Message -> Deduction -> AfterDeduction scene chain; new ending Back returns through BootScene to ChapterSelector.

## Root Cause

Confirmed H1 with native transitions and serialized UI hierarchy. DF's ending bypasses AboutUs; Chapter 9 selector independently accepts any DF record >=880000. Challenge Mode's button is inside the AllSong parent destroyed while GameCompleted is empty. The normal Chapter 9 password route requires Mirage's Challenge Mode unlock, while the new final ending writes GameCompleted. This forms a circular dependency for a fresh save. The new ending also omits old C8 completion flags.

H2 was not needed to explain the reported behavior: save import helpers restore flags but the story route never writes them in the first place. H3 is rejected as the explanation for missing permanent unlock: the IN >=920000 branch sets only DistortedFateINSGrade; DF's scene transition is independent of that branch.

Evidence and practical limits are consolidated in FOLLOWUP.md. No claim is made that edited saves, imported preexisting completion state, or abnormal scene entries are impossible.

## Fix

No game patch requested or applied. This task identifies and documents the existing logic.
