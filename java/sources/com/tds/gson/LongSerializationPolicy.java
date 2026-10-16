package com.tds.gson;

/* JADX INFO: loaded from: classes.dex */
public enum LongSerializationPolicy {
    DEFAULT { // from class: com.tds.gson.LongSerializationPolicy.1
        @Override // com.tds.gson.LongSerializationPolicy
        public JsonElement serialize(Long l) {
            return new JsonPrimitive(l);
        }
    },
    STRING { // from class: com.tds.gson.LongSerializationPolicy.2
        @Override // com.tds.gson.LongSerializationPolicy
        public JsonElement serialize(Long l) {
            return new JsonPrimitive(String.valueOf(l));
        }
    };

    public abstract JsonElement serialize(Long l);
}
