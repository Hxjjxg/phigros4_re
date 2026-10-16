package com.tds.gson.internal.bind;

import com.tds.gson.Gson;
import com.tds.gson.TypeAdapter;
import com.tds.gson.reflect.TypeToken;
import com.tds.gson.stream.JsonReader;
import com.tds.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* JADX INFO: loaded from: classes.dex */
final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {
    private final Gson context;
    private final TypeAdapter<T> delegate;
    private final Type type;

    TypeAdapterRuntimeTypeWrapper(Gson gson, TypeAdapter<T> typeAdapter, Type type) {
        this.context = gson;
        this.delegate = typeAdapter;
        this.type = type;
    }

    @Override // com.tds.gson.TypeAdapter
    /* JADX INFO: renamed from: read */
    public T read2(JsonReader jsonReader) throws IOException {
        return this.delegate.read2(jsonReader);
    }

    @Override // com.tds.gson.TypeAdapter
    public void write(JsonWriter jsonWriter, T t) throws IOException {
        TypeAdapter<T> adapter = this.delegate;
        Type runtimeTypeIfMoreSpecific = getRuntimeTypeIfMoreSpecific(this.type, t);
        if (runtimeTypeIfMoreSpecific != this.type) {
            adapter = this.context.getAdapter(TypeToken.get(runtimeTypeIfMoreSpecific));
            if (adapter instanceof ReflectiveTypeAdapterFactory.Adapter) {
                TypeAdapter<T> typeAdapter = this.delegate;
                if (!(typeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter)) {
                    adapter = typeAdapter;
                }
            }
        }
        adapter.write(jsonWriter, t);
    }

    private Type getRuntimeTypeIfMoreSpecific(Type type, Object obj) {
        if (obj != null) {
            return (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class)) ? obj.getClass() : type;
        }
        return type;
    }
}
