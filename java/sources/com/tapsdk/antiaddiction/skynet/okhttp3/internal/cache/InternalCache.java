package com.tapsdk.antiaddiction.skynet.okhttp3.internal.cache;

import com.tapsdk.antiaddiction.skynet.okhttp3.Request;
import com.tapsdk.antiaddiction.skynet.okhttp3.Response;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public interface InternalCache {
    Response get(Request request) throws IOException;

    CacheRequest put(Response response) throws IOException;

    void remove(Request request) throws IOException;

    void trackConditionalCacheHit();

    void trackResponse(CacheStrategy cacheStrategy);

    void update(Response response, Response response2);
}
