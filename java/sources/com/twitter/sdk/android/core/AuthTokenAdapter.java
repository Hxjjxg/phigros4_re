package com.twitter.sdk.android.core;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.twitter.sdk.android.core.internal.oauth.GuestAuthToken;
import com.twitter.sdk.android.core.internal.oauth.OAuth2Token;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AuthTokenAdapter implements JsonSerializer<AuthToken>, JsonDeserializer<AuthToken> {
    private static final String AUTH_TOKEN = "auth_token";
    private static final String AUTH_TYPE = "auth_type";
    static final Map<String, Class<? extends AuthToken>> authTypeRegistry;
    private final Gson gson = new Gson();

    static {
        HashMap map = new HashMap();
        authTypeRegistry = map;
        map.put("oauth1a", TwitterAuthToken.class);
        map.put("oauth2", OAuth2Token.class);
        map.put("guest", GuestAuthToken.class);
    }

    public JsonElement serialize(AuthToken authToken, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AUTH_TYPE, getAuthTypeString(authToken.getClass()));
        jsonObject.add(AUTH_TOKEN, this.gson.toJsonTree(authToken));
        return jsonObject;
    }

    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public AuthToken m15deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        String asString = asJsonObject.getAsJsonPrimitive(AUTH_TYPE).getAsString();
        return (AuthToken) this.gson.fromJson(asJsonObject.get(AUTH_TOKEN), authTypeRegistry.get(asString));
    }

    static String getAuthTypeString(Class<? extends AuthToken> cls) {
        for (Map.Entry<String, Class<? extends AuthToken>> entry : authTypeRegistry.entrySet()) {
            if (entry.getValue().equals(cls)) {
                return entry.getKey();
            }
        }
        return "";
    }
}
