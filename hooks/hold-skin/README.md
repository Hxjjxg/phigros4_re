# Six-sprite hold skin (Phigros 4.0.1, Android arm64)

Standalone Frida 16 hook for single/multi hold heads, bodies and tails.
The generated script is phigros_hold_skin.js; edit src/ and rebuild.
No bridge/npm dependency, APK rebuild or device file transfer is required.

**Status: static checks and local host tests pass; Android rendering and
chart/music synchronization after this revision still require device testing.**
Development checks attach only to isolated local test processes, never the game.

## Images and launch

Default image directory: /local/data/tmp, as in the original request.
Use --directory /data/local/tmp for the directory shown in the user's command.

| Logical slot | Default PNG |
|---|---|
| head_single | head_single.png |
| body_single | body_single.png |
| tail_single | tail_single.png |
| head_multi | head_multi.png |
| body_multi | body_multi.png |
| tail_multi | tail_multi.png |

Use --files with a UTF-8 JSON mapping to override filenames. The example now
uses head_single.png. If the actual image is head_sigle.png, map head_single
to that spelling explicitly. Filenames must not contain directory separators.
Each PNG is limited to 4096 pixels per axis, 8,388,608 pixels and 16 MiB.
All files must already be readable by the game process. The runner never pushes
files, changes permissions, forwards ports or restarts the game.

~~~powershell
python hooks/hold-skin/build.py
& 'F:/frida16/Scripts/python.exe' hooks/hold-skin/run.py --host 127.0.0.1:27042 --process Gadget --directory /data/local/tmp --files hooks/hold-skin/files.example.json
~~~

**Remain in song selection or Settings until the log says Ready, then start a
new chart.** Injecting during a chart leaves its existing holds untouched.
If warmup is incomplete when a hold is created, native sprites are retained;
chart loading does not wait for the skin or decode images on demand.

Runner commands: status, retry, quit. On an image error, fix the file and use
retry while in song selection/Settings. Images are cached after successful
warmup. To change source code or successfully loaded images, quit and rerun;
retry does not reload either. Rebuild after every source/profile change.

## Geometry and loading cost

Images retain their original PNG dimensions. One pixels-per-unit value scales
both axes uniformly; there is no Graphics.Blit, RenderTexture or ReadPixels.

- Body height in world units matches the native body length basis. Its width
  follows the PNG aspect ratio instead of being forced to the native width.
  Native animation still stretches the body vertically for hold duration.
- Head and tail use the corresponding native width as a uniform size anchor;
  their heights follow their own PNG aspect ratios, so caps are not flattened.
- Native normalized pivots are retained: head (0.5, 1), body/tail (0.5, 0).
  Source canvas size includes transparent padding; art content is not cropped.
- Geometry anchors come from this APK's static sprite metadata in
  native-profile.json, including fractional highlighted dimensions.

File reads and header validation are scheduled on the script thread. Unity
textures and sprites are prepared one image per song-selection/Settings Update
callback. Two complete Sprite[3] sets are built once, after all six succeed.
No PNG loading, GPU readback, sprite property queries or array cloning occurs
in the chart creation callback. Per-hold interception and GC handles still
have a cost; this is not a guarantee of zero loading latency.

Status includes prepared, phase, classifier, spriteSets, tracked, single,
multi, skipped, activeCalls, lastStepMs and maxStepMs. A ready skin has six
prepared images, two sprite sets and classifier native-branch. Timing values
measure individual Unity warmup steps, not end-to-end chart loading.

## Native selection and independent tails

The game does not assign three highlighted materials together. CreateNote
changes only noteImages[0] and [1] in its highlighted hold branches. Later,
HoldControl.NoteMove binds slots 0/1/2 to three separate SpriteRenderers.

This revision observes the actual highlighted branch execution in CreateNote;
**it does not compare head/body sprites or reimplement chord timing**.
At the common point immediately after native selection, it replaces the hold's
noteImages field once with the prepared single or multi Sprite[3] set.
Both sets contain an independent index-2 tail, regardless of the shared native
tail. Existing renderer fields, if populated, are synchronized as well.

Both above/below paths and the game's highlight setting are followed. Entry
context is correlated by thread ID and native entry stack pointer, with the
verified 96-byte ARM64 frame normalized at instruction probes. No CreateNote
return hook or per-frame HoldControl.NoteMove hook is installed. Interior sites
use bare function instruction probes, not function-entry onEnter objects.

The profile is specific to the original 4.0.1 arm64 binary. The entry and all
four interior instruction windows are checked before any hook is installed;
layout/code mismatch fails closed. Other patches touching those sites are not
supported. Build-time checks also compare the profile against the original
local ELF when it is present. See NATIVE_NOTES.md for addresses and evidence.

## Lifecycle

Managed handles and DontUnloadUnusedAsset retain owned sprites/textures.
Prepared arrays are shared per variant and never modified after creation.
Weak hold handles and rooted original arrays support safe restoration. Up to
eight dead records are pruned per menu callback, avoiding gameplay scans.
Disposal restores surviving holds before destroying assets and freeing handles.

For embedding, set globalThis.PHI_HOLD_SKIN_OPTIONS = {directory, files} before
loading. RPC methods: holdstatus, holdretry, holddispose. Await disposal before
unloading. Cleanup needs a ticking gameplay, song-selection or Settings frame.
If cleanup times out, the script remains loaded with new replacement disabled;
return to a ticking scene and retry quit. Do not force unload owned assets.

## Verification

~~~powershell
node --test hooks/hold-skin/tests/*.test.js
python -m unittest discover -s hooks/hold-skin/tests -p test_*.py -v
python hooks/hold-skin/build.py
& 'F:/frida16/Scripts/python.exe' hooks/hold-skin/tests/smoke_frida_probes.py
~~~

The last command is optional and Windows x64 only. It attaches to its own
Python child and exercises 1,000 synthetic native calls, checking branch
correlation, stack normalization and unchanged return registers. It does not
validate Android ARM64 interception, Unity rendering or chart/music timing.
Model tests cover native branch selection independent of head/body identity,
both sides, reentrancy, independent tails, aspect handling, transactional
warmup, error rollback and orderly restoration.

Device checks still needed: ordinary/multi holds on both sides, cross-line
chords, highlighting off, different artwork ratios, long/short holds, tail
alignment, repeated scene changes, quit/reinjection and chart/audio timing.
