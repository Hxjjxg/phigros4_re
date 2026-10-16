# Phigros DG-LAB 独立 APK

基于原始 Phigros 4.0.1 APK、Frida Gadget **16.7.19 arm64** 和 apktool **3.0.3**。
运行时无需 PC、Python、Frida server、root、`adb forward` 或 `/data/local/tmp` 的 dex。
当前仅构建 arm64 包；不包含 32 位库。原始包名、Manifest、资源与 IL2CPP 库保持不变。

2026-10-17，首个独立包经用户实机使用后确认“正常工作”。构建指纹、自动检查结果及
尚未逐项验证的场景见 [VALIDATION.md](VALIDATION.md)。

## 安装与使用

1. 备份现有游戏存档后安装 `out/Phigros_4.0.1-DGLab-arm64.apk`。
   修改版使用本地签名，与官方包或其他签名的开发包不同，不能直接覆盖它们。
2. 打开游戏，进入 Gameplay 设置，点击 **DG-LAB link**。
3. 在同机 DG-LAB 4 App 登录并连接设备，在 **Socket Control → Enter server URL**
   输入 `ws://127.0.0.1:9998/?tid=<面板当前配对ID>`，然后 Confirm。
   面板二维码 / Copy pairing link 提供的是包含该地址的完整配对链接；手动输入入口使用其中的 WS URL。
4. 确认面板状态为 `PAIRED` 且显示设备。当前自动判定输出支持 **COYOTE_030（郊狼 3）**。
   只有一个兼容设备时 slot ID 可留空；有多个时填写目标 slot ID。
5. 设置通道、Good / Bad / Miss 各档强度、持续时间、波形并保存。强度 0 表示关闭该档输出；
   Perfect 只统计，不触发输出。实际输出受 DG-LAB App 的静音和强度限制约束。
6. 游玩时触发相应判定；停止联动使用面板 **STOP**，它清理任务并关闭服务。
   再次启用需要勾选 Enable 并保存，必要时在 DG-LAB 重新连接。

日常使用不需要 `host.py`、电脑端测试服务器或端口转发。初始强度为 0；已保存的设置随应用保留。

## 构建

```powershell
& 'F:\frida16\Scripts\python.exe' packaging\build.py
```

默认输入：

- `Phigros_4.0.1.apk`
- `tools/apktool_3.0.3.jar`（从用户提供的 Downloads 复制）
- `tools/frida-gadget-16.7.19-android-arm64.so`（固定 SHA-256 校验，拒绝其他版本）
- JDK 17：`C:\Program Files\Java\jdk-17`
- Android SDK：`%LOCALAPPDATA%\Android\Sdk`，platform 36、build-tools 36.1.0
- Node.js（构建时 JS 语法检查）

可用 `--apk`、`--gadget`、`--jdk`、`--sdk` 覆盖路径。
构建过程不安装 APK、不操作手机、不改写原始 APK。解包/重建仅用于 Java dex；数 GB 的
游戏资源从原始 APK 流式复制。输出前验证 ZIP CRC、原文件 CRC/大小、注入内容、签名与对齐。

产物：`out/Phigros_4.0.1-DGLab-arm64.apk`，构建报告为相邻 `.build.json`。
中间文件位于 `work/package/build-*`。签名密钥和密码分别在
`work/signing/phigros-dglab.p12`、`work/signing/password.txt`，均被 Git 忽略。
请单独备份这两个文件，后续版本复用同一密钥。不同签名不能直接覆盖安装；安装前备份存档。

后续版本在原始 APK 上重新运行构建脚本，复用上述密钥。仅修改源码不会更新已经安装的包，
需要重新构建并安装。游戏版本变化时，还需验证 IL2CPP 函数/字段和 Activity 启动补丁。

### 大包构建注意事项

- 原始 APK 约 3.3 GB，构建额外可用空间至少为输入包大小的两倍加 500 MB。
- Python ZIP 写入阈值调整为标准 ZIP 的 4 GiB 上限；禁用 ZIP64。超过标准 ZIP 能力的包会失败。
- 对齐完成后删除本次 `unsigned.apk`，签名临时文件通过 `java.io.tmpdir` 指定到本次构建目录，
  避免系统临时盘放不下整包副本。
- 打包过程保留编译期 `com.tds.*` 桩用于 javac，但附加 dex 仅包含 `re.phigros.dglab.*` 实现。
- 工具、APK、签名私钥、密码和中间产物不入 Git；脚本、测试、文档和非秘密的验收指纹入库。

## 自动启动

1. `UnityPlayerActivity.onCreate()` 正常创建 Unity 后调用 `Bootstrap.start(Context)`。
2. 后台线程调用 `System.loadLibrary("phi_gadget")`，防重复启动，不阻塞 UI 等待脚本初始化。
3. `libphi_gadget.config.so` 选择 `script` 模式，从同目录读取 `libphi_script.so`。
   后者是 UTF-8 JS，使用 Android 会提取的 `lib*.so` 文件名；不是 ELF。
   原 Manifest 的 `extractNativeLibs=true` 保证这些文件实际落盘。
4. 脚本等待 Application，然后使用 APK 附加 dex 的 `EmbeddedTransport`。
   判定 hook 等待 IL2CPP domain 和类就绪，最多重试 120 秒；失败写入状态。
5. 通过已有 Gameplay 设置入口打开 DG-LAB 面板。初始判定强度仍为 0，用户保存后生效。

同机 DG-LAB 连接 `ws://127.0.0.1:9998/?tid=<配对ID>`。
独立包将配对 ID 保存在 `phigros_dglab_v4` SharedPreferences，进程重启不会改变。
外部 Frida 注入模式保留原有每次新建 ID 的行为。清除应用数据会重置 ID 和所有设置。
Gadget 不开放 27042 调试端口，手机端 WS 只监听 loopback。

## 状态与验收

- logcat 标签：`PhiDGLab`；启动失败不会主动退出游戏。
- 应用私有文件：`files/dglab-status.json`，每秒原子更新，包含 `timestamp`、
  `mode=standalone`、初始化错误、连接状态、设备和判定计数。旧时间戳表示快照已经过时。
- 有 root 的设备可通过 `adb shell su -c 'cat /data/user/0/com.PigeonGames.Phigros/files/dglab-status.json'` 读取。
- 运行测试：`node --test network/dglab/tests/core.test.js hooks/judgment/tests/startup.test.js`。
- 独立 APK 已获用户“正常工作”的实机反馈；该反馈未逐项描述全部生命周期测试。
  后续回归应覆盖设置保存/重开、STOP/恢复、前后台、杀进程与手机重启后的自动加载及配对。
  Android 后台回收仍可能中断同机连接。

不要在独立包上运行 `device_probe.py` 或另起 `host.py` 注入同一功能。
需要调试时使用原有 Gadget listener 开发包；独立包的配置是自动执行脚本，不提供 attach 服务。
