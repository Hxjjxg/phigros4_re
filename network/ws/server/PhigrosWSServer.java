package re.phigros;

import android.util.Log;

import com.tds.common.websocket.conn.WebSocket;
import com.tds.common.websocket.handshake.ClientHandshake;
import com.tds.common.websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 让 Phigros 进程自己当 WebSocket 服务端。
 *
 * 基类 com.tds.common.websocket.server.WebSocketServer 是 APK 里自带的完整
 * RFC6455 服务端实现，但它是 abstract 且全项目零子类、零引用 —— 这里补上
 * 缺失的那一层，然后由 Frida 动态加载进游戏进程。
 *
 * 5 个抽象方法的签名已逐个对照 classes.dex 字节码核对过：
 *   onClose(WebSocket, int, String, boolean)
 *   onError(WebSocket, Exception)
 *   onMessage(WebSocket, String)
 *   onOpen(WebSocket, ClientHandshake)
 *   onStart()
 *
 * 日志走 android.util.Log，tag = PhigrosWSS：
 *   adb logcat -s PhigrosWSS
 *
 * 这个文件本身不做 I/O，只是把事件打印出来 + 回显。要改行为改这里。
 */
public class PhigrosWSServer extends WebSocketServer {

    public static final String TAG = "PhigrosWSS";

    /** 当前活着的连接。Frida 侧通过 PhigrosWSServer.broadcast() 群发。 */
    public static final List<WebSocket> CLIENTS = new CopyOnWriteArrayList<WebSocket>();

    /**
     * 必须显式传地址。基类的无参构造绑的是 80 端口，
     * Android 上绑定 <1024 需要 root，所以不能走无参那条。
     */
    public PhigrosWSServer(InetSocketAddress addr) {
        super(addr);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart  port=" + getPort() + "  addr=" + getAddress());
    }

    @Override
    public void onOpen(WebSocket ws, ClientHandshake handshake) {
        CLIENTS.add(ws);
        String path = "?";
        try {
            // getResourceDescriptor 在 conn.WebSocket 接口上（已核对 dex），
            // 走 ws 而不是 handshake，省一层依赖
            path = ws.getResourceDescriptor();
        } catch (Throwable ignored) {
        }
        Log.i(TAG, "OPEN  " + ws.getRemoteSocketAddress()
                + "  path=" + path + "  clients=" + CLIENTS.size());
        try {
            ws.send("welcome from phigros (server side)");
        } catch (Throwable t) {
            Log.w(TAG, "欢迎语发送失败: " + t);
        }
    }

    @Override
    public void onClose(WebSocket ws, int code, String reason, boolean remote) {
        CLIENTS.remove(ws);
        Log.i(TAG, "CLOSE code=" + code + " reason=" + reason
                + " remote=" + remote + "  clients=" + CLIENTS.size());
    }

    @Override
    public void onMessage(WebSocket ws, String message) {
        Log.i(TAG, "RECV  " + message);
        try {
            ws.send("echo: " + message);
        } catch (Throwable t) {
            Log.w(TAG, "回声发送失败: " + t);
        }
    }

    @Override
    public void onError(WebSocket ws, Exception ex) {
        Log.w(TAG, "ERROR " + ex);
    }

    /** 给所有已连客户端群发，返回成功条数。 */
    public static int broadcast(String text) {
        int n = 0;
        for (WebSocket ws : CLIENTS) {
            try {
                ws.send(text);
                n++;
            } catch (Throwable ignored) {
            }
        }
        return n;
    }
}
