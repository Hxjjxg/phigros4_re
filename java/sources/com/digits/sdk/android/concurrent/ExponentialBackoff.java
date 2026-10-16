package com.digits.sdk.android.concurrent;

/* JADX INFO: loaded from: classes.dex */
public class ExponentialBackoff implements Backoff {
    private final long constant;

    ExponentialBackoff(long j) {
        this.constant = j;
    }

    @Override // com.digits.sdk.android.concurrent.Backoff
    public long getDelayMillis(int i) {
        return (long) (this.constant * Math.pow(2.0d, i));
    }
}
