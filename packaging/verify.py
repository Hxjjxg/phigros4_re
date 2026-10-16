"""Verify and publish a signed build, also usable after resuming a failed signing step."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import re
import subprocess
import zipfile

ROOT = Path(__file__).resolve().parents[1]


def digest(path):
    with Path(path).open('rb') as f:
        return hashlib.file_digest(f, 'sha256').hexdigest()


def signature_entry(n):
    return n.upper().startswith('META-INF/') and (
        n.upper().endswith(('.SF', '.RSA', '.DSA', '.EC')) or n.upper() == 'META-INF/MANIFEST.MF')


def verify_and_publish(original, candidate, final, build, gadget, java, buildtools):
    result = subprocess.run([str(java), '-jar', str(buildtools / 'lib/apksigner.jar'), 'verify',
        '--verbose', '--print-certs', str(candidate)], check=True, capture_output=True, text=True)
    print(result.stdout, flush=True)
    subprocess.run([str(buildtools / 'zipalign.exe'), '-c', '-P', '16', '4', str(candidate)], check=True)
    source_script = '\n'.join(p.read_text(encoding='utf-8') for p in [
        ROOT / 'packaging/standalone-prefix.js', ROOT / 'hooks/judgment/phigros_dglab.js',
        ROOT / 'packaging/standalone-suffix.js'])
    if (build / 'standalone.js').read_text(encoding='utf-8') != source_script:
        raise ValueError('Build script differs from current generated sources')
    with zipfile.ZipFile(original) as src, zipfile.ZipFile(build / 'shell-rebuilt.apk') as shell:
        dex_names = [n for n in src.namelist() if re.fullmatch(r'classes(?:\d+)?\.dex', n)]
        next_dex = max([int(re.search(r'\d+', n)[0]) if re.search(r'\d+', n) else 1 for n in dex_names]) + 1
        additions = {n: shell.read(n) for n in dex_names}
        additions.update({
            f'classes{next_dex}.dex': (build / 'dex/classes.dex').read_bytes(),
            'lib/arm64-v8a/libphi_gadget.so': gadget.read_bytes(),
            'lib/arm64-v8a/libphi_script.so': source_script.encode(),
            'lib/arm64-v8a/libphi_gadget.config.so': json.dumps({
                'interaction': {'type': 'script', 'path': 'libphi_script.so', 'on_change': 'ignore'},
                'runtime': 'qjs', 'teardown': 'minimal'
            }, indent=2).encode(),
        })
        kept = {i.filename: (i.CRC, i.file_size) for i in src.infolist()
            if i.filename not in additions and not signature_entry(i.filename)
            and (not i.filename.startswith('lib/') or i.filename.startswith('lib/arm64-v8a/'))}
    with zipfile.ZipFile(candidate) as z:
        names = z.namelist()
        if len(names) != len(set(names)): raise ValueError('Duplicate release entries')
        expected_names = set(kept) | set(additions)
        if {n for n in names if not signature_entry(n)} != expected_names:
            raise ValueError('Unexpected or missing APK entries')
        for n, expected in kept.items():
            info = z.getinfo(n)
            if (info.CRC, info.file_size) != expected: raise ValueError('Original entry changed: ' + n)
        for n, expected in additions.items():
            if z.read(n) != expected: raise ValueError('Packaged payload mismatch: ' + n)
        print('Checking complete archive CRCs...', flush=True)
        bad = z.testzip()
        if bad: raise ValueError('Corrupt ZIP entry: ' + bad)
    report = {
        'apk': str(final), 'size': candidate.stat().st_size, 'sha256': digest(candidate),
        'inputSha256': digest(original), 'gadgetVersion': '16.7.19', 'gadgetSha256': digest(gadget),
        'apktool': '3.0.3', 'abi': 'arm64-v8a', 'mode': 'script',
        'unchangedEntriesChecked': len(kept), 'additionalDex': f'classes{next_dex}.dex',
        'signatureVerification': result.stdout, 'deviceTest': 'not performed',
        'keystore': str(ROOT / 'work/signing/phigros-dglab.p12'), 'buildDirectory': str(build)
    }
    final.parent.mkdir(exist_ok=True)
    if candidate.resolve() != final.resolve(): os.replace(candidate, final)
    final.with_suffix('.build.json').write_text(json.dumps(report, indent=2), encoding='utf-8')
    print(json.dumps(report, indent=2), flush=True)
    return report


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--build-dir', required=True, type=Path)
    args = parser.parse_args()
    verify_and_publish(ROOT / 'Phigros_4.0.1.apk', args.build_dir / 'aligned.apk',
        ROOT / 'out/Phigros_4.0.1-DGLab-arm64.apk', args.build_dir.resolve(),
        ROOT / 'tools/frida-gadget-16.7.19-android-arm64.so',
        Path(r'C:\Program Files\Java\jdk-17\bin\java.exe'),
        Path(os.environ['LOCALAPPDATA']) / 'Android/Sdk/build-tools/36.1.0')
