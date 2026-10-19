# 云存档版本比较：4.0.1 arm64 移植

移植自 `F:\tool\phi_reversed\scripts\local_saves\cloud_sync_version_bypass_bridge_RVA.ts`（3.14.2）。TS 仅更新版本注释和三个 RVA；保留原有本地 / 云端 `gv`、`sv` 显示逻辑。

适用于 Android Phigros **4.0.1 (157)、arm64-v8a**。运行时地址为 `libil2cpp.so` 模块基址加 RVA。

| 位置 | 3.14.2 RVA | 4.0.1 RVA | 4.0.1 原指令 | 补丁 |
|---|---|---|---|---|
| SaveVersion 比较 | `0x1F3FF18` | `0x1D5CD70` | `B.HI 0x1D5CD84`（`a8 00 00 54`） | `NOP` |
| GameVersion 比较 | `0x1F3FF28` | `0x1D5CD80` | `B.LE 0x1D5E0F0`（`8d 9b 00 54`） | `B 0x1D5E0F0` |
| 正常同步入口 | `0x1F40C84` | `0x1D5E0F0` | `LDR X0, [X23]`（`e0 02 40 f9`） | 跳转目标 |

定位依据：`CloudSaveManager.<SyncSave>d__14.MoveNext()`，RVA `0x1D5C418`。云端 `SaveVersion` 或 `GameVersion` 大于本地时，原流程进入 `syncFailedText` / `updateText` 提示分支；补丁使其继续正常同步流程。

字段和方法已与 `il2cpp/dump.cs` 核对：`CloudSaveSummary` 的版本字段仍在 `0x10` / `0x14`，`SelectSavePopup.Awake()` 为 `0x1D60368`，`CloudSaveInfoDisplay.SetInfo(summary, updateTime, hideAT)` 为 `0x1D5EDCC`。这些 UI hook 仍由 bridge 按名称解析。

## 加载

直接加载已包含 `frida-il2cpp-bridge` 的 `.js`：

```powershell
& 'F:\frida16\Scripts\frida.exe' -U -f com.PigeonGames.Phigros -l hooks/cloud-save/cloud_sync_version_bypass_bridge_RVA.js
```

也可对已运行的游戏使用 `-p <游戏PID>`。需要可连接的 Frida server 或 Gadget；仓库当前独立 APK 的自动脚本型 Gadget 不开放 attach 端口。此脚本尚未加入 APK 自动加载。

安装完成会输出 `[cloud-sync] compare-branch bypass ready` 和 `[cloud-sync] hooks installed`。进入存档选择弹窗后，副标题下方显示本地与云端版本。

## 重编译

需要 Node.js 20 或更新版本，以及 npm。从仓库根目录运行：

```powershell
npm --prefix hooks/cloud-save ci
npm --prefix hooks/cloud-save run build
npm --prefix hooks/cloud-save run check
```

`build` 先用 TypeScript 检查源码，再由 `build.mjs` 将 bridge 和脚本打包为普通 IIFE JavaScript，覆盖同目录的 `.js`。输出无 source map，可直接交给 Frida 加载。构建只依赖本目录的 npm 包，无需原项目目录或全局编译器。

| 文件 | 用途 |
|---|---|
| `package.json` / `package-lock.json` | 构建命令与固定依赖；用 `npm ci` 重建环境 |
| `tsconfig.json` / `runtime.d.ts` | TypeScript 配置与 Frida `console` 声明 |
| `build.mjs` | esbuild 打包入口 |
| `cloud_sync_version_bypass_bridge_RVA.ts` | 可编辑源码 |
| `cloud_sync_version_bypass_bridge_RVA.js` | 已编译产物，随源码一起提交 |

`node_modules/` 已忽略。修改 TS 后重新执行 `build`、`check`，并一并提交更新后的 JS。

## 校验记录

- 已在 IDA 中核对版本比较的数据来源、失败分支与正常入口，并直接读取 ELF 核对三个地址的机器码。注意 ELF 文件偏移为这些 RVA 减 `0x4000`。
- 目标 `work/il2cpp/libil2cpp_arm64.so` SHA-256：`c1a2b1fbc40b4ba93306eccaba90ac13983e15964726904aec039aff0cb657da`。
- 构建依赖固定为 `typescript 5.8.3`、`esbuild 0.25.5`、`@types/frida-gum 19.0.0`、`frida-il2cpp-bridge 0.13.0`。已执行锁文件干净安装、类型检查和 IIFE 打包，产物通过 `node --check`。
- 尚未在设备中注入或执行云存档同步；弹窗显示和实际同步行为待实机验证。
