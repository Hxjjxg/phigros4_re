package com.tds.gson;

import com.tds.gson.reflect.TypeToken;

/* JADX INFO: loaded from: classes.dex */
public interface TypeAdapterFactory {
    <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken);
}
