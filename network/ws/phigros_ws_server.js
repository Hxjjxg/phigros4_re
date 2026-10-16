/*
 * phigros_ws_server.js — 让 Phigros 自己当 WebSocket 服务端，外部连进来
 *
 * 方向与 phigros_ws.js 相反：
 *   phigros_ws.js          游戏 = 客户端，连你的服务器
 *   本脚本                  游戏 = 服务端，你的机器去连它
 *
 * 原理：APK 里自带一套完整可用的 RFC6455 服务端
 *       (com.tds.common.websocket.server.WebSocketServer)，
 *       但它是 abstract 且全项目零子类、零引用。
 *       这里补上缺失的那层子类（已编译成 PhigrosWSServer.dex），
 *       运行时动态加载进游戏进程。
 *
 * 为什么用 dex 注入而不是 Java.registerClass：
 *   registerClass 不会按 superClass 自动生成构造器，得手写 $init 并在
 *   JS 里声明参数类型，语义在 frida 16/17 之间还可能不同。dex 注入没有
 *   这些不确定性 —— 子类是真的编译过的 Java，构造器、override 全是编译期确定的。
 *
 * ─── 跑之前 ────────────────────────────────────────────────────────
 *   1) 编译 dex（改了 PhigrosWSServer.java 才需要重来）
 *        .\build-server-dex.ps1
 *   2) 推到设备
 *        adb push PhigrosWSServer.dex /data/local/tmp/
 *        adb shell chmod 644 /data/local/tmp/PhigrosWSServer.dex
 *   3) 端口转发（PC → 设备，注意是 forward 不是 reverse）
 *        adb forward tcp:8767 tcp:8767
 *   4) 注入（用 16 那套，和 gadget 版本匹配）
 *        F:\frida16\Scripts\frida.exe -U -n Gadget -l phigros_ws_server.js
 *   5) 本机连
 *        ws://127.0.0.1:8767
 *
 *   服务端日志走 android.util.Log，tag = PhigrosWSS：
 *        adb logcat -s PhigrosWSS
 *
 * ─── REPL ─────────────────────────────────────────────────────────
 *   wssBroadcast("hi")   给所有已连客户端群发
 *   wssClients()         看当前连接数
 *   wssStop()            停服务
 *   wssRestart()         停 + 重起
 *   wssHelp()            帮助
 */

'use strict';

const PORT     = 8767;
const DEX_PATH = '/data/local/tmp/PhigrosWSServer.dex';
const CLASS    = 're.phigros.PhigrosWSServer';
const TAG      = '[wss]';

function log(m) { console.log(TAG + ' ' + m); }

Java.perform(function () {

    // ── 1. 加载 dex ─────────────────────────────────────────────────
    let Server;
    try {
        // openClassFile 走的是【设备侧】文件系统，不是 PC 的路径
        const cf = Java.openClassFile(DEX_PATH);
        cf.load();
        log('已加载 ' + DEX_PATH);
    } catch (e) {
        console.error(TAG + ' openClassFile 失败: ' + e);
        console.error(TAG + ' 检查：');
        console.error(TAG + '   1) 有没有 adb push ' + DEX_PATH);
        console.error(TAG + '   2) 权限是不是 644（adb shell chmod 644 ' + DEX_PATH + '）');
        console.error(TAG + '   3) 没有 dex 的话先跑 build-server-dex.ps1');
        return;
    }

    try {
        Server = Java.use(CLASS);
    } catch (e) {
        console.error(TAG + ' 找不到 ' + CLASS + ' —— dex 载入成功但类没注册上？');
        console.error(TAG + ' ' + e);
        return;
    }

    const InetSocketAddress = Java.use('java.net.InetSocketAddress');

    // ── 2. 起服务 ───────────────────────────────────────────────────
    let server = null;

    function startServer(port) {
        const p = port || PORT;
        if (server !== null) {
            log('已经在跑了，先 wssStop()');
            return;
        }
        try {
            // 基类的无参构造绑 80 端口，Android 上要 root —— 必须显式传地址
            server = Server.$new(InetSocketAddress.$new(p));
            server.start();
            log('start() 已调用，端口 ' + p);
            log('  日志: adb logcat -s PhigrosWSS');
        } catch (e) {
            console.error(TAG + ' 启动失败: ' + e);
            server = null;
        }
    }

    globalThis.wssStart   = startServer;
    globalThis.wssRestart = function (port) {
        globalThis.wssStop();
        setTimeout(function () { startServer(port); }, 300);
    };

    globalThis.wssBroadcast = function (text) {
        Java.perform(function () {
            try {
                const n = Server.broadcast(String(text));
                log('→ 群发 ' + n + ' 个客户端: ' + text);
            } catch (e) {
                console.error(TAG + ' broadcast 失败: ' + e);
            }
        });
    };

    globalThis.wssClients = function () {
        let n = 0;
        Java.perform(function () {
            try {
                const list = Server.CLIENTS.value;   // static List<WebSocket>
                n = list.size();
                log('当前连接数: ' + n);
                for (let i = 0; i < n; i++) {
                    let addr = '?';
                    try { addr = list.get(i).getRemoteSocketAddress().toString(); } catch (e) {}
                    log('  [' + i + '] ' + addr);
                }
            } catch (e) {
                console.error(TAG + ' 读 CLIENTS 失败: ' + e);
            }
        });
        return n;
    };

    globalThis.wssStop = function () {
        Java.perform(function () {
            if (server === null) { log('没在跑'); return; }
            try {
                server.stop();
                log('已停止');
            } catch (e) {
                log('stop() 报错（多半无害）: ' + e);
            }
            server = null;
        });
    };

    globalThis.wssHelp = function () {
        log('wssBroadcast(text) / wssClients() / wssStop() / wssRestart(port)');
    };

    // ── 3. 自动起 ───────────────────────────────────────────────────
    setTimeout(function () { startServer(PORT); }, 2000);

    // ── 4. 给 Python 侧调用（frida CLI 的 REPL 不受影响）────────────
    rpc.exports = {
        broadcast: function (text) {
            return Server.broadcast(String(text));
        },
        clientcount: function () {
            return Server.CLIENTS.value.size();
        },
        stop: function () {
            if (server !== null) {
                server.stop();
                server = null;
                return true;
            }
            return false;
        }
    };

    log('就绪 —— REPL 里敲 wssHelp()');
});
