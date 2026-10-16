# Phigros 4.0.1 网络层解剖

> 分析时间：2026-10-16
> 目标：`com.PigeonGames.Phigros` v4.0.1 (157) · Unity IL2CPP v31 · arm64-v8a / armeabi-v7a · APK 3.33 GB
> 目的：为 Frida 主动与外部服务器收发 WebSocket 铺路

---

## 00 · 结论先行

1. **Java 层有两套完整的 WebSocket 收发栈，但没有一个被游戏自身调用。**
   - `com.tds.common.websocket.*` —— TapTap TDS 自带的完整 Java-WebSocket（RFC6455）
   - `com.tapsdk.antiaddiction.skynet.okhttp3.*` —— 被 shade 重打包的 OkHttp 3
   - 两者在 `classes.dex` 内均为**零调用点**

2. **游戏真正的实时通道在 C#/IL2CPP 层**：LeanCloud Realtime
   - SDK 版本字面量：`LeanCloud-CSharp-SDK/1.0.3`
   - 底层包 `System.Net.WebSockets.ClientWebSocket`

3. **但这正是机会**：那套 Java-WebSocket 自包含、可独立驱动。要跟自有服务器收发 WS，直接复用它 —— 零移植成本。→ 见 §07

---

## 01 · 事实基线与产物

### 产物

| 产物 | 路径 | 说明 |
|---|---|---|
| Java 源码 | `E:\phigros4_re\java\sources\` | 3928 个 `.java`，4 个反编译错误 |
| Manifest 树 | `E:\phigros4_re\apk\AndroidManifest_tree.txt` | aapt2 导出的完整 XML 树 |
| IL2CPP 二进制 | `E:\phigros4_re\work\il2cpp\libil2cpp_arm64.so` | 71.7 MB |
| 元数据 | `E:\phigros4_re\work\il2cpp\global-metadata.dat` | 11.3 MB，Metadata Version 31 |
| C# 类型结构 | `E:\phigros4_re\il2cpp\dump.cs` | 27.8 MB，含 RVA / offset / VA |
| 符号脚本 | `E:\phigros4_re\work\il2cpp\dump\script.json` | 76 MB，喂 IDA / Ghidra |
| 字符串常量 | `E:\phigros4_re\il2cpp\stringliteral.json` | 16696 条，带地址 |
| 程序集桩 | `E:\phigros4_re\il2cpp\DummyDll\` | 97 个 `.dll`，供 dnSpy 读 |

### 工具链

| 工具 | 路径 | 版本 |
|---|---|---|
| jadx | `E:\phigros4_re\tools\jadx-1.5.6\bin\jadx.bat` | 1.5.6 |
| Il2CppDumper | `F:\tool\dumper\Il2CppDumper.exe` | — |
| aapt2 | `…\Android\Sdk\build-tools\36.1.0\aapt2.exe` | 36.1.0 |
| frida | `%APPDATA%\Python\Python313\Scripts\frida.exe` | 17.9.1 |
| java / dotnet | PATH | 17.0.10 / 6·8·9·10 |
| apktool | `C:\Windows\apktool.bat` | 3.0.2 |

### Il2CppDumper 输出摘要

```
Metadata Version: 31
Il2Cpp Version:    31
CodeRegistration:     0x3e98c68
MetadataRegistration: 0x4021ae8
```

日志里的 `WARNING: find JNI_OnLoad` / `ERROR: This file may be protected` 是**误报**，dump 完整无缺失。

---

## 02 · APK 解剖

2928 个 zip 条目，其中 **2694 个是 `assets/aa/`**（Unity Addressables 资源包），占了几乎全部体积。代码只占极小一部分：

| 文件 | 解压后 | 角色 |
|---|---|---|
| `classes.dex` | 5.86 MB | **Java** 纯 SDK 壳 |
| `lib/arm64-v8a/libil2cpp.so` | 71.7 MB | **C#** 全部游戏逻辑 + 网络栈 |
| `lib/arm64-v8a/libunity.so` | 20.3 MB | **Native** Unity 运行时 |
| `lib/arm64-v8a/libmain.so` | 6.7 KB | **Native** JNI 入口 |
| `lib/arm64-v8a/libtapsdkcore.so` | 4.2 MB | **Native** TapTap SDK core |
| `lib/arm64-v8a/libnativeaudioe7.so` | 106 KB | **Native** Exceed7 NativeAudio |
| `assets/bin/Data/Managed/Metadata/global-metadata.dat` | 11.3 MB | IL2CPP 元数据 |
| `assets/bin/Data/data.unity3d` | 176 MB | Unity 序列化资源 |

（armeabi-v7a 一套同名文件，`libil2cpp.so` 60.5 MB）

### Manifest 要点

- **入口**：`com.unity3d.player.UnityPlayerActivity`
  （`exported=true`，`launchMode=singleTask`，`screenOrientation=11`）
- **权限只有两个**：`INTERNET`、`ACCESS_NETWORK_STATE`
  —— 没有存储、没有定位，**网络是它唯一的对外能力**
- `android:extractNativeLibs="true"` → `.so` 会解到 `/data/app/…/lib/arm64/`，Frida 可直接 `Module.findBaseAddress`
- `android:networkSecurityConfig=@0x7f0d0000` → `res/8G.xml`，**明确放行明文 + 信任用户 CA**，见 §07
- 其他 Activity：
  - `com.taptap.sdk.TapTapActivity`
  - `com.taptap.sdk.ui.TapTapActivity`
  - `com.taptap.sdk.TapLoginHelperActivity`
  - `com.google.android.gms.common.api.GoogleApiActivity`
  - `com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.GooglePlayGameUIActivity`
- Provider：
  - `com.tds.common.provider.CurrentConfigProvider`（authority `com.PigeonGames.Phigros.tap_config.provider`）
  - `com.taptap.sdk.internal.user.CurrentUserProvider`（authority `com.PigeonGames.Phigros.tap_user.provider`）
- meta-data：
  - `com.google.android.gms.games.APP_ID = 358918849784`
  - `com.tapsdk.antiaddiction.loader = true`

---

## 03 · 三层执行栈

```
┌─────────────────────────────────────────────────────────────────┐
│  C# / IL2CPP 层          libil2cpp.so · 游戏逻辑与网络栈所在      │
│                                                                 │
│  [LeanCloud.Realtime] [LCWebSocketClient] [ClientWebSocket(BCL)] │
│                                          ← 真实实时通道          │
└─────────────────────────────────────────────────────────────────┘
              ▲
              │  JNI · AndroidPluginUtility.GetSingletonInstance()
              │  com.tds.common.bridge.Bridge / CommonBridge
              │
┌─────────────────────────────────────────────────────────────────┐
│  Android / Java 层        classes.dex · 全部第三方 SDK           │
│                                                                 │
│  [com.tds.common.websocket ★]  [shaded okhttp3]  [okhttp2+retro] │
│                          ★ 可复用                                │
└─────────────────────────────────────────────────────────────────┘
```

**关键认知**：这两条路径彼此独立。Java 层的 WS 栈不依赖游戏状态就能工作 —— 这正是方案 A 成立的前提。

---

## 04 · Java 层：两套 WebSocket

### 4.1 `com.tds.common.websocket` —— **可用**

完整移植的 **Java-WebSocket**（RFC6455），客户端和服务端都在。

- 协议栈齐全：`drafts/Draft_6455`、`framing/`（Text / Binary / Ping / Pong / Close / Continuous）、`handshake/`、`extensions/permessage_deflate`（压缩）、`conn/SSLSocketChannel`（wss）
- 二进制帧完整：`onMessage(ByteBuffer)`、`send(ByteBuffer)`、`sendFragmentedFrame`
- 服务端：`server/WebSocketServer`、`DefaultWebSocketServerFactory`

上层单例 `TDSWebSocketService`（`com/tds/common/websocket/TDSWebSocketService.java`）自带重连与心跳：

| 成员 | 行号 | 作用 |
|---|---|---|
| `STATE_OPEN/CLOSING/CLOSED` | 17–19 | 1 / 3 / 2 |
| `TRY_MAX_TIMES = 60` | 19 | 重连上限 |
| `setDestUrl(String, WebSocketConfig)` | 49 | **注入 URL + header，并立即 `connect()`** |
| `checkConnection()` | 64 | 断线补连 |
| `disconnect()` / `reset()` | 76 / 84 | 断开 / 清空 |
| `registerMessageListener(Type, Listener)` | 89 | 按消息类型注册监听 |
| `unregisterMessageListener(Type)` | 93 | 注销 |
| `addConnectionStateListener(Listener)` | 97 | 连接状态回调 |
| `retryConnectWithDelay()` | 127 | 延迟重连 |
| `getConnectionState()` | 229 | 查询状态 |
| `sendMessage(String)` | 241 | 出站 |

内部类：

```java
// L248
public static class WebSocketConfig {
    int connectionTimeout;
    public Map<String, String> heads;   // ← 鉴权 token 走这里
}

// L280 —— 默认 connectionTimeout = 45 秒
public static class WebSocketConfigBuilder {
    public static WebSocketConfigBuilder getBuilder();
    public WebSocketConfigBuilder connectTimeOut(int i);   // 已确认
    public WebSocketConfigBuilder heads(Map<String, String> map);  // ← heads()，不是 setHeads()
    public WebSocketConfig build();
}
```

消息类型枚举只有两个值：

```java
public enum Type { MESSAGE_TYPE_TAP_FRIEND, MESSAGE_TYPE_OTHER }
```

→ 原始用途是 **TapTap 好友服务**。

#### 为什么它现在是休眠的

三重验证：

1. `grep TDSWebSocketService` 在整个 dex 内除自身包外**零引用**（连反射所需的字符串都没有）
2. `setDestUrl` 只出现在定义处
3. C# 侧字符串常量里也没有这个类名

结论：它由 Unity 运行时通过 JNI 桥按需唤醒，静态拿不到 URL 和 token。
**但类本身是完整可用的** —— 这正是方案 A 的立足点。

### 4.2 `com.tapsdk.antiaddiction.skynet.okhttp3` —— **死代码**

被重打包的 **OkHttp 3**，WS 部分完整存在：

- `OkHttpClient.newWebSocket(Request, WebSocketListener)` —— `OkHttpClient.java:335`
- `internal/ws/RealWebSocket.java`、`WebSocketReader`、`WebSocketWriter`、`WebSocketProtocol`

但同模块实际调用路径是旁边的 `retrofit2` + `rest/utils/HttpUtil`。调用者为 `AntiAddictionImpl`、`AntiAddictionUIImpl`、`MockDataInterceptor` —— 全是防沉迷的 HTTP 接口。**WS 部分是死代码。**

### 4.3 `com.squareup.okhttp` + `retrofit` —— **无 WS**

`retrofit/client/OkClient.java` 引用 OkHttp 2.x，只走 `HttpURLConnection`，超时 15s / 20s。纯 HTTP。

---

## 05 · C#/IL2CPP 层：LeanCloud Realtime

### 命名空间

| 命名空间 | 内容 |
|---|---|
| `LeanCloud.Realtime` | LCRealtime、LCIMClient |
| `LeanCloud.Realtime.Internal.WebSocket` | **LCWebSocketClient** ← 核心 |
| `LeanCloud.Realtime.Internal.Router` | LCRTMRouter（服务器寻址） |
| `LeanCloud.Realtime.Internal.Connection` | LCConnection、LCRTMHeartBeat |
| `LeanCloud.Realtime.Internal.Protocol` | 协议编解码 |
| `LeanCloud.Realtime.Internal.Controller` | 会话控制 |
| `LeanCloud.LiveQuery` | LCLiveQueryConnection |
| `LeanCloud.Storage` | 对象存储 / 文件 / 查询 |
| `LeanCloud.Push` | 推送 |
| `LeanCloud.Common` | 公共 |

### LCWebSocketClient 内部结构（TypeDefIndex 9985）

自身不实现协议，包装 BCL 的 `System.Net.WebSockets.ClientWebSocket`：

```csharp
// 常量
SEND_BUFFER_SIZE = 5120      RECV_BUFFER_SIZE = 8192
MSG_BUFFER_SIZE  = 10240     CLOSE_TIMEOUT    = 5000
CONNECT_TIMEOUT  = 10000

// 字段
Action<byte[], int>                          OnMessage   @ 0x10   // ← 入站回调
Action                                       OnClose     @ 0x18
ClientWebSocket                              ws          @ 0x20
ConcurrentQueue<LCWebSocketClient.SendTask>  sendQueue   @ 0x28
```

`SendTask`（TypeDefIndex 9979）字段：`Tcs: TaskCompletionSource<object>`、`Bytes: ArraySegment<byte>`、`MessageType: WebSocketMessageType`。

### 可 Hook 的地址（arm64，VA）

| 方法 | VA | 文件偏移 |
|---|---|---|
| `LCWebSocketClient..ctor()` | `0x354FC94` | `0x354BC94` |
| `LCWebSocketClient.Connect(string, string, Dictionary<string,string>, TimeSpan)` | `0x3551378` | `0x354D378` |
| `LCWebSocketClient.Close()` | `0x3550150` | `0x354C150` |
| `LCWebSocketClient.Send(byte[], WebSocketMessageType)` | `0x3552190` | `0x354E190` |
| `LCWebSocketClient.Send(string)` | `0x3552F54` | `0x354EF54` |
| `LCWebSocketClient.StartSend()` | `0x3553028` | `0x354F028` |
| `LCWebSocketClient.StartReceive()` | `0x35530EC` | `0x354F0EC` |
| `LCWebSocketClient.HandleExceptionClose()` | `0x35531B0` | `0x354F1B0` |
| `LCRTMHeartBeat.SendPing()` | `0x3552AF4` | `0x354EAF4` |
| `LCRealtime.Pause()` | `0x3501BE4` | — |
| `LCRealtime.Resume()` | `0x3501D78` | — |

> `Connect()` 的四个参数 —— `server`、`subProtocol`、`headers`、`keepAliveInterval` —— **hook 这一个函数就能拿到完整连接上下文**，包括鉴权 header。

### 字符串常量证据

| 字面量 | 含义 |
|---|---|
| `LeanCloud-CSharp-SDK/1.0.3` | SDK 版本 |
| `https://app-router.com/2/route?appId=` | 服务器路由发现（国际版） |
| `/v1/route?appId=` | 路由接口另一形态 |
| `.api.lncldglobal.com` | API 域名后缀 |
| `.engine.lncldglobal.com` | 实时引擎域名后缀 |
| `.push.lncldglobal.com` | 推送域名后缀 |
| `Only Uris starting with 'ws://' or 'wss://' are supported.` | 协议校验 |
| `<RTMServer>k__BackingField` | 运行时记录的服务端地址 |

**寻址链路**：
```
appId → https://app-router.com/2/route?appId=… → {appid}.engine.lncldglobal.com → WS 服务端
```
**appId 是唯一需要提前知道的东西**，它藏在 IL2CPP 字符串常量或初始化代码里。

---

## 06 · JNI 桥：C# 如何驱动 Java

C# 侧调用 Java 的唯一入口是 Unity 的 `AndroidJavaObject`，封装在 `AndroidPluginUtility`（TypeDefIndex 9987）：

```csharp
AndroidPluginUtility.GetSingletonInstance(string _className,
                                          string _methodName = "getInstance")  // RVA 0x1C67934
AndroidPluginUtility.CreateClassObject(string _className)                       // RVA 0x1C67BD0
```

C# 侧字符串常量里出现的 Java 类名，证实了桥的形态：

- `com.tds.common.bridge.Bridge` / `CommonBridge` / `BridgeCallback` —— 通用命令分发
- `com.taptap.sdk.wrapper.TDSLoginService` / `TDSLoginServiceImpl` —— 登录

Java 侧对应实现在 `com/tds/common/bridge/`（含 `command/`、`result/`、`annotation/`、`utils/` 子包）。

> **观察桥的价值**：Hook `AndroidPluginUtility.GetSingletonInstance` 是**发现所有 Java 侧 SDK 被唤醒时机**的捷径 —— 其中就包括 `TDSWebSocketService`。想知道游戏是否真的启用了 Java WS，这是最直接的探针。

---

## 07 · 方案 A —— 复用 Java-WebSocket 【推荐】

不去碰游戏的 LeanCloud 连接，而是把 APK 里已经躺着的那套 RFC6455 实现当成通用 WS 库，从 Frida 直接驱动它连你自己的服务器。

**零移植、零编译、不依赖游戏是否启用了它。**

> ✅ **2026-10-16 已真机验证通过。** frida-gadget 路线注入，双向收发 + 服务端主动推送 + `heads()` header 注入握手 全部实测成功。实现与运行手册见 `network\ws\phigros_ws.js` 与 `network\ws\README.md`。

### 接口签名（已从源码坐实）

```java
// WebSocketConfigBuilder
public static WebSocketConfigBuilder getBuilder();
public WebSocketConfigBuilder connectTimeOut(int i);
public WebSocketConfigBuilder heads(Map<String, String> map);   // ← 注意是 heads()，不是 setHeads()
public WebSocketConfig build();

// WebSocketEventListener（注册监听器时实现这 4 个）
void onOpen();
void onMessage(String str);
void onClose(int i, String str, boolean z);
void onError(Exception exc);

// WebSocketStateListener
void onOpen();  void onClose();  void onError(String str);
```

**header 注入链路已确认**：`WebSocketClient(URI, WebSocketConfig, listener)` 构造器遍历 `config.heads` 逐个 `addHeader(k, v)`，并 `setConnectionLostTimeout(config.connectionTimeout)`（默认 45 秒自动发 ping）。

**收包安全性已确认**：`TDSWebSocketService.onMessage` 在无监听器时直接 `return`，不会 NPE。
**发包静默丢包**：`sendMessage` 在 `webSocketClient == null || !isOpen()` 时直接 return —— 所以必须先查 `getConnectionState()`。

### 建连 + 发送

```javascript
// frida -U -f com.PigeonGames.Phigros -l ws_out.js
Java.perform(function () {
  var Svc     = Java.use("com.tds.common.websocket.TDSWebSocketService");
  var Bld     = Java.use("com.tds.common.websocket.TDSWebSocketService$WebSocketConfigBuilder");
  var HashMap = Java.use("java.util.HashMap");

  var heads = HashMap.$new();
  heads.put("X-From", "frida");               // 鉴权 header 走这里

  var cfg = Bld.getBuilder()
               .connectTimeOut(45)
               .heads(heads)                  // ← heads()，不是 setHeads()
               .build();

  var svc = Svc.getInstance();
  svc.setDestUrl("wss://your.host/ws", cfg);  // 内部立即 connect()

  setTimeout(function () {
    Java.perform(function () {
      Svc.getInstance().sendMessage(JSON.stringify({ t: "ping", n: 1 }));
    });
  }, 3000);
});
```

### 接收

```javascript
// ① 直接挂单例的回调（文本帧）
var Svc = Java.use("com.tds.common.websocket.TDSWebSocketService");
Svc.onMessage.overload("java.lang.String").implementation = function (m) {
  console.log("[WS in] " + m);
  return this.onMessage(m);                   // 转调原实现
};

// ② 挂底层二进制帧
var Base = Java.use("com.tds.common.websocket.client.WebSocketClient");
Base.onMessage.overload("java.nio.ByteBuffer").implementation = function (buf) {
  console.log("[WS bin] " + Java.array("byte", buf.array()));
  return this.onMessage(buf);
};
```

若要注册自己的监听器，用 `Java.registerClass` 实现 `WebSocketEventListener`，再 `registerMessageListener(Type, listener)`。签名以前置脚本输出为准。

### 注意事项

| # | 事项 |
|---|---|
| 1 | **`ws://` 明文可用**。NSC（`res/8G.xml`）里 `cleartextTrafficPermitted="true"` 明确放行，不受 targetSdk 30 默认限制。**实测已通** |
| 2 | **自签名 `wss://` 也能用**。NSC 信任用户 CA（`<certificates src="user"/>`），且 `WebSocketClient` 建 SSL 上下文用的是 `SSLContext.init(null, null, null)`——null TrustManager 走系统默认，会读 NSC。把证书装进 Android **用户**证书库即可，无需 root |
| 3 | **线程**：WS 回调发生在 Java 子线程，Unity API 只能在主线程调。回灌游戏需 `runOnUiThread` 或 `UnityPlayer.currentActivity` 转发 |
| 4 | **生命周期**：`sendMessage` 前确认 `getConnectionState() == 1`，否则消息会被静默丢弃。重连由 `tryTimes` 控制，上限 60 次 |
| 5 | **不干扰游戏**：`setDestUrl` 会 `reset()` 掉已有 client。目前游戏没用这个单例所以安全；若发现它被激活了，改用独立类实例而非单例 |

---

## 08 · 方案 B —— Hook IL2CPP 的 LeanCloud

方案 A 是「用游戏里的库」，方案 B 是「**接管游戏自己的连接**」。观察和改写在跑的 LeanCloud 流量走这条。

### 用 frida-il2cpp-bridge（推荐）

比裸地址稳，不怕版本偏移，直接按类名方法名定位：

```typescript
import "frida-il2cpp-bridge";

Il2Cpp.perform(() => {
  // 程序集名：DummyDll 显示为 Realtime.dll（待确认）
  const k = Il2Cpp.domain
    .assembly("Realtime")
    .image.class("LeanCloud.Realtime.Internal.WebSocket.LCWebSocketClient");

  // 出站
  k.method("Send").overload("System.String").implementation = function (text) {
    console.log("[LC →] " + text);
    return this.method("Send").overload("System.String").invoke(text);
  };

  // 建连上下文：server / subProtocol / headers / keepAliveInterval
  k.method("Connect").implementation = function (server, subProto, headers, keepAlive) {
    console.log("[LC connect] " + server + "  proto=" + subProto);
    console.log("[LC headers] " + headers.toString());
    return this.method("Connect").invoke(server, subProto, headers, keepAlive);
  };
});
```

> **程序集名待定**：`DummyDll` 里唯一与 LeanCloud 相关的是 `Realtime.dll`，但命名空间是 `LeanCloud.Realtime.*`，两者是否对应需实际加载时确认。用 `Il2Cpp.domain.assemblies` 遍历一次即可定论。

### 裸 Interceptor（不依赖第三方库）

```javascript
const base = Module.findBaseAddress("libil2cpp.so");

// IL2CPP 字符串布局：klass(8) monitor(8) length(4) chars(2×len)
function readIl2CppString(p) {
  if (p.isNull()) return null;
  const len = p.add(0x10).readS32();
  return p.add(0x14).readUtf16String(len);
}

Interceptor.attach(base.add(0x3552F54), {          // Send(string)
  onEnter(args) {
    // args[0]=this  args[1]=Il2CppString*  args[2]=MethodInfo*
    console.log("[LC →] " + readIl2CppString(args[1]));
  }
});

Interceptor.attach(base.add(0x35530EC), {          // StartReceive()
  onEnter() { console.log("[LC] StartReceive 触发"); }
});
```

**地址校验**：`0x3552F54` 是 ELF 虚拟地址，运行时地址 = `libil2cpp.so` 基址 + VA（前提是首个 PT_LOAD 的 vaddr 为 0）。
挂之前先 `base.add(0x3552F54).readU32()` 看是否是合理指令字，别盲挂。

### 入站怎么拿

`StartReceive()` 是异步循环，读 `OnMessage` 字段（`@0x10`）更直接 —— 它是 `Action<byte[], int>`，挂在实例上。先用 bridge 拿到 `LCWebSocketClient` 实例，再读字段：

```typescript
const inst = /* 从 Connect 的 this 捕获 */;
const cb = inst.field("OnMessage").value;   // Action<byte[], int>
// 或将 callback 替换为自己的实现，转发原始调用
```

### 风险

IL2CPP 方法是编译后的 C++，参数是 `Il2CppObject*`，**没有类型信息**。
`Send(string)` 这种简单签名没问题；一旦涉及泛型共享（generic sharing）或值类型参数，裸 hook 极易读错。复杂场景请用 bridge。

---

## 09 · 未确认清单

文档里标 `?` 的地方都是**没有实地验证**的，按重要性排序：

| # | 待确认 | 影响 |
|---|---|---|
| ~~1~~ | ~~`WebSocketConfigBuilder` 方法名~~ → **已确认**：`heads(Map)` / `build()` / `connectTimeOut(int)` | 已解决 |
| ~~2~~ | ~~`WebSocketEventListener` 方法签名~~ → **已确认**：`onOpen()` / `onMessage(String)` / `onClose(int,String,boolean)` / `onError(Exception)` | 已解决 |
| 3 | `Realtime.dll` 是否就是 `LeanCloud.Realtime` 的宿主程序集 | 影响方案 B 的 bridge 写法 |
| 4 | LeanCloud **appId** 的具体值 | 决定能否直连引擎服务器 |
| ~~5~~ | ~~`networkSecurityConfig` 内容~~ → **已确认**：`res/8G.xml`，`cleartextTrafficPermitted="true"` + 信任 user CA | 已解决 |
| 6 | 游戏运行时是否真的唤醒了 `TDSWebSocketService` | 用 §06 的桥探针可验证 |
| 7 | arm64 的 VA → 运行时地址映射是否真的以 0 为基 | 挂 hook 前必须验 |

---

## 10 · 路径速查

| 要找什么 | 去哪 |
|---|---|
| Java 层全部源码 | `E:\phigros4_re\java\sources\` |
| 可复用的 WS 客户端 | `com\tds\common\websocket\WebSocketClient.java` |
| WS 单例与服务封装 | `com\tds\common\websocket\TDSWebSocketService.java` |
| WS 抽象基类 | `com\tds\common\websocket\client\WebSocketClient.java` |
| C# 类型结构 + 地址 | `E:\phigros4_re\il2cpp\dump.cs` |
| IDA / Ghidra 符号 | `E:\phigros4_re\work\il2cpp\dump\script.json` |
| 字符串常量（带地址） | `E:\phigros4_re\il2cpp\stringliteral.json` |
| dnSpy 可读程序集 | `E:\phigros4_re\il2cpp\DummyDll\` |
| Manifest 完整树 | `E:\phigros4_re\apk\AndroidManifest_tree.txt` |

---

### 可直接运行的实现

| 文件 | 作用 |
|---|---|
| `E:\phigros4_re\network\ws\ws_server.py` | PC 侧测试服务端（`websockets` 12.0，已本地验证双向 + 服务端主动推送） |
| `E:\phigros4_re\network\ws\phigros_ws.js` | Frida 脚本（挂钩子 + 自动连接 + `wsSend`/`wsState`/`wsConnect`/`wsClose` REPL 接口） |

运行步骤见 `network\ws\README.md`。
