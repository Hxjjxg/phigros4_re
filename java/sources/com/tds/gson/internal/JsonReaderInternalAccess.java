package com.tds.gson.internal;

import com.tds.gson.stream.JsonReader;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class JsonReaderInternalAccess {
    public static JsonReaderInternalAccess INSTANCE;

    public abstract void promoteNameToValue(JsonReader jsonReader) throws IOException;
}
