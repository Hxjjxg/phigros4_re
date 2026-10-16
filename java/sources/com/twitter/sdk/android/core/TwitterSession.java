package com.twitter.sdk.android.core;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.persistence.SerializationStrategy;

/* JADX INFO: loaded from: classes.dex */
public class TwitterSession extends Session<TwitterAuthToken> {
    public static final long LOGGED_OUT_USER_ID = 0;
    public static final long UNKNOWN_USER_ID = -1;
    public static final String UNKNOWN_USER_NAME = "";

    @SerializedName("user_name")
    private final String userName;

    public TwitterSession(TwitterAuthToken twitterAuthToken, long j, String str) {
        super(twitterAuthToken, j);
        if (twitterAuthToken == null) {
            throw new IllegalArgumentException("AuthToken must not be null.");
        }
        this.userName = str;
    }

    public long getUserId() {
        return getId();
    }

    public String getUserName() {
        return this.userName;
    }

    @Override // com.twitter.sdk.android.core.Session
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        String str = this.userName;
        String str2 = ((TwitterSession) obj).userName;
        return str == null ? str2 == null : str.equals(str2);
    }

    @Override // com.twitter.sdk.android.core.Session
    public int hashCode() {
        int iHashCode = super.hashCode() * 31;
        String str = this.userName;
        return iHashCode + (str != null ? str.hashCode() : 0);
    }

    static class Serializer implements SerializationStrategy<TwitterSession> {
        private final Gson gson = new Gson();

        @Override // io.fabric.sdk.android.services.persistence.SerializationStrategy
        public String serialize(TwitterSession twitterSession) {
            if (twitterSession == null || twitterSession.getAuthToken() == null) {
                return "";
            }
            try {
                return this.gson.toJson(twitterSession);
            } catch (Exception e) {
                Fabric.getLogger().d(TwitterCore.TAG, e.getMessage());
                return "";
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // io.fabric.sdk.android.services.persistence.SerializationStrategy
        public TwitterSession deserialize(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            try {
                return (TwitterSession) this.gson.fromJson(str, TwitterSession.class);
            } catch (Exception e) {
                Fabric.getLogger().d(TwitterCore.TAG, e.getMessage());
                return null;
            }
        }
    }
}
