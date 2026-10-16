package com.tds.gson.internal.bind;

import com.tds.gson.Gson;
import com.tds.gson.JsonSyntaxException;
import com.tds.gson.TypeAdapter;
import com.tds.gson.TypeAdapterFactory;
import com.tds.gson.reflect.TypeToken;
import com.tds.gson.stream.JsonReader;
import com.tds.gson.stream.JsonToken;
import com.tds.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public final class TimeTypeAdapter extends TypeAdapter<Time> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() { // from class: com.tds.gson.internal.bind.TimeTypeAdapter.1
        @Override // com.tds.gson.TypeAdapterFactory
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (typeToken.getRawType() == Time.class) {
                return new TimeTypeAdapter();
            }
            return null;
        }
    };
    private final DateFormat format = new SimpleDateFormat("hh:mm:ss a");

    @Override // com.tds.gson.TypeAdapter
    /* JADX INFO: renamed from: read, reason: avoid collision after fix types in other method */
    public synchronized Time read2(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        try {
            return new Time(this.format.parse(jsonReader.nextString()).getTime());
        } catch (ParseException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override // com.tds.gson.TypeAdapter
    public synchronized void write(JsonWriter jsonWriter, Time time) throws IOException {
        jsonWriter.value(time == null ? null : this.format.format((Date) time));
    }
}
