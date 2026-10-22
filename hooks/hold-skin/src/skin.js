'use strict';
globalThis.PhiHoldSkin = (() => {
    const C = PhiHoldCore, P = PhiHoldProfile, options = C.config(globalThis.PHI_HOLD_SKIN_OPTIONS);
    const listeners = [], assets = [], arrays = [], tracked = new Map(), calls = new Map();
    const textures = {}, sprites = [], sets = {};
    const state = {phase:'waiting', error:null, prepared:0, single:0, multi:0, skipped:0,
        classifier:'native-branch', lastStepMs:0, maxStepMs:0};
    const rendererFields = ['_holdHeadSpriteRenderer', '_holdSpriteRenderer', '_holdEndSpriteRenderer1'];
    let r, H, J, N, files = null, reading = false, stopped = false, disposed = false;
    let pendingDispose = null, pruneCursor = null, warnedUnready = false;
    const log = s => console.log('[hold-skin] ' + s);
    function fail(e) {
        const message = String(e.stack || e);
        if (state.error !== message) log(message);
        state.error = message;
    }
    function own(p) {
        if (p.isNull()) throw Error('Unity returned a null asset');
        const handle = r.a.gchandle_new(p, 0);
        assets.push({pointer:p, handle});
        r.call(p, 'set_hideFlags', [r.int(32)]);
        return p;
    }
    function releaseAssets() {
        while (assets.length) {
            const item = assets[assets.length - 1];
            r.destroy(item.pointer); r.a.gchandle_free(item.handle); assets.pop();
        }
    }
    function releaseArrays() {
        while (arrays.length) r.a.gchandle_free(arrays.pop().handle);
        delete sets.single; delete sets.multi;
    }
    // Stage files on the script thread, then decode at most one per menu frame.
    function stageFiles() {
        if (stopped) return;
        try {
            files = C.slots.map(slot => {
                const path = options.directory + '/' + options.files[slot];
                let file;
                try {
                    file = new File(path, 'rb');
                    const bytes = new Uint8Array(file.readBytes(16 * 1024 * 1024 + 1));
                    if (bytes.length > 16 * 1024 * 1024) throw Error('PNG exceeds 16 MiB');
                    return {slot, bytes, ...C.pngSize(bytes)};
                } catch (e) { throw Error('Cannot load PNG ' + slot + ' at ' + path + ': ' + String(e.message || e)); }
                finally { if (file) file.close(); }
            });
            state.phase = 'warming';
        } catch (e) { files = null; state.phase = 'failed'; fail(e); }
        finally { reading = false; }
    }
    function texture() {
        const k = r.unity('Texture2D'), p = r.pin(r.a.object_new(k));
        r.invoke(r.method(k, '.ctor', 4, ['System.Int32','System.Int32','UnityEngine.TextureFormat','System.Boolean']),
            p, [r.int(2), r.int(2), r.int(4), r.int(0)]);
        own(p);
        r.call(p, 'set_wrapMode', [r.int(1)]);
        r.call(p, 'set_filterMode', [r.int(1)]);
        return p;
    }
    function spriteArray(items) {
        const p = r.pin(r.a.array_new(r.unity('Sprite'), 3));
        if (p.isNull()) throw Error('Sprite array allocation failed');
        arrays.push({pointer:p, handle:r.a.gchandle_new(p, 0)});
        items.forEach((item, i) => r.setItem(p, i, item));
        return p;
    }
    function warmStep() {
        if (stopped || state.phase === 'failed' || state.phase === 'ready') return;
        if (!files) {
            if (!reading) { reading = true; state.phase = 'reading'; setImmediate(stageFiles); }
            return;
        }
        const started = Date.now(), index = state.prepared, image = files[index];
        try {
            const bytes = r.pin(r.a.array_new(r.klass('mscorlib.dll', 'System', 'Byte'), image.bytes.length));
            bytes.add(32).writeByteArray(image.bytes);
            const tex = texture();
            const loaded = r.invoke(r.method(r.unity('ImageConversion', 'ImageConversion'), 'LoadImage', 3),
                NULL, [tex, bytes, r.int(1)]);
            if (!r.bool(loaded)) throw Error('Unity rejected ' + options.files[image.slot]);
            if (r.number(r.call(tex, 'get_width')) !== image.width || r.number(r.call(tex, 'get_height')) !== image.height)
                throw Error('Decoded PNG dimensions differ from IHDR: ' + image.slot);
            const g = C.artGeometry(image, P.geometry[index], image.slot);
            const sprite = own(r.invoke(r.method(r.unity('Sprite'), 'Create', 6, 'UnityEngine.Texture2D'), NULL,
                [tex, r.floats([0,0,g.width,g.height]), r.floats(g.pivot), r.floats([g.ppu]), r.int(0), r.int(0)]));
            r.call(sprite, 'set_name', [r.str('PhiHoldSkin/' + image.slot)]);
            textures[image.slot] = {width:image.width, height:image.height};
            sprites.push(sprite); image.bytes = null; state.prepared++;
            if (state.prepared === C.slots.length) {
                sets.single = spriteArray(sprites.slice(0,3));
                sets.multi = spriteArray(sprites.slice(3));
                state.phase = 'ready'; state.error = null; files = null;
                log('Ready: six sprites prepared in menu; start a new chart');
            }
        } catch (e) {
            releaseArrays(); releaseAssets(); sprites.length = 0; files = null;
            Object.keys(textures).forEach(key => delete textures[key]);
            state.prepared = 0; state.phase = 'failed'; fail(e);
        } finally {
            state.lastStepMs = Date.now() - started;
            state.maxStepMs = Math.max(state.maxStepMs, state.lastStepMs);
        }
    }
    function restore(item, hold) {
        const original = r.a.gchandle_get_target(item.original);
        if (r.field(hold, H, 'noteImages').equals(item.replacement)) r.put(hold, H, 'noteImages', original);
        rendererFields.forEach((field, i) => {
            const renderer = r.field(hold, H, field);
            if (r.alive(renderer) && r.call(renderer, 'get_sprite').equals(r.item(item.replacement, i)))
                r.call(renderer, 'set_sprite', [r.item(original, i)]);
        });
    }
    function forget(key, item) {
        r.a.gchandle_free(item.weak); r.a.gchandle_free(item.original); tracked.delete(key);
    }
    function pruneSome() {
        if (!pruneCursor) pruneCursor = tracked.entries();
        for (let i = 0; i < 8; i++) {
            const next = pruneCursor.next();
            if (next.done) { pruneCursor = null; break; }
            const [key, item] = next.value;
            if (!r.alive(r.a.gchandle_get_target(item.weak))) forget(key, item);
        }
    }
    function apply(hold, variant) {
        const key = hold.toString(), previous = tracked.get(key);
        if (previous) {
            if (r.a.gchandle_get_target(previous.weak).equals(hold)) return;
            forget(key, previous);
        }
        const original = r.field(hold, H, 'noteImages'), replacement = sets[variant];
        if (original.isNull()) throw Error('Hold has no original sprite array');
        const item = {weak:r.a.gchandle_new_weakref(hold, 0), original:r.a.gchandle_new(original, 0), replacement};
        tracked.set(key, item);
        try {
            // Bind a complete, immutable head/body/tail set with one field write.
            r.put(hold, H, 'noteImages', replacement);
            rendererFields.forEach((field, i) => {
                const renderer = r.field(hold, H, field);
                if (r.alive(renderer)) r.call(renderer, 'set_sprite', [r.item(replacement, i)]);
            });
            state[variant]++;
        } catch (e) { restore(item, hold); throw e; }
    }
    function cleanup() {
        for (const item of tracked.values()) {
            const p = r.a.gchandle_get_target(item.weak);
            if (r.alive(p)) restore(item, p);
        }
        for (const [key, item] of tracked) forget(key, item);
        pruneCursor = null; releaseArrays(); releaseAssets(); sprites.length = 0;
        files = null; calls.clear();
        listeners.splice(0).forEach(l => l.detach()); disposed = true; state.phase = 'disposed';
    }
    function pumpDispose() {
        if (!pendingDispose) return;
        const job = pendingDispose; pendingDispose = null; clearTimeout(job.timer);
        try { r.scope(cleanup); setImmediate(() => job.resolve({disposed:true})); }
        catch (e) { fail(e); setImmediate(() => job.reject(e)); }
    }
    function menuFrame() {
        pumpDispose(); if (stopped) return;
        try { r.scope(() => { warmStep(); if (state.phase === 'ready') pruneSome(); }); }
        catch (e) { fail(e); }
    }
    function executable(address) {
        const range = Process.findRangeByAddress(address);
        if (!range || !range.protection.includes('x')) throw Error('No executable code at ' + address);
        return address;
    }
    function verify(address, expected, label) {
        executable(address);
        const actual = Array.from(new Uint8Array(address.readByteArray(expected.length / 2)),
            b => b.toString(16).padStart(2, '0')).join('');
        if (actual !== expected) throw Object.assign(Error('Unsupported native hold code at ' + label +
            '; expected ' + P.game + ' with unmodified probe sites'), {fatal:true});
    }
    function hook(k, name, count, callbacks) {
        listeners.push(Interceptor.attach(executable(r.method(k, name, count).readPointer()), callbacks));
    }
    function callKey(invocation, inside) {
        return invocation.threadId + ':' + invocation.context.sp.add(inside ? P.stackFrameSize : 0);
    }
    function install() {
        r = createPhiHoldRuntime(); H = r.game('HoldControl'); J = r.game('JudgeLineControl'); N = r.game('ChartNote');
        if (r.offset(H, 'noteImages') !== 0x50 || r.offset(H, '_holdSpriteRenderer') !== 0xa0 ||
            r.offset(J, 'HoldHL0') !== 0x48) throw Object.assign(Error('Unsupported game layout: ' + P.game), {fatal:true});
        r.offset(H, 'judgeLine'); rendererFields.forEach(field => r.offset(H, field));
        r.offset(N, 'type'); r.offset(J, 'notesAbove'); r.offset(J, 'notesBelow');
        const address = r.method(J, 'CreateNote', 2).readPointer();
        // Verify all probes before attaching any. No cross-version guessing.
        verify(address, P.entrySignature, 'CreateNote entry');
        P.sites.forEach(site => verify(address.add(site.offset), site.signature, site.name));
        try {
            listeners.push(Interceptor.attach(address, {
                onEnter(args) {
                    if (stopped || state.phase !== 'ready') return;
                    const key = callKey(this, false);
                    calls.delete(key); // Also discard a prior call that threw before its binding block.
                    try {
                        const line = args[0], above = args[2].toInt32() !== 0;
                        const list = r.field(line, J, above ? 'notesAbove' : 'notesBelow');
                        const note = r.listItemRaw(list, args[1].toInt32());
                        if (note.add(r.offset(N, 'type')).readS32() !== 3) return;
                        if (calls.size >= 256) throw Error('Too many incomplete native hold calls');
                        calls.set(key, {line, variant:'single', hold:null});
                    } catch (e) { state.skipped++; fail(e); }
                }
            }));
            // Interior PCs require instruction probes, not function-entry callbacks.
            for (const site of P.sites) listeners.push(Interceptor.attach(address.add(site.offset), function () {
                if (stopped) return;
                const key = callKey(this, true), frame = calls.get(key);
                try {
                    if (state.phase !== 'ready') {
                        if (site.kind === 'assign') {
                            state.skipped++;
                            if (!warnedUnready) { warnedUnready = true; log('Skin not ready; return to song selection/Settings and wait for Ready'); }
                        }
                        return;
                    }
                    if (!frame || !this.context.x19.equals(frame.line)) throw Error('Native hold context mismatch: ' + site.name);
                    const hold = this.context[site.holdRegister];
                    if (site.kind === 'multi') { frame.variant = 'multi'; frame.hold = hold; return; }
                    if (frame.hold && !frame.hold.equals(hold)) throw Error('Native hold identity changed before binding');
                    r.scope(() => {
                        if (hold.isNull() || !r.a.object_get_class(hold).equals(H) ||
                            !r.field(hold, H, 'judgeLine').equals(frame.line)) throw Error('Unexpected native HoldControl at ' + site.name);
                        apply(hold, frame.variant);
                    });
                } catch (e) { state.skipped++; fail(e); }
                finally { if (site.kind === 'assign') calls.delete(key); }
            }));
            for (const name of ['SongSelector','Setting']) hook(r.game(name), 'Update', 0, {onLeave:menuFrame});
            hook(r.game('LevelInformation'), 'Update', 0, {onLeave:pumpDispose});
            state.phase = 'waiting-menu';
            log('Installed native hold probes; preparing one image per song-selection/Settings frame');
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
        catch (e) { fail(e); if (e.fatal || Date.now() > deadline) { state.phase = 'failed'; clearInterval(timer); } }
    }, 250);
    return {
        status() { return {...state, directory:options.directory, files:{...options.files}, tracked:tracked.size,
            assets:assets.length, spriteSets:arrays.length, activeCalls:calls.size, images:{...textures}}; },
        retry() {
            if (stopped || !r || !listeners.length) throw Error('Hook not active');
            if (state.phase === 'failed') { files = null; state.phase = 'waiting-menu'; }
            state.error = null;
            return 'Will prepare in song selection/Settings; wait for Ready before starting a chart';
        },
        dispose() {
            if (disposed) return Promise.resolve({disposed:true});
            if (pendingDispose) return pendingDispose.promise;
            stopped = true; clearInterval(timer);
            if (!assets.length && !tracked.size && !arrays.length) {
                listeners.splice(0).forEach(l => l.detach()); files = null; calls.clear();
                disposed = true; state.phase = 'disposed'; return Promise.resolve({disposed:true});
            }
            const job = {};
            job.promise = new Promise((resolve,reject) => { job.resolve = resolve; job.reject = reject; });
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
