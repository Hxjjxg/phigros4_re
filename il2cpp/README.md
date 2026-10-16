# IL2CPP 层

游戏逻辑全在这里。`libil2cpp.so` 71.7 MB（arm64）/ 60.5 MB（armv7），配 `global-metadata.dat` 11.3 MB。

## Il2CppDumper 输出摘要

```
Metadata Version: 31
Il2Cpp Version:    31
CodeRegistration:     0x3e98c68
MetadataRegistration: 0x4021ae8
```

> 日志里的 `WARNING: find JNI_OnLoad` / `ERROR: This file may be protected` 是**误报**，dump 完整无缺失。

## 本目录文件

| 文件 | 大小 | 说明 |
|---|---|---|
| `dump.cs` | 27.8 MB | 全部 C# 类型结构，含 RVA / offset / VA |
| `stringliteral.json` | 1.5 MB | 16696 条字符串字面量（带地址） |
| `DummyDll/` | 18 MB | 97 个桩程序集，供 dnSpy 读（**未入库**） |

未入库（在 `../work/il2cpp/`，可重生成）：

| 文件 | 大小 | 说明 |
|---|---|---|
| `libil2cpp_arm64.so` | 71.7 MB | 从 APK 提取 |
| `global-metadata.dat` | 11.3 MB | 从 APK 提取 |
| `dump/il2cpp.h` | 73 MB | 结构体头文件，喂 IDA |
| `dump/script.json` | 76 MB | IDA / Ghidra 符号脚本 |

## 顶层命名空间分布

| 命名空间 | 类型数 | 是什么 |
|---|---|---|
| *(无命名空间)* | 5381 | 编译器生成 + 全局类型 |
| `System` | 3012 | BCL |
| `UnityEngine` | 2301 | Unity |
| `Sirenix` | 433 | Odin Inspector |
| `Cysharp` | 417 | UniTask |
| `LC` | 394 | LeanCloud 实时内部类 |
| `VoxelBusters` | 187 | Native Plugins |
| `LeanCloud` | 152 | **LeanCloud SDK** |
| `NaughtyAttributes` | 148 | 编辑器特性 |
| `Lean` | 115 | LeanTween |
| `TapTap` | 99 | TapTap SDK |
| **`Phigros2`** | **93** | **游戏自己的代码** |
| `LeTai` | 76 | TranslucentImage |
| `ExifLibrary` | 74 | EXIF 解析 |

`Phigros2` 子命名空间：`Phigros2.VisionReplay`、`Phigros2.Utils`、`Phigros2.Utils.MultiLanguageText`、`Phigros2.Utils.Addressable`、`Phigros2.SongSelector(.Scripts)`。

## 网络相关：LeanCloud Realtime

游戏**真正的实时通道**不是 Java 层，而是这里的 C# 实现。

| 命名空间 | 内容 |
|---|---|
| `LeanCloud.Realtime` | `LCRealtime`、`LCIMClient` |
| `LeanCloud.Realtime.Internal.WebSocket` | **`LCWebSocketClient`** ← 核心 |
| `LeanCloud.Realtime.Internal.Router` | `LCRTMRouter`（服务器寻址） |
| `LeanCloud.Realtime.Internal.Connection` | `LCConnection`、`LCRTMHeartBeat` |
| `LeanCloud.LiveQuery` | `LCLiveQueryConnection` |

`LCWebSocketClient` 自身不实现协议，包装 BCL 的 `System.Net.WebSockets.ClientWebSocket`：

```csharp
Action<byte[], int>  OnMessage  @ 0x10    // 入站回调
Action               OnClose    @ 0x18
ClientWebSocket      ws         @ 0x20
ConcurrentQueue<LCWebSocketClient.SendTask> sendQueue @ 0x28
```

### 可 Hook 地址（arm64，VA）

| 方法 | VA |
|---|---|
| `LCWebSocketClient..ctor()` | `0x354FC94` |
| `LCWebSocketClient.Connect(string, string, Dictionary<string,string>, TimeSpan)` | `0x3551378` |
| `LCWebSocketClient.Close()` | `0x3550150` |
| `LCWebSocketClient.Send(byte[], WebSocketMessageType)` | `0x3552190` |
| `LCWebSocketClient.Send(string)` | `0x3552F54` |
| `LCWebSocketClient.StartReceive()` | `0x35530EC` |
| `LCRTMHeartBeat.SendPing()` | `0x3552AF4` |
| `LCRealtime.Pause()` / `Resume()` | `0x3501BE4` / `0x3501D78` |

> 地址是 ELF 虚拟地址，运行时 = `Module.findBaseAddress("libil2cpp.so")` + VA。挂之前先 `readU32()` 验一下。

### 字符串常量证据

| 字面量 | 含义 |
|---|---|
| `LeanCloud-CSharp-SDK/1.0.3` | SDK 版本 |
| `https://app-router.com/2/route?appId=` | 服务器路由发现 |
| `.api.lncldglobal.com` / `.engine.lncldglobal.com` / `.push.lncldglobal.com` | 域名后缀 |
| `<RTMServer>k__BackingField` | 运行时记录的服务端地址 |

**寻址链路**：`appId` → 路由接口 → `{appid}.engine.lncldglobal.com` → WS 服务端。
**appId 是唯一需要提前知道的东西**，尚未定位。

## C# → Java 的 JNI 桥

```csharp
AndroidPluginUtility.GetSingletonInstance(string _className,
                                          string _methodName = "getInstance")  // RVA 0x1C67934
AndroidPluginUtility.CreateClassObject(string _className)                        // RVA 0x1C67BD0
```

C# 侧用到的 Java 类名：`com.tds.common.bridge.Bridge` / `CommonBridge` / `BridgeCallback`、`com.taptap.sdk.wrapper.TDSLoginService`。

> **探针技巧**：Hook `GetSingletonInstance` 是发现**所有 Java 侧 SDK 被唤醒时机**的捷径，包括 `TDSWebSocketService`。

## 重新生成

```powershell
Il2CppDumper.exe ..\work\il2cpp\libil2cpp_arm64.so ..\work\il2cpp\global-metadata.dat ..\work\il2cpp\dump
```

`.so` 与 `global-metadata.dat` 需要先从 APK 里提取（`build.ps1` 会做）。
