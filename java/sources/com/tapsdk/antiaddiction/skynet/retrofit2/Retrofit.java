package com.tapsdk.antiaddiction.skynet.retrofit2;

import com.tapsdk.antiaddiction.skynet.okhttp3.HttpUrl;
import com.tapsdk.antiaddiction.skynet.okhttp3.OkHttpClient;
import com.tapsdk.antiaddiction.skynet.okhttp3.RequestBody;
import com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class Retrofit {
    final HttpUrl baseUrl;
    final List<CallAdapter.Factory> callAdapterFactories;
    final com.tapsdk.antiaddiction.skynet.okhttp3.Call.Factory callFactory;
    final Executor callbackExecutor;
    final List<Converter.Factory> converterFactories;
    private final Map<Method, ServiceMethod<?, ?>> serviceMethodCache = new ConcurrentHashMap();
    final boolean validateEagerly;

    Retrofit(com.tapsdk.antiaddiction.skynet.okhttp3.Call.Factory factory, HttpUrl httpUrl, List<Converter.Factory> list, List<CallAdapter.Factory> list2, Executor executor, boolean z) {
        this.callFactory = factory;
        this.baseUrl = httpUrl;
        this.converterFactories = list;
        this.callAdapterFactories = list2;
        this.callbackExecutor = executor;
        this.validateEagerly = z;
    }

    public <T> T create(final Class<T> cls) {
        Utils.validateServiceInterface(cls);
        if (this.validateEagerly) {
            eagerlyValidateMethods(cls);
        }
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new InvocationHandler() { // from class: com.tapsdk.antiaddiction.skynet.retrofit2.Retrofit.1
            private final Platform platform = Platform.get();

            @Override // java.lang.reflect.InvocationHandler
            public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, objArr);
                }
                if (this.platform.isDefaultMethod(method)) {
                    return this.platform.invokeDefaultMethod(method, cls, obj, objArr);
                }
                ServiceMethod<?, ?> serviceMethodLoadServiceMethod = Retrofit.this.loadServiceMethod(method);
                return serviceMethodLoadServiceMethod.adapt(new OkHttpCall(serviceMethodLoadServiceMethod, objArr));
            }
        });
    }

    private void eagerlyValidateMethods(Class<?> cls) {
        Platform platform = Platform.get();
        for (Method method : cls.getDeclaredMethods()) {
            if (!platform.isDefaultMethod(method)) {
                loadServiceMethod(method);
            }
        }
    }

    ServiceMethod<?, ?> loadServiceMethod(Method method) {
        ServiceMethod serviceMethodBuild;
        ServiceMethod<?, ?> serviceMethod = this.serviceMethodCache.get(method);
        if (serviceMethod != null) {
            return serviceMethod;
        }
        synchronized (this.serviceMethodCache) {
            serviceMethodBuild = this.serviceMethodCache.get(method);
            if (serviceMethodBuild == null) {
                serviceMethodBuild = new ServiceMethod.Builder(this, method).build();
                this.serviceMethodCache.put(method, serviceMethodBuild);
            }
        }
        return serviceMethodBuild;
    }

    public com.tapsdk.antiaddiction.skynet.okhttp3.Call.Factory callFactory() {
        return this.callFactory;
    }

    public HttpUrl baseUrl() {
        return this.baseUrl;
    }

    public List<CallAdapter.Factory> callAdapterFactories() {
        return this.callAdapterFactories;
    }

    public CallAdapter<?, ?> callAdapter(Type type, Annotation[] annotationArr) {
        return nextCallAdapter(null, type, annotationArr);
    }

    public CallAdapter<?, ?> nextCallAdapter(CallAdapter.Factory factory, Type type, Annotation[] annotationArr) {
        Utils.checkNotNull(type, "returnType == null");
        Utils.checkNotNull(annotationArr, "annotations == null");
        int iIndexOf = this.callAdapterFactories.indexOf(factory) + 1;
        int size = this.callAdapterFactories.size();
        for (int i = iIndexOf; i < size; i++) {
            CallAdapter<?, ?> callAdapter = this.callAdapterFactories.get(i).get(type, annotationArr, this);
            if (callAdapter != null) {
                return callAdapter;
            }
        }
        StringBuilder sbAppend = new StringBuilder("Could not locate call adapter for ").append(type).append(".\n");
        if (factory != null) {
            sbAppend.append("  Skipped:");
            for (int i2 = 0; i2 < iIndexOf; i2++) {
                sbAppend.append("\n   * ").append(this.callAdapterFactories.get(i2).getClass().getName());
            }
            sbAppend.append('\n');
        }
        sbAppend.append("  Tried:");
        int size2 = this.callAdapterFactories.size();
        while (iIndexOf < size2) {
            sbAppend.append("\n   * ").append(this.callAdapterFactories.get(iIndexOf).getClass().getName());
            iIndexOf++;
        }
        throw new IllegalArgumentException(sbAppend.toString());
    }

    public List<Converter.Factory> converterFactories() {
        return this.converterFactories;
    }

    public <T> Converter<T, RequestBody> requestBodyConverter(Type type, Annotation[] annotationArr, Annotation[] annotationArr2) {
        return nextRequestBodyConverter(null, type, annotationArr, annotationArr2);
    }

    public <T> Converter<T, RequestBody> nextRequestBodyConverter(Converter.Factory factory, Type type, Annotation[] annotationArr, Annotation[] annotationArr2) {
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(annotationArr, "parameterAnnotations == null");
        Utils.checkNotNull(annotationArr2, "methodAnnotations == null");
        int iIndexOf = this.converterFactories.indexOf(factory) + 1;
        int size = this.converterFactories.size();
        for (int i = iIndexOf; i < size; i++) {
            Converter<T, RequestBody> converter = (Converter<T, RequestBody>) this.converterFactories.get(i).requestBodyConverter(type, annotationArr, annotationArr2, this);
            if (converter != null) {
                return converter;
            }
        }
        StringBuilder sbAppend = new StringBuilder("Could not locate RequestBody converter for ").append(type).append(".\n");
        if (factory != null) {
            sbAppend.append("  Skipped:");
            for (int i2 = 0; i2 < iIndexOf; i2++) {
                sbAppend.append("\n   * ").append(this.converterFactories.get(i2).getClass().getName());
            }
            sbAppend.append('\n');
        }
        sbAppend.append("  Tried:");
        int size2 = this.converterFactories.size();
        while (iIndexOf < size2) {
            sbAppend.append("\n   * ").append(this.converterFactories.get(iIndexOf).getClass().getName());
            iIndexOf++;
        }
        throw new IllegalArgumentException(sbAppend.toString());
    }

    public <T> Converter<ResponseBody, T> responseBodyConverter(Type type, Annotation[] annotationArr) {
        return nextResponseBodyConverter(null, type, annotationArr);
    }

    public <T> Converter<ResponseBody, T> nextResponseBodyConverter(Converter.Factory factory, Type type, Annotation[] annotationArr) {
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(annotationArr, "annotations == null");
        int iIndexOf = this.converterFactories.indexOf(factory) + 1;
        int size = this.converterFactories.size();
        for (int i = iIndexOf; i < size; i++) {
            Converter<ResponseBody, T> converter = (Converter<ResponseBody, T>) this.converterFactories.get(i).responseBodyConverter(type, annotationArr, this);
            if (converter != null) {
                return converter;
            }
        }
        StringBuilder sbAppend = new StringBuilder("Could not locate ResponseBody converter for ").append(type).append(".\n");
        if (factory != null) {
            sbAppend.append("  Skipped:");
            for (int i2 = 0; i2 < iIndexOf; i2++) {
                sbAppend.append("\n   * ").append(this.converterFactories.get(i2).getClass().getName());
            }
            sbAppend.append('\n');
        }
        sbAppend.append("  Tried:");
        int size2 = this.converterFactories.size();
        while (iIndexOf < size2) {
            sbAppend.append("\n   * ").append(this.converterFactories.get(iIndexOf).getClass().getName());
            iIndexOf++;
        }
        throw new IllegalArgumentException(sbAppend.toString());
    }

    public <T> Converter<T, String> stringConverter(Type type, Annotation[] annotationArr) {
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(annotationArr, "annotations == null");
        int size = this.converterFactories.size();
        for (int i = 0; i < size; i++) {
            Converter<T, String> converter = (Converter<T, String>) this.converterFactories.get(i).stringConverter(type, annotationArr, this);
            if (converter != null) {
                return converter;
            }
        }
        return BuiltInConverters.ToStringConverter.INSTANCE;
    }

    public Executor callbackExecutor() {
        return this.callbackExecutor;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static final class Builder {
        private HttpUrl baseUrl;
        private final List<CallAdapter.Factory> callAdapterFactories;
        private com.tapsdk.antiaddiction.skynet.okhttp3.Call.Factory callFactory;
        private Executor callbackExecutor;
        private final List<Converter.Factory> converterFactories;
        private final Platform platform;
        private boolean validateEagerly;

        Builder(Platform platform) {
            this.converterFactories = new ArrayList();
            this.callAdapterFactories = new ArrayList();
            this.platform = platform;
        }

        public Builder() {
            this(Platform.get());
        }

        Builder(Retrofit retrofit2) {
            ArrayList arrayList = new ArrayList();
            this.converterFactories = arrayList;
            ArrayList arrayList2 = new ArrayList();
            this.callAdapterFactories = arrayList2;
            this.platform = Platform.get();
            this.callFactory = retrofit2.callFactory;
            this.baseUrl = retrofit2.baseUrl;
            arrayList.addAll(retrofit2.converterFactories);
            arrayList.remove(0);
            arrayList2.addAll(retrofit2.callAdapterFactories);
            arrayList2.remove(arrayList2.size() - 1);
            this.callbackExecutor = retrofit2.callbackExecutor;
            this.validateEagerly = retrofit2.validateEagerly;
        }

        public Builder client(OkHttpClient okHttpClient) {
            return callFactory((com.tapsdk.antiaddiction.skynet.okhttp3.Call.Factory) Utils.checkNotNull(okHttpClient, "client == null"));
        }

        public Builder callFactory(com.tapsdk.antiaddiction.skynet.okhttp3.Call.Factory factory) {
            this.callFactory = (com.tapsdk.antiaddiction.skynet.okhttp3.Call.Factory) Utils.checkNotNull(factory, "factory == null");
            return this;
        }

        public Builder baseUrl(String str) {
            Utils.checkNotNull(str, "baseUrl == null");
            HttpUrl httpUrl = HttpUrl.parse(str);
            if (httpUrl == null) {
                throw new IllegalArgumentException("Illegal URL: " + str);
            }
            return baseUrl(httpUrl);
        }

        public Builder baseUrl(HttpUrl httpUrl) {
            Utils.checkNotNull(httpUrl, "baseUrl == null");
            List<String> listPathSegments = httpUrl.pathSegments();
            if (!"".equals(listPathSegments.get(listPathSegments.size() - 1))) {
                throw new IllegalArgumentException("baseUrl must end in /: " + httpUrl);
            }
            this.baseUrl = httpUrl;
            return this;
        }

        public Builder addConverterFactory(Converter.Factory factory) {
            this.converterFactories.add((Converter.Factory) Utils.checkNotNull(factory, "factory == null"));
            return this;
        }

        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            this.callAdapterFactories.add((CallAdapter.Factory) Utils.checkNotNull(factory, "factory == null"));
            return this;
        }

        public Builder callbackExecutor(Executor executor) {
            this.callbackExecutor = (Executor) Utils.checkNotNull(executor, "executor == null");
            return this;
        }

        public List<CallAdapter.Factory> callAdapterFactories() {
            return this.callAdapterFactories;
        }

        public List<Converter.Factory> converterFactories() {
            return this.converterFactories;
        }

        public Builder validateEagerly(boolean z) {
            this.validateEagerly = z;
            return this;
        }

        public Retrofit build() {
            if (this.baseUrl == null) {
                throw new IllegalStateException("Base URL required.");
            }
            com.tapsdk.antiaddiction.skynet.okhttp3.Call.Factory okHttpClient = this.callFactory;
            if (okHttpClient == null) {
                okHttpClient = new OkHttpClient();
            }
            com.tapsdk.antiaddiction.skynet.okhttp3.Call.Factory factory = okHttpClient;
            Executor executorDefaultCallbackExecutor = this.callbackExecutor;
            if (executorDefaultCallbackExecutor == null) {
                executorDefaultCallbackExecutor = this.platform.defaultCallbackExecutor();
            }
            Executor executor = executorDefaultCallbackExecutor;
            ArrayList arrayList = new ArrayList(this.callAdapterFactories);
            arrayList.add(this.platform.defaultCallAdapterFactory(executor));
            ArrayList arrayList2 = new ArrayList(this.converterFactories.size() + 1);
            arrayList2.add(new BuiltInConverters());
            arrayList2.addAll(this.converterFactories);
            return new Retrofit(factory, this.baseUrl, Collections.unmodifiableList(arrayList2), Collections.unmodifiableList(arrayList), executor, this.validateEagerly);
        }
    }
}
