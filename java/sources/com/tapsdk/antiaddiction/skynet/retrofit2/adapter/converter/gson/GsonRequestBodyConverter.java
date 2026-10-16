package com.tapsdk.antiaddiction.skynet.retrofit2.adapter.converter.gson;

import com.tapsdk.antiaddiction.skynet.okhttp3.MediaType;
import com.tapsdk.antiaddiction.skynet.okhttp3.RequestBody;
import com.tapsdk.antiaddiction.skynet.okio.Buffer;
import com.tapsdk.antiaddiction.skynet.retrofit2.Converter;
import com.tds.gson.Gson;
import com.tds.gson.TypeAdapter;
import com.tds.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
final class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final TypeAdapter<T> adapter;
    private final Gson gson;

    GsonRequestBodyConverter(Gson gson, TypeAdapter<T> typeAdapter) {
        this.gson = gson;
        this.adapter = typeAdapter;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
    @Deprecated
    public RequestBody convert(T t) throws IOException {
        Buffer buffer = new Buffer();
        JsonWriter jsonWriterNewJsonWriter = this.gson.newJsonWriter(new OutputStreamWriter(buffer.outputStream(), UTF_8));
        this.adapter.write(jsonWriterNewJsonWriter, t);
        jsonWriterNewJsonWriter.close();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Converter
    public RequestBody convert(T t, int i) throws IOException {
        Buffer buffer = new Buffer();
        JsonWriter jsonWriterNewJsonWriter = this.gson.newJsonWriter(new OutputStreamWriter(buffer.outputStream(), UTF_8));
        this.adapter.write(jsonWriterNewJsonWriter, t);
        jsonWriterNewJsonWriter.close();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}
