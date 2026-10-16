/*
 * phigros_ws.js — 在 Phigros 进程内复用其自带的 Java-WebSocket 客户端收发消息
 *
 * 原理：com.tds.common.websocket.* 是一套完整自包含的 RFC6455 实现，
 *       游戏自身从不调用它。我们从 Frida 直接驱动 TDSWebSocketService 单例，
 *       连自己的服务器。（详见 docs/network-analysis.md §07）
 *
 * 用法：
 *   adb reverse tcp:8765 tcp:8765
 *   frida -U -f com.PigeonGames.Phigros -l phigros_ws.js
 *
 * 连上后在 frida REPL 里可用：
 *   wsSend("hello")          主动发消息
 *   wsState()                查看连接状态
 *   wsConnect("ws://...")    换地址重连
 *   wsClose()                断开
 */

'use strict';

// ── 配置 ─────────────────────────────────────────────────────────────
// 推荐走 adb reverse（USB 转发，不依赖 IP / 防火墙）：
//   adb reverse tcp:8765 tcp:8765   →  用 127.0.0.1
// 走局域网则改成 PC 的 LAN IP，例如 ws://10.32.20.219:8765
// Optional embedding options; the standalone smoke-test defaults are unchanged.
const OPTIONS = globalThis.PHIGROS_WS_OPTIONS || {};
const WS_URL    = OPTIONS.url || 'ws://127.0.0.1:8765';
const AUTO_SEND = OPTIONS.autoSend !== false;    // 连上后自动发一条 hello，验证出站
const KEEPALIVE = false;   // 每 10 秒发一条计数心跳。已证明链路可用；
                           // 开着会刷屏 REPL —— 要长时间观察时再打开

const TAG = '[ws]';

let VERBOSE = OPTIONS.verbose !== false; // 消息流量日志开关

function log(msg) {                        // 系统日志：始终打印
    console.log(TAG + ' ' + msg);
}

function tlog(msg) {                       // 流量日志：可静音
    if (VERBOSE) console.log(TAG + ' ' + msg);
}

// ── 主逻辑 ───────────────────────────────────────────────────────────
Java.perform(function () {

    let Svc, Bld, HashMap;

    try {
        Svc     = Java.use('com.tds.common.websocket.TDSWebSocketService');
        Bld     = Java.use('com.tds.common.websocket.TDSWebSocketService$WebSocketConfigBuilder');
        HashMap = Java.use('java.util.HashMap');
    } catch (e) {
        console.error(TAG + ' 找不到 TDS WebSocket 类 —— 目标进程不对？');
        console.error(TAG + ' ' + e);
        return;
    }

    log('类已加载，开始挂钩子');

    // ── 入站：挂 TDSWebSocketService 的四个回调 ──────────────────────
    // onMessage 在没有注册监听器时会安全 return，转调原实现不会炸
    try {
        Svc.onMessage.overload('java.lang.String').implementation = function (m) {
            tlog('← ' + m);
            return this.onMessage(m);
        };
        log('  ✓ hook onMessage(String)');
    } catch (e) { console.error(TAG + ' hook onMessage 失败: ' + e); }

    try {
        Svc.onOpen.implementation = function () {
            log('✓ 连接已建立 (onOpen)');
            return this.onOpen();
        };
        log('  ✓ hook onOpen()');
    } catch (e) { console.error(TAG + ' hook onOpen 失败: ' + e); }

    try {
        Svc.onClose.overload('int', 'java.lang.String', 'boolean').implementation =
            function (code, reason, remote) {
                tlog('✗ 连接关闭 code=' + code +
                     ' reason=' + reason +
                     ' remote=' + remote);
                return this.onClose(code, reason, remote);
            };
        log('  ✓ hook onClose(int,String,boolean)');
    } catch (e) { console.error(TAG + ' hook onClose 失败: ' + e); }

    try {
        Svc.onError.overload('java.lang.Exception').implementation = function (e) {
            tlog('! 错误: ' + e);
            return this.onError(e);
        };
        log('  ✓ hook onError(Exception)');
    } catch (e) { console.error(TAG + ' hook onError 失败: ' + e); }

    // ── REPL 接口 ────────────────────────────────────────────────────
    const STATE = { 1: 'OPEN', 2: 'CLOSED', 3: 'CLOSING' };

    globalThis.wsConnect = function (url) {
        const target = url || WS_URL;
        Java.perform(function () {
            try {
                const heads = HashMap.$new();
                heads.put('X-From', 'frida');
                heads.put('X-PoC', 'phigros-ws');

                // 已确认签名：getBuilder() / connectTimeOut(int) / heads(Map) / build()
                const cfg = Bld.getBuilder()
                               .connectTimeOut(45)
                               .heads(heads)
                               .build();

                // setDestUrl is a no-op for the same URL/config, even after disconnect.
                Svc.getInstance().reset();
                Svc.getInstance().setDestUrl(target, cfg);
                log('→ 正在连接 ' + target);
            } catch (e) {
                console.error(TAG + ' 连接失败: ' + e);
            }
        });
    };

    globalThis.wsSend = function (text) {
        Java.perform(function () {
            const st = Svc.getInstance().getConnectionState();
            if (st !== 1) {
                // sendMessage 在非 OPEN 状态会静默丢弃，这里显式提醒
                log('未连接 (state=' + (STATE[st] || st) + ')，消息未发出');
                return;
            }
            Svc.getInstance().sendMessage(String(text));
            tlog('→ ' + text);
        });
    };

    globalThis.wsState = function () {
        let st;
        Java.perform(function () { st = Svc.getInstance().getConnectionState(); });
        return STATE[st] || ('UNKNOWN(' + st + ')');
    };

    globalThis.wsClose = function () {
        Java.perform(function () { Svc.getInstance().disconnect(); });
        log('已断开');
    };

    globalThis.wsMute = function () {
        VERBOSE = false;
        log('已静音 —— REPL 输入不再被打断。wsUnmute() 恢复');
    };

    globalThis.wsUnmute = function () {
        VERBOSE = true;
        log('已取消静音');
    };

    globalThis.wsHelp = function () {
        log('wsSend(text) / wsState() / wsConnect(url) / wsClose()');
        log('wsMute() / wsUnmute() —— 开关消息流量日志');
    };

    // ── 自动建连 ─────────────────────────────────────────────────────
    // 延后 2 秒，等 Unity 起来、避免和启动期抢线程
    if (OPTIONS.autoConnect !== false) setTimeout(function () {
        globalThis.wsConnect(WS_URL);

        if (AUTO_SEND) {
            setTimeout(function () {
                globalThis.wsSend(JSON.stringify({
                    from: 'game',
                    type: 'hello',
                    t: Date.now(),
                }));
            }, 3000);
        }

        if (KEEPALIVE) {
            let n = 0;
            setInterval(function () {
                n += 1;
                globalThis.wsSend(JSON.stringify({
                    from: 'game',
                    type: 'ping',
                    seq: n,
                    t: Date.now(),
                }));
            }, 10000);
        }
    }, 2000);

    log('就绪 —— REPL 里敲 wsHelp() 看可用命令');
});
