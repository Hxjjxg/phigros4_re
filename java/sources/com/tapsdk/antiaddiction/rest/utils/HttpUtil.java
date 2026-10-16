package com.tapsdk.antiaddiction.rest.utils;

import com.tapsdk.antiaddiction.skynet.okhttp3.RequestBody;
import com.tapsdk.antiaddiction.skynet.okio.Buffer;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class HttpUtil {
    public static String bodyToString(RequestBody requestBody) throws IOException {
        Buffer buffer = new Buffer();
        if (requestBody == null) {
            return "";
        }
        requestBody.writeTo(buffer);
        return buffer.readUtf8();
    }
}
