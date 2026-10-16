package com.tapsdk.antiaddiction.skynet.retrofit2;

import com.tapsdk.antiaddiction.skynet.okhttp3.Request;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
final class ExecutorCallAdapterFactory extends CallAdapter.Factory {
    final Executor callbackExecutor;

    ExecutorCallAdapterFactory(Executor executor) {
        this.callbackExecutor = executor;
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.CallAdapter.Factory
    public CallAdapter<?, ?> get(Type type, Annotation[] annotationArr, Retrofit retrofit2) {
        if (getRawType(type) != Call.class) {
            return null;
        }
        final Type callResponseType = Utils.getCallResponseType(type);
        return new CallAdapter<Object, Call<?>>() { // from class: com.tapsdk.antiaddiction.skynet.retrofit2.ExecutorCallAdapterFactory.1
            @Override // com.tapsdk.antiaddiction.skynet.retrofit2.CallAdapter
            public Type responseType() {
                return callResponseType;
            }

            @Override // com.tapsdk.antiaddiction.skynet.retrofit2.CallAdapter
            /* JADX INFO: renamed from: adapt */
            public Call<?> adapt2(Call<Object> call) {
                return new ExecutorCallbackCall(ExecutorCallAdapterFactory.this.callbackExecutor, call);
            }
        };
    }

    static final class ExecutorCallbackCall<T> implements Call<T> {
        final Executor callbackExecutor;
        final Call<T> delegate;

        ExecutorCallbackCall(Executor executor, Call<T> call) {
            this.callbackExecutor = executor;
            this.delegate = call;
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Call
        public void enqueue(final Callback<T> callback) {
            Utils.checkNotNull(callback, "callback == null");
            this.delegate.enqueue(new Callback<T>() { // from class: com.tapsdk.antiaddiction.skynet.retrofit2.ExecutorCallAdapterFactory.ExecutorCallbackCall.1
                @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Callback
                public void onResponse(Call<T> call, final Response<T> response) {
                    ExecutorCallbackCall.this.callbackExecutor.execute(new Runnable() { // from class: com.tapsdk.antiaddiction.skynet.retrofit2.ExecutorCallAdapterFactory.ExecutorCallbackCall.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (ExecutorCallbackCall.this.delegate.isCanceled()) {
                                callback.onFailure(ExecutorCallbackCall.this, new IOException("Canceled"));
                            } else {
                                callback.onResponse(ExecutorCallbackCall.this, response);
                            }
                        }
                    });
                }

                @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Callback
                public void onFailure(Call<T> call, final Throwable th) {
                    ExecutorCallbackCall.this.callbackExecutor.execute(new Runnable() { // from class: com.tapsdk.antiaddiction.skynet.retrofit2.ExecutorCallAdapterFactory.ExecutorCallbackCall.1.2
                        @Override // java.lang.Runnable
                        public void run() {
                            callback.onFailure(ExecutorCallbackCall.this, th);
                        }
                    });
                }
            });
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Call
        public boolean isExecuted() {
            return this.delegate.isExecuted();
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Call
        public Response<T> execute() throws IOException {
            return this.delegate.execute();
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Call
        public void cancel() {
            this.delegate.cancel();
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Call
        public boolean isCanceled() {
            return this.delegate.isCanceled();
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Call
        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public Call<T> m10clone() {
            return new ExecutorCallbackCall(this.callbackExecutor, this.delegate.m10clone());
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Call
        public Request request() {
            return this.delegate.request();
        }
    }
}
