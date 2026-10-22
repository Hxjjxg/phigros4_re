# Challenge Mode entry gate in Phigros 4.0.1

Read-only native analysis through ida-pro-mcp/idalib-mcp, plus serialized Unity scene inspection of `Phigros_4.0.1.apk`. RVAs equal IDA addresses (image base zero).

## Confirmed gate and scene binding

`RemoveAllSongControl.Awake` (RVA **0x1D5766C**) performs:

```csharp
if (SaveManagement.LoadString("GameCompleted", "") == "")
    Destroy(gameObject);
```

The read is at **0x1D576F0**, empty-string comparison **0x1D576FC**, and destruction **0x1D57740**. This is not a dedicated Challenge card. The serialized `ChapterSelector` scene (`level6`) proves that it destroys the **AllSong card, including its Challenge entry**:

- GameObject **638**, name `AllSong`, has `ChapterInfo` **4381** and `RemoveAllSongControl` **4382**.
- Its descendant `MoreInfo` (GameObject **658**) contains `ClassMode` (GameObject **17**).
- `ClassMode` has Button component **3702**. Its serialized persistent callback targets local component **3733**, i.e. the `ChapterSelector` script, invokes `LoadSongSelector`, and passes the string **`ChallengeMode`**.
- Button binary offsets: persistent call count at 308 = 1; target fileID at 312 = 0; target pathID at 316 = 3733 (64-bit); empty target assembly string length at 324; method string at 332 = `LoadSongSelector`; mode at 348 = 5 (String); string argument length at 408 = 13; argument at 412 = `ChallengeMode`; call state at 432 = 2 (RuntimeOnly).
- Exact hierarchy, component IDs, and readable raw strings: `challenge_assets.json`; raw callback: `level6_3702.bin`.

`ChapterSelector.LoadSongSelector` (**0x1D560E4**) assigns `GameInformation.nowChapterCode = chapterCode` at **0x1D561BC**. Other than special Chapter 8/9 routes, it starts `SongSelector` at **0x1D56304**. There is no extra Challenge unlock check here: the gate is the removal of its parent UI card.

`SongSelector.Start` (**0x1DA2114**) compares `nowChapterCode` with `ChallengeMode` at **0x1DA2668**, hides normal components, shows Challenge components, and binds the Challenge controller at **0x1DA2708**. This completes the callback-to-Challenge UI chain.

`ChapterSelector.Start` (**0x1D535F0**) also loads `GameCompleted` at **0x1D53908** and subtracts one from the chapter count if empty (**0x1D5392C**), consistent with this removed AllSong card.

## Completion key persistence

The native completion marker is **`GameCompleted`** (string literal pointer **0x42AB920**, `StringLiteral_4780`), default empty string. `SongSelector.Start` independently uses its non-emptiness to set `enableLegacy` at **0x1DA24FC**; this field alone is not the Challenge entry gate.

- `AboutUs.StartPlay` **0x1CB27F0** writes `GameCompleted = "4.0"` at **0x1CB28B0**, if its existing value differs. The credits subtask has separately confirmed the Chapter 9 completion writer in `C9AfterDeductionControl`.
- `GameProgressSaveModule.<Apply>g__SaveGameProgressKeys|36_0` **0x1D6356C** simply writes its `completed` field back to `GameCompleted` at **0x1D636B4**.
- `GameProgressSaveModule.<LoadFromLocal>g__LoadGameProgressKeys|38_0` **0x1D64C90** simply reads `GameCompleted` (default empty) into `completed` at **0x1D64DE4**.

Therefore an account that misses the credits/completion writer retains an empty marker and loses both the AllSong and Challenge Mode entry on every ChapterSelector load. Chapter 9 access can independently become available because it uses other progression keys. Existing saves with a nonempty completion marker retain the UI; this naturally makes fresh/incomplete accounts behave differently from old completed accounts.

These findings do not depend on a gameplay run; the scene linkage and native branches were inspected statically. No binary, save, or IDB edits were made.
