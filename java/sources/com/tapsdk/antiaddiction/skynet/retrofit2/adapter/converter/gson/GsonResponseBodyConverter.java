package com.tapsdk.antiaddiction.skynet.retrofit2.adapter.converter.gson;

import com.tapsdk.antiaddiction.models.ServerTimeModel;
import com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody;
import com.tapsdk.antiaddiction.skynet.retrofit2.Converter;
import com.tapsdk.antiaddiction.skynet.retrofit2.adapter.converter.RespInterceptor;
import com.tds.common.entities.AccessToken;
import com.tds.gson.Gson;
import com.tds.gson.JsonIOException;
import com.tds.gson.JsonObject;
import com.tds.gson.TypeAdapter;
import com.tds.gson.stream.JsonReader;
import com.tds.gson.stream.JsonToken;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;
    private final Gson gson;
    private final List<? extends RespInterceptor> interceptorList;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> typeAdapter, List<? extends RespInterceptor> list) {
        this.gson = gson;
        this.adapter = typeAdapter;
        this.interceptorList = list;
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
    @Deprecated
    public T convert(ResponseBody responseBody) throws IOException {
        T tFromJsonTree;
        JsonReader jsonReaderNewJsonReader = this.gson.newJsonReader(responseBody.charStream());
        try {
            JsonObject jsonObject = (JsonObject) this.gson.fromJson(jsonReaderNewJsonReader, JsonObject.class);
            Iterator<? extends RespInterceptor> it = this.interceptorList.iterator();
            while (it.hasNext()) {
                it.next().invoke(jsonObject, -1);
            }
            if (jsonObject.has(AccessToken.ROOT_ELEMENT_NAME)) {
                tFromJsonTree = this.adapter.fromJsonTree(jsonObject.get(AccessToken.ROOT_ELEMENT_NAME));
            } else {
                tFromJsonTree = this.adapter.read2(jsonReaderNewJsonReader);
            }
            try {
                if (jsonObject.has("now")) {
                    ServerTimeModel.setRecentServerTime(jsonObject.get("now").getAsLong());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jsonReaderNewJsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            responseBody.close();
            return tFromJsonTree;
        } catch (Throwable th) {
            responseBody.close();
            throw th;
        }
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
    public T convert(ResponseBody responseBody, int i) throws IOException {
        T tFromJsonTree;
        JsonReader jsonReaderNewJsonReader = this.gson.newJsonReader(responseBody.charStream());
        try {
            JsonObject jsonObject = (JsonObject) this.gson.fromJson(jsonReaderNewJsonReader, JsonObject.class);
            Iterator<? extends RespInterceptor> it = this.interceptorList.iterator();
            while (it.hasNext()) {
                it.next().invoke(jsonObject, i);
            }
            if (jsonObject.has(AccessToken.ROOT_ELEMENT_NAME)) {
                tFromJsonTree = this.adapter.fromJsonTree(jsonObject.get(AccessToken.ROOT_ELEMENT_NAME));
            } else {
                tFromJsonTree = this.adapter.read2(jsonReaderNewJsonReader);
            }
            try {
                if (jsonObject.has("now")) {
                    ServerTimeModel.setRecentServerTime(jsonObject.get("now").getAsLong());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jsonReaderNewJsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            responseBody.close();
            return tFromJsonTree;
        } catch (Throwable th) {
            responseBody.close();
            throw th;
        }
    }
}
