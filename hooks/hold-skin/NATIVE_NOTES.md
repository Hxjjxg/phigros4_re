# Static evidence: hold sprites, 4.0.1 arm64

Sources: this workspace's `il2cpp/dump.cs`, `work/il2cpp/libil2cpp_arm64.so`,
and its IDA database. Addresses below document analysis; runtime hooks resolve
metadata names. No device injection was used to obtain these findings.

| Function | RVA | Observed behavior |
|---|---|---|
| `JudgeLineControl.CreateNote(int,bool)` | `0x1D74334` | Instantiate, bind chart note/judge line, select HL head/body, append HoldControl to manager |
| `JudgeLineControl.Start` | `0x1D758B4` | Startup/preload opportunity on Unity thread |
| `HoldControl.Start` | `0x1D83278` | Set rotation based on judge-line side; does not initialize sprites |
| `HoldControl.NoteMove` | `0x1D83314` | Lazily add three renderers and bind array slots; animate body and head/tail transforms |

## Verified slot order

`HoldControl.noteImages` is at `+0x50` in this version:

| Index | Native renderer field | Offset | Highlight source |
|---|---|---|---|
| 0: head | `_holdHeadSpriteRenderer` | `0x98` | `JudgeLineControl.HoldHL0`, `+0x48` |
| 1: body | `_holdSpriteRenderer` | `0xA0` | `JudgeLineControl.HoldHL1`, `+0x50` |
| 2: tail | `_holdEndSpriteRenderer1` | `0xA8` | Shared prefab tail; no HL tail field |

IDA confirms body slot 1 assignment at `0x1D833F0`, head slot 0 assignment at
`0x1D83424`, and tail slot 2 assignment at `0x1D8345C`. The body renderer is
created first on the HoldControl game object; head and tail renderers are
created on `holdHead` (`+0x68`) and `holdEnd` (`+0x70`). Their sorting orders
are set to -1. These renderers continue handling native colour/alpha and movement.

`CreateNote` has separate above/below Hold cases. Both write only slots 0 and 1
when highlighted, then append to `NoteUpdateManager.holdControls` (`+0x48`).
The hook captures the pre-call count and identifies the newly appended instance
by its `noteInfor` AND `judgeLine`, avoiding a global last-created pointer.

## Classification

The native decision is gated by `JudgeLineControl.chordSupport` (`+0x114`).
It finds the current ChartNote in the global
`LevelInformation.chartNoteSortByTime` (`+0x38`), then checks adjacent
`ChartNote.realTime` values (`+0x2C`). The previous-neighbor comparison uses
`<= 0.001`, and the next-neighbor comparison uses `< 0.001` in the finite-value
paths inspected. Thus same raw chart tick, x coordinate, floor position, and
same-line-only scans are all insufficient substitutes.

This implementation reads the native result instead. Both head and body must
match a known pair; if native single and multi resources are identical or another
hook partially rewrites them, it fails closed for that hold. Highlight disabled
selects the single skin. No multi decision is made from the common tail.

## Ordering and compatibility

A custom manager can call `NoteMove` independently of Unity's ordinary Update
ordering. Therefore `HoldControl.Start` alone is not treated as proof of being
before first movement. Applying on `CreateNote` return covers both native cases
without touching internal branch instructions. Existing renderer fields are
also synchronized, making array replacement effective if initialization has
already occurred. No per-frame hold interception is installed.

Older scripts in `F:\tool\phi_reversed` target different binaries (e.g.
`CreateNote = 0x239739C`, `noteImages = 0x48`) and contain contradictory
head/body labels and timing claims. Those addresses and comments were not
ported. This build checks several metadata field offsets against the 4.0.1
layout before installing hooks.

Texture2D has two four-parameter constructors beginning `(int,int,...)`.
Resolution must match the complete `(Int32,Int32,TextureFormat,Boolean)`
signature, not just name/count or first argument. The settings Update callback
belongs to `Setting`, not `SettingItemsManager`.

## Validation boundary

Static UnityPy inspection of `assets/bin/Data/data.unity3d` in the workspace's
Phigros_4.0.1.apk finds these sprites in sharedassets12.assets:

| Sprite | Path ID | Rect width x height | PPU | Serialized normalized pivot |
|---|---|---|---|---|
| Hold_Head | 71 | 989 x 50 | 100 | 0.5, 1 |
| Hold | 61 | 989 x 1900 | 100 | 0.5, 0 |
| Hold_End | 69 | 989 x 50 | 100 | 0.5, 0 |
| Hold2HL_0 | 55 | 1062 x 97.52381134033203 | 97.52066040039062 | 0.5, 1 |
| Hold2HL_1 | 76 | 1062 x 1852.952392578125 | 97.52066040039062 | 0.5, 0 |

Rect's float fields are x/y/width/height at offsets 0/4/8/12 in dump.cs.
Requiring integer native widths/heights rejects the real highlighted sprites.
The fixture in tests/fixtures/native-hold-sprites.json preserves the extracted
values. Allocate ceiling integer texture dimensions, retain the fractional
Sprite rect and original PPU, and avoid truncating rect dimensions through int.
The scale/offset Graphics.Blit overload is stripped in this binary; use the
verified two-argument overload.

Host tests cover image transaction rollback, pair selection, independent tail
assignment, source array isolation, already-created renderers, restoration,
and GC handle release in a model. Static IDA inspection verifies the native
selection and binding paths. The user confirms image loading; final appearance
after the fractional geometry correction remains pending. Neither model tests
nor syntax checks establish device rendering correctness.
