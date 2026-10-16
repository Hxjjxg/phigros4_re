"""Persistent Frida 16 host; command/status files under work/dglab. No PC WS server."""
import json
import sys
import time
from pathlib import Path
import frida

assert frida.__version__.startswith('16.'), 'Use F:\\frida16\\Scripts\\python.exe'
sys.stdout.reconfigure(encoding='utf-8')
ROOT = Path(__file__).resolve().parents[2]
OUT = ROOT / 'work/dglab'
OUT.mkdir(parents=True, exist_ok=True)
session = frida.get_device_manager().add_remote_device('127.0.0.1:27042').attach('Gadget')
session.on('detached', lambda reason, crash: print(json.dumps({'event': 'detached', 'time': time.time(), 'reason': reason, 'crash': str(crash)}, ensure_ascii=False), flush=True))
script = session.create_script((ROOT / 'hooks/judgment/phigros_dglab.js').read_text(encoding='utf-8'))
script.on('destroyed', lambda: print(json.dumps({'event': 'script-destroyed', 'time': time.time()}), flush=True))
script.on('message', lambda m, d: print(json.dumps(m, ensure_ascii=False), flush=True))
script.load()
api = script.exports_sync
while not session.is_detached:
    command = OUT / 'command.json'
    if command.exists():
        try:
            request = json.loads(command.read_text(encoding='utf-8-sig'))
            command.unlink()
            operation = request['operation']
            if operation == 'quit':
                api.dispose()  # failure keeps the host alive for a retry
                session.detach()
                break
            if operation == 'settings': api.settings()
            elif operation == 'close': api.closesettings()
            elif operation == 'configure': api.configure(request['config'])
            elif operation == 'stop': api.dglabstop()
            elif operation == 'refresh': api.dglabrefresh()
        except Exception as error:
            print('COMMAND ERROR:', error, flush=True)
    status = api.status()
    status['hostTimestamp'] = time.time()
    tmp = OUT / 'status.tmp'
    tmp.write_text(json.dumps(status, ensure_ascii=False, indent=2), encoding='utf-8')
    tmp.replace(OUT / 'status.json')
    time.sleep(.5)
print('HOST STOPPED', flush=True)
