/* Phigros 4.0.1 (157), Android arm64, Frida 16.
 * Restore AllSong / Challenge Mode access without writing completion state.
 * Reload ChapterSelector after injection. No frida-il2cpp-bridge required.
 */
'use strict';

(function () {
    const tag = '[phi-challenge] ';
    const loadStringRva = 0x1cc42c8;
    const gates = [
        // BL SaveManagement.LoadString; return addresses are call RVA + 4.
        {call: 0x1d53908, bytes: '70 c2 fd 97 c1 02 40 f9 e2 03 1f aa f2 80 56 94'},
        {call: 0x1d576f0, bytes: 'f6 b2 fd 97 81 02 40 f9 e2 03 1f aa 78 71 56 94'}
    ];
    const state = {installed: false, enabled: true, replacements: 0, error: null};
    let listener = null, timer = null, disposed = false;
    let markerHandle = 0, getTarget = null, freeHandle = null;
    const deadline = Date.now() + 120000;

    function checkBytes(address, expected) {
        const want = expected.split(' ').map(x => parseInt(x, 16));
        const got = new Uint8Array(address.readByteArray(want.length));
        if (got.some((value, i) => value !== want[i])) {
            throw Error('Unsupported/modified binary at ' + address + '; expected Phigros 4.0.1 arm64');
        }
    }

    function readString(value) {
        if (value.isNull()) return null;
        // IL2CPP System.String: object header 16 bytes, length i32, UTF-16 data.
        const length = value.add(16).readS32();
        if (length < 0 || length > 64) return null;
        return length === 0 ? '' : value.add(20).readUtf16String(length);
    }

    function install(lib) {
        if (Process.arch !== 'arm64' || Process.pointerSize !== 8) {
            throw Error('Only Android arm64 is supported');
        }
        checkBytes(lib.base.add(loadStringRva), 'fe 0f 1d f8 f6 57 01 a9 f4 4f 02 a9 54 3d 01 d0');
        gates.forEach(gate => checkBytes(lib.base.add(gate.call), gate.bytes));
        const callers = new Set(gates.map(gate => lib.base.add(gate.call + 4).toString()));
        const newString = new NativeFunction(lib.getExportByName('il2cpp_string_new'), 'pointer', ['pointer']);
        const newHandle = new NativeFunction(lib.getExportByName('il2cpp_gchandle_new'), 'uint', ['pointer', 'bool']);
        getTarget = new NativeFunction(lib.getExportByName('il2cpp_gchandle_get_target'), 'pointer', ['uint']);
        freeHandle = new NativeFunction(lib.getExportByName('il2cpp_gchandle_free'), 'void', ['uint']);
        const markerUtf8 = Memory.allocUtf8String('challenge-mode-access');

        listener = Interceptor.attach(lib.base.add(loadStringRva), {
            onEnter(args) {
                this.isGate = false;
                if (!state.enabled || !callers.has(this.returnAddress.toString())) return;
                try { this.isGate = readString(args[0]) === 'GameCompleted'; }
                catch (error) { state.error = String(error); }
            },
            onLeave(retval) {
                if (!this.isGate || !state.enabled) return;
                try {
                    // Preserve real completed saves; only override the two UI reads.
                    if (readString(retval) !== '') return;
                    // Allocate on the game's calling thread, after IL2CPP is ready.
                    if (!markerHandle) {
                        const marker = newString(markerUtf8);
                        if (marker.isNull()) throw Error('il2cpp_string_new returned null');
                        markerHandle = newHandle(marker, 1);
                        if (!markerHandle) throw Error('Could not retain access marker');
                    }
                    retval.replace(getTarget(markerHandle));
                    state.replacements++;
                } catch (error) {
                    state.error = String(error);
                    console.error(tag + error);
                }
            }
        });
        state.installed = true;
        console.log(tag + 'Ready. Re-enter chapter selection, then All Songs -> Challenge Mode.');
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
            state.error = String(error);
            console.error(tag + error);
        }
    }

    globalThis.PhiChallengeMode = {
        status() { return Object.assign({}, state); },
        enable(value) {
            if (disposed) throw Error('Script disposed; reload it to enable');
            if (typeof value !== 'boolean') throw Error('Use enable(true/false)');
            state.enabled = value;
            return this.status();
        },
        dispose() {
            disposed = true;
            state.enabled = false;
            if (timer !== null) { clearTimeout(timer); timer = null; }
            if (listener) { listener.detach(); listener = null; }
            if (markerHandle) { freeHandle(markerHandle); markerHandle = 0; }
            state.installed = false;
            return this.status();
        }
    };
    rpc.exports = Object.assign({}, rpc.exports, {
        challengestatus: () => PhiChallengeMode.status(),
        challengedispose: () => PhiChallengeMode.dispose()
    });
    start();
})();
