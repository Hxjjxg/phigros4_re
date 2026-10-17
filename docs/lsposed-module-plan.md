# LSPosed 模块迁移计划

## 目标

制作一个可安装、可封包的 Phigros LSPosed 模块，不修改或重签游戏 APK。迁移目标是把当前 Frida 脚本中的 Android Java 业务移入模块：设置界面、JSON 配置、DG-LAB 连接与状态管理由 Java 层负责；Frida 继续负责 IL2CPP/native 层的观察、Hook 和指令 patch。

目标功能按阶段覆盖判定观察与修改、解锁、皮肤和 Spirit。首个支持目标限定为 Phigros 4.0.1、arm64；其他游戏版本和 ABI 必须先单独验证，不能直接复用旧 RVA 或对象字段偏移。

Frida 的职责按游戏逻辑边界划分。它可以通过 IL2CPP 元数据查类和方法、读写 IL2CPP 对象、构造游戏对象，或 Hook native 指令；但不负责 Android 面板、JSON 编辑流程、DG-LAB 网络业务或模块生命周期。Java 与 Frida 之间保留窄接口，用于下发配置、查询状态和接收判定事件。

## 技术依据

- `hooks/judgment/src/` 已有基于 IL2CPP 导出 API 的判定解析与 Hook 实现；`network/dglab/` 已有 DG-LAB 协议核心、Android WebSocket transport 和设置面板。
- `F:\tool\phi_reversed` 是独立的 Frida/IL2CPP 研究仓库。`index.ts` 使用 `frida-il2cpp-bridge` 做方法级 Hook；`hook_hold_png.ts` 创建 Unity `Texture2D`/`Sprite` 并修改 IL2CPP 对象，同时通过内部 ARM64 分支的寄存器识别多押。
- 参考仓库还有 `Memory.patchCode`/`Arm64Writer` 指令改写，以及 code cave 的分析。函数入口 Hook 与内部基本块 patch 是不同能力：Dobby/ShadowHook 一类入口 Hook 不会自动覆盖内部指令改写。
- 参考仓库里的 RVA 和字段偏移有明确版本依赖。接入前应确认对应游戏构建、ABI、目标地址处的原始指令和预期指令，校验失败时停止安装 patch。

## 分层方案

### LSPosed Java 模块

- 只选择 `com.PigeonGames.Phigros` 目标进程，并在 `Application` 初始化后启动模块。
- 提供 Android 设置界面，管理功能开关、数值选项、JSON 配置、DG-LAB 连接与运行状态。
- 保存配置，并提供稳定的进程内 Java 接口给 Frida agent。
- 处理模块加载错误、目标版本不匹配和功能启停，不因 Hook 初始化失败而退出游戏。

### Frida native/IL2CPP agent

- 等待 `libil2cpp.so` 和 IL2CPP domain 就绪，再安装相应 Hook。
- 负责判定、解锁、贴图/Spirit 等游戏内逻辑，以及确实需要的 ARM64 指令 patch。
- Hook 回调尽量只采集或改写必要数据；UI、JSON 解析和网络操作移出 Hook 回调。
- 默认不启动对外 Frida listener。模块和 agent 在游戏进程内协作。

### Java/Frida 接口

- Java 向 agent 提供经过校验的运行配置和启停状态。
- Agent 向 Java 上报判定事件、初始化结果和错误信息。
- 网络重连、DG-LAB 配对、波形和设置持久化只在 Java 侧实现一份。
- 选择一种明确的进程内接口实现，并避免让多个 Hook 引擎同时处理同一个地址。

首阶段建议保留现有 Frida Gadget/脚本运行环境来复用 native Hook 与 patch 能力；本项目当前不要求先把 Frida agent 重写成 Dobby/Gum 原生 C++。是否进一步移除 Gadget 属于后续独立决策。

## 阶段与验收

### 1. 建立模块骨架

- 增加 LSPosed 模块元数据、入口和 Phigros 作用域。
- Hook `Application` 启动时机，验证模块只在目标包加载。
- 加入模块内状态日志和失败保护，不执行游戏修改。

验收：LSPosed 能识别并启用模块；启动、重启和停用后游戏正常；其他应用不触发模块逻辑。

### 2. 固化 Java/Frida 接口

- 记录配置 schema、默认值、版本字段、状态字段和事件格式。
- 定义 Java UI、配置存储、DG-LAB transport 与 Frida agent 的调用边界。
- 保证配置无效或 agent 未就绪时不安装高风险 patch。

验收：配置保存/读取一致；agent 重启后能取得配置；断开或禁用后不会继续派发旧事件。

### 3. 迁移 Java 业务

- 将现有 Android 设置面板及 JSON 配置迁入 LSPosed 模块 Java 层。
- 将 DG-LAB WebSocket server、配对状态、设备状态和输出配置迁入 Java 层。
- 从 Frida 脚本删除 UI、JSON 编辑和网络业务代码，仅保留必要的窄接口调用。

验收：游戏内能打开设置、保存并恢复配置；DG-LAB 配对、设备状态、输出和 STOP/重启流程工作；关闭模块后游戏回到未修改状态。

### 4. 分功能接入 native Hook

1. 先迁入只读判定 Hook，并与现有真实判定计数对照。
2. 再实现判定修改，每一种修改都单独开关并能安全卸载或在重启后恢复。
3. 逐项实现解锁、皮肤和 Spirit，确认实际资源链路、存档影响和调用时机后再启用。
4. 对内部 ARM64 patch 加原始机器码校验、幂等处理、失败回滚和目标版本检查。

验收：每个功能有独立验证记录；不匹配的版本拒绝应用 patch；禁用某项功能不会影响其他 Hook；无崩溃、重复事件或意外网络输出。

### 5. 封包与回归

- 生成独立的 LSPosed 模块 APK，不重建 Phigros APK。
- 校验 APK 签名、模块元数据、内置载荷和 arm64 ABI。
- 在目标设备上覆盖冷启动、前后台、重复启动、配置保存、模块停用、游戏更新不匹配等场景。
- 单独备份模块签名密钥，不能把密钥或密码提交到 Git。

## 另一台 PC 的准备

当前主仓库状态基线：分支 `feat/lsposed-java-host`，提交 `f0cbd44`（DEX 加载路径参数化）。该分支目前只在本地；要从另一台 PC 获取，先在本机推送：

```powershell
git push -u origin feat/lsposed-java-host
```

然后在另一台 PC：

```powershell
git fetch origin
git switch --track origin/feat/lsposed-java-host
```

需在新 PC 安装或准备：

- JDK 17、Android SDK Platform/Build Tools；如采用 NDK native 模块，再准备匹配的 Android NDK 和 CMake。
- 测试设备、ADB 和已启用 LSPosed 的 root 环境。
- 目标 Phigros 4.0.1 arm64 APK，用于版本核对和真机测试。
- 如果第一阶段沿用 Gadget，准备 Frida Gadget 16.7.19 arm64 文件，并校验 SHA-256：`61be4bbeae285bb19164dc9eebee58669f6ad2ab4b4d64422ea0a779731b0898`。
- 模块签名密钥的安全备份。目标 APK、Gadget 二进制、密钥和构建中间产物不应提交到 Git。

`Phigros_4.0.1.apk`、工具二进制、Gadget、`work/` 和签名资料被主仓库忽略，不会随分支推送。要构建或真机测试时需在新 PC 单独准备。

`F:\tool\phi_reversed` 是另一个 Git 仓库，当前有未提交的 `scripts/judge_result_logger/judge_result_hook.js` 修改和 `run_frida.py` 新文件。它们不会包含在主仓库分支中；若新 PC 需要这些工作内容，应先单独提交/推送该仓库，或明确复制所需文件。

## 当前进度

- [x] 创建 `feat/lsposed-java-host` 分支。
- [x] 提交 `f0cbd44 Make embedded transport dex path configurable`。
- [ ] 编写并验证 LSPosed 模块骨架。
- [ ] 定义 Java/Frida 配置与事件接口。
- [ ] 迁移 Java 设置和 DG-LAB 业务。
- [ ] 按功能迁移并验收判定、解锁、皮肤和 Spirit。
- [ ] 在另一台 PC 完成模块 APK 构建和设备回归。
