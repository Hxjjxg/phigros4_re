/* Frida 16.x Java probe. Attach to the game or the TapTap process.
 * Observes certificate access and login failures; preserves original results.
 * Does not log certificates, tokens, authorization codes, or response bodies.
 */
'use strict';

Java.perform(function () {
    const target = 'com.PigeonGames.Phigros';
    const counts = new Map();
    let route = 'unknown';
    let installed = 0;
    const JSONObject = Java.use('org.json.JSONObject');

    function stack() {
        return Java.backtrace({ limit: 18 }).frames.map(function (f) {
            return f.signature;
        });
    }

    function emit(event, fields, trace) {
        const key = event + JSON.stringify(fields) + JSON.stringify(trace || []);
        const count = counts.get(key) || 0;
        if (count >= 3) return;
        counts.set(key, count + 1);
        console.log('[phi-sign] ' + JSON.stringify(Object.assign({
            event: event, pid: Process.id, route: route
        }, fields, trace ? { stack: trace } : {})));
    }

    function describeError(value) {
        const text = value === null ? '' : String(value);
        return {
            errorPresent: text.length > 0,
            signatureMismatch: /signature\s+(?:not\s+match|mismatch)/i.test(text)
        };
    }

    function describeThrowable(error) {
        if (error === null) return { errorPresent: false };
        return Object.assign({ errorClass: String(error.getClass().getName()) },
            describeError(error.getMessage()));
    }

    function observe(fn, receiver, args, result) {
        if (!fn) return;
        try { fn(receiver, args, result); }
        catch (_) { emit('probe-observer-error', {}); }
    }

    function hook(className, methodName, before, after) {
        try {
            const klass = Java.use(className);
            klass[methodName].overloads.forEach(function (original) {
                original.implementation = function () {
                    const args = Array.prototype.slice.call(arguments);
                    observe(before, this, args);
                    // Call once; preserve both return values and thrown exceptions.
                    const result = original.call(this, ...args);
                    observe(after, this, args, result);
                    return result;
                };
                installed++;
            });
        } catch (_) {
            emit('hook-unavailable', { className: className, method: methodName });
        }
    }

    hook('android.app.ApplicationPackageManager', 'getPackageInfo', function (_, args) {
        if (String(args[0]) !== target) return;
        const flags = typeof args[1] === 'number' ? args[1] : Number(String(args[1].getValue()));
        if ((flags & (0x40 | 0x08000000)) !== 0) {
            emit('package-signatures-requested', { packageName: target, flags: flags }, stack());
        }
    });

    ['checkSignatures', 'hasSigningCertificate'].forEach(function (name) {
        hook('android.app.ApplicationPackageManager', name, null, function (_, args, result) {
            const uidOverload = typeof args[0] === 'number';
            const related = String(args[0]) === target ||
                (name === 'checkSignatures' && String(args[1]) === target);
            if (uidOverload || related) {
                emit('package-' + name, { result: result, uidOverload: uidOverload }, stack());
            }
        });
    });

    ['getApkContentsSigners', 'getSigningCertificateHistory'].forEach(function (name) {
        hook('android.content.pm.SigningInfo', name, null, function (_, args, result) {
            emit('signers-' + name, { count: result === null ? 0 : result.length }, stack());
        });
    });

    ['toByteArray', 'toCharsString', 'equals'].forEach(function (name) {
        hook('android.content.pm.Signature', name, null, function (_, args, result) {
            emit('certificate-' + name, name === 'equals' ? { matched: result } : {}, stack());
        });
    });

    hook('java.security.Signature', 'verify', null, function (_, args, result) {
        const trace = stack();
        if (trace.some(function (frame) { return /taptap|tds|phigros/i.test(frame); })) {
            emit('crypto-signature-verify', { verified: result }, trace);
        }
    });

    [['ActivityHandler', 'external-client'], ['WebViewHandler', 'webview'],
        ['CloudGameHandler', 'cloud-game']].forEach(function (entry) {
        hook('com.taptap.sdk.ui.' + entry[0], 'authorize', function () {
            route = entry[1];
            emit('authorization-start', {});
        });
    });

    hook('com.taptap.sdk.LoginResponse', 'getResultFromIntent', null, function (_, args, result) {
        if (result === null) return;
        emit('login-intent-result', Object.assign({
            hasToken: result.token.value !== null,
            hasCode: result.code.value !== null,
            cancelled: result.cancel.value
        }, describeError(result.errorMessage.value)), stack());
    });

    hook('com.taptap.sdk.TapLoginWithCode', 'loginWithCode', function () {
        emit('token-exchange-start', {});
    });

    hook('com.taptap.sdk.TapLoginWithCode$1', 'onSuccess', function (_, args) {
        const response = Java.cast(args[0], JSONObject);
        const data = response.optJSONObject('data');
        emit('token-exchange-response', Object.assign({
            success: response.optBoolean('success')
        }, describeError(data === null ? null : data.optString('error_description'))));
    });

    hook('com.taptap.sdk.TapLoginWithCode$1', 'onError', function (_, args) {
        emit('token-exchange-error', describeThrowable(args[0]), stack());
    });

    hook('com.taptap.sdk.LoginManager', 'loginWithToken', function () {
        emit('profile-fetch-start', {});
    });

    hook('com.taptap.sdk.LoginManager$3', 'onError', function (_, args) {
        emit('profile-fetch-error', describeThrowable(args[0]), stack());
    });

    hook('com.taptap.sdk.TapLoginHelper', 'onLoginError', function (_, args) {
        emit('game-login-error', describeThrowable(args[0]), stack());
    });

    emit('ready', { installedOverloads: installed, targetPackage: target });
});
