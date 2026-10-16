"""Frida16 integration test with a simulated DG-LAB App. Never drives Bluetooth."""
import asyncio
import json
import subprocess
import sys
import time
from pathlib import Path
import frida
from websockets.asyncio.client import connect

assert frida.__version__.startswith('16.'), 'Use F:\\frida16\\Scripts\\python.exe'
sys.stdout.reconfigure(encoding='utf-8')
ROOT = Path(__file__).resolve().parents[3]
OUT = ROOT / 'network/dglab/evidence'
OUT.mkdir(exist_ok=True)
device = frida.get_device_manager().add_remote_device('127.0.0.1:27042')
session = device.attach('Gadget')
messages = []
source = (ROOT / 'hooks/judgment/phigros_dglab.js').read_text(encoding='utf-8')
# Development-only input; not present in the production bundle/API.
source += '''\nrpc.exports.testjudgment = function (grade) {
    Phi.enqueue({grade:grade,seq:999,timestamp:Date.now(),session:'integration-test'});
};'''
source += '\n' + (ROOT / 'hooks/judgment/tests/settings_driver.js').read_text(encoding='utf-8')
script = session.create_script(source)
script.on('message', lambda m, d: (messages.append(m), print(json.dumps(m, ensure_ascii=False), flush=True)))
script.load()
api = script.exports_sync

async def rpc(name, *args):
    return await asyncio.to_thread(getattr(api, name), *args)

async def until(predicate, seconds=5):
    end = time.monotonic() + seconds
    while time.monotonic() < end:
        result = await predicate()
        if result:
            return result
        await asyncio.sleep(.05)
    raise AssertionError('condition timed out')

async def main():
    async def ready():
        s = await rpc('status')
        return s if s['ready'] and s['connection'] == 'LISTENING' else None
    status = await until(ready)
    original = status['config']
    try:
        target = status['embedded']['targetId']
        url = 'ws://127.0.0.1:9998/?tid=' + target
        async with connect(url) as ws:
            hello = json.loads(await ws.recv())
            attached = json.loads(await ws.recv())
            assert hello['type'] == 'hello'
            assert attached == {'type': 'controller_attached', 'clientId': target}
            requests = []
            devices = [{'slotId': 'probe-slot', 'name': 'SIMULATED COYOTE', 'type': 'COYOTE_030',
                        'slotState': {'hasDevice': True}, 'props': {'power': 80}}]
            async def consume():
                async for text in ws:
                    frame = json.loads(text)
                    if frame['type'] != 'message':
                        continue
                    data = frame['data']; requests.append(data)
                    if data['m'] == 'devices.get': result = {'devices': devices}
                    elif data['m'] == 'device.op.clear': result = {'cleared': True}
                    else: result = {'type': data['data']['t'], 'reason': 'completed', 'slotId': 'probe-slot', 'channel': 0}
                    await ws.send(json.dumps({'type': 'message', 'data': {'t': 'resp', 'reqId': data['reqId'], 'result': result}}))
            consumer = asyncio.create_task(consume())
            async def discovered():
                return await rpc('dglabdevices')
            assert (await until(discovered))[0]['slotId'] == 'probe-slot'
            async with connect(url) as duplicate:
                assert json.loads(await duplicate.recv())['type'] == 'hello'
                assert json.loads(await duplicate.recv())['code'] == 'controller_busy'
            await ws.send(json.dumps({'type': 'message', 'data': {'t': 'ev', 'ev': 'slots.patch',
                'slots': [{'slotId': 'probe-slot', 'props': {'power': 79}}]}}))
            async def patched():
                d = await rpc('dglabdevices')
                return d if d and d[0]['props']['power'] == 79 else None
            await until(patched)
            await rpc('configure', {'rules': {g: {'intensity': 5, 'durationMs': 700, 'waveform': 'BUBBLE'} for g in ['good', 'bad', 'miss']}})
            for grade in ['good', 'bad', 'miss']:
                await rpc('testjudgment', grade)
                await asyncio.sleep(.25)
            async def triggered():
                s = await rpc('dglabstatus')
                return s if s['stats']['triggered'] == 3 and s['pending'] == 0 else None
            result = await until(triggered)
            ops = [r for r in requests if r['m'] == 'device.op']
            assert len(ops) == 6, ops
            assert [r['data']['t'] for r in ops] == [4, 0, 4, 0, 4, 0]
            assert all(r['data']['d'] == 700 and r['data']['im'] for r in ops)
            assert all(len(r['data']['v']) == 7 for r in ops if r['data']['t'] == 0)
            await rpc('dglabclear', 'probe-slot', 0)
            await rpc('configure', original)  # zero output baseline before any real app can pair
            await rpc('settings')
            await asyncio.sleep(.5)
            # Real widgets: bad input cannot commit; waveform/channel buttons and Save persist.
            await rpc('panel', {'set': {'dglab.good.durationMs': '0'}, 'click': 'save'})
            assert (await rpc('status'))['config'] == original
            await rpc('panel', {'set': {'dglab.good.durationMs': '900', 'dglab.good.intensity': '6'}, 'click': 'dglab.good.waveform'})
            await rpc('panel', {'click': 'dglab.channel'})
            await rpc('panel', {'click': 'save'})
            saved = (await rpc('status'))['config']
            assert saved['rules']['good'] == {'intensity': 6, 'durationMs': 900, 'waveform': 'RHYTHM'}
            assert saved['channel'] == 1
            await rpc('configure', original)
            await rpc('settings')
            await asyncio.sleep(1)
            subprocess.run(['adb', 'shell', 'screencap', '-p', '/sdcard/dglab-panel.png'], check=True)
            subprocess.run(['adb', 'pull', '/sdcard/dglab-panel.png', str(OUT / 'settings-panel.png')], check=True)
            result['requestsCaptured'] = requests
            result['pairingQR'] = await rpc('dglabpairing')
            await rpc('panel', {'click': 'dglab.stop'})
            async def stopped():
                return (await rpc('status'))['connection'] == 'STOPPED'
            await until(stopped)
            assert not (await rpc('status'))['config']['enabled']
            await rpc('panel', {'set': {'enabled': True}, 'click': 'save'})
            await until(ready)
            await ws.close()
            try:
                await consumer
            except Exception as e:
                assert '4000' in str(e), str(e)
        async def disconnected():
            s = await rpc('dglabstatus')
            return s['clientId'] is None and not s['devices']
        await until(disconnected)
        # Reconnect receives a new connection ID and no old command replay.
        async with connect(url) as reconnect:
            newhello = json.loads(await reconnect.recv())
            assert newhello['clientId'] != hello['clientId']
            assert json.loads(await reconnect.recv())['type'] == 'controller_attached'
            assert json.loads(await reconnect.recv())['data']['m'] == 'devices.get'
        await rpc('dispose')  # also exercise teardown with the dialog open
        assert not [m for m in messages if m['type'] == 'error'], messages
        result['frida'] = frida.__version__
        result['checks'] = ['handshake', 'one-peer-limit', 'discovery', 'slot-patch', 'three-grades-six-tasks',
                            'clear', 'QR-panel', 'real-widget-validation-save', 'stop-restart',
                            'reconnect-no-replay', 'open-dialog-dispose']
        (OUT / 'device-probe.json').write_text(json.dumps(result, ensure_ascii=False, indent=2), encoding='utf-8')
        print('DEVICE PROBE PASS', flush=True)
    finally:
        if (await rpc('status'))['config'] != original:
            await rpc('configure', original)

try:
    asyncio.run(main())
finally:
    api.dispose()
    session.detach()
