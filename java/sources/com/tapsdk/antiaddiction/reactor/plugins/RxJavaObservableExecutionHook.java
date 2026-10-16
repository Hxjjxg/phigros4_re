package com.tapsdk.antiaddiction.reactor.plugins;

import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Subscription;

/* JADX INFO: loaded from: classes.dex */
public abstract class RxJavaObservableExecutionHook {
    @Deprecated
    public <T> Observable.OnSubscribe<T> onCreate(Observable.OnSubscribe<T> onSubscribe) {
        return onSubscribe;
    }

    @Deprecated
    public <T, R> Observable.Operator<? extends R, ? super T> onLift(Observable.Operator<? extends R, ? super T> operator) {
        return operator;
    }

    @Deprecated
    public <T> Throwable onSubscribeError(Throwable th) {
        return th;
    }

    @Deprecated
    public <T> Subscription onSubscribeReturn(Subscription subscription) {
        return subscription;
    }

    @Deprecated
    public <T> Observable.OnSubscribe<T> onSubscribeStart(Observable<? extends T> observable, Observable.OnSubscribe<T> onSubscribe) {
        return onSubscribe;
    }
}
