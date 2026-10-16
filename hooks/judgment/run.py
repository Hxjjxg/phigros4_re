"""Frida 16 runner. Commands: status, settings, close, configure {JSON}, quit."""
import argparse
import json
import sys
from pathlib import Path
import frida

sys.stdout.reconfigure(encoding='utf-8')
sys.stderr.reconfigure(encoding='utf-8')

if frida.__version__.split('.')[0] != '16':
    raise SystemExit('Use F:\\frida16\\Scripts\\python.exe; Frida 17 is not supported here.')

parser = argparse.ArgumentParser(description=__doc__)
parser.add_argument('--host', default='127.0.0.1:27042')
parser.add_argument('--process', default='Gadget')
parser.add_argument('--extra', type=Path, help='Append a development-only test script')
parser.add_argument('--embedded', action='store_true', help='Load phigros_dglab.js (push EmbeddedTransport.dex first)')
args = parser.parse_args()
device = frida.get_device_manager().add_remote_device(args.host)
session = device.attach(args.process)
source = Path(__file__).with_name('phigros_dglab.js' if args.embedded else 'phigros_judgment.js').read_text(encoding='utf-8')
if args.extra:
    source += '\n' + args.extra.read_text(encoding='utf-8')
script = session.create_script(source)
script.on('message', lambda message, data: print(json.dumps(message, ensure_ascii=False), flush=True))
script.load()
print('Commands: status | settings | close | configure {JSON} | quit', flush=True)
try:
    while True:
        line = input().strip()
        if line == 'quit':
            try:
                script.exports_sync.dispose()
            except Exception as error:
                print(f'Cleanup not finished: {error}. Return to Gameplay settings and retry quit.', flush=True)
                continue
            break
        if line == 'status':
            print(json.dumps(script.exports_sync.status()), flush=True)
        elif line == 'settings':
            script.exports_sync.settings()
        elif line == 'close':
            script.exports_sync.closesettings()
        elif line.startswith('configure '):
            print(script.exports_sync.configure(json.loads(line[10:])), flush=True)
        elif line == 'fixture' and args.extra:
            print(script.exports_sync.fixture(), flush=True)
except (EOFError, KeyboardInterrupt):
    pass
finally:
    # Never detach in a finally block after a failed cleanup operation.
    script.exports_sync.dispose()
    session.detach()
