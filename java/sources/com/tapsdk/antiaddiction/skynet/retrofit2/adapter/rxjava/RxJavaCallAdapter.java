package com.tapsdk.antiaddiction.skynet.retrofit2.adapter.rxjava;

import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.schedulers.Scheduler;
import com.tapsdk.antiaddiction.skynet.retrofit2.Call;
import com.tapsdk.antiaddiction.skynet.retrofit2.CallAdapter;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes.dex */
final class RxJavaCallAdapter<R> implements CallAdapter<R, Object> {
    private final boolean isAsync;
    private final boolean isBody;
    private final boolean isResult;
    private final Type responseType;
    private final Scheduler scheduler;

    RxJavaCallAdapter(Type type, Scheduler scheduler, boolean z, boolean z2, boolean z3) {
        this.responseType = type;
        this.scheduler = scheduler;
        this.isAsync = z;
        this.isResult = z2;
        this.isBody = z3;
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.CallAdapter
    public Type responseType() {
        return this.responseType;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x002c  */
    /* JADX WARN: Code duplicated, block: B:17:? A[RETURN, SYNTHETIC] */
    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.CallAdapter
    /* JADX INFO: renamed from: adapt */
    public Object adapt2(Call<R> call) {
        Observable.OnSubscribe bodyOnSubscribe;
        Observable observableCreate;
        Scheduler scheduler;
        Observable.OnSubscribe callEnqueueOnSubscribe = this.isAsync ? new CallEnqueueOnSubscribe(call) : new CallExecuteOnSubscribe(call);
        if (this.isResult) {
            bodyOnSubscribe = new ResultOnSubscribe(callEnqueueOnSubscribe);
        } else {
            if (this.isBody) {
                bodyOnSubscribe = new BodyOnSubscribe(callEnqueueOnSubscribe);
            }
            observableCreate = Observable.create(callEnqueueOnSubscribe);
            scheduler = this.scheduler;
            if (scheduler != null) {
                return observableCreate.subscribeOn(scheduler);
            }
            return observableCreate;
        }
        callEnqueueOnSubscribe = bodyOnSubscribe;
        observableCreate = Observable.create(callEnqueueOnSubscribe);
        scheduler = this.scheduler;
        if (scheduler != null) {
            return observableCreate.subscribeOn(scheduler);
        }
        return observableCreate;
    }
}
