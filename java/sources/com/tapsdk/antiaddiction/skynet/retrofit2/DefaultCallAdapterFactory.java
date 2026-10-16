package com.tapsdk.antiaddiction.skynet.retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes.dex */
final class DefaultCallAdapterFactory extends CallAdapter.Factory {
    static final CallAdapter.Factory INSTANCE = new DefaultCallAdapterFactory();

    DefaultCallAdapterFactory() {
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.CallAdapter.Factory
    public CallAdapter<?, ?> get(Type type, Annotation[] annotationArr, Retrofit retrofit2) {
        if (getRawType(type) != Call.class) {
            return null;
        }
        final Type callResponseType = Utils.getCallResponseType(type);
        return new CallAdapter<Object, Call<?>>() { // from class: com.tapsdk.antiaddiction.skynet.retrofit2.DefaultCallAdapterFactory.1
            @Override // com.tapsdk.antiaddiction.skynet.retrofit2.CallAdapter
            /* JADX INFO: renamed from: adapt, reason: merged with bridge method [inline-methods] */
            public Call<?> adapt2(Call<Object> call) {
                return call;
            }

            @Override // com.tapsdk.antiaddiction.skynet.retrofit2.CallAdapter
            public Type responseType() {
                return callResponseType;
            }
        };
    }
}
