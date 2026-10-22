'use strict';
const test = require('node:test');
const assert = require('node:assert/strict');
const fs = require('node:fs');
const vm = require('node:vm');

function fixture() {
    const nil = {isNull: () => true};
    const object = {isNull: () => false};
    const handles = new Map(), flags = [];
    let next = 1;
    const api = {
        domain_get: () => object,
        domain_get_assemblies: () => object,
        class_get_type: () => object,
        type_get_object: () => object,
        runtime_invoke: () => object,
        gchandle_new: (p, pinned) => {
            flags.push(pinned); const h = next++; handles.set(h, p); return h;
        },
        gchandle_free: h => { assert.ok(handles.delete(h)); }
    };
    const context = {
        NULL: nil,
        Process: {arch: 'arm64', getModuleByName: () => ({getExportByName: n => n})},
        Memory: {alloc: () => ({
            value: nil,
            readU64: () => ({toNumber: () => 0}),
            writePointer(p) { this.value = p; return this; },
            readPointer() { return this.value; }
        })},
        NativeFunction: function (name, ret, types) {
            return (...args) => {
                types.forEach((type, i) => {
                    if (type === 'bool' && !Number.isInteger(args[i])) throw Error('expected an integer');
                });
                const fn = api[name.replace(/^il2cpp_/, '')];
                assert.ok(fn, 'Unexpected native call: ' + name);
                return fn(...args);
            };
        }
    };
    vm.runInNewContext(fs.readFileSync(require.resolve('../src/runtime.js'), 'utf8'), context);
    return {r: context.createPhiHoldRuntime(), object, nil, handles, flags};
}

test('runtime pin, type and invoke marshal GC handle bool as integer for Frida 16', () => {
    const f = fixture();
    assert.throws(() => f.r.a.gchandle_new(f.object, false), /expected an integer/);
    f.r.scope(() => {
        assert.equal(f.r.pin(f.object), f.object);
        assert.equal(f.r.type(f.object), f.object);
        assert.equal(f.r.invoke(f.object, f.object), f.object);
        assert.equal(f.handles.size, 3);
    });
    assert.deepEqual(f.flags, [0, 0, 0]);
    assert.equal(f.handles.size, 0);
});

test('runtime skips null roots and releases handles on scope failure', () => {
    const f = fixture();
    assert.throws(() => f.r.scope(() => {
        assert.equal(f.r.pin(f.nil), f.nil);
        f.r.pin(f.object);
        throw Error('scope test failure');
    }), /scope test failure/);
    assert.deepEqual(f.flags, [0]);
    assert.equal(f.handles.size, 0);
});
