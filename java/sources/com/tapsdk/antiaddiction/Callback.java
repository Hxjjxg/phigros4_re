package com.tapsdk.antiaddiction;

/* JADX INFO: loaded from: classes.dex */
public interface Callback<T> {
    void onError(Throwable th);

    void onSuccess(T t);
}
