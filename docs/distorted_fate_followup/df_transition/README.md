# Distorted Fate transition evidence

See [FINDINGS.md](FINDINGS.md) for the verified 4.0.1 control flow and addresses. The key distinction is that the story ending saves its result and returns through QDEvent without a score threshold, whereas MainStory9's separate selector condition requires any saved Distorted Fate chart score to reach 880000.

- `chapter9_config.json`: parsed MainStory9 configuration from the APK's actual serialized GameInformation object.
- `parse_chapter9.py`: reproducible parser for that chapter object.
- `exports_full_raw.json`: preserved IDA MCP responses.
- `*.c`: resolved pseudocode exports; ChapterSelector.Start is exported in selected pages.
- `resolve_exports.py`: string-literal resolution script used for the exports.

No binary or IDB modifications were made. These findings are from static analysis, without a device playthrough.
