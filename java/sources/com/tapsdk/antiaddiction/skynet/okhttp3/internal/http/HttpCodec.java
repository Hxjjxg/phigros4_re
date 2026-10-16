package com.tapsdk.antiaddiction.skynet.okhttp3.internal.http;

import com.tapsdk.antiaddiction.skynet.okhttp3.Request;
import com.tapsdk.antiaddiction.skynet.okhttp3.Response;
import com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody;
import com.tapsdk.antiaddiction.skynet.okio.Sink;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public interface HttpCodec {
    public static final int DISCARD_STREAM_TIMEOUT_MILLIS = 100;

    void cancel();

    Sink createRequestBody(Request request, long j);

    void finishRequest() throws IOException;

    void flushRequest() throws IOException;

    ResponseBody openResponseBody(Response response) throws IOException;

    Response.Builder readResponseHeaders(boolean z) throws IOException;

    void writeRequestHeaders(Request request) throws IOException;
}
