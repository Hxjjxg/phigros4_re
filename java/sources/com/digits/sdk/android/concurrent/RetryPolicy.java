package com.digits.sdk.android.concurrent;

/* JADX INFO: loaded from: classes.dex */
public interface RetryPolicy {
    boolean shouldRetry(int i, Throwable th);
}
