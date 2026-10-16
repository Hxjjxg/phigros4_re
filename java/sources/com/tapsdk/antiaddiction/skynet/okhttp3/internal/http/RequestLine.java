package com.tapsdk.antiaddiction.skynet.okhttp3.internal.http;

import com.tapsdk.antiaddiction.skynet.okhttp3.HttpUrl;
import com.tapsdk.antiaddiction.skynet.okhttp3.Request;
import java.net.Proxy;

/* JADX INFO: loaded from: classes.dex */
public final class RequestLine {
    private RequestLine() {
    }

    public static String get(Request request, Proxy.Type type) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.method());
        sb.append(' ');
        if (includeAuthorityInRequestLine(request, type)) {
            sb.append(request.url());
        } else {
            sb.append(requestPath(request.url()));
        }
        sb.append(" HTTP/1.1");
        return sb.toString();
    }

    private static boolean includeAuthorityInRequestLine(Request request, Proxy.Type type) {
        return !request.isHttps() && type == Proxy.Type.HTTP;
    }

    public static String requestPath(HttpUrl httpUrl) {
        String strEncodedPath = httpUrl.encodedPath();
        String strEncodedQuery = httpUrl.encodedQuery();
        return strEncodedQuery != null ? strEncodedPath + '?' + strEncodedQuery : strEncodedPath;
    }
}
