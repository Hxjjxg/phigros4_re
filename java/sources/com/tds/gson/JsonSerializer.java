package com.tds.gson;

import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes.dex */
public interface JsonSerializer<T> {
    JsonElement serialize(T t, Type type, JsonSerializationContext jsonSerializationContext);
}
