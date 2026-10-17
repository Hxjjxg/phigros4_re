/* Frida 16 adapter: all socket callbacks stay in Java; JS drains an event queue. */
(function () {
    const P = Phi;
    P.embedded = true;
    const rule = () => ({intensity: 0, durationMs: 1000, waveform: 'BUBBLE'});
    P.config = {enabled: true, url: 'ws://127.0.0.1:9998', good: true, bad: true, miss: true,
        perfect: false, qrText: '', slotId: '', channel: 0, cooldownMs: 150,
        rules: {good: rule(), bad: rule(), miss: rule()}};
    let Server, server = null, core = null, desired = null, active = null;
    let handshakeCompatibilityInstalled = false;
    let state = 'STOPPED', failure = null, quitting = false, ending = false, stopDeadline = 0;
    let pairingId = null;
    const perform = fn => new Promise((resolve, reject) => Java.perform(() => {
        try { resolve(fn()); } catch (e) { reject(e); }
    }));
    function validate(config) {
        if (!/^ws:\/\/127\.0\.0\.1:([0-9]{1,5})\/?$/.test(config.url))
            throw new Error('Same-phone server URL must be ws://127.0.0.1:PORT');
        const port = Number(config.url.match(/:([0-9]+)\/?$/)[1]);
        if (port < 1024 || port > 65535) throw new Error('Port must be 1024..65535');
        if (![0, 1].includes(config.channel)) throw new Error('Channel must be A or B');
        if (!Number.isInteger(config.cooldownMs) || config.cooldownMs < 100 || config.cooldownMs > 5000)
            throw new Error('Trigger interval must be 100..5000 ms');
        config.slotId = String(config.slotId).trim();
        const rules = {};
        ['good', 'bad', 'miss'].forEach(g => {
            const r = config.rules[g];
            if (!r || !Number.isInteger(r.intensity) || r.intensity < 0 || r.intensity > 200)
                throw new Error(g + ': intensity must be 0..200');
            if (!Number.isInteger(r.durationMs) || r.durationMs < 100 || r.durationMs > 30000)
                throw new Error(g + ': duration must be 100..30000 ms');
            if (!Object.prototype.hasOwnProperty.call(DGLAB_WAVES, r.waveform)) throw new Error(g + ': unknown waveform');
            rules[g] = {intensity: r.intensity, durationMs: r.durationMs, waveform: r.waveform};
        });
        config.rules = rules;
        config.perfect = false; config.qrText = '';
        return config;
    }
    function status() {
        return {state, error: failure, url: active, ...(core ? core.status() : {targetId: pairingId, clientId: null, devices: []})};
    }
    function pairing(url) {
        if (!pairingId) throw new Error('Start the embedded server first');
        const address = url.replace(/\/$/, '') + '/?tid=' + encodeURIComponent(pairingId);
        return 'https://dungeon-lab.cn/s/?v=1&action=socket&url=' + encodeURIComponent(address);
    }
    function start(url) {
        const port = Number(url.match(/:([0-9]+)\/?$/)[1]);
        if (!pairingId) pairingId = Java.use('java.util.UUID').randomUUID().toString().replace(/-/g, '');
        server = Java.retain(Server.$new('127.0.0.1', port));
        active = url; ending = false; state = 'STARTING'; failure = null;
        const transport = server;
        core = new DglabCore({
            send: (id, text) => transport.sendTo(id, text),
            close: (id, code, reason) => transport.closePeer(id, code, reason),
            connected: id => transport.connected(id)
        }, {targetId: pairingId});
        server.start();
    }
    function installHandshakeCompatibility() {
        if (handshakeCompatibilityInstalled) return;
        const Draft = Java.use('com.tds.common.websocket.drafts.Draft_6455');
        const method = Draft.postProcessHandshakeResponseAsServer.overload(
            'com.tds.common.websocket.handshake.ClientHandshake',
            'com.tds.common.websocket.handshake.ServerHandshakeBuilder');
        method.implementation = function (client, builder) {
            const response = method.call(this, client, builder);
            // The APK's draft copies the request's combined Connection header.
            // OkHttp may send "Upgrade; close", which strict clients reject.
            response.put('Connection', 'Upgrade');
            return response;
        };
        handshakeCompatibilityInstalled = true;
    }
    function beginStop() {
        if (!server || ending) return;
        ending = true; state = 'STOPPING'; stopDeadline = Date.now() + 1500;
        // Give clear a bounded opportunity to finish before closing the connection.
        if (core.peer) core.stopOutput().catch(() => {}).finally(() => { stopDeadline = 0; });
        else stopDeadline = 0;
    }
    function update() {
        if (server) {
            for (let i = 0; i < 128; i++) {
                const raw = server.poll(); if (raw === null) break;
                const event = JSON.parse(String(raw));
                if (event.type === 'open') {
                    if (ending) server.closePeer(event.id, 4000, 'controller_disconnected');
                    else core.open(event.id, event.data);
                } else if (event.type === 'message') core.receive(event.id, event.data);
                else if (event.type === 'close') core.disconnect(event.id);
                else failure = event.data;
            }
            core.tick();
            if (server.failure.value) {
                failure = String(server.failure.value); desired = null;
                beginStop();
            }
            if (active !== desired) beginStop();
            if (ending && Date.now() >= stopDeadline) { core.shutdown(); server.requestStop(); }
            if (server.stopped.value) {
                core.shutdown(); server.$dispose(); server = null; core = null;
                active = null; ending = false; state = failure ? 'ERROR' : 'STOPPED';
            } else if (!ending && server.ready.value) state = core.peer ? 'PAIRED' : 'LISTENING';
        }
        if (!server && desired && Server && !quitting) start(desired);
    }
    P.transport = {
        validate,
        configure: function (next, previous) {
            if (previous && core?.peer && JSON.stringify(next) !== JSON.stringify(previous))
                core.stopOutput().catch(e => { failure = String(e); });
            desired = next.enabled ? next.url : null;
            failure = null;
            update();
        },
        state: () => state,
        status, pairing,
        sendJudgment: event => !ending && core ? core.judgment(event, P.config, DGLAB_WAVES) : false,
        dispose: async function () {
            quitting = true; desired = null;
            const deadline = Date.now() + 8000;
            while (server) {
                await perform(update);
                if (Date.now() > deadline) throw new Error('Embedded server shutdown timed out; retry dispose');
                await new Promise(resolve => setTimeout(resolve, 20));
            }
            clearInterval(timer);
        }
    };
    globalThis.phiDglab = {
        status: () => perform(status),
        devices: () => perform(() => core ? core.status().devices : []),
        refresh: () => perform(() => { if (!core) throw new Error('Server is stopped'); return core.requestDevices(); }),
        operate: data => perform(() => { if (!core || ending) throw new Error('Server is not ready'); return core.operate(data); }),
        clear: (slotId, channel) => perform(() => { if (!core) throw new Error('Server is stopped'); return core.clear(slotId, channel); }),
        stop: () => globalThis.phiConfigure({enabled: false}),
        pairing: () => perform(() => pairing(P.config.url))
    };
    Object.keys(phiDglab).forEach(k => { rpc.exports['dglab' + k] = phiDglab[k]; });
    const timer = setInterval(() => Java.perform(() => {
        try { update(); } catch (e) { failure = String(e); desired = null; beginStop(); }
    }), 20);
    Java.perform(function () {
        try {
            pairingId = Java.use('java.util.UUID').randomUUID().toString().replace(/-/g, '');
            if (globalThis.PHIGROS_STANDALONE) {
                const app = Java.use('android.app.ActivityThread').currentApplication();
                const prefs = app.getSharedPreferences('phigros_dglab_v4', 0);
                const saved = prefs.getString('pairingId', null);
                if (saved && /^[a-f0-9]{32}$/.test(String(saved))) pairingId = String(saved);
                else prefs.edit().putString('pairingId', pairingId).apply();
            } else Java.openClassFile(globalThis.PHIGROS_EMBEDDED_TRANSPORT_PATH ||
                '/data/local/tmp/EmbeddedTransport.dex').load();
            Server = Java.use('re.phigros.dglab.EmbeddedTransport');
            installHandshakeCompatibility();
            update();
        } catch (e) { failure = String(e); state = 'ERROR'; P.log('Embedded server: ' + e); }
    });
})();
