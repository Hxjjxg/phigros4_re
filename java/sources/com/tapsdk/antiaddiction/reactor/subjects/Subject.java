package com.tapsdk.antiaddiction.reactor.subjects;

import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Observer;

/* JADX INFO: loaded from: classes.dex */
public abstract class Subject<T, R> extends Observable<R> implements Observer<T> {
    public abstract boolean hasObservers();

    public Subject(Observable.OnSubscribe<R> onSubscribe) {
        super(onSubscribe);
    }

    public final SerializedSubject<T, R> toSerialized() {
        if (getClass() == SerializedSubject.class) {
            return (SerializedSubject) this;
        }
        return new SerializedSubject<>(this);
    }
}
