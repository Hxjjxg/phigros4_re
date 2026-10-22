"""Explicitly attach hold skin to a Frida 16 development listener with orderly cleanup."""
import argparse
import json
from pathlib import Path
import sys


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--host', default='127.0.0.1:27042')
    parser.add_argument('--process', default='Gadget')
    parser.add_argument('--directory', default='/local/data/tmp')
    parser.add_argument('--files', type=Path, help='UTF-8 JSON mapping six logical slots to PNG filenames')
    args = parser.parse_args()
    import frida
    if frida.__version__.split('.')[0] != '16':
        raise SystemExit('Use F:\\frida16\\Scripts\\python.exe (Frida 16).')
    if not sys.stdin.isatty():
        raise SystemExit('Run interactively so cleanup errors can be retried before unloading.')
    options = {'directory': args.directory}
    if args.files:
        options['files'] = json.loads(args.files.read_text(encoding='utf-8-sig'))
    source = 'globalThis.PHI_HOLD_SKIN_OPTIONS = ' + json.dumps(options) + ';\n'
    source += Path(__file__).with_name('phigros_hold_skin.js').read_text(encoding='utf-8')
    device = frida.get_device_manager().add_remote_device(args.host)
    process = int(args.process) if args.process.isdigit() else args.process
    session = device.attach(process)
    script = session.create_script(source)
    script.on('message', lambda msg, data: print(json.dumps(msg, ensure_ascii=False), flush=True))
    script.load()
    print('Commands: status | retry | quit. Stay in song selection/Settings until [hold-skin] Ready, then start a new chart.')
    while True:
        try:
            command = input('hold-skin> ').strip()
        except (EOFError, KeyboardInterrupt):
            command = 'quit'
        try:
            if command == 'status':
                print(json.dumps(script.exports_sync.holdstatus(), ensure_ascii=False, indent=2))
            elif command == 'retry':
                print(script.exports_sync.holdretry())
            elif command == 'quit':
                script.exports_sync.holddispose()
                script.unload()
                session.detach()
                break
        except Exception as error:
            print(str(error), flush=True)
            print('Script remains loaded. Keep gameplay, song selection, or Settings ticking and retry quit.')


if __name__ == '__main__':
    main()
