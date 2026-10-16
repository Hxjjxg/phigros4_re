package com.tds.common.websocket.server;

import com.tds.common.websocket.conn.WebSocket;
import com.tds.common.websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;

/**
 * 编译期桩。签名逐个对照 classes.dex 核过：
 *   <init>(Ljava/net/InetSocketAddress;)V                      抽象类，public 构造器
 *   onClose(WebSocket;ILjava/lang/String;Z)V                   抽象
 *   onError(WebSocket;Ljava/lang/Exception;)V                  抽象
 *   onMessage(WebSocket;Ljava/lang/String;)V                   抽象
 *   onOpen(WebSocket;ClientHandshake;)V                        抽象
 *   onStart()V                                                 抽象
 *   start()V  stop()V  getPort()I  getAddress()Ljava/net/InetSocketAddress;   非抽象
 *
 * 设备上由 APK 自带的真类提供；这个桩不会被打进 dex。
 */
public abstract class WebSocketServer {

    protected WebSocketServer(InetSocketAddress address) {
    }

    public abstract void onClose(WebSocket ws, int code, String reason, boolean remote);
    public abstract void onError(WebSocket ws, Exception ex);
    public abstract void onMessage(WebSocket ws, String message);
    public abstract void onOpen(WebSocket ws, ClientHandshake handshake);
    public abstract void onStart();

    public void start() {
    }

    public void setConnectionLostTimeout(int seconds) {}
    public void setReuseAddr(boolean enabled) {}
    public void setTcpNoDelay(boolean enabled) {}

    public void stop() {
    }

    public int getPort() {
        return 0;
    }

    public InetSocketAddress getAddress() {
        return null;
    }
}
