'use strict';

// Only the public IL2CPP C API is used for discovery and managed invocation.
// MethodInfo.methodPointer is the first pointer in the Unity IL2CPP v31 ABI.
globalThis.Phi = {
    config: {enabled: true, url: 'ws://127.0.0.1:8765', good: true, bad: true,
             miss: true, perfect: false, qrText: ''},
    stats: {observed: {perfect: 0, good: 0, bad: 0, miss: 0}, handedOff: 0,
            dropped: 0, errors: 0},
    listeners: [], queue: [], disposed: false, ready: false, sequence: 0,
    session: Date.now().toString(36),
    log: function (s) { console.log('[judgment] ' + s); }
};

Phi.initRuntime = function () {
    if (Process.arch !== 'arm64') throw new Error('This build is verified for arm64 only');
    const lib = Process.getModuleByName('libil2cpp.so');
    const api = {};
    function bind(name, ret, args) {
        api[name] = new NativeFunction(lib.getExportByName('il2cpp_' + name), ret, args);
    }
    bind('domain_get', 'pointer', []);
    bind('domain_get_assemblies', 'pointer', ['pointer', 'pointer']);
    bind('thread_attach', 'pointer', ['pointer']);
    bind('assembly_get_image', 'pointer', ['pointer']);
    bind('image_get_name', 'pointer', ['pointer']);
    bind('class_from_name', 'pointer', ['pointer', 'pointer', 'pointer']);
    bind('class_get_method_from_name', 'pointer', ['pointer', 'pointer', 'int']);
    bind('class_get_field_from_name', 'pointer', ['pointer', 'pointer']);
    bind('field_get_offset', 'int', ['pointer']);
    bind('field_get_type', 'pointer', ['pointer']);
    bind('class_from_type', 'pointer', ['pointer']);
    bind('class_get_type', 'pointer', ['pointer']);
    bind('type_get_object', 'pointer', ['pointer']);
    bind('object_new', 'pointer', ['pointer']);
    bind('object_unbox', 'pointer', ['pointer']);
    bind('string_new', 'pointer', ['pointer']);
    bind('array_new', 'pointer', ['pointer', 'ulong']);
    bind('gchandle_new', 'uint', ['pointer', 'bool']);
    bind('gchandle_free', 'void', ['uint']);
    bind('runtime_invoke', 'pointer', ['pointer', 'pointer', 'pointer', 'pointer']);
    const domain = api.domain_get();
    if (domain.isNull()) throw new Error('IL2CPP domain is not initialized');
    api.thread_attach(domain);
    const size = Memory.alloc(8);
    const assemblies = api.domain_get_assemblies(domain, size);
    const images = {};
    for (let i = 0; i < size.readU64().toNumber(); i++) {
        const im = api.assembly_get_image(assemblies.add(i * 8).readPointer());
        images[api.image_get_name(im).readCString()] = im;
    }
    const classes = {}, methods = {}, fields = {};
    const r = {
        api: api, base: lib.base,
        klass: function (assembly, ns, name) {
            const key = assembly + ':' + ns + '.' + name;
            if (!classes[key]) {
                if (!images[assembly]) throw new Error('Missing assembly ' + assembly);
                const k = api.class_from_name(images[assembly], Memory.allocUtf8String(ns), Memory.allocUtf8String(name));
                if (k.isNull()) throw new Error('Missing class ' + key);
                classes[key] = k;
            }
            return classes[key];
        },
        method: function (klass, name, argc) {
            const key = klass + ':' + name + '/' + argc;
            if (!methods[key]) {
                const m = api.class_get_method_from_name(klass, Memory.allocUtf8String(name), argc);
                if (m.isNull()) throw new Error('Missing method ' + key);
                methods[key] = m;
            }
            return methods[key];
        },
        field: function (klass, name) {
            const key = klass + ':' + name;
            if (!fields[key]) {
                const f = api.class_get_field_from_name(klass, Memory.allocUtf8String(name));
                if (f.isNull()) throw new Error('Missing field ' + key);
                fields[key] = f;
            }
            return fields[key];
        },
        offset: function (klass, name) { return api.field_get_offset(r.field(klass, name)); },
        str: function (s) { return api.string_new(Memory.allocUtf8String(s)); },
        invoke: function (method, self, args) {
            args = args || [];
            const argv = args.length ? Memory.alloc(args.length * 8) : NULL;
            args.forEach((arg, i) => argv.add(i * 8).writePointer(arg));
            const exception = Memory.alloc(8); exception.writePointer(NULL);
            const result = api.runtime_invoke(method, self || NULL, argv, exception);
            if (!exception.readPointer().isNull()) throw new Error('Managed exception from ' + method);
            return result;
        },
        hook: function (method, callbacks) {
            const address = method.readPointer();
            const range = Process.findRangeByAddress(address);
            if (address.isNull() || !range || range.protection.indexOf('x') < 0)
                throw new Error('Method has no executable code: ' + method);
            const h = Interceptor.attach(address, callbacks);
            Phi.listeners.push(h);
            return address.sub(lib.base).toString();
        },
        game: function (name) { return r.klass('Assembly-CSharp.dll', '', name); }
    };
    Phi.runtime = r;
    return r;
};
