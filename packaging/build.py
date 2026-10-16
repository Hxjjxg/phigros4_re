"""Build an autonomous arm64 APK with Frida Gadget 16.7.19; never installs it."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import re
import secrets
import shutil
import struct
import subprocess
import sys
import time
import zipfile
from verify import verify_and_publish

# APKs support classic ZIP offsets up to 4 GiB, but not ZIP64. Python's default
# conservative 2 GiB threshold would reject the original 3.3 GB game assets.
zipfile.ZIP64_LIMIT = (1 << 32) - 1

ROOT = Path(__file__).resolve().parents[1]
GADGET_SHA256 = '61be4bbeae285bb19164dc9eebee58669f6ad2ab4b4d64422ea0a779731b0898'


def run(*args):
    subprocess.run([str(a) for a in args], cwd=ROOT, check=True)


def old_signature(name):
    return name.upper().startswith('META-INF/') and (
        name.upper().endswith(('.SF', '.RSA', '.DSA', '.EC')) or
        name.upper() == 'META-INF/MANIFEST.MF')


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--apk', type=Path, default=ROOT / 'Phigros_4.0.1.apk')
    parser.add_argument('--gadget', type=Path, default=ROOT / 'tools/frida-gadget-16.7.19-android-arm64.so')
    parser.add_argument('--jdk', type=Path, default=Path(r'C:\Program Files\Java\jdk-17'))
    parser.add_argument('--sdk', type=Path, default=Path(os.environ['LOCALAPPDATA']) / 'Android/Sdk')
    args = parser.parse_args()
    apktool = ROOT / 'tools/apktool_3.0.3.jar'
    android = args.sdk / 'platforms/android-36/android.jar'
    buildtools = args.sdk / 'build-tools/36.1.0'
    java = args.jdk / 'bin/java.exe'
    javac = args.jdk / 'bin/javac.exe'
    keytool = args.jdk / 'bin/keytool.exe'
    for path in [args.apk, args.gadget, apktool, android, java, javac, keytool,
                 buildtools / 'lib/apksigner.jar', buildtools / 'lib/d8.jar', buildtools / 'zipalign.exe']:
        if not path.is_file(): raise FileNotFoundError(path)
    gadget = args.gadget.read_bytes()
    if (hashlib.sha256(gadget).hexdigest() != GADGET_SHA256 or gadget[:5] != b'\x7fELF\x02'
            or struct.unpack_from('<H', gadget, 18)[0] != 183 or b'16.7.19' not in gadget):
        raise ValueError('Expected the pinned Frida 16.7.19 arm64 Gadget; refusing another binary')
    build = ROOT / 'work/package' / time.strftime('build-%Y%m%d-%H%M%S')
    build.mkdir(parents=True)
    out = ROOT / 'out'
    out.mkdir(exist_ok=True)
    final = out / 'Phigros_4.0.1-DGLab-arm64.apk'
    if shutil.disk_usage(ROOT).free < args.apk.stat().st_size * 2 + 500_000_000:
        raise RuntimeError('Need space for unsigned and aligned APKs plus build intermediates')
    print('Extracting only manifest/resources table/dex for apktool...', flush=True)
    shell = build / 'shell.apk'
    with zipfile.ZipFile(args.apk) as src, zipfile.ZipFile(shell, 'w') as dst:
        names = src.namelist()
        if len(names) != len(set(names)): raise ValueError('Duplicate entries in input APK')
        dex_names = [n for n in names if re.fullmatch(r'classes(?:\d+)?\.dex', n)]
        for n in ['AndroidManifest.xml', 'resources.arsc', *dex_names]:
            dst.writestr(n, src.read(n))
    decoded = build / 'decoded'
    run(java, '-jar', apktool, 'd', '-r', shell, '-o', decoded)
    matches = list(decoded.glob('smali*/com/unity3d/player/UnityPlayerActivity.smali'))
    if len(matches) != 1: raise ValueError('Expected exactly one UnityPlayerActivity')
    activity = matches[0]
    text = activity.read_text(encoding='utf-8')
    pattern = r'(\.method protected onCreate\(Landroid/os/Bundle;\)V\n)(.*?)(\.end method)'
    match = re.search(pattern, text, flags=re.S)
    if not match or match[2].count('return-void') != 1:
        raise ValueError('Unexpected onCreate layout; inspect before patching')
    body = match[2].replace('    return-void',
        '    invoke-static {p0}, Lre/phigros/dglab/Bootstrap;->start(Landroid/content/Context;)V\n\n    return-void')
    text = text[:match.start()] + match[1] + body + match[3] + text[match.end():]
    activity.write_text(text, encoding='utf-8')
    rebuilt = build / 'shell-rebuilt.apk'
    run(java, '-jar', apktool, 'b', decoded, '-o', rebuilt)

    classes = build / 'classes'; classes.mkdir()
    dexdir = build / 'dex'; dexdir.mkdir()
    sources = [ROOT / 'packaging/android/Bootstrap.java', ROOT / 'network/dglab/android/EmbeddedTransport.java']
    sources += list((ROOT / 'network/ws/server/stubs/com').rglob('*.java'))
    run(javac, '-encoding', 'UTF-8', '-source', '8', '-target', '8', '-classpath', android,
        '-d', classes, *sources)
    # APK classes supply com.tds.*. Never package compile-time stubs.
    run(java, '-cp', buildtools / 'lib/d8.jar', 'com.android.tools.r8.D8',
        '--lib', android, '--min-api', '26', '--output', dexdir, *list((classes / 're').rglob('*.class')))
    run(sys.executable, ROOT / 'hooks/judgment/build.py', '--embedded')
    script = '\n'.join(p.read_text(encoding='utf-8') for p in [
        ROOT / 'packaging/standalone-prefix.js', ROOT / 'hooks/judgment/phigros_dglab.js',
        ROOT / 'packaging/standalone-suffix.js'])
    (build / 'standalone.js').write_text(script, encoding='utf-8')
    run('node', '--check', build / 'standalone.js')
    next_dex = max([int(re.search(r'(\d+)', n)[1]) if re.search(r'(\d+)', n) else 1 for n in dex_names]) + 1
    additions = {
        f'classes{next_dex}.dex': (dexdir / 'classes.dex').read_bytes(),
        'lib/arm64-v8a/libphi_gadget.so': gadget,
        'lib/arm64-v8a/libphi_gadget.config.so': json.dumps({
            'interaction': {'type': 'script', 'path': 'libphi_script.so', 'on_change': 'ignore'},
            'runtime': 'qjs', 'teardown': 'minimal'
        }, indent=2).encode(),
        'lib/arm64-v8a/libphi_script.so': script.encode(),
    }
    with zipfile.ZipFile(rebuilt) as z:
        additions.update({n: z.read(n) for n in dex_names})
    unsigned = build / 'unsigned.apk'
    print('Streaming original game resources into the release APK...', flush=True)
    with zipfile.ZipFile(args.apk) as src, zipfile.ZipFile(unsigned, 'w', allowZip64=False) as dst:
        for info in src.infolist():
            n = info.filename
            if n in additions or old_signature(n) or (n.startswith('lib/') and not n.startswith('lib/arm64-v8a/')):
                continue
            # Remove old alignment fields; zipalign recalculates them below.
            info.extra = b''
            with src.open(info) as source, dst.open(info, 'w') as target:
                shutil.copyfileobj(source, target, 1024 * 1024)
        for n, data in additions.items():
            dst.writestr(n, data, compress_type=zipfile.ZIP_STORED)
    aligned = build / 'aligned.apk'
    run(buildtools / 'zipalign.exe', '-P', '16', '4', unsigned, aligned)
    unsigned.unlink()  # Alignment succeeded; reserve room for signing's full-size copy.

    signing = ROOT / 'work/signing'; signing.mkdir(exist_ok=True)
    keystore = signing / 'phigros-dglab.p12'
    password = signing / 'password.txt'
    if not keystore.exists():
        if password.exists(): raise RuntimeError('Password exists without keystore; inspect signing directory')
        password.write_text(secrets.token_urlsafe(32), encoding='ascii')
        run(keytool, '-genkeypair', '-keystore', keystore, '-storetype', 'PKCS12',
            '-storepass:file', password, '-alias', 'phigros-dglab', '-keyalg', 'RSA',
            '-keysize', '3072', '-validity', '10000', '-dname', 'CN=Phigros DG-LAB Local Build')
    if not password.exists(): raise RuntimeError('Missing signing password file')
    signer = buildtools / 'lib/apksigner.jar'
    run(java, '-Djava.io.tmpdir=' + str(build), '-jar', signer, 'sign', '--ks', keystore, '--ks-key-alias', 'phigros-dglab',
        '--ks-pass', 'file:' + str(password), '--v1-signing-enabled', 'true',
        '--v2-signing-enabled', 'true', '--v3-signing-enabled', 'true', '--v4-signing-enabled', 'false', aligned)
    verify_and_publish(args.apk, aligned, final, build, args.gadget, java, buildtools)


if __name__ == '__main__':
    main()
