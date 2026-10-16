package com.tapsdk.antiaddiction.skynet.retrofit2.adapter.rxjava;

import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.skynet.retrofit2.Call;
import com.tapsdk.antiaddiction.skynet.retrofit2.Callback;
import com.tapsdk.antiaddiction.skynet.retrofit2.Response;

/* JADX INFO: loaded from: classes.dex */
public class CallEnqueueOnSubscribe<T> implements Observable.OnSubscribe<Response<T>> {
    private final Call<T> originalCall;

    CallEnqueueOnSubscribe(Call<T> call) {
        this.originalCall = call;
    }

    @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
    public void call(Subscriber<? super Response<T>> subscriber) {
        Call<T> callM11clone = this.originalCall.m11clone();
        final CallArbiter callArbiter = new CallArbiter(callM11clone, subscriber);
        subscriber.setProducer(callArbiter);
        callM11clone.enqueue(new Callback<T>() { // from class: com.tapsdk.antiaddiction.skynet.retrofit2.adapter.rxjava.CallEnqueueOnSubscribe.1
            @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Callback
            public void onResponse(Call<T> call, Response<T> response) {
                callArbiter.emitResponse(response);
            }

            @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Callback
            public void onFailure(Call<T> call, Throwable th) {
                callArbiter.emitError(th);
            }
        });
    }
}
