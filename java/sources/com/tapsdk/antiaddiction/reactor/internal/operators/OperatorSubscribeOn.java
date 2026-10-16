package com.tapsdk.antiaddiction.reactor.internal.operators;

import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Producer;
import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.reactor.functions.Action0;
import com.tapsdk.antiaddiction.reactor.schedulers.Scheduler;

/* JADX INFO: loaded from: classes.dex */
public final class OperatorSubscribeOn<T> implements Observable.OnSubscribe<T> {
    final boolean requestOn;
    final Scheduler scheduler;
    final Observable<T> source;

    public OperatorSubscribeOn(Observable<T> observable, Scheduler scheduler, boolean z) {
        this.scheduler = scheduler;
        this.source = observable;
        this.requestOn = z;
    }

    @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
    public void call(Subscriber<? super T> subscriber) {
        Scheduler.Worker workerCreateWorker = this.scheduler.createWorker();
        SubscribeOnSubscriber subscribeOnSubscriber = new SubscribeOnSubscriber(subscriber, this.requestOn, workerCreateWorker, this.source);
        subscriber.add(subscribeOnSubscriber);
        subscriber.add(workerCreateWorker);
        workerCreateWorker.schedule(subscribeOnSubscriber);
    }

    static final class SubscribeOnSubscriber<T> extends Subscriber<T> implements Action0 {
        final Subscriber<? super T> actual;
        final boolean requestOn;
        Observable<T> source;
        Thread t;
        final Scheduler.Worker worker;

        SubscribeOnSubscriber(Subscriber<? super T> subscriber, boolean z, Scheduler.Worker worker, Observable<T> observable) {
            this.actual = subscriber;
            this.requestOn = z;
            this.worker = worker;
            this.source = observable;
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onError(Throwable th) {
            try {
                this.actual.onError(th);
            } finally {
                this.worker.unsubscribe();
            }
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onCompleted() {
            try {
                this.actual.onCompleted();
            } finally {
                this.worker.unsubscribe();
            }
        }

        @Override // com.tapsdk.antiaddiction.reactor.functions.Action0
        public void call() {
            Observable<T> observable = this.source;
            this.source = null;
            this.t = Thread.currentThread();
            observable.unsafeSubscribe(this);
        }

        @Override // com.tapsdk.antiaddiction.reactor.Subscriber
        public void setProducer(final Producer producer) {
            this.actual.setProducer(new Producer() { // from class: com.tapsdk.antiaddiction.reactor.internal.operators.OperatorSubscribeOn.SubscribeOnSubscriber.1
                @Override // com.tapsdk.antiaddiction.reactor.Producer
                public void request(final long j) {
                    if (SubscribeOnSubscriber.this.t == Thread.currentThread() || !SubscribeOnSubscriber.this.requestOn) {
                        producer.request(j);
                    } else {
                        SubscribeOnSubscriber.this.worker.schedule(new Action0() { // from class: com.tapsdk.antiaddiction.reactor.internal.operators.OperatorSubscribeOn.SubscribeOnSubscriber.1.1
                            @Override // com.tapsdk.antiaddiction.reactor.functions.Action0
                            public void call() {
                                producer.request(j);
                            }
                        });
                    }
                }
            });
        }
    }
}
