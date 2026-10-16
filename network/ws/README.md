# WebSocket — 运行手册

APK 里同时带着**完整可用的客户端和服务端**两套 RFC6455 实现，游戏自己一个都不用。
这里把两边都接出来。

## 两个方向，别搞混

| | 方向 A：游戏当**客户端** | 方向 B：游戏当**服务端** |
|---|---|---|
| 谁连谁 | 游戏 → 你的 PC | 你的 PC → 游戏 |
| 复用 APK 里的 | `...websocket.WebSocketClient` | `...websocket.server.WebSocketServer` |
| Frida 脚本 | `phigros_ws.js` | `phigros_ws_server.js` |
| PC 侧配套 | `ws_server.py`（PC 当服务端） | 随便一个 WS 客户端 |
| 端口转发 | `adb reverse tcp:8765 tcp:8765` | `adb forward tcp:8767 tcp:8767` |
| 需要编译 | 否 | **是**（`build-server-dex.ps1`） |

> 命名注意：`ws_server.py` 是 **PC 侧**的服务端（给方向 A 用）；
> `PhigrosWSServer` 是**游戏侧**的服务端（方向 B）。两个 "server" 不是一回事。

本文档 §1–§7 讲**方向 A**，§8 讲**方向 B**。

| 文件 | 作用 |
|---|---|
| `ws_server.py` | 方向 A：PC 侧测试服务端 |
| `phigros_ws.js` | 方向 A：Frida 注入脚本 |
| `server/PhigrosWSServer.java` | 方向 B：子类源码 |
| `server/stubs/` | 方向 B：编译期桩（不入 dex） |
| `build-server-dex.ps1` | 方向 B：javac + d8 |
| `PhigrosWSServer.dex` | 方向 B：编译产物 |
| `phigros_ws_server.js` | 方向 B：Frida 注入脚本 |

---

## 0 · 当前状态

**两个方向都已在真机跑通**（OPPO PGJM10 · frida 16.7.19 · gadget 16.7.19）。

| 方向 | 环节 | 状态 |
|---|---|---|
| A · 游戏当客户端 | PC 服务端 / 双向收发 / 控制台推送 | ✅ 2026-10-16 验证 |
| A · 游戏当客户端 | `heads()` 注入握手 header | ✅ 服务端收到 `x-from: frida` |
| B · 游戏当服务端 | dex 动态加载 / `start()` / 握手 | ✅ 2026-10-17 验证 |
| B · 游戏当服务端 | 回声 / 服务端主动推 / RPC 群发 / 关闭码 1000 | ✅ 2026-10-17 验证 |

方向 B 的证据见 [§8.4](#84-注入)。

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

## 8 · 方向 B —— 游戏当服务端

APK 里那套 `com.tds.common.websocket.server.WebSocketServer` 是完整可用的服务端
（**已离线编译实测**：握手 / 收发 / 多客户端并发 / 干净关闭全通过），但它是 `abstract`
且全项目零子类、零引用。这里补上缺失的子类。

### 8.1 编译 dex

```powershell
cd E:\phigros4_re\network\ws
.\build-server-dex.ps1
```

产出 `PhigrosWSServer.dex`（约 3.4 KB）。改了 `server/PhigrosWSServer.java` 才需要重来。

编译用的是 `server/stubs/` 下的**编译期桩** —— 签名逐个对照 `classes.dex` 字节码核过。
桩只为让 javac 通过，**不会被打进 dex**；真实基类在设备运行时由 APK 提供。

### 8.2 推到设备

```powershell
adb push PhigrosWSServer.dex /data/local/tmp/
adb shell chmod 644 /data/local/tmp/PhigrosWSServer.dex
```

> `Java.openClassFile()` 读的是**设备侧**路径，不是 PC 的。这一步不能省。

### 8.3 端口转发（PC → 设备）

```powershell
adb forward tcp:8767 tcp:8767
```

注意是 **`forward`** —— 方向 A 用的是 `reverse`，两者相反。

不想用 adb 的话，让 PC 直接连设备的局域网 IP:`8767` 也行。

### 8.4 注入

**连接方式用 `-H 127.0.0.1:27042`（走 adb forward），不是 `-U`：**

```powershell
F:\frida16\Scripts\frida.exe -H 127.0.0.1:27042 -n Gadget -l phigros_ws_server.js
```

预期：

```
[wss] 已加载 /data/local/tmp/PhigrosWSServer.dex
[wss] start() 已调用，端口 8767
[wss]   日志: adb logcat -s PhigrosWSS
[wss] 就绪 —— REPL 里敲 wssHelp()
```

另开窗口看服务端日志：

```powershell
adb logcat -s PhigrosWSS
```

### ✅ 2026-10-17 真机验证通过

设备 OPPO PGJM10 / frida 16.7.19 / gadget 16.7.19。两侧日志：

```
# 设备侧 (adb logcat -s PhigrosWSS)
00:09:37.898  I PhigrosWSS: onStart  port=8767  addr=::/:::8767
00:09:40.188  I PhigrosWSS: OPEN  /127.0.0.1:44403  path=/probe  clients=1
00:09:40.195  I PhigrosWSS: RECV  hello from PC
00:10:07.957  I PhigrosWSS: CLOSE code=1000 reason= remote=true  clients=0

# PC 侧
① 欢迎语 : welcome from phigros (server side)
② 回声   : echo: hello from PC
③ 推送   : server-push #1 / #2 / #3 from frida
```

验证覆盖：dex 动态加载 / `$new(InetSocketAddress)` / `start()` / 握手（自定义 path）/
服务端主动推送 / 客户端→服务端 / `onMessage` 回声 / **Frida RPC 触发广播** / 关闭码 1000。

两个实测细节：

- 绑定结果是 `::/:::8767` —— IPv6 通配地址，Android 双栈会一并接 IPv4
- 设备看到的客户端地址是 `127.0.0.1:44403` —— `adb forward` 的关系，不是真实 PC IP

### 8.5 REPL 命令

| 命令 | 作用 |
|---|---|
| `wssBroadcast("hi")` | 给所有已连客户端群发 |
| `wssClients()` | 看当前连接数和各自地址 |
| `wssStop()` | 停服务 |
| `wssRestart()` | 停 + 重起 |
| `wssHelp()` | 帮助 |

### 8.6 排障

| 现象 | 原因 |
|---|---|
| `openClassFile 失败` | dex 没 push / 权限不是 644 / 没跑 build 脚本 |
| `$new 失败` | 设备上那个 `WebSocketServer` 的构造器签名和桩对不上（改动过版本？重核 dex） |
| `start()` 后没反应 | 端口被占，或忘了显式传地址（无参构造绑 **80**，Android 上要 root） |
| 客户端连不上 | `adb forward` 忘了做；或设备防火墙 |
| 想看服务端输出 | `adb logcat -s PhigrosWSS` |

### 8.7 为什么用 dex 注入而不是 `Java.registerClass`

`registerClass` 不会按 `superClass` 自动生成构造器 —— 得手写 `$init`，还要在 JS 里
声明参数类型，而且这套语义在 frida 16 / 17 之间不保证一致（本项目跑在 **16.7.19**）。

dex 注入没有这些不确定性：子类是真的编译过的 Java，构造器、override、签名全是
编译期确定的。代价是多一步编译 + push。

---

## 附：这套东西为什么能work

`com.tds.common.websocket.*` 是完整移植的 Java-WebSocket（RFC6455），TapTap TDS 用它做好友服务。**但游戏自身在 dex 里从不调用它**——没有调用点，没有反射字符串。

等于 APK 里白送了一套 WS 库。我们从 Frida 直接驱动它的单例，连自己的服务器。

详见 `E:\phigros4_re\docs\network-analysis.md` §04 与 §07。
