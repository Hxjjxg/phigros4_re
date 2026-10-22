import json
import pathlib
import re

out = pathlib.Path(__file__).parent
root = out.parents[2]
strings = {int(x['address'], 16): x['value'] for x in json.loads((root / 'il2cpp/stringliteral.json').read_text(encoding='utf-8'))}
fallback = {f'StringLiteral_{n + 1}': v for n, v in enumerate(strings.values())}
for item in json.loads((out / 'exports_full_raw.json').read_text(encoding='utf-8')):
    mapping = {r['name']: strings[int(r['addr'], 16)] for r in item.get('refs', []) if r['name'].startswith('StringLiteral_')}
    code = re.sub(r'\bStringLiteral_\d+\b', lambda m: json.dumps(mapping.get(m[0], fallback.get(m[0], m[0])), ensure_ascii=False), item['code'])
    (out / (item['export_key'] + '.c')).write_text(code, encoding='utf-8')
