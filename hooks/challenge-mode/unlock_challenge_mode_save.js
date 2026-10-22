/* Phigros 4.0.1 (157), Android arm64, Frida 16.
 * Persist Challenge Mode access using the game's encrypted save wrapper.
 * Unload the runtime-only hook before loading this script.
 */
'use strict';

(function () {
    const tag = '[phi-challenge-save] ';
    const backupKey = 'PhiChallengeMode.Backup.v1';
    const completedValue = '4.0';
    const rva = {load: 0x1cc42c8, save: 0x1cc33dc, flush: 0x3c53f20};
    const signatures = [
        [rva.load, 'fe 0f 1d f8 f6 57 01 a9 f4 4f 02 a9 54 3d 01 d0'],
        [rva.save, 'fe 0f 1d f8 f6 57 01 a9 f4 4f 02 a9 55 3d 01 f0'],
        [rva.flush, 'fe 4f bf a9 53 41 00 90 60 da 44 f9 a0 00 00 b5'],
        [0x1d53908, '70 c2 fd 97 c1 02 40 f9 e2 03 1f aa f2 80 56 94'],
        [0x1d576f0, 'f6 b2 fd 97 81 02 40 f9 e2 03 1f aa 78 71 56 94']
    ];
    const state = {installed: false, pending: 'unlock', lastResult: null, error: null};
    let listener = null, timer = null, disposed = false, busy = false;
    const deadline = Date.now() + 120000;

    function readString(value) {
        if (value.isNull()) throw Error('Unexpected null managed string');
        const length = value.add(16).readS32();
        if (length < 0 || length > 16384) throw Error('Invalid managed string length');
        return length ? value.add(20).readUtf16String(length) : '';
    }

    function parseBackup(text) {
        if (!text) return null;
        const data = JSON.parse(text);
        if (!data || data.version !== 1 || typeof data.original !== 'string' ||
            data.replacement !== completedValue) throw Error('Invalid backup; refusing to overwrite it');
        return data;
    }

    function install(lib) {
        if (Process.arch !== 'arm64' || Process.pointerSize !== 8) throw Error('Only Android arm64 is supported');
        for (const [offset, expected] of signatures) {
            const want = expected.split(' ').map(x => parseInt(x, 16));
            const got = new Uint8Array(lib.base.add(offset).readByteArray(want.length));
            if (got.some((v, i) => v !== want[i])) throw Error('Unsupported/modified binary at RVA 0x' + offset.toString(16));
        }
        const callers = new Set([0x1d5390c, 0x1d576f4].map(x => lib.base.add(x).toString()));
        const load = new NativeFunction(lib.base.add(rva.load), 'pointer', ['pointer', 'pointer', 'pointer']);
        const save = new NativeFunction(lib.base.add(rva.save), 'void', ['pointer', 'pointer', 'pointer']);
        const flush = new NativeFunction(lib.base.add(rva.flush), 'void', ['pointer']);
        const newString = new NativeFunction(lib.getExportByName('il2cpp_string_new'), 'pointer', ['pointer']);
        const newHandle = new NativeFunction(lib.getExportByName('il2cpp_gchandle_new'), 'uint', ['pointer', 'bool']);
        const target = new NativeFunction(lib.getExportByName('il2cpp_gchandle_get_target'), 'pointer', ['uint']);
        const free = new NativeFunction(lib.getExportByName('il2cpp_gchandle_free'), 'void', ['uint']);
        const nil = lib.base.sub(lib.base);

        // Called only inside the game's own chapter UI read, on its calling thread.
        function execute(mode) {
            const handles = [];
            const interned = new Map();
            function managed(text) {
                if (!interned.has(text)) {
                    const string = newString(Memory.allocUtf8String(text));
                    if (string.isNull()) throw Error('String allocation failed');
                    const handle = newHandle(string, 1); // Frida bool ABI expects 0/1.
                    if (!handle) throw Error('GC root allocation failed');
                    handles.push(handle);
                    interned.set(text, handle);
                }
                return target(interned.get(text));
            }
            const read = key => readString(load(managed(key), managed(''), nil));
            function write(key, value) {
                save(managed(key), managed(value), nil);
                flush(nil);
                if (read(key) !== value) throw Error('Read-back failed for ' + key);
            }
            try {
                const original = read('GameCompleted');
                const backup = parseBackup(read(backupKey));
                if (mode === 'restore') {
                    if (!backup) throw Error('No backup found for this save');
                    if (original !== backup.replacement && original !== backup.original) {
                        throw Error('GameCompleted changed since patch; restore refused');
                    }
                    write('GameCompleted', backup.original);
                    write(backupKey, '');
                    return {action: 'restored', previous: original, current: backup.original};
                }
                if (original !== '') return {action: 'unchanged', current: original};
                if (backup && backup.original !== original) throw Error('Existing backup belongs to another state');
                if (!backup) {
                    write(backupKey, JSON.stringify({version: 1, original, replacement: completedValue}));
                }
                write('GameCompleted', completedValue);
                return {action: 'unlocked', previous: original, current: completedValue};
            } finally {
                handles.forEach(handle => free(handle));
            }
        }

        listener = Interceptor.attach(lib.base.add(rva.load), {
            onEnter(args) {
                this.run = false;
                if (busy || !state.pending || !callers.has(this.returnAddress.toString())) return;
                try { this.run = readString(args[0]) === 'GameCompleted'; }
                catch (error) { state.error = String(error); }
            },
            onLeave() {
                if (!this.run || busy || !state.pending || disposed) return;
                const mode = state.pending;
                state.pending = null;
                state.error = null;
                busy = true;
                try {
                    state.lastResult = execute(mode);
                    console.log(tag + JSON.stringify(state.lastResult));
                    console.log(tag + 'Saved. Leave chapter selection and enter it again.');
                } catch (error) {
                    state.error = String(error);
                    console.error(tag + error);
                } finally { busy = false; }
            }
        });
        state.installed = true;
        console.log(tag + 'Ready. Enter chapter selection to write the save; wait for the Saved message.');
    }

    function start() {
        timer = null;
        if (disposed) return;
        try {
            const lib = Process.findModuleByName('libil2cpp.so');
            if (lib) { install(lib); return; }
            if (Date.now() >= deadline) throw Error('Timed out waiting for libil2cpp.so');
            timer = setTimeout(start, 250);
        } catch (error) {
            state.pending = null;
            state.error = String(error);
            console.error(tag + error);
        }
    }

    function queue(mode) {
        if (disposed || !state.installed) throw Error('Script is not installed');
        if (busy) throw Error('Save operation is in progress');
        state.pending = mode;
        state.lastResult = null;
        state.error = null;
        console.log(tag + mode + ' queued. Re-enter chapter selection.');
        return PhiChallengeSave.status();
    }
    globalThis.PhiChallengeSave = {
        status() { return Object.assign({}, state); },
        unlock() { return queue('unlock'); },
        restore() { return queue('restore'); },
        dispose() {
            disposed = true;
            state.pending = null;
            if (timer !== null) { clearTimeout(timer); timer = null; }
            if (listener) { listener.detach(); listener = null; }
            state.installed = false;
            return this.status();
        }
    };
    rpc.exports = Object.assign({}, rpc.exports, {
        challengesavestatus: () => PhiChallengeSave.status(),
        challengesaverestore: () => PhiChallengeSave.restore(),
        challengesaveunlock: () => PhiChallengeSave.unlock(),
        challengesavedispose: () => PhiChallengeSave.dispose()
    });
    start();
})();
