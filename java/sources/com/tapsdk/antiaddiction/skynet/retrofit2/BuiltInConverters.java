package com.tapsdk.antiaddiction.skynet.retrofit2;

import com.tapsdk.antiaddiction.skynet.okhttp3.RequestBody;
import com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody;
import com.tapsdk.antiaddiction.skynet.retrofit2.http.Streaming;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes.dex */
final class BuiltInConverters extends Converter.Factory {
    BuiltInConverters() {
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter.Factory
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotationArr, Retrofit retrofit2) {
        if (type == ResponseBody.class) {
            return Utils.isAnnotationPresent(annotationArr, Streaming.class) ? StreamingResponseBodyConverter.INSTANCE : BufferingResponseBodyConverter.INSTANCE;
        }
        if (type == Void.class) {
            return VoidResponseBodyConverter.INSTANCE;
        }
        return null;
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter.Factory
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] annotationArr, Annotation[] annotationArr2, Retrofit retrofit2) {
        if (RequestBody.class.isAssignableFrom(Utils.getRawType(type))) {
            return RequestBodyConverter.INSTANCE;
        }
        return null;
    }

    static final class VoidResponseBodyConverter implements Converter<ResponseBody, Void> {
        static final VoidResponseBodyConverter INSTANCE = new VoidResponseBodyConverter();

        VoidResponseBodyConverter() {
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
        public Void convert(ResponseBody responseBody) {
            responseBody.close();
            return null;
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
        public Void convert(ResponseBody responseBody, int i) throws IOException {
            responseBody.close();
            return null;
        }
    }

    static final class RequestBodyConverter implements Converter<RequestBody, RequestBody> {
        static final RequestBodyConverter INSTANCE = new RequestBodyConverter();

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
        public RequestBody convert(RequestBody requestBody) throws IOException {
            return requestBody;
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
        public RequestBody convert(RequestBody requestBody, int i) {
            return requestBody;
        }

        RequestBodyConverter() {
        }
    }

    static final class StreamingResponseBodyConverter implements Converter<ResponseBody, ResponseBody> {
        static final StreamingResponseBodyConverter INSTANCE = new StreamingResponseBodyConverter();

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
        public ResponseBody convert(ResponseBody responseBody) {
            return responseBody;
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
        public ResponseBody convert(ResponseBody responseBody, int i) throws IOException {
            return responseBody;
        }

        StreamingResponseBodyConverter() {
        }
    }

    static final class BufferingResponseBodyConverter implements Converter<ResponseBody, ResponseBody> {
        static final BufferingResponseBodyConverter INSTANCE = new BufferingResponseBodyConverter();

        BufferingResponseBodyConverter() {
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
        public ResponseBody convert(ResponseBody responseBody) throws IOException {
            try {
                return Utils.buffer(responseBody);
            } finally {
                responseBody.close();
            }
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
        public ResponseBody convert(ResponseBody responseBody, int i) throws IOException {
            return convert(responseBody);
        }
    }

    static final class ToStringConverter implements Converter<Object, String> {
        static final ToStringConverter INSTANCE = new ToStringConverter();

        ToStringConverter() {
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
        public String convert(Object obj) {
            return obj.toString();
        }

        @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
        public String convert(Object obj, int i) throws IOException {
            return obj.toString();
        }
    }
}
