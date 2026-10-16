package com.tapsdk.antiaddiction.skynet.okio;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class ForwardingSink implements Sink {
    private final Sink delegate;

    public ForwardingSink(Sink sink) {
        if (sink == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.delegate = sink;
    }

    public final Sink delegate() {
        return this.delegate;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.Sink
    public void write(Buffer buffer, long j) throws IOException {
        this.delegate.write(buffer, j);
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        this.delegate.flush();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.Sink
    public Timeout timeout() {
        return this.delegate.timeout();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.delegate.close();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }
}
