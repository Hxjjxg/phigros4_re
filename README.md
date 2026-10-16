# Phigros 4.0.1 逆向

对 `com.PigeonGames.Phigros` v4.0.1 (157) 的静态 + 动态逆向工作区。

**当前目标：在游戏进程内与外部服务器主动收发 WebSocket。** ✅ 已跑通。

| | |
|---|---|
| 包名 | `com.PigeonGames.Phigros` |
| 版本 | 4.0.1 (versionCode 157) |
| 引擎 | Unity IL2CPP v31 |
| ABI | arm64-v8a / armeabi-v7a |
| min / target SDK | 26 / 30 |
| APK | 3.33 GB（未入库，见下） |

---

## 目录

| 目录 | 内容 |
|---|---|
| [`apk/`](apk/) | APK 解剖：Manifest、资源表、包结构 |
| [`java/`](java/) | Java 层（`classes.dex` → jadx 反编译产物） |
| [`il2cpp/`](il2cpp/) | IL2CPP 层（C# 类型结构、字符串常量） |
| [`network/`](network/) | **网络层** —— 三条通路的总览 |
| [`network/ws/`](network/ws/) | **WebSocket 实现** —— Frida 脚本 + 测试服务端 |
| [`docs/`](docs/) | 分析报告 |
| [`tools/`](tools/) | 工具链说明（二进制不入库） |
| `work/` | 工作区（APK 提取物、大中间产物）—— 未入库 |
| `build.ps1` | 从 APK 一键重生成全部产物 |

---

## 快速开始

### 网络 / WebSocket（当前主线）

```powershell
# 1. 起测试服务端
python network\ws\ws_server.py

# 2. 端口转发
adb reverse tcp:8765 tcp:8765

# 3. 注入
frida -U -n Gadget -l network\ws\phigros_ws.js
```

细节见 [`network/ws/README.md`](network/ws/README.md)。

### 从零重建分析产物

```powershell
.\build.ps1 -ApkPath .\Phigros_4.0.1.apk
```

需要 `java`、`python`、`Il2CppDumper`、`jadx`、`aapt2`。见 [`tools/README.md`](tools/README.md)。

---

## 核心结论

### 三层执行栈

```
C# / IL2CPP 层   libil2cpp.so      ← 游戏逻辑 + LeanCloud Realtime（真实实时通道）
      ▲ JNI
Java 层          classes.dex       ← 全部第三方 SDK，无游戏业务代码
```

### 三条网络通路

| 通路 | 位置 | 状态 |
|---|---|---|
| **LeanCloud Realtime** | C# `LCWebSocketClient` | 游戏真正的实时通道 |
| **Java-WebSocket** | `com.tds.common.websocket.*` | 完整可用，**但游戏自身从不调用** ← 我们复用的就是它 |
| shaded OkHttp3 | `com.tapsdk.antiaddiction.skynet.okhttp3` | WS 部分是死代码 |

**关键洞察**：Java 层那套 Java-WebSocket 是自包含的 RFC6455 完整实现，游戏不用它。等于 APK 里白送一个 WS 库——从 Frida 直接驱动即可，零移植成本。

### 安全配置（`res/8G.xml`）

```xml
<network-security-config>
    <base-config cleartextTrafficPermitted="true">
        <trust-anchors>
            <certificates src="user" />
            <certificates src="system" />
        </trust-anchors>
    </base-config>
</network-security-config>
```

- `cleartextTrafficPermitted="true"` → **`ws://` 明文被明确允许**
- `<certificates src="user"/>` → **信任用户安装的 CA**，自签名证书装进用户证书库即可用于 `wss://`，无需 root

---

## 文档

- [`docs/network-analysis.md`](docs/network-analysis.md) —— 网络层完整分析（三层拆解 + 两套 Frida 方案 + 未确认清单）
- [`docs/network-analysis.html`](docs/network-analysis.html) —— 同上，浏览器阅读版

---

## 版本管理说明

**已入库**：分析文档、脚本、`java/sources/`（jadx 产物）、`il2cpp/dump.cs`、`il2cpp/stringliteral.json`

**未入库**（可重生成，见 `build.ps1`）：
- `Phigros_4.0.1.apk`（3.33 GB）
- `tools/jadx-1.5.6/`（工具二进制）
- `work/`——`libil2cpp.so`、`global-metadata.dat`、`il2cpp.h`、`script.json`
- `il2cpp/DummyDll/`（dnSpy 用桩程序集）

要精简仓库的话，把 `.gitignore` 里的 `java/sources/` 和 `il2cpp/dump.cs` 加进去，仓库从 ~70 MB 降到 ~2 MB，代价是每次读代码前得先跑 `build.ps1`。
