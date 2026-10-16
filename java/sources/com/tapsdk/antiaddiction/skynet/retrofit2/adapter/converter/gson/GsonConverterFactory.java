package com.tapsdk.antiaddiction.skynet.retrofit2.adapter.converter.gson;

import com.tapsdk.antiaddiction.skynet.okhttp3.RequestBody;
import com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody;
import com.tapsdk.antiaddiction.skynet.retrofit2.Converter;
import com.tapsdk.antiaddiction.skynet.retrofit2.Retrofit;
import com.tapsdk.antiaddiction.skynet.retrofit2.adapter.converter.RespInterceptor;
import com.tapsdk.antiaddiction.skynet.retrofit2.adapter.converter.StringResponseBodyConverter;
import com.tds.gson.Gson;
import com.tds.gson.reflect.TypeToken;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class GsonConverterFactory extends Converter.Factory {
    private final Gson gson;
    private final List<? extends RespInterceptor> interceptorList;

    public static GsonConverterFactory create(List<? extends RespInterceptor> list) {
        return create(new Gson(), list);
    }

    public static GsonConverterFactory create(Gson gson, List<? extends RespInterceptor> list) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        return new GsonConverterFactory(gson, list);
    }

    private GsonConverterFactory(Gson gson) {
        this.gson = gson;
        this.interceptorList = new ArrayList();
    }

    private GsonConverterFactory(Gson gson, List<? extends RespInterceptor> list) {
        this.gson = gson;
        if (list == null) {
            this.interceptorList = new ArrayList();
        } else {
            this.interceptorList = list;
        }
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter.Factory
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotationArr, Retrofit retrofit2) {
        if (type.equals(String.class)) {
            return new StringResponseBodyConverter();
        }
        return new GsonResponseBodyConverter(this.gson, this.gson.getAdapter(TypeToken.get(type)), this.interceptorList);
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter.Factory
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] annotationArr, Annotation[] annotationArr2, Retrofit retrofit2) {
        return new GsonRequestBodyConverter(this.gson, this.gson.getAdapter(TypeToken.get(type)));
    }
}
