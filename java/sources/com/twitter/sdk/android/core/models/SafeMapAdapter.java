package com.twitter.sdk.android.core.models;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SafeMapAdapter implements TypeAdapterFactory {
    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> typeToken) {
        final TypeAdapter delegateAdapter = gson.getDelegateAdapter(this, typeToken);
        return new TypeAdapter<T>() { // from class: com.twitter.sdk.android.core.models.SafeMapAdapter.1
            public void write(JsonWriter jsonWriter, T t) throws IOException {
                delegateAdapter.write(jsonWriter, t);
            }

            public T read(JsonReader jsonReader) throws IOException {
                T t = (T) delegateAdapter.read(jsonReader);
                if (!Map.class.isAssignableFrom(typeToken.getRawType())) {
                    return t;
                }
                if (t == null) {
                    return (T) Collections.EMPTY_MAP;
                }
                return (T) Collections.unmodifiableMap((Map) t);
            }
        };
    }
}
