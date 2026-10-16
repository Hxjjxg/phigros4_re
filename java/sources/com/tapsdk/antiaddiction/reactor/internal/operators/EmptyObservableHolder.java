package com.tapsdk.antiaddiction.reactor.internal.operators;

import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Subscriber;

/* JADX INFO: loaded from: classes.dex */
public enum EmptyObservableHolder implements Observable.OnSubscribe<Object> {
    INSTANCE;

    static final Observable<Object> EMPTY = Observable.unsafeCreate(INSTANCE);

    public static <T> Observable<T> instance() {
        return (Observable<T>) EMPTY;
    }

    @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
    public void call(Subscriber<? super Object> subscriber) {
        subscriber.onCompleted();
    }
}
