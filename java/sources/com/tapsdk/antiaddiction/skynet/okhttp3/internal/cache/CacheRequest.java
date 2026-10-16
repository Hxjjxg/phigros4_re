package com.tapsdk.antiaddiction.skynet.okhttp3.internal.cache;

import com.tapsdk.antiaddiction.skynet.okio.Sink;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public interface CacheRequest {
    void abort();

    Sink body() throws IOException;
}
