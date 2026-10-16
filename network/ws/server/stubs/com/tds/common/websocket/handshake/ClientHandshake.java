package com.tds.common.websocket.handshake;

/**
 * 编译期桩。只作为 onOpen 的形参类型出现，我们不调用它的任何方法，
 * 所以留空即可（真实接口还继承 Handshakedata）。
 * 方法签名无需匹配 —— javac 不会在这里解析任何成员。
 */
public interface ClientHandshake {
}
