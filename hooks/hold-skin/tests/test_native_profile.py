"""Static instruction verification; never attaches to the game or a device."""
import copy
import json
from pathlib import Path
import runpy
import unittest

ROOT = Path(__file__).resolve().parents[1]
BINARY = ROOT.parents[1] / 'work' / 'il2cpp' / 'libil2cpp_arm64.so'
VERIFY = runpy.run_path(str(ROOT / 'build.py'))['verify_native_profile']


@unittest.skipUnless(BINARY.exists(), 'Original local IL2CPP binary is unavailable')
class NativeProfileTests(unittest.TestCase):
    def test_all_five_windows_match_the_original_binary(self):
        profile = json.loads((ROOT / 'native-profile.json').read_text(encoding='utf-8'))
        VERIFY(BINARY, profile)

    def test_modified_probe_signature_is_rejected(self):
        profile = copy.deepcopy(json.loads((ROOT / 'native-profile.json').read_text(encoding='utf-8')))
        profile['sites'][0]['signature'] = '00' * 16
        with self.assertRaisesRegex(ValueError, 'fingerprint mismatch: multi_above'):
            VERIFY(BINARY, profile)


if __name__ == '__main__':
    unittest.main()
