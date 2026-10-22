import base64
import json
import pathlib
import struct
import zipfile
import UnityPy

out = pathlib.Path(__file__).parent
archive = zipfile.ZipFile('Phigros_4.0.1.apk')
catalog = json.loads(archive.read('assets/aa/catalog.json'))
bucket = base64.b64decode(catalog['m_BucketDataString'])
keydata = base64.b64decode(catalog['m_KeyDataString'])
entries = base64.b64decode(catalog['m_EntryDataString'])
keys, locations = [], []
cursor = 4
for _ in range(struct.unpack_from('<i', bucket)[0]):
    offset, count = struct.unpack_from('<ii', bucket, cursor)
    cursor += 8
    indices = struct.unpack_from('<' + 'i' * count, bucket, cursor)
    cursor += 4 * count
    kind = keydata[offset]
    if kind in (0, 1):
        length = struct.unpack_from('<i', keydata, offset + 1)[0]
        value = keydata[offset + 5:offset + 5 + length].decode('ascii' if kind == 0 else 'utf-16le')
    else:
        value = repr(keydata[offset:offset + 17])
    keys.append(value)
    locations.append(indices)

def entry(index):
    return struct.unpack_from('<7i', entries, 4 + index * 28)

targets = ['LevelMod/LuminescenceLevelEffect', 'LevelMod/RetributionEffectSecondPhase']
bundle_indices = set()
for index, key in enumerate(keys):
    if not any(target in key for target in targets):
        continue
    print(key, locations[index], flush=True)
    for item in locations[index]:
        values = entry(item)
        print('entry', values, catalog['m_InternalIds'][values[0]], 'dep', keys[values[2]] if values[2] >= 0 else None, flush=True)
        if values[2] >= 0:
            for dep in locations[values[2]]:
                depentry = entry(dep)
                bundle_indices.add(depentry[0])

for index in bundle_indices:
    internal = catalog['m_InternalIds'][index]
    if not internal.endswith('.bundle'):
        continue
    bundle = 'assets/aa/Android/' + internal.rsplit('/', 1)[-1]
    env = UnityPy.load(archive.read(bundle))
    print('BUNDLE', bundle, 'objects', len(env.objects), flush=True)
    for obj in env.objects:
        if obj.type.name not in ('MonoBehaviour', 'TextAsset'):
            continue
        scriptname = ''
        if obj.type.name == 'MonoBehaviour':
            try:
                scriptname = obj.read().m_Script.read().m_Name
            except Exception:
                pass
        raw = obj.get_raw_data()
        if scriptname not in ('LuminescenceLevelEffect', 'RetributionEffectSecondPhase') and not any(s in raw for s in (b'DistortedFate', b'RetributionEffectSecondPhase', b'C8Retribution', b'chapter8UnlockSecondPhase')):
            continue
        name = f'{obj.assets_file.name}_{obj.path_id}'
        (out / (name + '.bin')).write_bytes(raw)
        print('MATCH', name, scriptname, len(raw), flush=True)
        try:
            tree = obj.read_typetree()
            (out / (name + '.json')).write_text(json.dumps(tree, ensure_ascii=False, indent=2), encoding='utf-8')
            print('tree keys', list(tree)[:25], flush=True)
        except Exception as exc:
            print(type(exc).__name__, str(exc)[:150], flush=True)
