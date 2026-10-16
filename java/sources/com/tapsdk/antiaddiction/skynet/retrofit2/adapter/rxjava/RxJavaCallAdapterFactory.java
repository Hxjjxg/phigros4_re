package com.tapsdk.antiaddiction.skynet.retrofit2.adapter.rxjava;

import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.schedulers.Scheduler;
import com.tapsdk.antiaddiction.skynet.retrofit2.CallAdapter;
import com.tapsdk.antiaddiction.skynet.retrofit2.Response;
import com.tapsdk.antiaddiction.skynet.retrofit2.Retrofit;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes.dex */
public final class RxJavaCallAdapterFactory extends CallAdapter.Factory {
    private final boolean isAsync;
    private final Scheduler scheduler;

    public static RxJavaCallAdapterFactory create() {
        return new RxJavaCallAdapterFactory(null, false);
    }

    public static RxJavaCallAdapterFactory createAsync() {
        return new RxJavaCallAdapterFactory(null, true);
    }

    public static RxJavaCallAdapterFactory createWithScheduler(Scheduler scheduler) {
        if (scheduler == null) {
            throw new NullPointerException("scheduler == null");
        }
        return new RxJavaCallAdapterFactory(scheduler, false);
    }

    private RxJavaCallAdapterFactory(Scheduler scheduler, boolean z) {
        this.scheduler = scheduler;
        this.isAsync = z;
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.CallAdapter.Factory
    public CallAdapter<?, ?> get(Type type, Annotation[] annotationArr, Retrofit retrofit2) {
        boolean z;
        boolean z2;
        Type parameterUpperBound;
        if (getRawType(type) != Observable.class) {
            return null;
        }
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalStateException("Observable return type must be parameterized as Observable<Foo> or Observable<? extends Foo>");
        }
        Type parameterUpperBound2 = getParameterUpperBound(0, (ParameterizedType) type);
        Class<?> rawType = getRawType(parameterUpperBound2);
        if (rawType == Response.class) {
            if (!(parameterUpperBound2 instanceof ParameterizedType)) {
                throw new IllegalStateException("Response must be parameterized as Response<Foo> or Response<? extends Foo>");
            }
            parameterUpperBound = getParameterUpperBound(0, (ParameterizedType) parameterUpperBound2);
            z = false;
            z2 = false;
        } else {
            if (rawType != Result.class) {
                z = false;
                z2 = true;
            } else {
                if (!(parameterUpperBound2 instanceof ParameterizedType)) {
                    throw new IllegalStateException("Result must be parameterized as Result<Foo> or Result<? extends Foo>");
                }
                parameterUpperBound2 = getParameterUpperBound(0, (ParameterizedType) parameterUpperBound2);
                z2 = false;
                z = true;
            }
            parameterUpperBound = parameterUpperBound2;
        }
        return new RxJavaCallAdapter(parameterUpperBound, this.scheduler, this.isAsync, z, z2);
    }
}
