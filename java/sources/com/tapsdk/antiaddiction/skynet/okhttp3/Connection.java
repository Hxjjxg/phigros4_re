package com.tapsdk.antiaddiction.skynet.okhttp3;

import java.net.Socket;

/* JADX INFO: loaded from: classes.dex */
public interface Connection {
    Handshake handshake();

    Protocol protocol();

    Route route();

    Socket socket();
}
