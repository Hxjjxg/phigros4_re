# Distorted Fate、Challenge Mode 与第九章研究索引

研究对象：Phigros 4.0.1（157），Android arm64。研究日期：2026-10-22。
通过 IDA MCP 分析本地 IL2CPP 数据库，并核对 APK 的 Unity 场景、按钮回调和资源配置。下列地址均为 RVA，分析时 IDA image base 为 0。

## 已确认的逻辑

| 现象 | 对应条件与证据 |
|---|---|
| 可以进入 Distorted Fate 剧情谱 | 第八章第二阶段的 Luminescence 特殊结尾接 Retribution HP 挑战，再进入 DFUnlock；[详细条件](README.md)。这不等于永久解锁歌曲。 |
| DF 剧情结束后没有片尾，也没有永久解锁 DF | `DFLevelEffect.Update`（`0x1CD0FB0`）保存成绩后进入 QDEvent；其结尾（`0x1CD5FE0`）回到 ChapterSelector，不写 `chapter8Passed`、`C8DistortedFateUnlocked` 或 `GameCompleted`。 |
| 第九章仍然可以开放 | MainStory9 使用 DF 成绩条件：任意难度的已保存成绩至少 880000，独立于 DF 永久解锁及通关标志。 |
| Challenge Mode 入口消失 | `RemoveAllSongControl.Awake`（`0x1D5766C`）在 `GameCompleted` 为空时销毁 AllSong 卡片；APK 场景证明挑战按钮就在该卡片子节点中。章节选择器同时按这个标志扣减卡片数量。 |
| 第九章流程需要 Challenge Mode | Mirage 隐藏挑战要求已进入第九章、已读 `poyidomejiu1`，并按同一难度选择 Evanescent → About The Universe；游戏补入 Exoplanetary Mirage。获得其解锁标志后才允许开启密码输入流程，继续 C9S6。 |
| 通关标志被移到第九章结尾 | Message → Deduction → AfterDeduction；新片尾协程（`0x1D3FFB0`）写 `GameCompleted = "4.0"` 和第九章标志，但不补旧第八章标志。 |

因此，在所分析版本的正常 UI 路线上，缺少已有通关标志的存档会遇到依赖环：挑战入口需要通关标志，第九章推进需要挑战模式，新的通关标志又在第九章结尾写入。旧片尾 `AboutUs.Back` 中仍存在第八章解锁代码，不能据此推断 DF 当前流程会调用它。

DF AT 的独立条件是 `chapter8Passed && DistortedFateINSGrade`；后者要求 DF IN 至少 920000。IN S 标志、DF 永久歌曲标志、第九章可见性、全局通关标志不能混为一谈。

## 报告与证据

- [修正后的综合结论与依赖链](FOLLOWUP.md)：优先阅读，明确更正最初对旧片尾可达性的误判。
- [剧情谱进入条件、Luminescence 时间窗与分数阈值、DF AT](README.md)。
- [DF 实际结尾与第九章成绩门槛](../distorted_fate_followup/df_transition/FINDINGS.md)。
- [Challenge Mode 的原生判断及场景按钮绑定](challenge_followup/README.md)。
- [第九章隐藏挑战与密码流程依赖](ch9_followup/README.md)。
- [新片尾的实际写入及旧片尾残留代码](credits_followup/README.md)。
- 各目录保留对应 `.c` 反编译证据、JSON 导出及少量 Unity 对象原始数据；`.c` 是分析导出，不是可编译的项目源码。

两个研究目录从被忽略的 `out/` 原样搬到 `docs/`，保留相邻布局及相对链接。资源解析脚本从仓库根目录运行；`extract_rules.py` 与 `df_transition/parse_chapter9.py` 可使用已归档的 `level0_50.bin` 重现配置解析。重新提取 APK 需要自行提供 `Phigros_4.0.1.apk` 和 UnityPy；重新解析字符串符号需要本地生成的 `il2cpp/stringliteral.json`。APK、SO 和 IDB 不包含在此次提交中。

## 解锁脚本与验证状态

[Challenge Mode 脚本和使用说明](../../hooks/challenge-mode/README.md)包含两个方案：

- `unlock_challenge_mode.js`：仅覆盖两个 UI 调用点的空 `GameCompleted` 读取结果，不写存档。
- `unlock_challenge_mode_save.js`：先在同一存档保存原值备份，再经游戏的 `SaveManagement.SaveString`（`0x1CC33DC`）写入 `GameCompleted = "4.0"`，调用 `PlayerPrefs.Save`（`0x3C53F20`）并读回核对。提供恢复原值的命令；此全局标志还影响旧谱及设置中的片尾入口。

用户实机反馈证明原入口脚本已通过安装检查，但执行时出现 `expected an integer`，不能把 Ready 日志当作入口已经成功解锁。已在本地真实 Frida 16 中复现原因：原生 `bool` 参数传 JS `true` 会报此错，传整数 `1` 成功。两个脚本均采用整数参数。

10 项模拟 Frida/IL2CPP 测试及 JavaScript 语法检查通过，覆盖入口作用域、已有状态、机器码校验、备份顺序、读回失败保护和重载后的恢复。修正版入口与存档版的 Android 实机解锁效果，以及第九章完整流程，尚未获得验证反馈。静态研究报告中的“未修改存档”描述其分析阶段；后续新增了可选的存档修改脚本，不能将两者混同。
