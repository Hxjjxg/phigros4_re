'use strict';

const assert = require('node:assert/strict');
const fs = require('node:fs');
const path = require('node:path');
const test = require('node:test');
const vm = require('node:vm');

const source = fs.readFileSync(path.join(__dirname, '../unlock_challenge_mode.js'), 'utf8');
const saveSource = fs.readFileSync(path.join(__dirname, '../unlock_challenge_mode_save.js'), 'utf8');
const BASE = 0x7100000000;
const LOAD = 0x1cc42c8;
const CALLERS = [0x1d5390c, 0x1d576f4];
// Bytes recorded from the analyzed arm64 ELF, independently of the loaded script.
const CODE = new Map([
    [BASE + LOAD, 'fe 0f 1d f8 f6 57 01 a9 f4 4f 02 a9 54 3d 01 d0'],
    [BASE + 0x1d53908, '70 c2 fd 97 c1 02 40 f9 e2 03 1f aa f2 80 56 94'],
    [BASE + 0x1d576f0, 'f6 b2 fd 97 81 02 40 f9 e2 03 1f aa 78 71 56 94'],
    [BASE + 0x1cc33dc, 'fe 0f 1d f8 f6 57 01 a9 f4 4f 02 a9 55 3d 01 f0'],
    [BASE + 0x3c53f20, 'fe 4f bf a9 53 41 00 90 60 da 44 f9 a0 00 00 b5']
]);

function harness(options = {}) {
    const strings = new Map(), roots = new Map(), timers = new Map();
    const store = options.store || new Map();
    const calls = {allocations: 0, handles: 0, freed: [], attaches: 0, detached: 0, writes: [], flushes: 0};
    let nextAddress = 0x7200000000, callbacks;
    class Pointer {
        constructor(address) { this.address = address; }
        add(offset) { return new Pointer(this.address + offset); }
        sub(pointer) { return new Pointer(this.address - pointer.address); }
        toString() { return '0x' + this.address.toString(16); }
        isNull() { return this.address === 0; }
        readByteArray(size) {
            const bytes = Buffer.from(CODE.get(this.address).split(' ').map(x => parseInt(x, 16)));
            if (options.corrupt === this.address) bytes[0] ^= 1;
            assert.equal(bytes.length, size);
            return Uint8Array.from(bytes).buffer;
        }
        readS32() {
            assert.ok(strings.has(this.address - 16), 'must read a managed string length');
            return strings.get(this.address - 16).length;
        }
        readUtf16String(length) {
            const value = strings.get(this.address - 20);
            assert.equal(value.length, length);
            return value;
        }
        replace(value) { this.address = value.address; }
    }
    function managedString(value) {
        nextAddress += 0x100;
        strings.set(nextAddress, value);
        return new Pointer(nextAddress);
    }
    const exports = {
        il2cpp_string_new(value) {
            calls.allocations++;
            return managedString(value.utf8);
        },
        il2cpp_gchandle_new(pointer, pinned) {
            assert.equal(pinned, 1, 'Frida native bool arguments require integer 0/1');
            const handle = ++calls.handles;
            roots.set(handle, pointer);
            return handle;
        },
        il2cpp_gchandle_get_target(handle) {
            assert.ok(roots.has(handle));
            return roots.get(handle);
        },
        il2cpp_gchandle_free(handle) {
            assert.ok(roots.delete(handle));
            calls.freed.push(handle);
        }
    };
    const native = new Map([
        [BASE + LOAD, (key, fallback) => managedString(store.get(strings.get(key.address)) ?? strings.get(fallback.address))],
        [BASE + 0x1cc33dc, (key, value) => {
            const k = strings.get(key.address), v = strings.get(value.address);
            calls.writes.push([k, v]);
            if (options.failWrite !== k) store.set(k, v);
        }],
        [BASE + 0x3c53f20, () => { calls.flushes++; }]
    ]);
    const lib = {
        base: new Pointer(BASE),
        getExportByName(name) {
            assert.ok(Object.hasOwn(exports, name), 'only expected IL2CPP APIs may be bound');
            return name;
        }
    };
    const process = {
        arch: options.arch || 'arm64', pointerSize: options.pointerSize || 8,
        findModuleByName(name) {
            assert.equal(name, 'libil2cpp.so');
            return options.pending ? null : lib;
        }
    };
    const context = vm.createContext({
        Process: process,
        NativeFunction: function (name) {
            if (name instanceof Pointer) {
                assert.ok(native.has(name.address), 'only verified save APIs may be bound');
                return native.get(name.address);
            }
            return exports[name];
        },
        Memory: {allocUtf8String: utf8 => ({utf8})},
        Interceptor: {
            attach(address, handlers) {
                assert.equal(address.address, BASE + LOAD);
                calls.attaches++;
                callbacks = handlers;
                return {detach() { calls.detached++; }};
            }
        },
        console: {log() {}, error() {}},
        rpc: {exports: {existing: () => 'preserved'}},
        setTimeout(callback, delay) {
            assert.equal(delay, 250);
            timers.set(1, callback);
            return 1;
        },
        clearTimeout(id) { assert.ok(timers.delete(id)); }
    });
    vm.runInContext(options.persistent ? saveSource : source, context);
    return {
        api: options.persistent ? context.PhiChallengeSave : context.PhiChallengeMode, calls, timers, context, store,
        invoke(caller, key = 'GameCompleted', value = '') {
            assert.ok(callbacks, 'hook must be installed');
            const invocation = {returnAddress: new Pointer(BASE + caller)};
            const retval = value === null ? new Pointer(0) : managedString(value);
            const original = retval.address;
            callbacks.onEnter.call(invocation, [managedString(key)]);
            callbacks.onLeave.call(invocation, retval);
            return {changed: retval.address !== original, value: strings.get(retval.address)};
        },
        loadModule() {
            options.pending = false;
            const callback = timers.get(1);
            assert.ok(callback);
            timers.delete(1);
            callback();
        }
    };
}

test('both UI gates gain access with one rooted temporary string', () => {
    const h = harness();
    assert.equal(h.api.status().installed, true);
    assert.equal(h.calls.allocations, 0, 'allocate only on a matching game invocation');
    for (const caller of CALLERS) {
        assert.deepEqual(h.invoke(caller), {changed: true, value: 'challenge-mode-access'});
    }
    assert.equal(h.calls.allocations, 1);
    assert.equal(h.calls.handles, 1);
    assert.equal(h.api.status().replacements, 2);
    assert.equal(h.api.status().error, null);
    assert.equal(h.context.rpc.exports.existing(), 'preserved');
    assert.equal(h.context.rpc.exports.challengestatus().installed, true);
});

test('other callers, keys, nulls and completed saves keep their original result', () => {
    const h = harness();
    assert.equal(h.invoke(CALLERS[0] + 4).changed, false);
    assert.equal(h.invoke(CALLERS[1], 'C8DistortedFateUnlocked').changed, false);
    for (const caller of CALLERS) {
        assert.deepEqual(h.invoke(caller, 'GameCompleted', '4.0'), {changed: false, value: '4.0'});
        assert.equal(h.invoke(caller, 'GameCompleted', null).changed, false);
    }
    assert.equal(h.calls.allocations, 0);
    assert.equal(h.api.status().replacements, 0);
});

test('disable, re-enable and dispose control the hook and release its GC root once', () => {
    const h = harness();
    h.api.enable(false);
    assert.equal(h.invoke(CALLERS[0]).changed, false);
    assert.throws(() => h.api.enable('true'), /true\/false/);
    h.api.enable(true);
    assert.equal(h.invoke(CALLERS[0]).changed, true);
    const status = h.context.rpc.exports.challengedispose();
    assert.equal(status.installed, false);
    assert.equal(status.enabled, false);
    h.api.dispose();
    assert.equal(h.calls.detached, 1);
    assert.deepEqual(h.calls.freed, [1]);
    assert.throws(() => h.api.enable(true), /disposed/);
});

test('architecture and each code signature reject an incompatible binary before hooking', () => {
    for (const options of [
        {arch: 'arm'}, {pointerSize: 4},
        ...Array.from(CODE.keys()).slice(0, 3).map(corrupt => ({corrupt}))
    ]) {
        const h = harness(options);
        assert.equal(h.calls.attaches, 0);
        assert.equal(h.api.status().installed, false);
        assert.match(h.api.status().error, /arm64|Unsupported\/modified binary/);
    }
});

test('wait for IL2CPP, or cancel pending installation on dispose', () => {
    const h = harness({pending: true});
    assert.equal(h.calls.attaches, 0);
    assert.equal(h.timers.size, 1);
    h.loadModule();
    assert.equal(h.calls.attaches, 1);
    assert.equal(h.timers.size, 0);

    const canceled = harness({pending: true});
    canceled.api.dispose();
    assert.equal(canceled.timers.size, 0);
    assert.equal(canceled.calls.attaches, 0);
});

const BACKUP = 'PhiChallengeMode.Backup.v1';

test('persistent unlock flushes and verifies the backup before writing completion', () => {
    const h = harness({persistent: true});
    assert.equal(h.api.status().pending, 'unlock');
    assert.equal(h.calls.writes.length, 0, 'no saves from the injection thread');
    h.invoke(CALLERS[0] + 4);
    h.invoke(CALLERS[0], 'OtherKey');
    assert.equal(h.calls.writes.length, 0);
    h.invoke(CALLERS[0]);
    assert.equal(h.store.get('GameCompleted'), '4.0');
    assert.deepEqual(JSON.parse(h.store.get(BACKUP)), {version: 1, original: '', replacement: '4.0'});
    assert.deepEqual(h.calls.writes.map(([key]) => key), [BACKUP, 'GameCompleted']);
    assert.equal(h.calls.flushes, 2);
    assert.equal(h.api.status().lastResult.action, 'unlocked');
    assert.equal(h.api.status().error, null);
    assert.equal(h.calls.freed.length, h.calls.handles);
    h.invoke(CALLERS[1]);
    assert.equal(h.calls.writes.length, 2, 'run only once');
    h.api.dispose();
    assert.equal(h.calls.detached, 1);
});

test('persistent backup survives script reload and restores the original value', () => {
    const store = new Map();
    const first = harness({persistent: true, store});
    first.invoke(CALLERS[0]);
    first.api.dispose();
    const second = harness({persistent: true, store});
    second.api.restore();
    second.invoke(CALLERS[1]);
    assert.equal(store.get('GameCompleted'), '');
    assert.equal(store.get(BACKUP), '');
    assert.equal(second.api.status().lastResult.action, 'restored');
    assert.equal(second.calls.freed.length, second.calls.handles);
    second.api.unlock();
    second.invoke(CALLERS[0]);
    assert.equal(store.get('GameCompleted'), '4.0');
});

test('persistent mode preserves completed saves and refuses invalid backups or changed state', () => {
    const completed = harness({persistent: true, store: new Map([['GameCompleted', '3.0']])});
    completed.invoke(CALLERS[0]);
    assert.equal(completed.calls.writes.length, 0);
    assert.equal(completed.api.status().lastResult.action, 'unchanged');
    const invalid = harness({persistent: true, store: new Map([[BACKUP, 'invalid json']])});
    invalid.invoke(CALLERS[0]);
    assert.equal(invalid.calls.writes.length, 0);
    assert.ok(invalid.api.status().error);
    assert.equal(invalid.calls.freed.length, invalid.calls.handles);
    const changed = harness({persistent: true, store: new Map([
        ['GameCompleted', 'future-value'],
        [BACKUP, JSON.stringify({version: 1, original: '', replacement: '4.0'})]
    ])});
    changed.api.restore();
    changed.invoke(CALLERS[0]);
    assert.equal(changed.calls.writes.length, 0);
    assert.match(changed.api.status().error, /changed since patch/);
});

test('backup write failure prevents completion write; missing backup prevents restore', () => {
    const failed = harness({persistent: true, failWrite: BACKUP});
    failed.invoke(CALLERS[0]);
    assert.equal(failed.store.has('GameCompleted'), false);
    assert.match(failed.api.status().error, /Read-back failed/);
    assert.equal(failed.calls.freed.length, failed.calls.handles);
    const missing = harness({persistent: true});
    missing.api.restore();
    missing.invoke(CALLERS[0]);
    assert.match(missing.api.status().error, /No backup/);
    assert.equal(missing.calls.writes.length, 0);
});

test('persistent mode validates every code signature before attaching', () => {
    for (const corrupt of CODE.keys()) {
        const h = harness({persistent: true, corrupt});
        assert.equal(h.calls.attaches, 0);
        assert.match(h.api.status().error, /Unsupported\/modified/);
        assert.equal(h.calls.writes.length, 0);
    }
});
