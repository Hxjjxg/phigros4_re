package com.tapsdk.antiaddiction.skynet.okhttp3.internal.connection;

import com.tapsdk.antiaddiction.skynet.okhttp3.Interceptor;
import com.tapsdk.antiaddiction.skynet.okhttp3.OkHttpClient;
import com.tapsdk.antiaddiction.skynet.okhttp3.Request;
import com.tapsdk.antiaddiction.skynet.okhttp3.Response;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.http.RealInterceptorChain;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class ConnectInterceptor implements Interceptor {
    public final OkHttpClient client;

    public ConnectInterceptor(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        RealInterceptorChain realInterceptorChain = (RealInterceptorChain) chain;
        Request request = realInterceptorChain.request();
        StreamAllocation streamAllocation = realInterceptorChain.streamAllocation();
        return realInterceptorChain.proceed(request, streamAllocation, streamAllocation.newStream(this.client, chain, !request.method().equals(HttpRequest.METHOD_GET)), streamAllocation.connection());
    }
}
