package com.tapsdk.antiaddiction.skynet.logging;

import com.tapsdk.antiaddiction.skynet.okhttp3.Call;
import com.tapsdk.antiaddiction.skynet.okhttp3.Connection;
import com.tapsdk.antiaddiction.skynet.okhttp3.EventListener;
import com.tapsdk.antiaddiction.skynet.okhttp3.Handshake;
import com.tapsdk.antiaddiction.skynet.okhttp3.Protocol;
import com.tapsdk.antiaddiction.skynet.okhttp3.Request;
import com.tapsdk.antiaddiction.skynet.okhttp3.Response;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class LoggingEventListener extends EventListener {
    private final HttpLoggingInterceptor.Logger logger;
    private long startNs;

    private LoggingEventListener(HttpLoggingInterceptor.Logger logger) {
        this.logger = logger;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void callStart(Call call) {
        this.startNs = System.nanoTime();
        logWithTime("callStart: " + call.request());
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void dnsStart(Call call, String str) {
        logWithTime("dnsStart: " + str);
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void dnsEnd(Call call, String str, List<InetAddress> list) {
        logWithTime("dnsEnd: " + list);
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        logWithTime("connectStart: " + inetSocketAddress + " " + proxy);
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void secureConnectStart(Call call) {
        logWithTime("secureConnectStart");
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void secureConnectEnd(Call call, Handshake handshake) {
        logWithTime("secureConnectEnd");
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol) {
        logWithTime("connectEnd: " + protocol);
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol, IOException iOException) {
        logWithTime("connectFailed: " + protocol + " " + iOException);
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void connectionAcquired(Call call, Connection connection) {
        logWithTime("connectionAcquired: " + connection);
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void connectionReleased(Call call, Connection connection) {
        logWithTime("connectionReleased");
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void requestHeadersStart(Call call) {
        logWithTime("requestHeadersStart");
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void requestHeadersEnd(Call call, Request request) {
        logWithTime("requestHeadersEnd");
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void requestBodyStart(Call call) {
        logWithTime("requestBodyStart");
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void requestBodyEnd(Call call, long j) {
        logWithTime("requestBodyEnd: byteCount=" + j);
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void responseHeadersStart(Call call) {
        logWithTime("responseHeadersStart");
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void responseHeadersEnd(Call call, Response response) {
        logWithTime("responseHeadersEnd: " + response);
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void responseBodyStart(Call call) {
        logWithTime("responseBodyStart");
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void responseBodyEnd(Call call, long j) {
        logWithTime("responseBodyEnd: byteCount=" + j);
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void callEnd(Call call) {
        logWithTime("callEnd");
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener
    public void callFailed(Call call, IOException iOException) {
        logWithTime("callFailed: " + iOException);
    }

    private void logWithTime(String str) {
        this.logger.log("[" + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this.startNs) + " ms] " + str);
    }

    public static class Factory implements EventListener.Factory {
        private final HttpLoggingInterceptor.Logger logger;

        public Factory() {
            this(HttpLoggingInterceptor.Logger.DEFAULT);
        }

        public Factory(HttpLoggingInterceptor.Logger logger) {
            this.logger = logger;
        }

        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.EventListener.Factory
        public EventListener create(Call call) {
            return new LoggingEventListener(this.logger);
        }
    }
}
