# Static evidence: hold sprites, 4.0.1 arm64

Sources: local il2cpp/dump.cs, work/il2cpp/libil2cpp_arm64.so, its IDA database,
and the workspace APK. No game injection was used for these findings.

| Function | RVA | Behavior |
|---|---|---|
| JudgeLineControl.CreateNote(int,bool) | 0x1D74334 | Instantiate, select HL head/body, append hold to manager |
| HoldControl.Start | 0x1D83278 | Rotate for line side; does not initialize sprites |
| HoldControl.NoteMove | 0x1D83314 | Lazily add renderers, bind slots, animate transforms |
| SongSelector.Update | 0x1DA5610 | Song-selection warmup callback |
| Setting.Update | 0x1D95438 | Settings warmup callback |

## Slots and transforms

HoldControl.noteImages is at +0x50:

| Index | Renderer field | Offset | Highlight source |
|---|---|---|---|
| 0: head | _holdHeadSpriteRenderer | 0x98 | JudgeLineControl.HoldHL0 (+0x48) |
| 1: body | _holdSpriteRenderer | 0xA0 | JudgeLineControl.HoldHL1 (+0x50) |
| 2: tail | _holdEndSpriteRenderer1 | 0xA8 | Shared original tail |

NoteMove assigns body at 0x1D833F0, head at 0x1D83424 and tail at 0x1D8345C.
The body renderer belongs to the hold object; head and tail belong to its
holdHead and holdEnd children. Native code retains colour/alpha and animation.

The parent scale uses x = scale * 0.22 and a y derived from hold duration,
speed and remaining time. Head/tail child y scales compensate with
0.22 / parent.localScale.y. Therefore body world height must retain the native
length basis; changing body width by preserving source aspect is safe for the
length calculation. Caps can retain their own image aspect without dynamic
per-frame transform hooks. The native note-size setting still applies.

## Native chord decision

CreateNote gates highlighting with chordSupport (+0x114). It finds the current
note in LevelInformation.chartNoteSortByTime (+0x38) and compares neighboring
ChartNote.realTime values (+0x2C). The inspected finite-value paths use <= 0.001
for the previous neighbor and < 0.001 for the next. Same-line or raw-tick-only
scans cannot reliably reproduce cross-line decisions.

The hook follows native control flow directly, without reading the selected
head/body resources. CreateNote entry filters hold type from notesAbove/Below.
The actual highlighted branches mark that invocation as multi. The common
binding point swaps in a prebuilt three-sprite set, including its own tail.
Highlight disabled naturally bypasses the multi branch and selects single.

## Verified instruction probes

All offsets are relative to CreateNote. The entry signature is
EB2BBA6DE923016DFE6702A9F85F03A9. Its prologue allocates 96 bytes. The original
function uses no additional dynamic frame adjustment before these sites.
At all four interior sites x19 is JudgeLineControl.

| Probe | RVA | Offset | Hold register |
|---|---|---|---|
| multi_above | 0x1D74C18 | 0x8E4 | x21 |
| assign_above | 0x1D74C4C | 0x918 | x21 |
| multi_below | 0x1D75390 | 0x105C | x22 |
| assign_below | 0x1D753C4 | 0x1090 | x22 |

Each multi block writes only noteImages[0]/[1] from HoldHL0/HoldHL1. Each assign
probe follows those writes and precedes the manager's holdControls append.
The replacement array is consequently not mutated by the native HL writes.
Instruction signatures, offsets, registers and geometry are centralized in
native-profile.json. Runtime verifies all five code windows before hooking.
Build verification maps ELF64 PT_LOAD segments and checks the same signatures
and entry frame allocation. Changed code fails closed rather than guessing.

Entry uses a function-entry listener with onEnter only. Interior PCs use bare
function instruction probes. Local Frida 16.7.19 x64 experiments reproduced an
access violation with interior onEnter objects, while the same native bytes
with instruction probes completed both branches. Entry/interior correlation
uses thread ID plus the normalized native stack pointer. No native return
listener is installed, and nested/different-thread calls have separate keys.
The Windows smoke test establishes this mechanism locally, not ARM64 safety.

## Geometry evidence

UnityPy extraction from assets/bin/Data/data.unity3d, sharedassets12.assets:

| Sprite | Path ID | Rect width x height | PPU | Normalized pivot |
|---|---|---|---|---|
| Hold_Head | 71 | 989 x 50 | 100 | 0.5, 1 |
| Hold | 61 | 989 x 1900 | 100 | 0.5, 0 |
| Hold_End | 69 | 989 x 50 | 100 | 0.5, 0 |
| Hold2HL_0 | 55 | 1062 x 97.52381134033203 | 97.52066040039062 | 0.5, 1 |
| Hold2HL_1 | 76 | 1062 x 1852.952392578125 | 97.52066040039062 | 0.5, 0 |

These are size anchors, not target texture dimensions. Each PNG retains its own
rect and a uniform PPU. For body, PPU = source height * reference PPU /
reference height; for caps use width instead. This preserves body world length
and cap world width without anisotropic resampling. Both tail anchors use the
original tail metadata, with distinct single/multi Sprite objects and images.

The prior integer guard rejected valid fractional highlighted rects; the prior
Graphics.Blit fit also changed source aspect. Neither restriction remains in
the image creation path. No GPU readback or runtime native sprite getter is
needed. Fixture tests preserve the extracted fractional values for auditing.

## Compatibility and validation boundary

Texture2D's four-argument constructor is resolved by its full
(Int32,Int32,TextureFormat,Boolean) signature. Unity callbacks belong to Setting,
not SettingItemsManager. Frida 16 native bool arguments use integer 0/1.

Older scripts with CreateNote = 0x239739C or noteImages = 0x48 target different
binaries and are not offset/slot authorities for this implementation.
Other hooks that modify these guarded sites or shared replacement arrays are
not covered. Layout, instruction and instance checks fail closed on mismatch.

Static evidence and model tests cannot establish Android ABI, visual alignment,
colour space, scene lifecycle or chart/audio synchronization. Device tests are
still required; no successful Android run of this revision is claimed.
