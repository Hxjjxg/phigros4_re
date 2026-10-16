package re.phigros.dglab;

import com.tds.common.websocket.conn.WebSocket;
import com.tds.common.websocket.handshake.ClientHandshake;
import com.tds.common.websocket.server.WebSocketServer;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/** Platform adapter only. No Frida callbacks on Java network threads. */
public final class EmbeddedTransport extends WebSocketServer {
    private final Map<String, WebSocket> sockets = new ConcurrentHashMap<>();
    private final Map<WebSocket, String> ids = new ConcurrentHashMap<>();
    private final ArrayBlockingQueue<String> events = new ArrayBlockingQueue<>(512);
    public volatile boolean ready = false;
    public volatile boolean stopped = false;
    public volatile boolean stopping = false;
    public volatile String failure = "";
    private int bufferedSends = 0;

    public EmbeddedTransport(String host, int port) {
        super(new InetSocketAddress(host, port));
        setReuseAddr(true);
        setTcpNoDelay(true);
        // APK library owns native ping/pong and dead-peer detection.
        setConnectionLostTimeout(10);
    }

    private void event(String type, String id, String data) {
        try {
            JSONObject value = new JSONObject();
            value.put("type", type); value.put("id", id); value.put("data", data);
            if (!events.offer(value.toString())) {
                failure = "transport event queue overflow";
                requestStop(); // Never silently lose close/state transitions.
            }
        } catch (Exception e) { failure = e.toString(); requestStop(); }
    }

    public String poll() { return events.poll(); }
    public boolean connected(String id) {
        WebSocket ws = sockets.get(id);
        return ws != null && ws.isOpen();
    }
    public synchronized boolean sendTo(String id, String text) {
        WebSocket ws = sockets.get(id);
        if (ws == null || !ws.isOpen() || stopping) return false;
        // Do not accumulate commands behind a stalled recipient.
        if (!ws.hasBufferedData()) bufferedSends = 0;
        if (++bufferedSends > 128) { ws.close(1013, "slow_consumer"); return false; }
        try { ws.send(text); return true; }
        catch (Exception e) { return false; }
    }
    public void closePeer(String id, int code, String reason) {
        WebSocket ws = sockets.get(id);
        if (ws != null) ws.close(code, reason);
    }
    @Override public void onStart() { ready = true; }
    @Override public synchronized void onOpen(WebSocket ws, ClientHandshake handshake) {
        if (stopping || sockets.size() >= 4) { ws.close(1008, "connection_limit"); return; }
        String id = UUID.randomUUID().toString().replace("-", "");
        sockets.put(id, ws); ids.put(ws, id);
        event("open", id, ws.getResourceDescriptor());
    }
    @Override public void onMessage(WebSocket ws, String message) {
        String id = ids.get(ws);
        if (id == null || stopping) return;
        if (message.length() > 65536) { ws.close(1009, "frame_too_large"); return; }
        event("message", id, message);
    }
    @Override public void onClose(WebSocket ws, int code, String reason, boolean remote) {
        String id = ids.remove(ws);
        if (id != null) { sockets.remove(id); event("close", id, reason); }
    }
    @Override public void onError(WebSocket ws, Exception error) {
        if (ws == null) { failure = error.toString(); requestStop(); }
        else event("error", ids.get(ws), error.toString());
    }
    public synchronized void requestStop() {
        if (stopping) return;
        stopping = true;
        new Thread(new Runnable() {
            @Override public void run() {
                try { stop(); }
                catch (Exception e) { failure = e.toString(); }
                finally { ready = false; stopped = true; sockets.clear(); ids.clear(); }
            }
        }, "Phigros-DGLab-stop").start();
    }
}
