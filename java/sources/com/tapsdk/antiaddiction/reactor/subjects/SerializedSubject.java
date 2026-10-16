package com.tapsdk.antiaddiction.reactor.subjects;

import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.reactor.observers.SerializedObserver;

/* JADX INFO: loaded from: classes.dex */
public class SerializedSubject<T, R> extends Subject<T, R> {
    private final Subject<T, R> actual;
    private final SerializedObserver<T> observer;

    public SerializedSubject(final Subject<T, R> subject) {
        super(new Observable.OnSubscribe<R>() { // from class: com.tapsdk.antiaddiction.reactor.subjects.SerializedSubject.1
            @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
            public void call(Subscriber<? super R> subscriber) {
                subject.unsafeSubscribe(subscriber);
            }
        });
        this.actual = subject;
        this.observer = new SerializedObserver<>(subject);
    }

    @Override // com.tapsdk.antiaddiction.reactor.Observer
    public void onCompleted() {
        this.observer.onCompleted();
    }

    @Override // com.tapsdk.antiaddiction.reactor.Observer
    public void onError(Throwable th) {
        this.observer.onError(th);
    }

    @Override // com.tapsdk.antiaddiction.reactor.Observer
    public void onNext(T t) {
        this.observer.onNext(t);
    }

    @Override // com.tapsdk.antiaddiction.reactor.subjects.Subject
    public boolean hasObservers() {
        return this.actual.hasObservers();
    }
}
