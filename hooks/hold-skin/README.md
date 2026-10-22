# Six-sprite hold skin (Phigros 4.0.1, Android arm64)

Standalone Frida 16 hook for single/multi hold heads, bodies, and tails. The
generated script is `phigros_hold_skin.js`. It uses exported IL2CPP APIs, with
no npm/bridge dependency and no APK rebuild. It is independent of the chart
editor and DG-LAB hooks.

**Status: the user confirms six-image loading; final rendering remains pending.**
Host tests include the actual APK's fractional hold sprite geometry. A read-only
directory listing confirms all six canonical filenames in `/data/local/tmp`.
This debugging pass does not inject another script or modify the phone.

## Images and launch

Default directory: `/local/data/tmp` (the path in the request).
Default filenames:

| Slot | Default file |
|---|---|
| Single head | `head_single.png` |
| Single body | `body_single.png` |
| Single tail | `tail_single.png` |
| Multi head | `head_multi.png` |
| Multi body | `body_multi.png` |
| Multi tail | `tail_multi.png` |

Pass `--directory /data/local/tmp` if that is the intended Android directory.
The example mapping now uses the observed `head_single.png` spelling. If a file
really is named `head_sigle.png`, explicitly map head_single to that filename.
The mapping also accepts filenames such as `hold_head.png`, `hold_body.png`,
`hold_tail.png`/`hold_end.png`, and their multi equivalents when explicitly set.
Files must already be readable by the game process. The runner never changes
permissions, pushes files, restarts the game, or establishes port forwarding.

After the device is available and a development listener is ready:

```powershell
python hooks/hold-skin/build.py
& 'F:\frida16\Scripts\python.exe' hooks/hold-skin/run.py --host 127.0.0.1:27042 --process Gadget --directory /data/local/tmp --files hooks/hold-skin/files.example.json
```

Start a new chart after injection. Holds that already existed before injection
are not scanned. Runner commands: `status`, `retry`, `quit`. Correct missing or
invalid files, run `retry`, then restart the chart. A successful image set is
cached until orderly disposal; reload the script to use changed images.

If an older build reports `expected an integer` at `pin`, rebuild and reload
the script. Frida 16 requires integer `0` for the native GC-handle Boolean
arguments; the sources now use it at every strong/weak handle creation site.
Use `quit`, rerun the launch command, and start a new chart. `retry` alone
does not reload code.

For embedding, define `globalThis.PHI_HOLD_SKIN_OPTIONS = {directory, files}` before
the generated script. RPC exports are `holdstatus`, `holdretry`, `holddispose`.
In the Frida REPL use `PhiHoldSkin.status()` and `await PhiHoldSkin.dispose()`.
Await disposal before unloading; native restoration/destruction needs a ticking
gameplay, song-selection, or Settings frame. On timeout, the hook stays loaded
with new replacement work disabled so cleanup can be retried.

## Tail strategy

The native game assigns only the highlighted head/body in `CreateNote`, so the
shared tail cannot identify whether a hold is highlighted. This hook reads the
native head/body pair after `CreateNote` returns, compares both to that judge
line's original prefab and `HoldHL0/HoldHL1`, and picks all three images together.
It clones the instance's `noteImages` array and replaces **index 2 with the
selected single or multi tail**. Any already-created renderers are synchronized
immediately, including `_holdEndSpriteRenderer1`.

This follows the game's cross-line and cross-side multi-press decision and its
highlight setting. Ambiguous pairs are reported and left untouched. The original
prefab and highlight fields remain the classification sources. There is no
shared-material mutation, timestamp reimplementation, mid-function assembly
patch, or `HoldControl.NoteMove` hook. Both native creation paths are covered by
the same function-return hook. No persistent raw-pointer `seen` set is used.

Images become new Texture2D/Sprite objects on Unity's thread. All six files and
textures must load successfully before any hold is changed. Sprites use each
native template's rect dimensions, pixels-per-unit, and normalized pivot. Images
of different dimensions are resampled into that rect using a temporary render
texture, restoring the previous render target afterward. This fits art to the
native shape; artwork with deliberate transparent padding should account for
that fit. `FullRect` meshes retain the body endpoints. Textures use clamp wrapping.
Both tails use the native tail's geometry but separate images and Sprite objects.
Native multi head/body rect heights are fractional in this APK. They remain
fractional in Sprite.Create; only texture/RenderTexture allocation and ReadPixels
use ceiling integer dimensions. The sprite samples its exact native rect inside
that buffer (less than one pixel at the top/right buffer edge can be excluded).
Native pivot, PPU, world extents and the shared tail template stay unchanged.

Managed GC handles and `DontUnloadUnusedAsset` retain owned sprites/textures.
Weak hold handles and rooted original arrays support restoration. Dead records
are pruned at chart-line startup and periodically at hold creation. Disposal
restores surviving holds before destroying owned assets and releasing handles.
Host tests exercise rollback after missing/corrupt images and renderer failure.

## Verification

```powershell
node --test hooks/hold-skin/tests/*.test.js
python hooks/hold-skin/build.py
```

Tests use a simulated Unity/Frida runtime; they cannot establish Android ABI,
GPU readback, colour-space, or real rendering correctness. Device verification
still needs ordinary and multi holds above/below lines, cross-line hold+tap and
hold+hold chords, highlight off, restarts/scene changes, short/long holds,
tail alignment, and orderly unload/reinjection. The status counts should show
both variants with zero skips and no error. No device test is claimed here.

See `NATIVE_NOTES.md` for current-binary evidence and why older hold scripts
must not be used as offsets or slot-order authority.
