package com.tapsdk.antiaddiction.rest.interceptors;

import com.tapsdk.antiaddiction.skynet.okhttp3.Interceptor;
import com.tapsdk.antiaddiction.skynet.okhttp3.Response;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class MockDataInterceptor implements Interceptor {
    private final MockResponseGenerator generator;

    public interface MockResponseGenerator {
        String getLocalResponse(Interceptor.Chain chain);
    }

    private MockDataInterceptor() {
        this(null);
    }

    public MockDataInterceptor(MockResponseGenerator mockResponseGenerator) {
        this.generator = mockResponseGenerator;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        createResponseBodyStr(chain);
        return chain.proceed(chain.request());
    }

    public String createResponseBodyStr(Interceptor.Chain chain) {
        return this.generator.getLocalResponse(chain);
    }
}
