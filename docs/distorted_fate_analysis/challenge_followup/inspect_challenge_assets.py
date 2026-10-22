import json
import pathlib
import zipfile
import UnityPy

out = pathlib.Path(__file__).parent
with zipfile.ZipFile('Phigros_4.0.1.apk') as archive:
    env = UnityPy.load(archive.read('assets/bin/Data/data.unity3d'))
    result = []
    for obj in env.objects:
        if obj.type.name != 'MonoBehaviour':
            continue
        try:
            data = obj.read()
            scriptname = data.m_Script.read().m_Name
        except Exception:
            continue
        raw = obj.get_raw_data()
        if b'ChallengeMode' in raw or b'GameCompleted' in raw:
            name = f'{obj.assets_file.name}_{obj.path_id}'
            (out / (name + '.bin')).write_bytes(raw)
            print('RAW_MATCH', name, scriptname, flush=True)
        if scriptname not in ('RemoveAllSongControl', 'ChapterInfo', 'ChapterSelector') and b'ChallengeMode' not in raw and b'GameCompleted' not in raw:
            continue
        rec = {'asset': obj.assets_file.name, 'path_id': obj.path_id, 'script': scriptname}
        try:
            tree = obj.read_typetree()
            rec['tree'] = tree
        except Exception as exc:
            rec['tree_error'] = str(exc)
        try:
            go = data.m_GameObject.read()
            rec['gameobject_name'] = go.m_Name
            rec['gameobject_id'] = data.m_GameObject.path_id
            if go.m_Name == 'AllSong':
                def children(transform):
                    tr = transform.read()
                    child_go = tr.m_GameObject.read()
                    row = {'name': child_go.m_Name, 'gameobject_id': tr.m_GameObject.path_id, 'components': []}
                    for child_c in child_go.m_Component:
                        component_obj = child_c.component
                        cr = component_obj.read()
                        component_row = {'path_id': component_obj.path_id, 'type': component_obj.type.name}
                        if hasattr(cr, 'm_Script'):
                            component_row['script'] = cr.m_Script.read().m_Name
                            raw_child = component_obj.get_raw_data()
                            component_row['raw_strings'] = __import__('re').findall(rb'[\x20-\x7e]{4,}', raw_child)
                            component_row['raw_strings'] = [s.decode('ascii') for s in component_row['raw_strings']]
                            if component_row['script'] == 'Button':
                                (out / f'{component_obj.assets_file.name}_{component_obj.path_id}.bin').write_bytes(raw_child)
                        row['components'].append(component_row)
                    row['children'] = [children(c) for c in tr.m_Children]
                    return row
                rec['hierarchy'] = children(go.m_Component[0].component)
            rec['components'] = []
            for component in go.m_Component:
                cr = component.component.read()
                row = {'path_id': component.component.path_id, 'type': component.component.type.name}
                if hasattr(cr, 'm_Script'):
                    row['script'] = cr.m_Script.read().m_Name
                    try:
                        row['tree'] = component.component.read_typetree()
                    except Exception as exc:
                        row['tree_error'] = str(exc)
                rec['components'].append(row)
        except Exception as exc:
            rec['go_error'] = str(exc)
        result.append(rec)
        print(rec['asset'], rec['path_id'], scriptname, rec.get('gameobject_name'), flush=True)
    (out / 'challenge_assets.json').write_text(json.dumps(result, ensure_ascii=False, indent=2), encoding='utf-8')
