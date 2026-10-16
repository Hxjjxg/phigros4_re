package com.tapsdk.antiaddiction.skynet.retrofit2.adapter.converter;

import com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody;
import com.tapsdk.antiaddiction.skynet.retrofit2.Converter;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class StringResponseBodyConverter implements Converter<ResponseBody, String> {
    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
    public String convert(ResponseBody responseBody) throws IOException {
        return responseBody.string();
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
    public String convert(ResponseBody responseBody, int i) throws IOException {
        return responseBody.string();
    }
}
