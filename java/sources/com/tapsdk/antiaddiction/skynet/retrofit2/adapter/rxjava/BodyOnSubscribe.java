package com.tapsdk.antiaddiction.skynet.retrofit2.adapter.rxjava;

import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.skynet.retrofit2.Response;

/* JADX INFO: loaded from: classes.dex */
final class BodyOnSubscribe<T> implements Observable.OnSubscribe<T> {
    private final Observable.OnSubscribe<Response<T>> upstream;

    BodyOnSubscribe(Observable.OnSubscribe<Response<T>> onSubscribe) {
        this.upstream = onSubscribe;
    }

    @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
    public void call(Subscriber<? super T> subscriber) {
        this.upstream.call((Response<T>) new BodySubscriber(subscriber));
    }

    private static class BodySubscriber<R> extends Subscriber<Response<R>> {
        private final Subscriber<? super R> subscriber;
        private boolean subscriberTerminated;

        BodySubscriber(Subscriber<? super R> subscriber) {
            super(subscriber);
            this.subscriber = subscriber;
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onNext(Response<R> response) {
            if (response.isSuccessful()) {
                this.subscriber.onNext(response.body());
                return;
            }
            this.subscriberTerminated = true;
            try {
                this.subscriber.onNext(response.body());
            } catch (Exception unused) {
                this.subscriber.onError(new Exception(response.toString()));
            }
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onError(Throwable th) {
            if (this.subscriberTerminated) {
                return;
            }
            this.subscriber.onError(th);
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onCompleted() {
            if (this.subscriberTerminated) {
                return;
            }
            this.subscriber.onCompleted();
        }
    }
}
