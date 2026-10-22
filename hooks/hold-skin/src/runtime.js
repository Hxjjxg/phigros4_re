'use strict';
// Small, independent IL2CPP bridge. Unity calls are made only inside game callbacks.
globalThis.createPhiHoldRuntime = function () {
    if (Process.arch !== 'arm64') throw Error('Hold skin supports Android arm64');
    const lib = Process.getModuleByName('libil2cpp.so'), a = {};
    const bind = (n, ret, args) => a[n] = new NativeFunction(lib.getExportByName('il2cpp_' + n), ret, args);
    bind('domain_get', 'pointer', []);
    bind('domain_get_assemblies', 'pointer', ['pointer', 'pointer']);
    bind('assembly_get_image', 'pointer', ['pointer']);
    bind('image_get_name', 'pointer', ['pointer']);
    bind('class_from_name', 'pointer', ['pointer', 'pointer', 'pointer']);
    bind('class_get_method_from_name', 'pointer', ['pointer', 'pointer', 'int']);
    bind('class_get_methods', 'pointer', ['pointer', 'pointer']);
    bind('method_get_name', 'pointer', ['pointer']);
    bind('method_get_param_count', 'uint', ['pointer']);
    bind('method_get_param', 'pointer', ['pointer', 'uint']);
    bind('type_get_name', 'pointer', ['pointer']);
    bind('free', 'void', ['pointer']);
    bind('class_get_field_from_name', 'pointer', ['pointer', 'pointer']);
    bind('field_get_offset', 'int', ['pointer']);
    bind('object_get_class', 'pointer', ['pointer']);
    bind('object_new', 'pointer', ['pointer']);
    bind('object_unbox', 'pointer', ['pointer']);
    bind('class_get_type', 'pointer', ['pointer']);
    bind('type_get_object', 'pointer', ['pointer']);
    bind('string_new', 'pointer', ['pointer']);
    bind('array_new', 'pointer', ['pointer', 'ulong']);
    bind('gchandle_new', 'uint', ['pointer', 'bool']);
    bind('gchandle_new_weakref', 'uint', ['pointer', 'bool']);
    bind('gchandle_get_target', 'pointer', ['uint']);
    bind('gchandle_free', 'void', ['uint']);
    bind('gc_wbarrier_set_field', 'void', ['pointer', 'pointer', 'pointer']);
    bind('runtime_invoke', 'pointer', ['pointer', 'pointer', 'pointer', 'pointer']);
    const domain = a.domain_get();
    if (domain.isNull()) throw Error('IL2CPP domain not initialized');
    const count = Memory.alloc(8), images = {}, classes = new Map(), methods = new Map(), offsets = new Map();
    const assemblies = a.domain_get_assemblies(domain, count);
    for (let i = 0; i < count.readU64().toNumber(); i++) {
        const im = a.assembly_get_image(assemblies.add(i * 8).readPointer());
        images[a.image_get_name(im).readCString()] = im;
    }
    let roots = null;
    const R = {a,
        scope(fn) {
            const previous = roots; roots = [];
            try { return fn(); } finally { roots.forEach(h => a.gchandle_free(h)); roots = previous; }
        },
        // Frida 16 marshals native bool arguments from integers, not JS Booleans.
        pin(p) { if (!p.isNull() && roots) roots.push(a.gchandle_new(p, 0)); return p; },
        klass(assembly, ns, name) {
            const id = [assembly, ns, name].join(':');
            if (!classes.has(id)) {
                if (!images[assembly]) throw Error('Missing assembly ' + assembly);
                const p = a.class_from_name(images[assembly], Memory.allocUtf8String(ns), Memory.allocUtf8String(name));
                if (p.isNull()) throw Error('Missing type ' + id);
                classes.set(id, p);
            }
            return classes.get(id);
        },
        game(name) { return R.klass('Assembly-CSharp.dll', '', name); },
        unity(name, module = 'Core') { return R.klass('UnityEngine.' + module + 'Module.dll', 'UnityEngine', name); },
        method(k, name, argc, firstType) {
            const key = [k, name, argc, firstType].join(':');
            if (!methods.has(key)) {
                let m = NULL;
                if (!firstType) m = a.class_get_method_from_name(k, Memory.allocUtf8String(name), argc);
                else {
                    const iter = Memory.alloc(8); iter.writePointer(NULL);
                    for (;;) {
                        const candidate = a.class_get_methods(k, iter); if (candidate.isNull()) break;
                        if (a.method_get_name(candidate).readCString() !== name || a.method_get_param_count(candidate) !== argc) continue;
                        const types = Array.isArray(firstType) ? firstType : [firstType];
                        const matches = types.every((expected, i) => {
                            const type = a.type_get_name(a.method_get_param(candidate, i));
                            try { return type.readCString() === expected; } finally { a.free(type); }
                        });
                        if (matches) { m = candidate; break; }
                    }
                }
                if (m.isNull()) throw Error('Missing method ' + key);
                methods.set(key, m);
            }
            return methods.get(key);
        },
        offset(k, name) {
            const key = k + ':' + name;
            if (!offsets.has(key)) {
                const f = a.class_get_field_from_name(k, Memory.allocUtf8String(name));
                if (f.isNull()) throw Error('Missing field ' + key);
                offsets.set(key, a.field_get_offset(f));
            }
            return offsets.get(key);
        },
        field(p, k, name) { return p.add(R.offset(k, name)).readPointer(); },
        put(p, k, name, value) { a.gc_wbarrier_set_field(p, p.add(R.offset(k, name)), value); },
        item(p, index) {
            if (p.isNull() || index < 0 || index >= p.add(24).readU64().toNumber()) throw Error('Invalid managed array index');
            return p.add(32 + index * 8).readPointer();
        },
        setItem(p, index, value) { R.item(p, index); a.gc_wbarrier_set_field(p, p.add(32 + index * 8), value); },
        invoke(m, self, args = []) {
            const argv = args.length ? Memory.alloc(args.length * 8) : NULL;
            args.forEach((p, i) => argv.add(i * 8).writePointer(p));
            const err = Memory.alloc(8); err.writePointer(NULL);
            const out = a.runtime_invoke(m, self || NULL, argv, err);
            if (!err.readPointer().isNull()) throw Error('Managed exception: ' + a.method_get_name(m).readCString());
            return R.pin(out);
        },
        call(p, name, args = []) { return R.invoke(R.method(a.object_get_class(p), name, args.length), p, args); },
        int(n) { return Memory.alloc(4).writeS32(n); },
        floats(values) { const p = Memory.alloc(values.length * 4); values.forEach((v,i) => p.add(i*4).writeFloat(v)); return p; },
        str(s) { return R.pin(a.string_new(Memory.allocUtf8String(s))); },
        type(k) { return R.pin(a.type_get_object(a.class_get_type(k))); },
        bool(p) { return !p.isNull() && a.object_unbox(p).readU8() !== 0; },
        number(p) { return a.object_unbox(p).readS32(); },
        alive(p) { return !p.isNull() && R.bool(R.invoke(R.method(R.unity('Object'), 'op_Implicit', 1), NULL, [p])); },
        destroy(p) { if (R.alive(p)) R.invoke(R.method(R.unity('Object'), 'Destroy', 1), NULL, [p]); },
        component(go, k) { return R.invoke(R.method(R.unity('GameObject'), 'GetComponent', 1, 'System.Type'), go, [R.type(k)]); },
        listCount(p) { return R.number(R.call(p, 'get_Count')); },
        listItem(p, i) { return R.call(p, 'get_Item', [R.int(i)]); }
    };
    return R;
};
