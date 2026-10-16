# Phigros 内置 DG-LAB V4 服务端

采用 `prompt2.txt` 的方案 B：游戏进程内的 Java WS listener + 独立协议核心 + 内部控制 API。
没有 Bun、Node 或连回自己的 WS 客户端；PC 仅用于 Frida 注入。DG-LAB 4 App
在同一手机连接 `ws://127.0.0.1:9998/?tid=...`，由官方 App 管理蓝牙。

## 模块边界

| 文件 | 职责 |
| --- | --- |
| `android/EmbeddedTransport.java` | 复用 APK 自带 `com.tds.common.websocket.server.WebSocketServer`；事件队列、连接、原生 ping/pong、关闭线程 |
| `core.js` | 单控制方/单 App 配对、RPC、超时、设备缓存、判定到操作的映射；无 Java/Frida 依赖 |
| `frida.js` | Java adapter、20 ms 事件轮询、启停、校验、RPC 导出 |
| `vendor/waveforms.js` | kit 的 BUBBLE / RHYTHM / CLIMB 波形数据及许可证 |
| `../../hooks/judgment/src/` | 已验证的 IL2CPP 判定 hook、Unity 设置入口、Android 设置面板 |

网络端只接受带正确 `tid` 或 `targetId` 的 DG-LAB 被控 App；不提供外部控制者连接。
控制 API 是进程内部 JS/Frida RPC，不是另一个开放的网络控制端点。第二个 App 返回
`controller_busy` 并关闭。每次注入生成随机配对 ID，同一次注入内停启服务不改变 ID。

## 构建和启动（Frida 16）

在仓库根目录执行：

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File network\dglab\build-dex.ps1
python hooks\judgment\build.py --embedded
adb push network\dglab\EmbeddedTransport.dex /data/local/tmp/EmbeddedTransport.dex
adb forward tcp:27042 tcp:27042
& 'F:\frida16\Scripts\python.exe' hooks\judgment\run.py --embedded
```

用 runner 的 `settings` 打开面板，或点击游戏 Gameplay 设置中新增的 **DG-LAB link**。
`status` 查看服务端与设备状态。`quit` 等待 UI、网络、Unity 入口全部清理后卸载。
不要同时注入旧判定 bundle 和内置服务端 bundle。修改 Java 后，已加载的类要重启
游戏才能更新；单纯重新 push dex 不会替换同名已加载类。

可使用 `network/dglab/host.py` 持续托管。它将状态写到 `work/dglab/status.json`，
读取 `work/dglab/command.json`，支持：

```json
{"operation":"settings"}
{"operation":"close"}
{"operation":"configure","config":{"enabled":true}}
{"operation":"stop"}
{"operation":"quit"}
```

一次写一个 JSON 文件。`stop` 禁用并清理输出；`quit` 清理并退出宿主。
如果 Unity 设置页暂停导致清理报错，回到游戏设置页后再次发送 `quit`。
不要直接结束宿主进程或使用 CLI 自动热重载；这会绕过 await dispose。

## 面板和同机配对

- 监听地址固定为 loopback，可改端口（1024–65535），无需局域网和 adb reverse。
- Good / Bad / Miss 各自设置强度、持续时间和波形；点击波形按钮轮换预设。
- 通道 A/B；可指定 slotId。留空仅在恰好一个兼容设备时自动选择。
- 自动判定输出目前支持 **COYOTE_030（郊狼 3）**，波形 `ver:3`。其他设备仍可发现、
  查看状态；不能把郊狼 3 波形直接用于其他设备。底层 `operate` API 支持显式 V2/V3 帧。
- 强度默认 0（关闭该档输出）。输入范围 0–200，100–30000 ms；这只是本集成的参数范围，
  实际输出还受 DG-LAB App 的通道限制和设备状态约束。
- 默认最小触发间隔 150 ms，可设 100–5000 ms。密集判定不逐条排队；新任务替换同通道
  同类型旧任务。Perfect hook 保留用于统计，不触发设备。
- 底部固定 STOP 按钮禁用联动，发送 `device.op.clear` 后关闭服务端。
- 配置存于 `phigros_dglab_v4` SharedPreferences，与旧 WS 客户端配置分开。

二维码内容遵循上游 README：

```text
https://dungeon-lab.cn/s/?v=1&action=socket&url=<encodeURIComponent(wsURL-with-tid)>
```

同机不能用相机扫描自己的屏幕，可保存截图后导入，或使用 DG-LAB 4 的
**Socket Control → Enter server URL** 输入 `ws://127.0.0.1:9998/?tid=<targetId>`。
面板提供 Copy pairing link（复制完整二维码链接）；部分 App 入口需要的是内层 WS URL，
不能把两者混用。App 4.2.6 的 Socket Control 需要先登录。

## 自定义 API

以下均是注入脚本内可调用的异步 JS API；可通过 Python `script.exports_sync` 调用表中 RPC。

| JS | Frida RPC | 用途 |
| --- | --- | --- |
| `phiDglab.status()` | `dglabstatus()` | 状态、设备、pending、错误和最近 40 条事件 |
| `phiDglab.devices()` | `dglabdevices()` | 缓存设备列表 |
| `phiDglab.refresh()` | `dglabrefresh()` | `devices.get` 并等待响应 |
| `phiDglab.operate(data)` | `dglaboperate(data)` | 校验并发送一个 `device.op`，等待任务结束响应 |
| `phiDglab.clear(slotId?, channel?)` | `dglabclear(...)` | 清理全部/指定设备/指定通道的任务 |
| `phiDglab.stop()` | `dglabstop()` | 持久化禁用联动并停止服务端 |
| `phiDglab.pairing()` | `dglabpairing()` | 当前配对二维码文本 |
| `phiConfigure(config)` | `configure(config)` | 更新设置（rules 提交完整三档） |

例如，清理某个设备的 A 通道：

```javascript
await phiDglab.clear('slot-from-device-list', 0);
```

通用 `operate` 参数：`{s:slotId, c:0|1, t:0|3|4|7, v:..., d:milliseconds,
p:0|1|2, im:boolean}`。t=0 为波形，t=3 相对强度，t=4 临时强度，t=7 只能将强度置 0。
波形传 `ver:2` 的三字节帧或 `ver:3` 的八字节帧，允许十六进制字符串或字节数组。
自动判定会把预设循环展开成 `ceil(duration/100)` 帧，发送并行的 t=4 和 t=0 两个任务，
均使用有限 `d` 和 `im:true`；不会等待强度任务结束才发送波形。

`device.op` 响应的 `reason` 保留 `completed/cleared/replaced/cancelled`，不能把发送计数
当作设备执行完成。默认 RPC 超时 8 秒，持续任务取 `max(8秒,d+2秒)`。断线拒绝 pending、
清空缓存，不重发操作；500 ms 以上的过时判定丢弃。最多 128 个 pending、256 个待处理判定、
512 个 Java 网络事件；队列溢出停止服务。原生 WS ping/pong 由 APK 库处理，业务 heartbeat
间隔 30 秒。嵌入控制器不使用上游远程控制者的 5 分钟空闲退出规则。

## 验证记录与当前限制

```powershell
node --test network\dglab\tests\core.test.js
adb forward tcp:9998 tcp:9998
& 'F:\frida16\Scripts\python.exe' network\dglab\tests\device_probe.py
```

2026-10-17，OPPO PGJM10 / Frida 16.7.19：

- 5 项协议核心测试通过，包括超时、错误输入、快照/嵌套增量、来源匹配、断线与无重放。
- 真机 Java 服务端 + PC 模拟 DG-LAB App：握手、单 App 限制、设备发现/状态增量，
  三种模拟判定产生六条任务、清理、真实面板输入/保存、STOP/重启、重连、开面板卸载通过。
- 二维码截图经 OpenCV 解码与目标文本一致。证据在 `evidence/device-probe.json`、
  `evidence/settings-panel.png`；截图中的设备明确是模拟设备。
- 原有四种原生判定 hook 已有真实游玩对照证据，见 `hooks/judgment/evidence/`。
  本次新增映射测试通过测试专用入口送入判定队列；未宣称已完成实体设备输出测试。
- 官方 DG-LAB 4.2.6 已完成同机配对、COYOTE_030 设备发现及实际游玩触发，用户确认实体设备成功输出。
  修复了 APK WS 库回显 `Connection: Upgrade; close` 导致官方 OkHttp 拒绝握手的问题；
  `frida.js` 将服务端响应头规范化为 `Connection: Upgrade`。
- 100 ms 任务的 26 条响应样本：派发到任务响应 63–175 ms，中位数 109 ms，平均 104 ms。
  这是应用层任务响应时间，不是物理输出起始延迟，也不包含判定 hook 到派发的完整调度时间；
  不应直接减去设定持续时间来推断蓝牙或物理输出延迟。
- ADB 断线曾导致宿主退出；后台长期可靠性仍需验证。`status.json` 的旧快照不代表服务仍在运行，
  应检查 `hostTimestamp` 是否更新。宿主重启后配对 ID 会改变，需更新 DG-LAB 的连接地址。

`device_probe.py` 会自行注入并启动服务，只应在没有其他判定脚本或 `host.py` 运行时使用。
它通过模拟 App 测试，不代替官方 App 与实体设备验收。该测试需要 27042、9998 转发，
不需要 8767；官方 App 在同一手机连接时本身不需要 ADB 转发。

当前仍是 Frida 注入式嵌入；不是已重打包到 APK、脱离 Frida 自动启动的发布版本。
