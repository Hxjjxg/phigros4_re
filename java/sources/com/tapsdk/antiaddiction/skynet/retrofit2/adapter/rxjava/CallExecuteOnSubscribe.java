package com.tapsdk.antiaddiction.skynet.retrofit2.adapter.rxjava;

import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.skynet.retrofit2.Call;
import com.tapsdk.antiaddiction.skynet.retrofit2.Response;

/* JADX INFO: loaded from: classes.dex */
final class CallExecuteOnSubscribe<T> implements Observable.OnSubscribe<Response<T>> {
    private final Call<T> originalCall;

    CallExecuteOnSubscribe(Call<T> call) {
        this.originalCall = call;
    }

    @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
    public void call(Subscriber<? super Response<T>> subscriber) {
        Call<T> callM11clone = this.originalCall.m11clone();
        CallArbiter callArbiter = new CallArbiter(callM11clone, subscriber);
        subscriber.setProducer(callArbiter);
        try {
            callArbiter.emitResponse(callM11clone.execute());
        } catch (Throwable th) {
            callArbiter.emitError(th);
        }
    }
}
