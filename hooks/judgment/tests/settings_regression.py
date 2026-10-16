"""Run with F:\\frida16\\Scripts\\python.exe while Gameplay settings is open.

Exercises real Android widgets and orderly unload. Restores the initial config.
Requires adb; screenshots/report are written to work/judgment.
"""
import json
import subprocess
import sys
import time
from pathlib import Path
import frida

sys.stdout.reconfigure(encoding='utf-8')
assert frida.__version__.startswith('16.'), 'Frida 16 required'
ROOT = Path(__file__).resolve().parents[3]
OUT = ROOT / 'work/judgment'
OUT.mkdir(parents=True, exist_ok=True)
SOURCE = (ROOT / 'hooks/judgment/phigros_judgment.js').read_text(encoding='utf-8')
SOURCE += '\n' + Path(__file__).with_name('settings_driver.js').read_text(encoding='utf-8')
device = frida.get_device_manager().add_remote_device('127.0.0.1:27042')
results = []
errors = []

def adb(*args):
    return subprocess.check_output(['adb', *args]).decode('utf-8').strip()

def screenshot(name):
    time.sleep(.5)  # Let Android window/checkmark animations finish before visual QA.
    data = subprocess.check_output(['adb', 'exec-out', 'screencap', '-p'])
    (OUT / name).write_bytes(data)

def message(m, data):
    if m.get('type') == 'error':
        errors.append(m)
    print(json.dumps(m, ensure_ascii=False), flush=True)

def load():
    session = device.attach('Gadget')
    script = session.create_script(SOURCE)
    script.on('message', message)
    script.load()
    for _ in range(50):
        if script.exports_sync.status()['ready']:
            break
        time.sleep(.1)
    else:
        raise AssertionError('hooks not ready')
    time.sleep(.3)
    return session, script

def open_panel(script):
    script.exports_sync.settings()
    for _ in range(30):
        data = script.exports_sync.panel({})
        if 'judgment.save' in data['fields']:
            return data
        time.sleep(.1)
    raise AssertionError('panel did not open')

def passed(name):
    results.append(name)
    print('PASS:', name, flush=True)

initial_pid = adb('shell', 'pidof', 'com.PigeonGames.Phigros')
session, script = load()
baseline = script.exports_sync.status()['config']
(OUT / 'baseline-config.json').write_text(json.dumps(baseline), encoding='utf-8')
try:
    open_panel(script)
    screenshot('panel-default.png')
    passed('open panel and capture default QR')
    for invalid in ['http://127.0.0.1:8765', 'ws://', 'ws://127.0.0.1:0',
                    'ws://127.0.0.1:65536', 'ws://localhost:8765/#fragment']:
        script.exports_sync.panel({'set': {'url': invalid}, 'click': 'save'})
        assert script.exports_sync.status()['config'] == baseline
        texts = script.exports_sync.panel({})['texts']
        assert any(('valid port' in s or 'URISyntaxException' in s) for s in texts), texts
    passed('invalid URL does not change live or saved configuration')
    expected = dict(baseline, enabled=False, url='ws://127.0.0.1:18765/pair',
                    good=False, bad=True, miss=True, perfect=True,
                    qrText='phigros://pair?name=测试&token=local-test')
    script.exports_sync.panel({'set': expected, 'click': 'save'})
    assert script.exports_sync.status()['config'] == expected
    assert script.exports_sync.status()['connection'] == 'CLOSED'
    passed('real Save button applies URL, grade filters, disable, Unicode QR')
    screenshot('panel-custom-qr.png')
    script.exports_sync.panel({'click': 'close'})
    assert 'judgment.save' not in script.exports_sync.panel({})['fields']
    reopened = open_panel(script)
    assert expected['url'] in reopened['texts'] and expected['qrText'] in reopened['texts']
    passed('Close button and reopen retain saved values')
    for _ in range(3):
        open_panel(script)  # Replaces an already open dialog.
        script.exports_sync.panel({'click': 'close'})
        assert 'judgment.save' not in script.exports_sync.panel({})['fields']
    passed('replacement dialogs keep functional callbacks')
    open_panel(script)
    adb('shell', 'input', 'keyevent', '4')
    time.sleep(.2)
    assert 'judgment.save' not in script.exports_sync.panel({})['fields']
    passed('Android Back dismisses dialog')
    for cycle in range(3):
        open_panel(script)
        assert script.exports_sync.dispose() == {'disposed': True}
        assert script.exports_sync.dispose() == {'disposed': True}  # Idempotent cleanup.
        script.unload()
        session.detach()
        assert adb('shell', 'pidof', 'com.PigeonGames.Phigros') == initial_pid
        session, script = load()
        assert script.exports_sync.status()['config'] == expected
    passed('3 open-dialog dispose/unload/reinject cycles, same PID, saved config restored')
    # Restore through the real form and capture final default layout.
    open_panel(script)
    script.exports_sync.panel({'set': baseline, 'click': 'save'})
    assert script.exports_sync.status()['config'] == baseline
    screenshot('panel-final.png')
    assert not errors, errors
    passed('initial settings restored; no script error events')
finally:
    # Await cleanup before unloading, even on assertions.
    script.exports_sync.configure(baseline)
    script.exports_sync.dispose()
    script.unload()
    session.detach()
    (OUT / 'settings-regression.json').write_text(json.dumps({
        'frida': frida.__version__, 'pid': initial_pid, 'passed': results, 'errors': errors
    }, indent=2, ensure_ascii=False), encoding='utf-8')
