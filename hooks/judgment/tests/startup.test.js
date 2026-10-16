// Cold-start timing tests without a phone or device output.
const test = require('node:test');
const assert = require('node:assert/strict');
const fs = require('node:fs');
const vm = require('node:vm');
const path = require('node:path');

function fixture() {
    let now = 0, tick, cancelled = false, loaded = false, domainReady = false;
    let attempts = 0, settingsInstalls = 0, detached = 0;
    const p = {listeners: [], queue: [], config: {}, stats: {observed: {}}, log() {},
        installSettingsEntry() { settingsInstalls++; },
        initRuntime() {
            attempts++;
            if (!domainReady) throw new Error('IL2CPP domain is not initialized');
            return {game: () => ({}), offset: () => 0, method: (_, name) => name,
                hook: () => { p.listeners.push({detach() { detached++; }}); return '0x100'; }};
        }};
    vm.runInNewContext(fs.readFileSync(path.join(__dirname, '../src/judgment.js'), 'utf8'), {
        Phi: p, Date: {now: () => now}, Process: {findModuleByName: () => loaded},
        rpc: {exports: {}}, setInterval: fn => { tick = fn; return 1; },
        clearInterval: () => { cancelled = true; }, setImmediate: fn => fn()
    });
    return {p, tick: () => tick(), time: n => { now = n; }, load: () => { loaded = true; },
        ready: () => { domainReady = true; },
        stats: () => ({attempts, settingsInstalls, detached, cancelled})};
}

test('cold start waits for both library and IL2CPP domain, then installs once', () => {
    const f = fixture();
    f.tick(); assert.equal(f.stats().attempts, 0);
    f.load(); f.tick(); assert.equal(f.p.ready, false);
    assert.equal(f.stats().cancelled, false);
    f.ready(); f.tick();
    assert.equal(f.p.ready, true);
    assert.equal(f.p.listeners.length, 4);
    assert.equal(f.p.initializationError, null);
    assert.equal(f.stats().settingsInstalls, 1);
    assert.equal(f.stats().cancelled, true);
});

test('startup timeout is visible instead of silently disabling hooks', () => {
    const f = fixture(); f.time(120000); f.tick();
    assert.equal(f.p.initializationError, 'libil2cpp.so did not load');
    assert.equal(f.stats().cancelled, true);
});

test('failed partial hook installation is rolled back before retry', () => {
    const f = fixture(); f.load(); f.ready();
    const initialize = f.p.initRuntime;
    let fail = true;
    f.p.initRuntime = () => {
        const runtime = initialize(); const hook = runtime.hook;
        runtime.hook = (...args) => {
            if (fail && f.p.listeners.length === 2) throw new Error('method not ready');
            return hook(...args);
        };
        return runtime;
    };
    f.tick(); assert.equal(f.p.listeners.length, 0); assert.equal(f.stats().detached, 2);
    fail = false; f.tick(); assert.equal(f.p.listeners.length, 4); assert.equal(f.p.ready, true);
});

test('standalone wrapper waits for Application and starts only once', () => {
    let tick, app = null, started = 0, cancelled = false;
    const prefix = fs.readFileSync(path.join(__dirname, '../../../packaging/standalone-prefix.js'), 'utf8');
    vm.runInNewContext(prefix + '\nrecordStart();\n}', {
        Java: {available: true, perform: fn => fn(), use: () => ({currentApplication: () => app})},
        Date, console, recordStart: () => { started++; },
        setInterval: fn => { tick = fn; return 1; }, clearInterval: () => { cancelled = true; }
    });
    tick(); assert.equal(started, 0); assert.equal(cancelled, false);
    app = {}; tick(); tick(); assert.equal(started, 1); assert.equal(cancelled, true);
});
