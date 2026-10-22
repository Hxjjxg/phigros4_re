# Challenge Mode script debugging

## Observations

- Device reports `Ready`, followed by `Error: expected an integer` on matching UI reads.
- Hook installation and code signatures pass. No device stack trace is available.
- The failing path calls `il2cpp_gchandle_new(marker, true)` through a Frida NativeFunction with a `bool` parameter.
- The previous Node mock accepted JS booleans and did not reproduce native argument conversion.

## Hypotheses

1. **Root hypothesis:** Frida's native `bool` argument requires integer 0/1. Supported by the exact error and the boolean argument in the executed path. Test with a real Frida NativeCallback/NativeFunction pair.
2. GC handle return type is wrong. Both `uint` and the actual uint32_t are 32-bit here, conflicting with this explanation. Test argument conversion separately without handles.
3. Managed string pointer/layout is wrong. Signatures passed and no invalid-memory error was reported, but this cannot be excluded from device logs alone. Test the integer error independently before changing string layout.

## Experiments

A five-line Python experiment spawned an owned Python child, attached local Frida 16, and invoked a `void(bool)` NativeCallback through NativeFunction. `f(true)` produced exactly `Error: expected an integer`; `f(1)` succeeded. The child was terminated after the experiment. No game process was changed.

## Root Cause

The script passed a JS boolean to Frida's native bool argument instead of numeric 1. The mock missed Frida's conversion requirement.

## Fix

Use numeric 1 for the pinned argument and enforce that in the regression mock. The user's requested persistent alternative uses the same corrected native ABI.

IDA additionally confirmed SaveManagement.SaveString at 0x1CC33DC encrypts both key and value before PlayerPrefs.SetString; use this wrapper, then PlayerPrefs.Save at 0x3C53F20, rather than writing an unencrypted PlayerPrefs key.

Host regression tests and a local Frida native ABI reproduction are available. Persistent changes in the actual Android game still require device verification. Changes have not been committed because the user requested script changes, not a commit.
