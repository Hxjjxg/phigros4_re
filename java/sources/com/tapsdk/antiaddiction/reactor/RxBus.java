package com.tapsdk.antiaddiction.reactor;

import com.tapsdk.antiaddiction.reactor.subjects.PublishSubject;
import com.tapsdk.antiaddiction.reactor.subjects.SerializedSubject;
import com.tapsdk.antiaddiction.reactor.subjects.Subject;

/* JADX INFO: loaded from: classes.dex */
public class RxBus {
    private final Subject<Object, Object> bus;

    private RxBus() {
        this.bus = new SerializedSubject(PublishSubject.create());
    }

    static class Holder {
        public static RxBus INSTANCE = new RxBus();

        Holder() {
        }
    }

    public static RxBus getInstance() {
        return Holder.INSTANCE;
    }

    public void send(Object obj) {
        this.bus.onNext(obj);
    }

    public Observable<Object> toObservable() {
        return this.bus;
    }

    public boolean hasObservers() {
        return this.bus.hasObservers();
    }
}
