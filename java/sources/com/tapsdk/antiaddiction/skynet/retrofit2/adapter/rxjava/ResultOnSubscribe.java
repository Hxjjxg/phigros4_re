package com.tapsdk.antiaddiction.skynet.retrofit2.adapter.rxjava;

import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.skynet.retrofit2.Response;

/* JADX INFO: loaded from: classes.dex */
final class ResultOnSubscribe<T> implements Observable.OnSubscribe<Result<T>> {
    private final Observable.OnSubscribe<Response<T>> upstream;

    ResultOnSubscribe(Observable.OnSubscribe<Response<T>> onSubscribe) {
        this.upstream = onSubscribe;
    }

    @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
    public void call(Subscriber<? super Result<T>> subscriber) {
        this.upstream.call((Response<T>) new ResultSubscriber(subscriber));
    }

    private static class ResultSubscriber<R> extends Subscriber<Response<R>> {
        private final Subscriber<? super Result<R>> subscriber;

        ResultSubscriber(Subscriber<? super Result<R>> subscriber) {
            super(subscriber);
            this.subscriber = subscriber;
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onNext(Response<R> response) {
            this.subscriber.onNext(Result.response(response));
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onError(Throwable th) {
            try {
                this.subscriber.onNext(Result.error(th));
                this.subscriber.onCompleted();
            } catch (Throwable th2) {
                try {
                    this.subscriber.onError(th2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onCompleted() {
            this.subscriber.onCompleted();
        }
    }
}
