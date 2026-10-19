'use strict';

const assert = require('node:assert/strict');
const fs = require('node:fs');
const path = require('node:path');
const test = require('node:test');
const vm = require('node:vm');
const source = fs.readFileSync(path.join(__dirname, '../force_webview.js'), 'utf8');

function fixture(scriptSource = source) {
    const events = [];
    const config = { codeVerifier: { value: null } };
    const connection = {};
    const bindings = new Set();
    const client = {
        request: { value: null },
        webViewHandler: { value: {} },
        cloudGameHandler: { value: { getCloudGameServiceConnection: () => connection } }
    };
    function method(callback) {
        const fn = { call: (receiver, ...args) => callback.apply(receiver, args) };
        fn.overload = () => fn;
        return fn;
    }
    const send = method(function () {
        events.push('original-route');
        bindings.add(connection);
        return true;
    });
    const classes = {
        'com.taptap.sdk.ui.LoginClient': {
            sendLoginRequest: send,
            initLoginRequest: method(function (request) {
                assert.equal(config.codeVerifier.value, 'fixture-verifier');
                request.initialized = true;
                events.push('request-initialized');
            })
        },
        'com.taptap.sdk.ui.CodeUtil': { getCodeVerifier: method(function (length) {
            assert.equal(length, 128);
            return 'fixture-verifier';
        }) },
        'com.taptap.sdk.TapLoginInnerConfig': config,
        'com.taptap.sdk.ui.WebViewHandler': { authorize: method(function (request) {
            assert.equal(request.initialized, true);
            assert.equal(client.request.value, request);
            // SDK lifecycle contract: replacing LoginBlock destroys it before
            // creating WebBlock; destruction unbinds any exposed connection.
            const handler = client.cloudGameHandler.value;
            if (handler !== null) {
                const service = handler.getCloudGameServiceConnection();
                if (service !== null && !bindings.delete(service)) {
                    throw new Error('Service not registered');
                }
            }
            events.push('web-block-created');
        }) },
        'java.lang.IllegalStateException': { $new: message => new Error(message) }
    };
    const context = vm.createContext({
        Java: { perform: callback => callback(), use: name => classes[name] },
        console: { log() {}, error() {} }
    });
    vm.runInContext(scriptSource, context);
    return { context, client, events, send };
}

test('forced route creates WebBlock without unbinding an unregistered service', () => {
    const f = fixture();
    assert.equal(f.send.implementation.call(f.client, {}), false);
    assert.deepEqual(f.events, ['request-initialized', 'web-block-created']);
});

test('lifecycle fixture reproduces the v1 failure without the correction', () => {
    const f = fixture(source.replace('this.cloudGameHandler.value = null;', ''));
    assert.throws(() => f.send.implementation.call(f.client, {}), /Service not registered/);
});

test('disabled hook delegates to the original route without clearing its handler', () => {
    const f = fixture();
    const handler = f.client.cloudGameHandler.value;
    f.context.phiWebLogin(false);
    assert.equal(f.send.implementation.call(f.client, {}), true);
    assert.equal(f.client.cloudGameHandler.value, handler);
    assert.deepEqual(f.events, ['original-route']);
});
