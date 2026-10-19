/* Phigros 4.0.1 / TapTap Android SDK 3.30.3 / Frida 16.x / v2.
 * Force the SDK's existing WebView authorization flow in the game process.
 * REPL: phiWebLogin(false) restores normal routing for subsequent attempts.
 *       phiWebLogin(true) enables forced WebView routing again.
 */
'use strict';

(function () {
    const tag = '[phi-web-login] ';
    let enabled = true;
    let attempts = 0;

    Java.perform(function () {
        try {
            const LoginClient = Java.use('com.taptap.sdk.ui.LoginClient');
            const CodeUtil = Java.use('com.taptap.sdk.ui.CodeUtil');
            const Config = Java.use('com.taptap.sdk.TapLoginInnerConfig');
            const WebViewHandler = Java.use('com.taptap.sdk.ui.WebViewHandler');
            const IllegalStateException = Java.use('java.lang.IllegalStateException');
            const requestType = 'com.taptap.sdk.LoginRequest';
            const sendRequest = LoginClient.sendLoginRequest.overload(requestType);
            const initRequest = LoginClient.initLoginRequest.overload(requestType);
            const authorize = WebViewHandler.authorize.overload(requestType);
            const getVerifier = CodeUtil.getCodeVerifier.overload('int');

            sendRequest.implementation = function (request) {
                if (!enabled) return sendRequest.call(this, request);

                const handler = this.webViewHandler.value;
                if (request === null || handler === null) {
                    console.error(tag + 'Missing request or WebViewHandler; authorization not started.');
                    throw IllegalStateException.$new('WebView login requires a request and WebViewHandler');
                }

                console.log(tag + 'Opening SDK WebView, attempt=' + (++attempts));
                // Same initialization as the original sendLoginRequest prologue.
                // initLoginRequest sets loginVersion, redirect URI and PKCE S256.
                Config.codeVerifier.value = getVerifier.call(CodeUtil, 128);
                initRequest.call(this, request);
                this.request.value = request;
                // This route never binds the cloud-game service. LoginBlock's
                // synchronous destruction during startBlock must not unbind it.
                this.cloudGameHandler.value = null;
                authorize.call(handler, request);
                // LoginBlock stores this as isAuthorizeByClient, not login success.
                return false;
            };

            globalThis.phiWebLogin = function (value) {
                if (typeof value !== 'boolean') {
                    console.log(tag + 'enabled=' + enabled + '; use phiWebLogin(true/false)');
                    return enabled;
                }
                enabled = value;
                console.log(tag + 'Forced WebView ' + (enabled ? 'enabled' : 'disabled') +
                    ' for subsequent login attempts.');
                return enabled;
            };

            console.log(tag + 'Ready v2: LoginClient.sendLoginRequest -> WebViewHandler.authorize (cloud-service cleanup fixed)');
        } catch (error) {
            console.error(tag + 'Hook installation failed: ' + error);
        }
    });
})();
