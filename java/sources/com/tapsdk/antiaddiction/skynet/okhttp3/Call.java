package com.tapsdk.antiaddiction.skynet.okhttp3;

import com.tapsdk.antiaddiction.skynet.okio.Timeout;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public interface Call extends Cloneable {

    public interface Factory {
        Call newCall(Request request);
    }

    void cancel();

    Call clone();

    void enqueue(Callback callback);

    Response execute() throws IOException;

    boolean isCanceled();

    boolean isExecuted();

    Request request();

    Timeout timeout();
}
