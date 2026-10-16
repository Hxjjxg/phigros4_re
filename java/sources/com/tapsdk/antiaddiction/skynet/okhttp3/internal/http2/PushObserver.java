package com.tapsdk.antiaddiction.skynet.okhttp3.internal.http2;

import com.tapsdk.antiaddiction.skynet.okio.BufferedSource;
import java.io.IOException;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface PushObserver {
    public static final PushObserver CANCEL = new PushObserver() { // from class: com.tapsdk.antiaddiction.skynet.okhttp3.internal.http2.PushObserver.1
        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.internal.http2.PushObserver
        public boolean onHeaders(int i, List<Header> list, boolean z) {
            return true;
        }

        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.internal.http2.PushObserver
        public boolean onRequest(int i, List<Header> list) {
            return true;
        }

        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.internal.http2.PushObserver
        public void onReset(int i, ErrorCode errorCode) {
        }

        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.internal.http2.PushObserver
        public boolean onData(int i, BufferedSource bufferedSource, int i2, boolean z) throws IOException {
            bufferedSource.skip(i2);
            return true;
        }
    };

    boolean onData(int i, BufferedSource bufferedSource, int i2, boolean z) throws IOException;

    boolean onHeaders(int i, List<Header> list, boolean z);

    boolean onRequest(int i, List<Header> list);

    void onReset(int i, ErrorCode errorCode);
}
