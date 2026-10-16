package com.digits.sdk.android.concurrent;

/* JADX INFO: loaded from: classes.dex */
public class DigitsRetryPolicy implements RetryPolicy {
    private final int maxRetries;

    public DigitsRetryPolicy() {
        this(0);
    }

    public DigitsRetryPolicy(int i) {
        this.maxRetries = i;
    }

    @Override // com.digits.sdk.android.concurrent.RetryPolicy
    public boolean shouldRetry(int i, Throwable th) {
        return i < this.maxRetries;
    }
}
