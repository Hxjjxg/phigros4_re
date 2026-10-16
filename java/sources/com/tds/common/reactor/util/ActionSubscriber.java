package com.tds.common.reactor.util;

import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.functions.Action0;
import com.tds.common.reactor.functions.Action1;

/* JADX INFO: loaded from: classes.dex */
public final class ActionSubscriber<T> extends Subscriber<T> {
    final Action0 onCompleted;
    final Action1<Throwable> onError;
    final Action1<? super T> onNext;

    public ActionSubscriber(Action1<? super T> action1, Action1<Throwable> action2, Action0 action0) {
        this.onNext = action1;
        this.onError = action2;
        this.onCompleted = action0;
    }

    @Override // com.tds.common.reactor.Observer
    public void onNext(T t) {
        this.onNext.call(t);
    }

    @Override // com.tds.common.reactor.Observer
    public void onError(Throwable th) {
        this.onError.call(th);
    }

    @Override // com.tds.common.reactor.Observer
    public void onCompleted() {
        this.onCompleted.call();
    }
}
