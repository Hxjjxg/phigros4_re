package com.tapsdk.antiaddiction.reactor.internal.operators;

import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Producer;
import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.reactor.exceptions.Exceptions;
import com.tapsdk.antiaddiction.reactor.exceptions.OnErrorThrowable;
import com.tapsdk.antiaddiction.reactor.functions.Func1;
import com.tapsdk.antiaddiction.reactor.plugins.RxJavaHooks;

/* JADX INFO: loaded from: classes.dex */
public final class OnSubscribeMap<T, R> implements Observable.OnSubscribe<R> {
    final Observable<T> source;
    final Func1<? super T, ? extends R> transformer;

    public OnSubscribeMap(Observable<T> observable, Func1<? super T, ? extends R> func1) {
        this.source = observable;
        this.transformer = func1;
    }

    @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
    public void call(Subscriber<? super R> subscriber) {
        MapSubscriber mapSubscriber = new MapSubscriber(subscriber, this.transformer);
        subscriber.add(mapSubscriber);
        this.source.unsafeSubscribe(mapSubscriber);
    }

    static final class MapSubscriber<T, R> extends Subscriber<T> {
        final Subscriber<? super R> actual;
        boolean done;
        final Func1<? super T, ? extends R> mapper;

        public MapSubscriber(Subscriber<? super R> subscriber, Func1<? super T, ? extends R> func1) {
            this.actual = subscriber;
            this.mapper = func1;
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onNext(T t) {
            try {
                this.actual.onNext(this.mapper.call(t));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                unsubscribe();
                onError(OnErrorThrowable.addValueAsLastCause(th, t));
            }
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaHooks.onError(th);
            } else {
                this.done = true;
                this.actual.onError(th);
            }
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onCompleted() {
            if (this.done) {
                return;
            }
            this.actual.onCompleted();
        }

        @Override // com.tapsdk.antiaddiction.reactor.Subscriber
        public void setProducer(Producer producer) {
            this.actual.setProducer(producer);
        }
    }
}
