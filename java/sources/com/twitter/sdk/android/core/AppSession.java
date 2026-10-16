package com.twitter.sdk.android.core;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twitter.sdk.android.core.internal.oauth.GuestAuthToken;
import com.twitter.sdk.android.core.internal.oauth.OAuth2Token;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.persistence.SerializationStrategy;

/* JADX INFO: loaded from: classes.dex */
public class AppSession extends Session<OAuth2Token> {
    AppSession(OAuth2Token oAuth2Token) {
        super(oAuth2Token, 0L);
    }

    AppSession(GuestAuthToken guestAuthToken) {
        super(guestAuthToken, 0L);
    }

    static class Serializer implements SerializationStrategy<AppSession> {
        private final Gson gson = new GsonBuilder().registerTypeAdapter(OAuth2Token.class, new AuthTokenAdapter()).create();

        @Override // io.fabric.sdk.android.services.persistence.SerializationStrategy
        public String serialize(AppSession appSession) {
            if (appSession == null || appSession.getAuthToken() == null) {
                return "";
            }
            try {
                return this.gson.toJson(appSession);
            } catch (Exception e) {
                Fabric.getLogger().d(TwitterCore.TAG, "Failed to serialize session " + e.getMessage());
                return "";
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // io.fabric.sdk.android.services.persistence.SerializationStrategy
        public AppSession deserialize(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            try {
                return (AppSession) this.gson.fromJson(str, AppSession.class);
            } catch (Exception e) {
                Fabric.getLogger().d(TwitterCore.TAG, "Failed to deserialize session " + e.getMessage());
                return null;
            }
        }
    }
}
