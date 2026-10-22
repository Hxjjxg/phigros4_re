"""Optional Windows x64/Frida 16 smoke test. Attaches only to its own child.

This validates entry/instruction probe correlation, not Android/Unity rendering.
Run with F:/frida16/Scripts/python.exe; no phone or listener is used.
"""
import json
import platform
import subprocess
import sys


SOURCE = r"""
const code = Memory.alloc(112);
Memory.protect(code, 112, 'rwx');
const bytes = Array(112).fill(0x90);
// Windows x64: push rbp; mov rbp,rsp; mov eax,ecx; test ecx,ecx; jz common.
[0x55,0x48,0x89,0xe5,0x89,0xc8,0x85,0xc9,0x74,54].forEach((b,i)=>bytes[i]=b);
bytes[96] = 0x5d; bytes[97] = 0xc3; // pop rbp; ret
code.writeByteArray(bytes);
const frames = new Map(), result = {single:0, multi:0, errors:[]};
const key = (call, inside) => call.threadId + ':' + call.context.rsp.add(inside ? 8 : 0);
const hooks = [Interceptor.attach(code, {onEnter(args) {
    frames.set(key(this, false), {argument:args[0].toInt32(), multi:false});
}})];
hooks.push(Interceptor.attach(code.add(32), function () {
    const frame = frames.get(key(this, true));
    if (!frame) { result.errors.push('multi frame missing'); return; }
    frame.multi = true;
}));
hooks.push(Interceptor.attach(code.add(64), function () {
    const id = key(this, true), frame = frames.get(id);
    if (!frame) { result.errors.push('binding frame missing'); return; }
    if (frame.multi !== (frame.argument !== 0)) result.errors.push('wrong native decision');
    result[frame.multi ? 'multi' : 'single']++;
    frames.delete(id);
}));
Interceptor.flush();
const invoke = new NativeFunction(code, 'int', ['int']);
rpc.exports.run = () => {
    for (let i=0; i<1000; i++) {
        const argument = i % 2;
        if (invoke(argument) !== argument) result.errors.push('return register corrupted');
    }
    return {...result, activeCalls:frames.size};
};
"""


def main():
    if sys.platform != 'win32' or platform.machine().lower() not in ('amd64', 'x86_64'):
        raise SystemExit('This isolated smoke fixture requires Windows x64.')
    import frida
    if frida.__version__.split('.')[0] != '16':
        raise SystemExit('Use the Frida 16 Python environment.')
    child = subprocess.Popen([sys.executable, '-c', 'import time; time.sleep(60)'])
    session = None
    try:
        session = frida.attach(child.pid)
        script = session.create_script(SOURCE)
        errors = []
        script.on('message', lambda message, data: errors.append(message))
        script.load()
        result = script.exports_sync.run()
        expected = {'single':500, 'multi':500, 'errors':[], 'activeCalls':0}
        if errors or result != expected:
            raise AssertionError(json.dumps({'messages':errors, 'result':result}))
        print(json.dumps({'frida':frida.__version__, **result}))
        script.unload()
    finally:
        try:
            if session:
                session.detach()
        finally:
            child.terminate()
            child.wait()


if __name__ == '__main__':
    main()
