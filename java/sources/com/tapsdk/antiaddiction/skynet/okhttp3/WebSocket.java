package com.tapsdk.antiaddiction.skynet.okhttp3;

import com.tapsdk.antiaddiction.skynet.okio.ByteString;

/* JADX INFO: loaded from: classes.dex */
public interface WebSocket {

    public interface Factory {
        WebSocket newWebSocket(Request request, WebSocketListener webSocketListener);
    }

    void cancel();

    boolean close(int i, String str);

    long queueSize();

    Request request();

    boolean send(ByteString byteString);

    boolean send(String str);
}
