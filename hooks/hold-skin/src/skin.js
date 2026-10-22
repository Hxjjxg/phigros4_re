'use strict';
globalThis.PhiHoldSkin = (() => {
    const C = PhiHoldCore, options = C.config(globalThis.PHI_HOLD_SKIN_OPTIONS);
    const listeners = [], assets = [], textures = {}, spriteSets = new Map(), tracked = new Map();
    const state = {phase: 'waiting', error: null, single: 0, multi: 0, skipped: 0};
    const rendererFields = ['_holdHeadSpriteRenderer', '_holdSpriteRenderer', '_holdEndSpriteRenderer1'];
    let r, H, J, M, stopped = false, pendingDispose = null, disposed = false;
    const log = s => console.log('[hold-skin] ' + s);
    function fail(e) {
        const message = String(e.stack || e);
        if (state.error !== message) log(message);
        state.error = message;
    }
    function own(p) {
        if (p.isNull()) throw Error('Unity returned a null asset');
        const handle = r.a.gchandle_new(p, 0);
        assets.push({pointer: p, handle});
        r.call(p, 'set_hideFlags', [r.int(32)]); // Strong GC handle + native asset lifetime.
        return p;
    }
    function releaseFrom(index) {
        while (assets.length > index) {
            const item = assets[assets.length - 1];
            r.destroy(item.pointer); r.a.gchandle_free(item.handle); assets.pop();
        }
    }
    function texture(width, height) {
        const k = r.unity('Texture2D'), p = r.pin(r.a.object_new(k));
        r.invoke(r.method(k, '.ctor', 4, ['System.Int32', 'System.Int32', 'UnityEngine.TextureFormat', 'System.Boolean']),
            p, [r.int(width), r.int(height), r.int(4), r.int(0)]);
        own(p); // RGBA32, no mipmaps.
        r.call(p, 'set_wrapMode', [r.int(1)]); // Clamp prevents seams at hold endpoints.
        r.call(p, 'set_filterMode', [r.int(1)]);
        return p;
    }
    function loadImages() {
        if (state.phase === 'loaded') return;
        if (state.phase === 'failed') throw Error('Image load failed; use holdretry after correcting files');
        const base = assets.length, staged = {};
        try {
            // Validate all files before allocating Unity textures. No device shell or permissions changes.
            const files = C.slots.map(slot => {
                const path = options.directory + '/' + options.files[slot];
                let f;
                try {
                    f = new File(path, 'rb');
                    const bytes = new Uint8Array(f.readBytes(16 * 1024 * 1024 + 1));
                    if (bytes.length > 16 * 1024 * 1024) throw Error('PNG exceeds 16 MiB: ' + path);
                    return {slot, bytes, size: C.pngSize(bytes)};
                } catch (e) {
                    throw Error('Cannot load PNG ' + slot + ' at ' + path + ': ' + String(e.message || e));
                } finally { if (f) f.close(); }
            });
            for (const f of files) {
                const bytes = r.pin(r.a.array_new(r.klass('mscorlib.dll', 'System', 'Byte'), f.bytes.length));
                bytes.add(32).writeByteArray(f.bytes);
                const p = texture(2, 2);
                const loaded = r.invoke(r.method(r.unity('ImageConversion', 'ImageConversion'), 'LoadImage', 3), NULL, [p, bytes, r.int(1)]);
                if (!r.bool(loaded)) throw Error('Unity rejected ' + options.files[f.slot]);
                if (r.number(r.call(p, 'get_width')) !== f.size.width || r.number(r.call(p, 'get_height')) !== f.size.height) throw Error('Decoded PNG dimensions differ from IHDR');
                staged[f.slot] = {pointer: p, ...f.size};
            }
            Object.assign(textures, staged); state.phase = 'loaded'; state.error = null;
            log('Loaded six PNG textures from ' + options.directory);
        } catch (e) { releaseFrom(base); state.phase = 'failed'; throw e; }
    }
    function spriteGeometry(p, slot) {
        if (!r.alive(p)) throw Error('Missing native sprite template');
        const rect = r.a.object_unbox(r.call(p, 'get_rect'));
        const pivot = r.a.object_unbox(r.call(p, 'get_pivot'));
        try {
            return C.geometry([rect.add(8).readFloat(), rect.add(12).readFloat()],
                [pivot.readFloat(), pivot.add(4).readFloat()], r.a.object_unbox(r.call(p, 'get_pixelsPerUnit')).readFloat());
        } catch (e) { throw Error('Native sprite ' + slot + ' (' + p + '): ' + String(e.message || e)); }
    }
    function resized(source, g) {
        if (source.width === g.width && source.height === g.height) return source.pointer;
        const width = Math.ceil(g.width), height = Math.ceil(g.height);
        // Fit the art to the native rect so the game's hard-coded hold length and
        // child tail position remain consistent even with a different source aspect ratio.
        const rt = r.unity('RenderTexture');
        const target = r.invoke(r.method(rt, 'GetTemporary', 3), NULL, [r.int(width), r.int(height), r.int(0)]);
        if (target.isNull()) throw Error('RenderTexture allocation failed');
        const previous = r.invoke(r.method(rt, 'get_active', 0), NULL);
        try {
            const output = texture(width, height);
            r.invoke(r.method(r.unity('Graphics'), 'Blit', 2, 'UnityEngine.Texture'), NULL, [source.pointer, target]);
            r.invoke(r.method(rt, 'set_active', 1), NULL, [target]);
            r.call(output, 'ReadPixels', [r.floats([0, 0, width, height]), r.int(0), r.int(0)]);
            r.call(output, 'Apply', [r.int(0), r.int(1)]);
            return output;
        } finally {
            try { r.invoke(r.method(rt, 'set_active', 1), NULL, [previous]); }
            finally { r.invoke(r.method(rt, 'ReleaseTemporary', 1), NULL, [target]); }
        }
    }
    function templates(line) {
        const prefab = r.component(r.field(line, J, 'Hold'), H);
        if (prefab.isNull()) throw Error('Hold prefab has no HoldControl');
        const array = r.field(prefab, H, 'noteImages');
        const single = [0,1,2].map(i => r.item(array, i));
        return {single, multi: [r.field(line, J, 'HoldHL0'), r.field(line, J, 'HoldHL1'), single[2]]};
    }
    function prepare(t) {
        loadImages();
        const geometry = [...t.single, ...t.multi].map((p, i) => spriteGeometry(p, C.slots[i]));
        const key = JSON.stringify(geometry);
        if (spriteSets.has(key)) return spriteSets.get(key);
        if (spriteSets.size >= 16) throw Error('Too many distinct native hold geometries');
        const start = assets.length;
        try {
            const sprites = C.slots.map((slot, i) => {
                const g = geometry[i], tex = resized(textures[slot], g);
                const sprite = own(r.invoke(r.method(r.unity('Sprite'), 'Create', 6, 'UnityEngine.Texture2D'), NULL,
                    [tex, r.floats([0,0,g.width,g.height]), r.floats(g.pivot), r.floats([g.ppu]), r.int(0), r.int(0)]));
                r.call(sprite, 'set_name', [r.str('PhiHoldSkin/' + slot)]);
                return sprite;
            });
            const set = {single: sprites.slice(0,3), multi: sprites.slice(3)};
            spriteSets.set(key, set); return set;
        } catch (e) { releaseFrom(start); throw e; }
    }
    function prune() {
        for (const [key, item] of tracked) {
            const p = r.a.gchandle_get_target(item.weak);
            if (r.alive(p)) continue;
            r.a.gchandle_free(item.weak); r.a.gchandle_free(item.original); r.a.gchandle_free(item.replacement);
            tracked.delete(key);
        }
    }
    function restore(item, hold) {
        const original = r.a.gchandle_get_target(item.original);
        const replacement = r.a.gchandle_get_target(item.replacement);
        if (r.field(hold, H, 'noteImages').equals(replacement)) r.put(hold, H, 'noteImages', original);
        rendererFields.forEach((field, i) => {
            const renderer = r.field(hold, H, field);
            if (r.alive(renderer) && r.call(renderer, 'get_sprite').equals(r.item(replacement, i))) {
                r.call(renderer, 'set_sprite', [r.item(original, i)]);
            }
        });
    }
    function apply(hold, line, t, set) {
        const key = hold.toString();
        if (tracked.has(key)) {
            const entry = tracked.get(key);
            if (r.a.gchandle_get_target(entry.weak).equals(hold)) return;
            prune();
        }
        const original = r.field(hold, H, 'noteImages');
        const ids = list => list.map(p => p.isNull() ? null : p.toString());
        const variant = C.classify(line.add(r.offset(J, 'chordSupport')).readU8() !== 0,
            ids([r.item(original, 0), r.item(original, 1)]), ids(t.single.slice(0,2)), ids(t.multi.slice(0,2)));
        // Never mutate the prefab's array, even if Unity or another hook shared it.
        const copy = r.invoke(r.method(r.klass('mscorlib.dll', 'System', 'Array'), 'Clone', 0), original);
        set[variant].forEach((sprite, i) => r.setItem(copy, i, sprite));
        const entry = {weak: r.a.gchandle_new_weakref(hold, 0), original: r.a.gchandle_new(original, 0), replacement: r.a.gchandle_new(copy, 0)};
        tracked.set(key, entry); // Keep restoration information even if a setter throws.
        try {
            r.put(hold, H, 'noteImages', copy);
            rendererFields.forEach((field, i) => {
                const renderer = r.field(hold, H, field);
                if (r.alive(renderer)) r.call(renderer, 'set_sprite', [set[variant][i]]);
            });
            state[variant]++;
        } catch (e) { restore(entry, hold); throw e; }
    }
    function cleanup() {
        for (const item of tracked.values()) {
            const p = r.a.gchandle_get_target(item.weak);
            if (r.alive(p)) restore(item, p);
        }
        for (const item of tracked.values()) {
            r.a.gchandle_free(item.weak); r.a.gchandle_free(item.original); r.a.gchandle_free(item.replacement);
        }
        tracked.clear(); releaseFrom(0); spriteSets.clear();
        listeners.splice(0).forEach(l => l.detach()); disposed = true; state.phase = 'disposed';
    }
    function pump() {
        if (!pendingDispose) return;
        const job = pendingDispose; pendingDispose = null; clearTimeout(job.timer);
        try { r.scope(cleanup); setImmediate(() => job.resolve({disposed: true})); }
        catch (e) { fail(e); setImmediate(() => job.reject(e)); }
    }
    function hook(k, name, count, callbacks) {
        const address = r.method(k, name, count).readPointer();
        const range = Process.findRangeByAddress(address);
        if (!range || !range.protection.includes('x')) throw Error('No code for ' + name);
        listeners.push(Interceptor.attach(address, callbacks));
    }
    function install() {
        r = createPhiHoldRuntime(); H = r.game('HoldControl'); J = r.game('JudgeLineControl'); M = r.game('NoteUpdateManager');
        // Reject old layouts instead of applying the misleading body/head mapping in old scripts.
        if (r.offset(H, 'noteImages') !== 0x50 || r.offset(H, '_holdSpriteRenderer') !== 0xa0 ||
            r.offset(J, 'HoldHL0') !== 0x48) throw Error('Unsupported game layout; expected Phigros 4.0.1 arm64');
        try {
            hook(J, 'Start', 0, {onEnter(args) { this.line = args[0]; }, onLeave() {
                pump(); if (stopped) return;
                // Start is only a preload opportunity; classification happens after CreateNote.
                if (state.phase === 'failed') return;
                try { r.scope(() => { prune(); prepare(templates(this.line)); }); } catch (e) { fail(e); }
            }});
            hook(J, 'CreateNote', 2, {
                onEnter(args) {
                    this.contextData = null;
                    if (stopped || state.phase === 'failed') return;
                    try { r.scope(() => {
                        const line = args[0], index = args[1].toInt32(), above = args[2].toInt32() !== 0;
                        const list = r.field(line, J, above ? 'notesAbove' : 'notesBelow');
                        if (index < 0 || index >= r.listCount(list)) return;
                        const note = r.listItem(list, index);
                        if (note.add(r.offset(r.game('ChartNote'), 'type')).readS32() !== 3) return;
                        const manager = r.field(line, J, '_noteUpdateManager');
                        const holds = r.field(manager, M, 'holdControls');
                        const t = templates(line), set = prepare(t);
                        // Native objects can cross the original call (and its allocations).
                        this.roots = [line, note, holds, ...t.single, ...t.multi].map(p => r.a.gchandle_new(p, 0));
                        this.contextData = {line, note, holds, before: r.listCount(holds), t, set};
                    }); } catch (e) { state.skipped++; fail(e); }
                },
                onLeave() {
                    const c = this.contextData; if (!c) return;
                    try { r.scope(() => {
                        if (stopped) return;
                        const end = r.listCount(c.holds);
                        let found = false;
                        for (let i = c.before; i < end; i++) {
                            const hold = r.listItem(c.holds, i);
                            if (r.field(hold, H, 'noteInfor').equals(c.note) && r.field(hold, H, 'judgeLine').equals(c.line)) {
                                apply(hold, c.line, c.t, c.set); found = true;
                            }
                        }
                        if (!found) throw Error('CreateNote did not append the expected HoldControl');
                        if ((state.single + state.multi) % 128 === 0) prune();
                    }); } catch (e) { state.skipped++; fail(e); }
                    finally { this.roots.forEach(h => r.a.gchandle_free(h)); }
                }
            });
            // These callbacks only drain explicit disposal work; no per-hold frame hook.
            for (const name of ['SongSelector', 'Setting', 'LevelInformation']) hook(r.game(name), 'Update', 0, {onLeave: pump});
            state.phase = 'ready';
            log('Ready; six-image loading occurs on Unity thread at next chart');
        } catch (e) { listeners.splice(0).forEach(l => l.detach()); throw e; }
    }
    const deadline = Date.now() + 120000;
    const timer = setInterval(() => {
        if (stopped) { clearInterval(timer); return; }
        if (!Process.findModuleByName('libil2cpp.so')) {
            if (Date.now() > deadline) { state.phase = 'failed'; fail(Error('IL2CPP startup timed out')); clearInterval(timer); }
            return;
        }
        try { install(); clearInterval(timer); }
        catch (e) { fail(e); if (Date.now() > deadline) { state.phase = 'failed'; clearInterval(timer); } }
    }, 250);
    return {
        status() { return {...state, directory: options.directory, files: {...options.files}, tracked: tracked.size,
            assets: assets.length, spriteSets: spriteSets.size,
            images: Object.fromEntries(Object.entries(textures).map(([key, t]) => [key, {width:t.width, height:t.height}]))}; },
        retry() {
            if (stopped || !r || !listeners.length) throw Error('Hook not active');
            if (state.phase === 'failed') state.phase = 'ready';
            state.error = null;
            return 'Will retry loading at next JudgeLineControl.Start/CreateNote';
        },
        dispose() {
            if (disposed) return Promise.resolve({disposed: true});
            if (pendingDispose) return pendingDispose.promise;
            stopped = true; clearInterval(timer);
            if (!assets.length && !tracked.size) {
                listeners.splice(0).forEach(l => l.detach()); disposed = true; state.phase = 'disposed';
                return Promise.resolve({disposed: true});
            }
            const job = {};
            job.promise = new Promise((resolve, reject) => { job.resolve = resolve; job.reject = reject; });
            job.timer = setTimeout(() => {
                if (pendingDispose === job) pendingDispose = null;
                job.reject(Error('Unity is not ticking; return to gameplay, song selection, or Settings and retry holddispose'));
            }, 15000);
            pendingDispose = job; return job.promise;
        }
    };
})();
rpc.exports.holdstatus = () => PhiHoldSkin.status();
rpc.exports.holdretry = () => PhiHoldSkin.retry();
rpc.exports.holddispose = () => PhiHoldSkin.dispose();
