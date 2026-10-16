package com.tapsdk.antiaddiction.skynet.okhttp3.internal.http;

import com.tapsdk.antiaddiction.skynet.okhttp3.Interceptor;
import com.tapsdk.antiaddiction.skynet.okhttp3.Request;
import com.tapsdk.antiaddiction.skynet.okhttp3.Response;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.Util;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.connection.RealConnection;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.connection.StreamAllocation;
import com.tapsdk.antiaddiction.skynet.okio.Buffer;
import com.tapsdk.antiaddiction.skynet.okio.BufferedSink;
import com.tapsdk.antiaddiction.skynet.okio.ForwardingSink;
import com.tapsdk.antiaddiction.skynet.okio.Okio;
import com.tapsdk.antiaddiction.skynet.okio.Sink;
import java.io.IOException;
import java.net.ProtocolException;

/* JADX INFO: loaded from: classes.dex */
public final class CallServerInterceptor implements Interceptor {
    private final boolean forWebSocket;

    public CallServerInterceptor(boolean z) {
        this.forWebSocket = z;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response responseBuild;
        RealInterceptorChain realInterceptorChain = (RealInterceptorChain) chain;
        HttpCodec httpCodecHttpStream = realInterceptorChain.httpStream();
        StreamAllocation streamAllocation = realInterceptorChain.streamAllocation();
        RealConnection realConnection = (RealConnection) realInterceptorChain.connection();
        Request request = realInterceptorChain.request();
        long jCurrentTimeMillis = System.currentTimeMillis();
        realInterceptorChain.eventListener().requestHeadersStart(realInterceptorChain.call());
        httpCodecHttpStream.writeRequestHeaders(request);
        realInterceptorChain.eventListener().requestHeadersEnd(realInterceptorChain.call(), request);
        Response.Builder responseHeaders = null;
        if (HttpMethod.permitsRequestBody(request.method()) && request.body() != null) {
            if ("100-continue".equalsIgnoreCase(request.header("Expect"))) {
                httpCodecHttpStream.flushRequest();
                realInterceptorChain.eventListener().responseHeadersStart(realInterceptorChain.call());
                responseHeaders = httpCodecHttpStream.readResponseHeaders(true);
            }
            if (responseHeaders == null) {
                realInterceptorChain.eventListener().requestBodyStart(realInterceptorChain.call());
                CountingSink countingSink = new CountingSink(httpCodecHttpStream.createRequestBody(request, request.body().contentLength()));
                BufferedSink bufferedSinkBuffer = Okio.buffer(countingSink);
                request.body().writeTo(bufferedSinkBuffer);
                bufferedSinkBuffer.close();
                realInterceptorChain.eventListener().requestBodyEnd(realInterceptorChain.call(), countingSink.successfulCount);
            } else if (!realConnection.isMultiplexed()) {
                streamAllocation.noNewStreams();
            }
        }
        httpCodecHttpStream.finishRequest();
        if (responseHeaders == null) {
            realInterceptorChain.eventListener().responseHeadersStart(realInterceptorChain.call());
            responseHeaders = httpCodecHttpStream.readResponseHeaders(false);
        }
        Response responseBuild2 = responseHeaders.request(request).handshake(streamAllocation.connection().handshake()).sentRequestAtMillis(jCurrentTimeMillis).receivedResponseAtMillis(System.currentTimeMillis()).build();
        int iCode = responseBuild2.code();
        if (iCode == 100) {
            responseBuild2 = httpCodecHttpStream.readResponseHeaders(false).request(request).handshake(streamAllocation.connection().handshake()).sentRequestAtMillis(jCurrentTimeMillis).receivedResponseAtMillis(System.currentTimeMillis()).build();
            iCode = responseBuild2.code();
        }
        realInterceptorChain.eventListener().responseHeadersEnd(realInterceptorChain.call(), responseBuild2);
        if (this.forWebSocket && iCode == 101) {
            responseBuild = responseBuild2.newBuilder().body(Util.EMPTY_RESPONSE).build();
        } else {
            responseBuild = responseBuild2.newBuilder().body(httpCodecHttpStream.openResponseBody(responseBuild2)).build();
        }
        if ("close".equalsIgnoreCase(responseBuild.request().header("Connection")) || "close".equalsIgnoreCase(responseBuild.header("Connection"))) {
            streamAllocation.noNewStreams();
        }
        if ((iCode == 204 || iCode == 205) && responseBuild.body().contentLength() > 0) {
            throw new ProtocolException("HTTP " + iCode + " had non-zero Content-Length: " + responseBuild.body().contentLength());
        }
        return responseBuild;
    }

    static final class CountingSink extends ForwardingSink {
        long successfulCount;

        CountingSink(Sink sink) {
            super(sink);
        }

        @Override // com.tapsdk.antiaddiction.skynet.okio.ForwardingSink, com.tapsdk.antiaddiction.skynet.okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            super.write(buffer, j);
            this.successfulCount += j;
        }
    }
}
