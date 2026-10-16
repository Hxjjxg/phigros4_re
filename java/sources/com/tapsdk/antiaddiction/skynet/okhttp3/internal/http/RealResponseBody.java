package com.tapsdk.antiaddiction.skynet.okhttp3.internal.http;

import com.tapsdk.antiaddiction.skynet.okhttp3.MediaType;
import com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody;
import com.tapsdk.antiaddiction.skynet.okio.BufferedSource;

/* JADX INFO: loaded from: classes.dex */
public final class RealResponseBody extends ResponseBody {
    private final long contentLength;
    private final String contentTypeString;
    private final BufferedSource source;

    public RealResponseBody(String str, long j, BufferedSource bufferedSource) {
        this.contentTypeString = str;
        this.contentLength = j;
        this.source = bufferedSource;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody
    public MediaType contentType() {
        String str = this.contentTypeString;
        if (str != null) {
            return MediaType.parse(str);
        }
        return null;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody
    public long contentLength() {
        return this.contentLength;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody
    public BufferedSource source() {
        return this.source;
    }
}
