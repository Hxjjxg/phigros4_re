package com.tapsdk.antiaddiction.skynet.okhttp3.internal.http;

import com.tapsdk.antiaddiction.skynet.okhttp3.Cookie;
import com.tapsdk.antiaddiction.skynet.okhttp3.CookieJar;
import com.tapsdk.antiaddiction.skynet.okhttp3.Interceptor;
import com.tapsdk.antiaddiction.skynet.okhttp3.MediaType;
import com.tapsdk.antiaddiction.skynet.okhttp3.Request;
import com.tapsdk.antiaddiction.skynet.okhttp3.RequestBody;
import com.tapsdk.antiaddiction.skynet.okhttp3.Response;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.Util;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.Version;
import com.tapsdk.antiaddiction.skynet.okio.GzipSource;
import com.tapsdk.antiaddiction.skynet.okio.Okio;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class BridgeInterceptor implements Interceptor {
    private final CookieJar cookieJar;

    public BridgeInterceptor(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builderNewBuilder = request.newBuilder();
        RequestBody requestBodyBody = request.body();
        if (requestBodyBody != null) {
            MediaType mediaTypeContentType = requestBodyBody.contentType();
            if (mediaTypeContentType != null) {
                builderNewBuilder.header("Content-Type", mediaTypeContentType.toString());
            }
            long jContentLength = requestBodyBody.contentLength();
            if (jContentLength != -1) {
                builderNewBuilder.header(HttpRequest.HEADER_CONTENT_LENGTH, Long.toString(jContentLength));
                builderNewBuilder.removeHeader("Transfer-Encoding");
            } else {
                builderNewBuilder.header("Transfer-Encoding", "chunked");
                builderNewBuilder.removeHeader(HttpRequest.HEADER_CONTENT_LENGTH);
            }
        }
        boolean z = false;
        if (request.header("Host") == null) {
            builderNewBuilder.header("Host", Util.hostHeader(request.url(), false));
        }
        if (request.header("Connection") == null) {
            builderNewBuilder.header("Connection", "Keep-Alive");
        }
        if (request.header(HttpRequest.HEADER_ACCEPT_ENCODING) == null && request.header("Range") == null) {
            builderNewBuilder.header(HttpRequest.HEADER_ACCEPT_ENCODING, HttpRequest.ENCODING_GZIP);
            z = true;
        }
        List<Cookie> listLoadForRequest = this.cookieJar.loadForRequest(request.url());
        if (!listLoadForRequest.isEmpty()) {
            builderNewBuilder.header("Cookie", cookieHeader(listLoadForRequest));
        }
        if (request.header("User-Agent") == null) {
            builderNewBuilder.header("User-Agent", Version.userAgent());
        }
        Response responseProceed = chain.proceed(builderNewBuilder.build());
        HttpHeaders.receiveHeaders(this.cookieJar, request.url(), responseProceed.headers());
        Response.Builder builderRequest = responseProceed.newBuilder().request(request);
        if (z && HttpRequest.ENCODING_GZIP.equalsIgnoreCase(responseProceed.header(HttpRequest.HEADER_CONTENT_ENCODING)) && HttpHeaders.hasBody(responseProceed)) {
            GzipSource gzipSource = new GzipSource(responseProceed.body().source());
            builderRequest.headers(responseProceed.headers().newBuilder().removeAll(HttpRequest.HEADER_CONTENT_ENCODING).removeAll(HttpRequest.HEADER_CONTENT_LENGTH).build());
            builderRequest.body(new RealResponseBody(responseProceed.header("Content-Type"), -1L, Okio.buffer(gzipSource)));
        }
        return builderRequest.build();
    }

    private String cookieHeader(List<Cookie> list) {
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append("; ");
            }
            Cookie cookie = list.get(i);
            sb.append(cookie.name()).append('=').append(cookie.value());
        }
        return sb.toString();
    }
}
