package com.tapsdk.antiaddiction.reactor.internal.operators;

import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Subscriber;

/* JADX INFO: loaded from: classes.dex */
public final class OnSubscribeThrow<T> implements Observable.OnSubscribe<T> {
    private final Throwable exception;

    public OnSubscribeThrow(Throwable th) {
        this.exception = th;
    }

    @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
    public void call(Subscriber<? super T> subscriber) {
        subscriber.onError(this.exception);
    }
}
