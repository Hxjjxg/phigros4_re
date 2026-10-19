"""Read-only search of decompressed Unity bundles inside an APK."""
import gc
import json
import sys
import zipfile
from pathlib import Path

import UnityPy


def scan_file(file, location, hits):
    if hasattr(file, "files") and not hasattr(file, "reader"):
        for name, child in file.files.items():
            scan_file(child, location + "/" + str(name), hits)
        return
    reader = getattr(file, "reader", file)
    if not hasattr(reader, "bytes"):
        return
    data = bytes(reader.bytes)
    for value in ("signature not match", "Login Failed", "Please check your connection"):
        for encoding in ("utf-8", "utf-16le"):
            offset = data.lower().find(value.lower().encode(encoding))
            if offset >= 0:
                hits.append(dict(location=location, value=value, encoding=encoding, offset=hex(offset)))


def main():
    hits, errors = [], []
    with zipfile.ZipFile(sys.argv[1]) as archive:
        names = [n for n in archive.namelist() if n.endswith(".bundle")]
        for index, name in enumerate(names, 1):
            try:
                env = UnityPy.load(archive.read(name))
                for file in env.files.values():
                    scan_file(file, name, hits)
                del env
            except Exception as exc:
                errors.append(dict(name=name, error=str(exc)))
            if index % 200 == 0:
                gc.collect()
                print(f"Scanned {index}/{len(names)} bundles", flush=True)
    result = dict(bundle_count=len(names), hits=hits, errors=errors)
    Path(sys.argv[2]).write_text(json.dumps(result, ensure_ascii=False, indent=2), encoding="utf-8")
    print(json.dumps(dict(bundle_count=len(names), hits=hits, error_count=len(errors)), ensure_ascii=True), flush=True)


if __name__ == "__main__":
    main()
