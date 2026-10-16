package com.tds.common.websocket.conn;

import java.net.InetSocketAddress;

/**
 * 编译期桩。真实接口的方法签名已对照 classes.dex 核对：
 *   send(Ljava/lang/String;)V
 *   getRemoteSocketAddress()Ljava/net/InetSocketAddress;
 *   getResourceDescriptor()Ljava/lang/String;
 *
 * 这里只声明我们用到的三个。设备上由 APK 自带的真类提供。
 */
public interface WebSocket {
    void send(String text);
    InetSocketAddress getRemoteSocketAddress();
    String getResourceDescriptor();
}
