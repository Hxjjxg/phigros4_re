# WebSocket 主动收发测试 — 运行手册

用 Phigros 自带的 Java-WebSocket 客户端，从游戏进程内连你自己的服务器收发消息。

| 文件 | 作用 |
|---|---|
| `ws_server.py` | PC 侧测试服务端 |
| `phigros_ws.js` | Frida 注入脚本 |

---

## 0 · 当前状态

| 环节 | 状态 |
|---|---|
| PC 服务端 | ✅ 已验证 |
| Frida 脚本 | ✅ **已在真机跑通**（frida-gadget 重打包路线） |
| 双向收发 | ✅ 游戏→服务端、服务端→游戏、服务端控制台推送，全部验证 |
| header 注入 | ✅ 服务端收到 `x-from: frida` |

**2026-10-16 实测结论：整条链路可用。** 以下是当时的验证证据：

```
服务端侧                                    游戏侧
CONNECT ('127.0.0.1', 2091)                 [ws] ✓ 连接已建立 (onOpen)
  header  x-from: frida                     [ws] ← {"type":"welcome",...}
  ✓ Frida 侧的 header 注入生效               [ws] → {"from":"game","type":"hello",...}
← {"from":"game","type":"hello",...}        [ws] ← {"type":"heartbeat","seq":1,...}
PUSH heartbeat #1 → 1 个客户端               [ws] ← {"from":"console","msg":"hellofromserver"}
```

---

## 1 · 起服务端（PC）

```powershell
python E:\phigros4_re\network\ws\ws_server.py
```

监听 `0.0.0.0:8765`。起来后会打印横幅，然后：

- 游戏发来的消息 → 打印在窗口里
- **在窗口里直接敲字 + 回车 → 推给游戏**（这是验证下行最方便的办法）
- 每 15 秒自动推一条 heartbeat
- `/quit` 退出

想换端口：`python ws_server.py --port 9000`

---

## 2 · 连接设备

```powershell
adb devices
```

必须看到设备序列号。没有的话：USB 连上 + 打开「USB 调试」，或 `adb connect <手机IP>:5555`。

> 注意本机有两个 IP：`10.32.20.219` 是真实局域网，`26.43.55.187` 看着像 VPN（Radmin）。**别用 26 那个。**

---

## 3 · 端口转发（推荐）

```powershell
adb reverse tcp:8765 tcp:8765
```

手机上的 `127.0.0.1:8765` 直接转发到 PC 的 8765。
**走这条就不需要管 IP、不需要管防火墙**——USB 通了就行。脚本默认就用这个地址。

不想用 adb reverse 的话，把 `phigros_ws.js` 顶部的 `WS_URL` 改成：

```javascript
const WS_URL = 'ws://10.32.20.219:8765';
```

并确保 Windows 防火墙放行 8765 入站。

---

## 4 · 起 frida-server（需要 root）

```powershell
adb push <frida-server-android-arm64> /data/local/tmp/frida-server
adb shell "su -c 'chmod 755 /data/local/tmp/frida-server'"
adb shell "su -c '/data/local/tmp/frida-server &'"
```

frida-server 版本要跟 PC 上的 frida **一致**。本机 CLI 是 `17.9.1`：

```powershell
frida --version
```

> 没有 root 的话得走另一条路：用 apktool 解包 → 塞 frida-gadget → 重打包签名。本仓库 `apk-reverse` skill 的 `rebuild-sign-install.ps1` 能闭环，但工作量比有 root 大得多。

---

## 5 · 注入

```powershell
frida -U -f com.PigeonGames.Phigros -l E:\phigros4_re\network\ws\phigros_ws.js
```

脚本会自动：

1. 挂 `onOpen` / `onMessage` / `onClose` / `onError` 四个回调
2. 等 2 秒 → 连 `ws://127.0.0.1:8765`
3. 连上 3 秒后 → 自动发一条 `hello`（`AUTO_SEND`）
4. ~~每 10 秒发一条计数 `ping`~~ —— `KEEPALIVE` 默认已关（会刷屏 REPL）

**预期输出：**

```
[ws] 类已加载，开始挂钩子
[ws]   ✓ hook onMessage(String)
[ws]   ✓ hook onOpen()
[ws]   ✓ hook onClose(int,String,boolean)
[ws]   ✓ hook onError(Exception)
[ws] 就绪 —— REPL 里敲 wsHelp() 看可用命令
[ws] → 正在连接 ws://127.0.0.1:8765
[ws] ✓ 连接已建立 (onOpen)
[ws] → {"from":"game","type":"hello","t":...}
[ws] ← {"from":"server","type":"welcome",...}
[ws] ← {"from":"server","type":"echo",...}
```

服务端窗口同时应显示：

```
[HH:MM:SS] #1      CONNECT  ('127.0.0.1', xxxxx)  path=/
[HH:MM:SS] #1        header  x-from: frida
[HH:MM:SS] #1        ✓ X-From: frida —— Frida 侧的 header 注入生效
```

> 看到 `X-From: frida` 就说明 **`heads(Map)` 注入握手生效**了——这是整条链路的关键验证点。

---

## 6 · REPL 命令

注入后 frida 会进入交互式 REPL，直接敲：

| 命令 | 作用 |
|---|---|
| `wsSend("hello")` | 主动发一条 |
| `wsState()` | 返回 `OPEN` / `CLOSING` / `CLOSED` |
| `wsConnect("ws://other:9000")` | 换地址重连 |
| `wsClose()` | 断开 |
| `wsMute()` / `wsUnmute()` | 开关消息流量日志 |
| `wsHelp()` | 帮助 |

### ⚠️ REPL 会被异步日志冲烂

Frida REPL 是行式输入，而 `console.log` 从别的线程随时插进来。消息一多就会看到：

```
[Remote::Gadget ]-> wsSend(hellofromclie[ws] ← {"from":"server",...}
nt)
ReferenceError: 'hellofromclient' is not defined
```

**打错的不是你的手，是日志把字符串劈开了。** 三种解法：

1. **`wsMute()`** —— 打字前先静音，最省事
2. **把日志重定向到文件**，REPL 就干净了：
   ```powershell
   frida -U -n Gadget -l E:\phigros4_re\network\ws\phigros_ws.js -o ws.log
   ```
   另开一个窗口 `Get-Content ws.log -Wait` 看消息
3. 别开 `KEEPALIVE`（默认已关）

> 另：`AUTO_SEND` 和 `KEEPALIVE` 是连通性冒烟测试用的脚手架。链路验证完之后，把这两个都设成 `false` 就是个干净的客户端。

---

## 7 · 排障

| 现象 | 原因 / 处理 |
|---|---|
| `找不到 TDS WebSocket 类` | 进程不对，确认包名 `com.PigeonGames.Phigros` |
| `onError: java.net.ConnectException` | 服务端没起 / `adb reverse` 没做 / 防火墙 |
| `未连接 (state=CLOSED)，消息未发出` | `sendMessage` 在非 OPEN 状态**静默丢弃**，先 `wsState()` |
| 一直 `CLOSED`，服务端无日志 | 多半是 `adb reverse` 没做，或服务端没起（明文不是原因，见下） |
| 中文乱码 | frida 控制台先 `chcp 65001` |

### 关于明文 `ws://` —— 已确认可用

两条理由，任一条都够了：

1. **应用自己放行了明文。** manifest 里的 `android:networkSecurityConfig=@0x7f0d0000` 指向 `res/8G.xml`：
   ```xml
   <base-config cleartextTrafficPermitted="true">
   ```
   不受 targetSdk 30 的默认禁明文限制。

2. **这套库根本不查策略。** `com.tds.common.websocket` 是 Java-WebSocket，走裸 `SocketChannel`；`NetworkSecurityPolicy` 那套只约束主动查询它的库（OkHttp / HttpURLConnection）。

**实测已通** —— `ws://127.0.0.1:8765` 全链路跑通。

### 关于 `wss://` —— 自签名也能用

`res/8G.xml` 里还有 `<certificates src="user" />`，即**信任用户安装的 CA**。

配合 `WebSocketClient` 建 SSL 上下文的方式：

```java
SSLContext sSLContext = SSLContext.getInstance("TLSv1.2");
sSLContext.init(null, null, null);      // null TrustManager = 走系统默认 → 读 NSC
```

`null` TrustManager 意味着使用系统默认信任管理器，而 Android N+ 的默认信任管理器会读取 network security config。

**结论**：把自签证书装进 Android 的**用户**证书库（设置 → 安全 → 安装证书），`wss://` 就能用，**无需 root**。附带好处——抓 HTTPS 包也不再需要 root 改系统证书。

---

## 附：这套东西为什么能work

`com.tds.common.websocket.*` 是完整移植的 Java-WebSocket（RFC6455），TapTap TDS 用它做好友服务。**但游戏自身在 dex 里从不调用它**——没有调用点，没有反射字符串。

等于 APK 里白送了一套 WS 库。我们从 Frida 直接驱动它的单例，连自己的服务器。

详见 `E:\phigros4_re\docs\network-analysis.md` §04 与 §07。
