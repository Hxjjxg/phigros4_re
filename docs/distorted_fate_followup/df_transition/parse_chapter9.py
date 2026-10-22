import json
import pathlib
import struct

out = pathlib.Path(__file__).parent
raw = (out.parents[1] / 'distorted_fate_analysis' / 'level0_50.bin').read_bytes()
cursor = 10940

def integer():
    global cursor
    value = struct.unpack_from('<i', raw, cursor)[0]
    cursor += 4
    return value

def string():
    global cursor
    length = integer()
    assert 0 <= length < 10000
    value = raw[cursor:cursor + length].decode('utf-8')
    cursor = (cursor + length + 3) & ~3
    return value

def strings():
    return [string() for _ in range(integer())]

chapter = {'chapterCode': string()}
chapter['songInfo'] = {key: string() for key in ('title', 'subTitle', 'banner')}
chapter['songInfo']['songs'] = [
    dict(songsId=string(), unlockType=integer(), unlockInfo=strings(), secretType=integer(), secretInfo=strings())
    for _ in range(integer())
]
chapter['unlockInfo'] = dict(unlockMode=integer(), **{key: string() for key in ('unlockFlag', 'unlockTip', 'fakeCoverFlag', 'fakeCoverCode', 'fakeCoverBlurCode')})
assert chapter['chapterCode'] == 'MainStory9'
text = json.dumps(chapter, ensure_ascii=False, indent=2)
(out / 'chapter9_config.json').write_text(text, encoding='utf-8')
print(text)
print('End offset:', cursor)
