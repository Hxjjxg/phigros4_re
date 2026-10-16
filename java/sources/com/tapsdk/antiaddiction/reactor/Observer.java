package com.tapsdk.antiaddiction.reactor;

/* JADX INFO: loaded from: classes.dex */
public interface Observer<T> {
    void onCompleted();

    void onError(Throwable th);

    void onNext(T t);
}
