# 网络层

这个游戏有三条互不相干的网络通路。搞清哪条是哪条，是后面所有工作的前提。

```
┌──────────────────────────────────────────────────────────────┐
│  C# / IL2CPP 层    libil2cpp.so                              │
│                                                              │
│   LeanCloud Realtime  ──→  LCWebSocketClient                 │
│         └─ System.Net.WebSockets.ClientWebSocket             │
│                            ★ 游戏真正的实时通道               │
└──────────────────────────────────────────────────────────────┘
                    ▲
                    │  JNI · AndroidPluginUtility / com.tds.common.bridge.Bridge
                    │
┌──────────────────────────────────────────────────────────────┐
│  Java 层    classes.dex                                      │
│                                                              │
│   com.tds.common.websocket ★      完整 Java-WebSocket        │
│         └─ 游戏自身零调用  ←── 我们复用的就是它               │
│                                                              │
│   shaded okhttp3 (防沉迷)         WS 部分是死代码              │
│   okhttp2 + retrofit              纯 HTTP                    │
└──────────────────────────────────────────────────────────────┘
```

## 三条通路

| # | 通路 | 位置 | 协议 | 状态 |
|---|---|---|---|---|
| 1 | **LeanCloud Realtime** | C# `LCWebSocketClient` | WSS + Protobuf | 游戏在用 |
| 2 | **Java-WebSocket** | `com.tds.common.websocket.*` | RFC6455 | 完整可用，**零调用点** |
| 3 | shaded OkHttp3 | `com.tapsdk.antiaddiction.skynet.okhttp3` | WS 死代码 | 只跑 HTTP |

### 关键洞察

Java 层那套 Java-WebSocket 是**完整自包含的 RFC6455 实现**（客户端 + 服务端 + 压缩扩展 + wss），而游戏自己从不用它。

**等于 APK 里白送一个 WebSocket 库。** 从 Frida 直接驱动它的单例，连你自己的服务器——零移植、零编译、不依赖游戏状态。

## 目录

| 路径 | 内容 |
|---|---|
| [`ws/`](ws/) | **WebSocket 实现**——Frida 脚本 + 测试服务端 + 运行手册 |
| [`../docs/network-analysis.md`](../docs/network-analysis.md) | 完整分析报告（三层拆解 + 两套 Frida 方案 + 未确认清单） |
| [`../docs/network-analysis.html`](../docs/network-analysis.html) | 同上，浏览器版 |

## 安全配置

`res/8G.xml`（即 manifest 里的 `@0x7f0d0000`）：

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

两个直接后果：

1. **`ws://` 明文被明确允许** —— 不受 targetSdk 30 的默认禁明文限制
2. **信任用户安装的 CA** —— 自签名证书装进 Android 用户证书库即可用于 `wss://`，**无需 root**

第 2 条与 `WebSocketClient` 的实现吻合：它建 SSL 上下文用的是

```java
SSLContext sSLContext = SSLContext.getInstance("TLSv1.2");
sSLContext.init(null, null, null);      // null TrustManager = 走系统默认 → 读 NSC
```

`null` 的 TrustManager 参数意味着使用系统默认信任管理器，而 Android N+ 的默认信任管理器会读取 network security config——所以 `<certificates src="user"/>` 在这里是生效的。

## 未确认

| # | 项 | 影响 |
|---|---|---|
| 1 | LeanCloud **appId** 的具体值 | 决定能否直连引擎服务器（而不只是连自建服务器） |
| 2 | `Realtime.dll` 是否 `LeanCloud.Realtime` 的宿主程序集 | 影响方案 B 的 frida-il2cpp-bridge 写法 |
| 3 | 游戏运行时是否真会唤醒 `TDSWebSocketService` | 用 `AndroidPluginUtility.GetSingletonInstance` 探针可验 |
