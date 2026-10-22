import json
import pathlib
import struct

out = pathlib.Path(__file__).parent
raw = (out / 'level0_50.bin').read_bytes()
cursor = 35904

def integer():
    global cursor
    value = struct.unpack_from('<i', raw, cursor)[0]
    cursor += 4
    return value

def floating():
    global cursor
    value = struct.unpack_from('<f', raw, cursor)[0]
    cursor += 4
    return value

def string():
    global cursor
    length = integer()
    value = raw[cursor:cursor + length].decode('utf-8')
    cursor = (cursor + length + 3) & ~3
    return value

song = {field: string() for field in ('songsId', 'songsKey', 'songsName', 'songsTitle')}
song['difficulty'] = [floating() for _ in range(integer())]
song['illustrator'] = string()
song['charter'] = [string() for _ in range(integer())]
song['composer'] = string()
song['levels'] = [string() for _ in range(integer())]
song['previewTime'] = floating()
song['previewEndTime'] = floating()
song['unlockInfo'] = []
for _ in range(integer()):
    song['unlockInfo'].append({'unlockType': integer(), 'unlockInfo': [string() for _ in range(integer())]})
assert song['songsId'] == 'DistortedFate.Sakuzyo.0'
assert len(song['unlockInfo']) == 4
(out / 'song_unlock_rules.json').write_text(json.dumps(song, ensure_ascii=False, indent=2), encoding='utf-8')
print(json.dumps(song, ensure_ascii=True, indent=2))
prefab = json.loads(next(out.glob('CAB-c3dd*.json')).read_text(encoding='utf-8'))
for difficulty in ('ez', 'hd', 'in'):
    threshold = struct.unpack('<f', struct.pack('<f', prefab[difficulty + 'TargetScore'] * struct.unpack('<f', struct.pack('<f', .92))[0]))[0]
    print(difficulty.upper(), 'score threshold at 130.434 seconds:', threshold)
