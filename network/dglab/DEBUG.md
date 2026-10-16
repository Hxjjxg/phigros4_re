# 2026-10-17 联调记录

## 重启后复现（用户授权继续）

- 新游戏 PID 11382。最终 padding 调整后的真机 probe 全部通过，截图文字清晰。
- 新宿主 PID 11544，增加 detached reason / destroyed 时间日志。切换到 DG-LAB 后约 30 秒
  状态停在 01:00:37；在后台测试 TCP/WS 握手 3 秒超时。PID 仍为 11382，v2 freezer=0。
- 01:04:47 切回游戏，收到 `connection-terminated`，crash=None，script destroyed。
  因 PID 未变，不能将这次事件归因为 Android 杀进程。
- 系统 logcat 明确存在 OplusHansManager 对 DG-LAB 的 freeze、OAppNetControlService 网络黑名单；
  Phigros 对应时段日志已滚动掉，尚未直接确认其规则。
- 当前根假设：ColorOS 后台管控导致网络/调度停顿。对比假设：Gadget 独立失联、Java
  polling 阻塞。实验：先只改变 Phigros 的系统“允许后台活动”，再进行同样前后台切换，
  同时采集 Hans 日志和宿主状态；若仍失败，检查后两项，不改协议代码。
- 曾尝试新脚本 Java.choose 清理旧 transport，默认 app classloader 找不到动态 dex 类。
  这是 classloader 选择问题，未执行任何清理；不能据此认为 Java server 不存在。

## 已完成

- `core.test.js` 5 tests passed。
- `device_probe.py` 两次通过，第二次增加真实 Android 控件校验、波形/通道切换、保存、
  STOP/重启和重连。Game PID 20924，Frida16 16.7.19。
- 首张二维码经 OpenCV decode 与配对文本相同。
- 第二张 UI 截图处于窗口切换动画中，且缩小布局后按钮默认 padding 导致文字裁切。
  已将嵌入模式的固定高度按钮和数值输入设为零竖向 padding，截图等待改为 1 秒。
  此最后一轮视觉调整尚未重新真机验收。

## 运行观察：切换 App 后宿主状态停滞

- Persistent host PID 35052，F:\frida16\Scripts\python.exe。
- `work/dglab/status.json` 最后更新时间 00:39:54，之前状态 LISTENING。
- 手机前台 DG-LAB 4（com.bjsm.dungeonlabs4，4.2.6），用户已手动登录。
- 所有判定输出强度为 0；没有与官方 App 完成配对，也没有发送实体设备输出任务。
- 00:50 左右只读检查：Phigros PID 20924 存在，主线程 sleeping；ActivityManager 报
  `isFrozen=false`，`/sys/fs/cgroup/uid_10292/pid_20924/cgroup.freeze` 为 0。
- 切回 Phigros 后宿主 RPC 报 `frida.InvalidOperationError: script has been destroyed`。
- 日志：`work/dglab/host.log`、`work/dglab/host-error.log`。

## 待验证假设（未确认，不应据此修改代码）

1. 系统/游戏在切换 App 后暂停或回收了注入会话。支持：停滞与切换同时发生；冲突：检查时
   kernel freezer=0，PID 当时仍在。实验：用户允许继续后，在前后台切换时同步记录 PID、
   detach reason、Java queue 与独立纯 JS timer。
2. 宿主连接/ADB Gadget 转发失联。支持：RPC 阻塞而宿主仍在；冲突：还未独立探测 socket。
   实验：保留现有会话，限时连接 listener 验证 TCP/WS 与 Frida 各自状态。
3. Java polling 或 Frida Java bridge 在后台时阻塞。支持：当前 timer 访问 Java；冲突：短时
   前台联调与多次正常 teardown 都通过。实验：只读的无 Java JS timer 对比 Java poll。

用户初始要求遇到 Frida 困难停止并报告；故在看到上述错误后停止手机操作，未切换 Frida17，
未盲目重启或修改系统冻结策略。需要用户允许恢复调试后再继续验证。

## 2026-10-17 当前复现：主机连接被拒绝

### Observations

- `device_probe.py` 在连接 `ws://127.0.0.1:9998` 时失败：`ConnectionRefusedError: [WinError 1225]`。
- 本机 `netstat` 没有 9998/8767 listener；`adb forward --list` 只剩 `tcp:27042`。
- `work/dglab/host.log` 记录上一次宿主因 `connection-terminated` 收到 detach，随后 `HOST STOPPED`；`status.json` 是旧的 `LISTENING` 快照。
- `EmbeddedTransport.java` 当前 `sendTo()` 已不再直接拒绝 `hasBufferedData()`，但设备上的 dex 仍需在新进程启动时加载。

### Hypotheses

1. **H1（ROOT）：宿主进程/ADB 9998 转发已停止，导致连接被拒绝。** 支持：无 listener、无宿主输出、转发列表缺失；实验：恢复两个 forward 并启动 Frida 16 host，观察 9998 是否可连接。
2. H2：设备仍加载旧 dex，握手会在第二条消息时触发 backpressure。支持：动态 dex 只在新游戏进程加载；冲突：当前源码已移除立即拒绝；实验：重建并 push dex 后重启游戏，观察 host 日志和 clientId。
3. H3：DG-LAB 使用了过期 targetId。支持：宿主每次重启会生成新 targetId；冲突：过期 ID 通常应是协议拒绝而不是 TCP connection refused；实验：连接前读取最新 status.json。

### Experiments

- 先执行 H1：恢复 `adb forward tcp:9998 tcp:9998` / `8767`，启动 Frida 16 host，并运行最小 WebSocket 探测。成功标准是 TCP/WS 不再 connection refused，且 status.json 更新时间推进。

### Experiment result: H1

- Restored all three ADB forwards and launched Phigros, then attached with `F:\frida16\Scripts\python.exe`.
- 9998 and 8767 are now listening on the host; status timestamp advances and a WebSocket handshake returned `hello` plus `controller_attached`.
- A simulated client completed `devices.get` request/response without `Connection unavailable or backpressure`.
- H1 confirmed: the immediate user-facing failure was the stopped host/absent port forward. The current target ID is regenerated on each host session, so stale QR text must be replaced.

### Root Cause (current incident)

The test was pointed at a host port whose Frida host process had detached; no TCP listener existed on 9998.

### H4 (ROOT for official App failure): strict OkHttp handshake rejects the APK server header

- Official DG-LAB logcat at 01:47:23 shows the connection reaches the embedded listener, then fails with `java.net.ProtocolException: Expected 'Connection' header value 'Upgrade' but was 'Upgrade; close', 101`.
- The server history has no stable paired client because OkHttp closes immediately after parsing the 101 response.
- Python `websockets` accepted the same server, which explains why the earlier simulated probe passed.
- The APK's `Draft_6455` copies the request's combined `Connection` header into the response. The official client can supply `Upgrade; close`; strict OkHttp requires the response value to be exactly `Upgrade`.
- Production fix: install a Frida hook on `Draft_6455.postProcessHandshakeResponseAsServer` that normalizes the response header to `Connection: Upgrade` after the APK implementation runs.
