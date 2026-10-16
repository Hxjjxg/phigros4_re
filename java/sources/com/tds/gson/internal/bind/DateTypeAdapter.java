package com.tds.gson.internal.bind;

import com.tds.gson.Gson;
import com.tds.gson.JsonSyntaxException;
import com.tds.gson.TypeAdapter;
import com.tds.gson.TypeAdapterFactory;
import com.tds.gson.internal.JavaVersion;
import com.tds.gson.internal.PreJava9DateFormatProvider;
import com.tds.gson.internal.bind.util.ISO8601Utils;
import com.tds.gson.reflect.TypeToken;
import com.tds.gson.stream.JsonReader;
import com.tds.gson.stream.JsonToken;
import com.tds.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class DateTypeAdapter extends TypeAdapter<Date> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() { // from class: com.tds.gson.internal.bind.DateTypeAdapter.1
        @Override // com.tds.gson.TypeAdapterFactory
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (typeToken.getRawType() == Date.class) {
                return new DateTypeAdapter();
            }
            return null;
        }
    };
    private final List<DateFormat> dateFormats;

    public DateTypeAdapter() {
        ArrayList arrayList = new ArrayList();
        this.dateFormats = arrayList;
        arrayList.add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            arrayList.add(DateFormat.getDateTimeInstance(2, 2));
        }
        if (JavaVersion.isJava9OrLater()) {
            arrayList.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
        }
    }

    @Override // com.tds.gson.TypeAdapter
    /* JADX INFO: renamed from: read, reason: avoid collision after fix types in other method */
    public Date read2(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return deserializeToDate(jsonReader.nextString());
    }

    private synchronized Date deserializeToDate(String str) {
        Iterator<DateFormat> it = this.dateFormats.iterator();
        while (it.hasNext()) {
            try {
                return it.next().parse(str);
            } catch (ParseException unused) {
            }
        }
        try {
            return ISO8601Utils.parse(str, new ParsePosition(0));
        } catch (ParseException e) {
            throw new JsonSyntaxException(str, e);
        }
    }

    @Override // com.tds.gson.TypeAdapter
    public synchronized void write(JsonWriter jsonWriter, Date date) throws IOException {
        try {
            if (date == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(this.dateFormats.get(0).format(date));
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
