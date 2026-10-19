# Cloud-save login investigation

## Current result (2026-10-19)
- User confirmed that forced WebView v2 now works on PGJM10 / Android 13 with Frida 16.7.19 and a listening Gadget. Their console shows `Ready v2` and four `Opening SDK WebView` attempts. No further crash was reported.
- The committed hook preserves SDK PKCE/request initialization and uses WebViewHandler.authorize. Clearing the unused cloudGameHandler fixes the earlier unbind of a service never bound by this route.
- Node regression checks cover the v1 lifecycle failure, corrected WebBlock creation, and delegation to the original routing when disabled. These fixtures do not emulate Android or server authentication.
- The user feedback establishes the practical workaround. The exact signature verifier remains unidentified; the provided console does not independently establish cloud-save reads/writes or toggle/cancellation behavior.
- Earlier observations below retain the investigation chronology, including periods without an attached device. The signature probe remains unvalidated on-device. No APK was rebuilt or save data modified by the agent.

## Observations
- User reports: `Login Failed` / `Please check your connection (-1: signature not match)`; punctuation uncertain.
- IDA input: `work/il2cpp/libil2cpp_arm64.so`, image base 0; static inspection only, runtime failure not reproduced.
- Full message is absent from IDA indexed strings. Dumped metadata contains the title and connection text separately.

## Hypotheses
### H1: Native/managed client signature validation rejects the package
- Supports: error mentions signature; workspace contains APK instrumentation.
- Conflicts: no local exact error string found yet.
- Test: follow error UI callers and inspect signature validation before callback.
### H2: TapTap authentication returns a signature error (initial root hypothesis)
- Supports: cloud login uses TapSDKWrapper; error is code plus free-form text.
- Conflicts: callback and error origin not traced yet.
- Test: decompile login async state machine and follow SDK exception construction.
### H3: LeanCloud request signing fails after TapTap authentication
- Supports: cloud storage depends on LeanCloud/TDSUser.
- Conflicts: stage of failure unknown.
- Test: distinguish TapTap login completion from TDSUser login and inspect HTTP signing/error propagation.

## Experiments
- Read-only IDA and metadata inspection; no patches or authentication requests performed.
- Metadata string xrefs lead to `TapSDKWrapper..cctor` (0x1DB352C) and `TapSDKWrapper.<Login>d__14.MoveNext` (0x1DB3B28).
- Hex-Rays omitted the exception handler from pseudocode. ARM64 disassembly at 0x1DB3F78 onward confirms the TapException catch. At 0x1DB4014 it reads code at +0x8C; 0x1DB40D0 formats ASCII ` ({0}: {1})`; 0x1DB40F0 calls PhigrosUI.ShowOkMessageBox. Codes 80002 and 80081 skip the popup. Failure clears User and completes Task<bool> with false.
- IDA 0x3A9BBB8 confirms the Java bridge callback: failed LoginCallbackCode -> TapError.SafeConstructorTapError -> TapException(code, message) -> TrySetException. This does not verify a signature.
- Java `LoginManager.onActivityResult` reads LoginResponse.errorMessage from the returned Intent and forwards it as IllegalArgumentException. A token-exchange response may also supply error_description. `TapLoginHelper.onLoginError` wraps Throwable in AccountGlobalError("permission_result", th), assigning code -1 and preserving th.getMessage(). `TDSLoginServiceImpl` forwards that JSON as LoginWrapperBean(..., 2).
- Decompiled Java branches were checked against existing apktool smali for LoginClient, LoginManager, AccountGlobalError, TapLoginHelper, and TDSLoginServiceImpl$1.
- Literal scan of all DEX and both ABIs' native libraries in the original APK found no `signature not match` / `signature mismatch` text. Raw APK-entry scan found the UI labels only in global-metadata.dat. Compressed bundle contents require a separate decompressed scan.
- Bundle scanner initially encountered SerializedFile.files exposing integer object IDs; corrected traversal to read SerializedFile.reader and reran. Synthetic positive controls found UTF-8 signature error and UTF-16LE title correctly.
- Completed decompressed scan of all 2,691 `.bundle` entries in the original APK: zero errors and zero matches for `signature not match`, `Login Failed`, or `Please check your connection` in UTF-8/UTF-16LE (case-insensitive). Full result: `work/cloud-login/bundle-scan.json`. This excludes those literal messages in these bundles; it does not exclude arbitrary logic or encoded text.
- No ADB device is connected, so which runtime branch returns the message cannot be established here.

## Root Cause
The popup is confirmed to present a TapException from the SDK login path. Its code -1 is consistent with Java's generic permission_result wrapper, not evidence of a native signature verifier. The origin of the signature mismatch (external TapTap app versus remote authorization/token/profile response) is still unconfirmed without a runtime trace. A locally re-signed APK exists in this workspace and is a plausible trigger, not a proven cause.

## Fix
Analysis requested; no binary modification performed. Forcing bool success or suppressing the popup cannot produce a valid cloud session. LoggedIn (0x1DB30E4) is simply User != null; the UI success path at 0x1D9DF38 dereferences that User, and cloud APIs still require authenticated credentials.

The selected workaround uses the SDK's existing WebView authorization path in LoginClient.sendLoginRequest: retain codeVerifier generation, initLoginRequest (PKCE S256), and request assignment, then use webViewHandler.authorize(request) and return false (isAuthorizeByClient). This avoids the failing external-client route while still obtaining genuine authorization credentials. The v2 lifecycle correction and user validation are recorded below.

## Navigation
All native addresses are VA/RVA in the original 4.0.1 ARM64 library (IDA base 0); runtime address is module base + RVA.

| Function/site | RVA | Purpose |
|---|---|---|
| StatsFrameController.<Login>d__73.MoveNext | 0x1D9DD48 | Login UI caller |
| TapSDKWrapper.Login | 0x1DB323C | Task<bool> entry |
| TapSDKWrapper.<Login>d__14.MoveNext | 0x1DB3B28 | Await SDK login and handle exceptions |
| PhigrosUI.ShowOkMessageBox call | 0x1DB40F0 | Exact reported popup |
| TapSDKWrapper..cctor | 0x1DB352C | Initialize localized UI text |
| TDSUser.<LoginWithTapTap>d__40.MoveNext | 0x3A7BB08 | TapTap auth, then LeanCloud auth |
| TDSUser.<LoginTapTap>d__44.MoveNext | 0x3A7AB34 | Obtain token/profile, assemble authData |
| TapLoginImpl register-login callback | 0x3A9BBB8 | Convert Java bridge error to TapException |
| TapSDKWrapper.get_LoggedIn | 0x1DB30E4 | User != null |

Metadata slots: Login Failed = 0x42AE500; Please check your connection = 0x42B0590; ASCII format ` ({0}: {1})` = 0x42A2818. These are IL2CPP string slots, not pointers to inline character bytes.

## Runtime discrimination still needed
- Observe only LoginResponse.errorMessage/loginVersion and whether code/token is present in LoginManager.onActivityResult; do not log credential values.
- Observe whether TapLoginWithCode's token response supplies error_description, and whether LoginManager.loginWithToken fails fetching profile.
- Error already in the returning Intent points to external authorization. Error introduced during token/profile HTTP processing points to that endpoint. Only then choose the corresponding change.
- If re-signing caused the rejection, retaining the official signed APK and injecting the existing instrumentation at runtime is another candidate consistent with the workspace's LSPosed plan. This has not been validated here.

## Follow-up: locating a hookable verifier
- Added `hooks/cloud-login/signature_probe.js`: observes package signing queries, certificate extraction/comparison, relevant Java crypto verification, and TapTap login stages. It calls originals once and preserves return values/exceptions. It logs no credential values.
- The same probe can be attached to the game or the external TapTap process. Game-process hooks cannot intercept verification performed in the TapTap process, Android system services, or a server.
- ADB was checked again: no devices. No bypass was applied; a runtime verifier has not been identified.

## Follow-up: force WebView experiment
- User reports that uninstalling the TapTap client selects WebView and login succeeds. This supports the external-client authorization route as the differentiating factor; the exact signature verifier remains unidentified.
- Added `hooks/cloud-login/force_webview.js` at the user's request. The routing change is five operations: generate the original verifier, call original initLoginRequest, save request, invoke the existing WebViewHandler, return false (isAuthorizeByClient).
- Optional REPL toggle `phiWebLogin(false)` delegates subsequent attempts to the original sendLoginRequest. No success result, token, certificate, or cloud state is fabricated.
- Confirmed the method's initialization and return semantics against Java and smali. The script is an experiment pending device validation; no APK was rebuilt or installed.

## Follow-up: first forced-WebView crash (2026-10-19)
### Observations
- User reports `Opening SDK WebView, attempt=1`, then Frida `Error: expected an unsigned integer` and process termination.
- ADB now connected: PGJM10, Android 13 / API 33; host Frida 16.7.19; Gadget endpoint forwarded at 127.0.0.1:27042.
- Crash buffer at 21:14:45, PID 8457: `Unable to start activity TapTapActivity`, caused by `IllegalArgumentException: Service not registered: CloudGameHandler$CloudGameServiceConnection`.
- Exact Java path: WebViewHandler.authorize -> ActivityDelegate.startBlock -> BlockManager.replace -> LoginBlock.onDestroy -> ContextWrapper.unbindService. The script reached WebView launch, so initial verifier/init/field operations completed.

### Hypotheses
1. ROOT: Replacing sendLoginRequest skips CloudGameHandler.bindCloudGameService. Replacing LoginBlock with WebBlock still runs onDestroy and attempts to unbind the connection. Supports: exact fatal stack. Test: inspect constructor/bind/getServiceConnection state and LoginBlock destruction contract; preserve native route lifecycle or remove the unused handler for the forced flow.
2. Frida numeric argument conversion in verifier/init calls. Supports: console message. Conflicts: fatal stack reached WebViewHandler.authorize, after these calls. Test: compare Java fatal stack with script execution order. Rejected as the immediate Java crash cause.
3. WebView/UI thread or initialization failure. Supports: failure during activity startup. Conflicts: exact exception is unbind of a service never bound, before web page setup. Test: inspect BlockManager.replace destruction ordering. Not the immediate cause.

### Experiments
- Read-only crash-buffer inspection identified a Java lifecycle failure, rather than relying on Frida's generic console error. Device process was already stopped; no restart or login request performed yet.
- Source confirms CloudGameHandler allocates a non-null ServiceConnection in its constructor. LoginBlock.onDestroy checks only for null, then unbinds; the original sendLoginRequest always attempts bindCloudGameService before reaching WebView fallback. Our replacement omitted that attempt.
- Minimal experiment: insert only `this.cloudGameHandler.value = null;` immediately before launching WebView, in an in-memory copy of the original script. This forced route never binds or uses that handler. Confirmation criterion: WebBlock opens and cancellation/retry does not raise Service not registered. Do not complete account authorization or modify cloud saves.
- Device validation attempt did not reach hook readiness: loading the host harness terminated the process with SIGSEGV in gum-js-loop at 21:19:01. This is a separate Frida load failure, not confirmation of the routing correction. User then took over device testing; no further device control performed.

### Correction and validation
- v2 adds the one-line lifecycle correction before authorize: clear this forced-flow instance's unused cloudGameHandler, so LoginBlock.getServiceConnection returns null during destruction. The hook announces `Ready v2` to identify the corrected script.
- Regression fixture follows BlockManager/LoginBlock's synchronous destruction/unbind contract: v1 reproduces Service not registered; v2 creates WebBlock; disabling the hook retains the original handler and original routing. All three Node tests pass; JavaScript syntax check passes.
- User subsequently loaded v2 through the existing remote Gadget connection and reported that it now works, supplying logs for four forced WebView attempts. Cancellation and restoring original routing were not separately reported. No APK or save data modified by the agent.
