# Hold-skin Frida integer argument failure

## Observations

- The user reports `Error: expected an integer` at `pin`, reached from both
  `type`/`component`/`templates` and `invoke`/`listCount` in the generated script.
- Both paths call `il2cpp_gchandle_new(pointer, false)`, whose second argument
  is declared as Frida `bool`. Persistent and weak handles use the same form.
- The existing skin tests mock the runtime and do not validate native argument
  conversion. The local Frida installation is 16.7.19.
- Investigation uses local processes only; no phone connection or injection.

## Hypotheses

### H1: Frida requires an integer for native `bool` arguments (ROOT HYPOTHESIS)
- Supports: every reported trace reaches the same native call with literal false.
- Conflicts: the existing mocks accept false, but do not implement Frida.
- Test: call a local Frida NativeCallback through NativeFunction, comparing
  false with 0 while keeping the pointer and signature identical.

### H2: A managed pointer is invalid or passed as a numeric value
- Supports: pin receives objects returned by several IL2CPP entry points.
- Conflicts: invalid native addresses usually produce access violations, and
  both traces terminate at argument marshalling in the same helper.
- Test: use the same known-valid pointer argument in both local calls.

### H3: The GC-handle return value or cleanup handle is incorrectly typed
- Supports: handles are uint and later passed to uint-argument functions.
- Conflicts: the stack identifies handle creation before cleanup.
- Test: return a known uint from the callback and inspect the successful call.

## Experiments

1. Isolated local Python child process with Frida 16.7.19, wrapping a
   NativeCallback returning uint 7 in a NativeFunction with arguments
   `['pointer', 'bool']`. No production code was changed for this experiment.
   With the same null pointer, false and true both threw `expected an integer`;
   0 and 1 both returned 7. H1 confirmed; H2/H3 do not explain this reproduction.
2. Added a runtime mock enforcing this observed Frida conversion, and tightened
   the existing full skin fixture to require 0 for all strong/weak handle flags.
   Running against unchanged production sources establishes the regression.

## Root Cause

Frida 16 NativeFunction requires integer 0/1 for native bool arguments, but
the runtime and skin passed JavaScript false when creating GC handles.

## Fix

- Pass integer 0 at every strong and weak GC-handle creation site in both
  runtime.js and skin.js. The native ABI signatures remain bool.
- Add runtime-level regression tests and enforce integer flags in the existing
  skin fixture, including asset ownership, cross-call roots and restoration.
- Rebuild phigros_hold_skin.js so run.py loads the corrected sources.

## Validation

- Before the fix: both new runtime tests and four existing skin tests failed
  with strict native argument validation (six failures out of seven).
- After the fix: all 12 host tests pass; build.py and Node syntax check pass.
- Local Frida 16.7.19 reproduced the exact reported error and accepted the
  corrected integer argument. Android rendering remains unverified here.
- No device access, unrelated edits, or commits were performed for this fix.

## Reload

The already-loaded Frida script keeps the old code. Use quit and rerun the
original launch command, then start a new chart. The retry command only retries
image loading; it does not reload the script source.

# Image filename mismatch (2026-10-22)

## Observations

- The user's next run reaches loadImages and fails opening a file with
  `No such file or directory`, before allocating textures.
- The launch command supplies files.example.json, which maps head_single
  to head_sigle.png. A read-only adb directory listing confirms the actual
  file is /data/local/tmp/head_single.png. The other five canonical PNGs exist.
- No game attachment, injection, file push or device modification is used.

## Hypotheses

1. Filename mismatch (ROOT): the explicit mapping requests a nonexistent
   head_sigle.png. Confirmed by reading the config and directory listing.
2. Wrong image directory: conflicts with all six canonical files being present
   under the exact configured /data/local/tmp directory.
3. Read permissions: the listed images are mode 777; the reported error is
   ENOENT. This does not explain the verified nonexistent mapped name.

## Experiment

Add a host fixture using the shipped mapping with the nonexistent typo path
rejected, and require the image-open error to identify its full file path.
Both tests should fail before the mapping/diagnostic correction.

The user corrected the mapping during this investigation and confirmed successful
six-image texture loading. The missing-file diagnostic now includes slot/path.

# Native sprite geometry failure (2026-10-22)

## Observations

- The user corrected the filename mapping. The game now reports all six PNG
  textures loaded, then rejects native spriteGeometry during prepare.
- geometry currently demands exact integer rect dimensions and emits no values.
- The host fixture provides integer rectangles and identity unboxing only.
- The current trace does not expose the native dimensions, pivot or PPU.

## Hypotheses

1. Native Sprite rect has fractional dimensions or float representation noise.
   Supports: exact-integer comparison is stricter than a float Rect contract.
   Test: inspect bundled sprite metadata and reproduce with its dimensions.
2. Wrong struct layout or return-value decoding. Supports: current mocks do not
   model boxed Rect/Vector2 layouts. Test: inspect this binary's Sprite getters
   and metadata, and add value-bearing error diagnostics.
3. Invalid native template/PPU or dimensions exceeding resource limits.
   Supports: also triggers the same guard. Test: inspect template assets and
   include slot, rect, pivot and PPU in validation failures.

Static/host investigation continues without attaching to the running game.

## Experiment and root cause

Static UnityPy extraction from the workspace APK finds Hold2HL_0 rect height
97.52381134033203 and Hold2HL_1 height 1852.952392578125, both with PPU
97.52066040039062. Replaying those exact values in host tests reproduces the
reported geometry exception. Rect field offsets in dump.cs match the reader.
The exact-integer guard rejects valid native highlighted sprites.

## Fix and validation

- Keep native fractional rect width/height, normalized pivot and PPU. Only GPU
  texture/temporary-target allocation and ReadPixels use ceiling dimensions.
- Retain the two-argument Graphics.Blit present in dump.cs. Its scale/offset
  overload is stripped; no new API dependency is introduced. The final Sprite
  rect can exclude less than one buffer pixel at top/right for fractional sizes.
- Report slot, rect, pixel pivot and PPU for invalid native geometry.
- Record APK sprite metadata in a regression fixture and exercise actual multi
  head/body geometry, integer allocations and independent tail selection.
- All 16 host tests pass; the generated Frida script is rebuilt and syntax valid.
- No device access or injection in this geometry investigation. Rendering after
  reload remains unverified; quit/relaunch is required to replace loaded code.
