import json
import pathlib
import zipfile
import UnityPy

out = pathlib.Path(__file__).parent
with zipfile.ZipFile('Phigros_4.0.1.apk') as archive:
    env = UnityPy.load(archive.read('assets/bin/Data/data.unity3d'))
    for obj in env.objects:
        if obj.type.name not in ('MonoBehaviour', 'TextAsset'):
            continue
        raw = obj.get_raw_data()
        scriptname = ''
        if obj.type.name == 'MonoBehaviour':
            try:
                scriptname = obj.read().m_Script.read().m_Name
            except Exception:
                pass
        if scriptname not in ('SongBase', 'GameInformation', 'SongSelectorInfo', 'Chapter8SelectMusicControl', 'Chapter8SecPhaseEffect') and not any(s in raw for s in (b'DistortedFate.Sakuzyo', b'RetributionEffectSecondPhase', b'DistortedFateINSGrade')):
            continue
        name = f'{obj.assets_file.name}_{obj.path_id}'
        print(name, obj.type.name, scriptname, len(raw), flush=True)
        (out / (name + '.bin')).write_bytes(raw)
        try:
            tree = obj.read_typetree()
            (out / (name + '.json')).write_text(json.dumps(tree, ensure_ascii=False, indent=2), encoding='utf-8')
            print('tree keys', list(tree)[:30], flush=True)
        except Exception as exc:
            print(type(exc).__name__, str(exc)[:160], flush=True)
