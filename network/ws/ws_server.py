#!/usr/bin/env python3
"""
Phigros WS 测试服务端
=====================
配 phigros_ws.js 使用。目的：验证游戏进程内那条 Java-WebSocket 能双向收发。

    python ws_server.py                 # 监听 0.0.0.0:8765
    python ws_server.py --port 9000

行为：
  · 连接时打印握手 header（用来验证 Frida 注入的 X-From 是否真的到了）
  · 收到消息 -> 打印并原样回显（带 [echo] 前缀）
  · 每 15 秒主动推一条时间戳（证明服务端发起方向也通）
  · 在服务端控制台敲字 + 回车 -> 广播给所有客户端
"""

import argparse
import asyncio
import json
import sys
import threading
import time
import datetime

try:
    import websockets
except ImportError:
    sys.exit("缺少依赖：pip install websockets")

# Windows 控制台默认 GBK，中文会炸
try:
    sys.stdout.reconfigure(encoding="utf-8")
    sys.stderr.reconfigure(encoding="utf-8")
except Exception:
    pass

CLIENTS = set()
LOOP = None
_counter = 0


def stamp():
    return datetime.datetime.now().strftime("%H:%M:%S")


def log(tag, msg):
    print(f"[{stamp()}] {tag:<7} {msg}", flush=True)


async def handler(ws, *args):
    """*args 让本函数同时兼容新旧两套 API：
       新版 serve 回调 1 个参数，legacy 回调 2 个 (ws, path)。"""
    global _counter
    _counter += 1
    cid = _counter

    # ---- 握手信息：这里能直接看到 Frida 注入的 header ----
    # 新版 API: ws.request.headers / ws.request.path
    # legacy  : ws.request_headers / ws.path
    headers = dict(getattr(getattr(ws, "request", None), "headers", {}) or {})
    path = getattr(getattr(ws, "request", None), "path", None)
    if not headers:
        headers = dict(getattr(ws, "request_headers", {}) or {})
    if path is None:
        path = getattr(ws, "path", "/") or "/"
        if args:
            path = args[0]

    log(f"#{cid}", f"CONNECT  {ws.remote_address}  path={path}")
    for k, v in headers.items():
        if k.lower() in ("x-from", "x-poc", "user-agent", "origin", "sec-websocket-protocol"):
            log(f"#{cid}", f"  header  {k}: {v}")

    # HTTP header 名大小写不敏感，dict 化之后要自己兜住
    def hget(name):
        for k, v in headers.items():
            if k.lower() == name.lower():
                return v
        return None

    if hget("X-From") == "frida":
        log(f"#{cid}", "  ✓ X-From: frida —— Frida 侧的 header 注入生效")
    else:
        log(f"#{cid}", "  · 没看到 X-From: frida（可能不是 Frida 连上来的）")

    CLIENTS.add(ws)
    try:
        await ws.send(json.dumps({
            "from": "server",
            "type": "welcome",
            "cid": cid,
            "t": time.time(),
            "msg": "服务端已就绪，回一条消息试试",
        }, ensure_ascii=False))
        log(f"#{cid}", "→ welcome")

        async for raw in ws:
            text = raw if isinstance(raw, str) else f"<{len(raw)} bytes binary>"
            log(f"#{cid}", f"← {text}")
            reply = json.dumps({
                "from": "server",
                "type": "echo",
                "t": time.time(),
                "msg": f"echo: {text}",
            }, ensure_ascii=False)
            await ws.send(reply)
            log(f"#{cid}", f"→ echo")

    except websockets.exceptions.ConnectionClosed as e:
        log(f"#{cid}", f"DISCONNECT  code={e.code} reason={e.reason!r}")
    except Exception as e:
        log(f"#{cid}", f"ERROR  {type(e).__name__}: {e}")
    finally:
        CLIENTS.discard(ws)


async def heartbeat():
    """服务端主动推送 —— 验证 服务端 -> 游戏 方向。"""
    n = 0
    while True:
        await asyncio.sleep(15)
        if not CLIENTS:
            continue
        n += 1
        payload = json.dumps({
            "from": "server",
            "type": "heartbeat",
            "seq": n,
            "t": time.time(),
        })
        await asyncio.gather(
            *[c.send(payload) for c in list(CLIENTS)],
            return_exceptions=True,
        )
        log("PUSH", f"heartbeat #{n} → {len(CLIENTS)} 个客户端")


async def broadcast(text):
    if not CLIENTS:
        log("CONSOLE", "当前没有客户端连接")
        return
    payload = json.dumps({"from": "console", "type": "push", "msg": text}, ensure_ascii=False)
    await asyncio.gather(*[c.send(payload) for c in list(CLIENTS)], return_exceptions=True)
    log("CONSOLE", f"→ {len(CLIENTS)} 个客户端: {text}")


def stdin_reader():
    """在服务端控制台敲字并回车，广播给所有客户端。"""
    for line in sys.stdin:
        line = line.rstrip("\r\n")
        if not line:
            continue
        if line in ("/quit", "/exit"):
            log("CONSOLE", "退出")
            if LOOP:
                LOOP.call_soon_threadsafe(LOOP.stop)
            return
        if LOOP:
            asyncio.run_coroutine_threadsafe(broadcast(line), LOOP)
        if not CLIENTS:
            # 没客户端时也给个回显，免得以为卡住了
            pass


async def main(host, port):
    global LOOP
    LOOP = asyncio.get_running_loop()

    print("=" * 62)
    print(f"  Phigros WS 测试服务端  ws://{host}:{port}/")
    print("=" * 62)
    print("  在游戏侧连上后：")
    print("    · 游戏发的消息会打印在这里")
    print("    · 在下面直接敲字并回车 → 推给游戏")
    print("    · 输入 /quit 退出")
    print("=" * 62, flush=True)

    threading.Thread(target=stdin_reader, daemon=True).start()

    try:
        async with websockets.serve(
            handler, host, port,
            ping_interval=20, ping_timeout=20, max_size=1 << 20,
        ):
            await heartbeat()
    except OSError as e:
        # WinError 10048 = 端口被占
        if getattr(e, "winerror", None) == 10048 or e.errno in (48, 98, 10048):
            print(f"\n[!] 端口 {port} 已被占用。\n", file=sys.stderr)
            print("    查是谁占着：", file=sys.stderr)
            print(f'      netstat -ano | findstr :{port}', file=sys.stderr)
            print("    然后杀掉：", file=sys.stderr)
            print("      taskkill /PID <上面查到的PID> /F\n", file=sys.stderr)
            print(f"    或者直接换端口： python ws_server.py --port {port + 1}",
                  file=sys.stderr)
            sys.exit(1)
        raise


if __name__ == "__main__":
    ap = argparse.ArgumentParser()
    ap.add_argument("--host", default="0.0.0.0")
    ap.add_argument("--port", type=int, default=8765)
    args = ap.parse_args()
    try:
        asyncio.run(main(args.host, args.port))
    except KeyboardInterrupt:
        print("\n已停止")
