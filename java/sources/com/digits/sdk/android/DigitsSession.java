package com.digits.sdk.android;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twitter.sdk.android.core.AuthToken;
import com.twitter.sdk.android.core.AuthTokenAdapter;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.internal.oauth.OAuth2Token;
import com.twitter.sdk.android.core.models.User;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.persistence.SerializationStrategy;
import retrofit.client.Header;

/* JADX INFO: loaded from: classes.dex */
public class DigitsSession extends Session<AuthToken> {
    public static final long LOGGED_OUT_USER_ID = 0;
    static final String SECRET_HEADER = "x-twitter-new-account-oauth-secret";
    static final String TOKEN_HEADER = "x-twitter-new-account-oauth-access-token";
    public static final long UNKNOWN_USER_ID = -1;

    public DigitsSession(AuthToken authToken, long j) {
        super(authToken, j);
    }

    public DigitsSession(OAuth2Token oAuth2Token) {
        this(oAuth2Token, 0L);
    }

    public boolean isLoggedOutUser() {
        return getId() == 0;
    }

    static DigitsSession create(Result<User> result) {
        if (result == null) {
            throw new IllegalArgumentException("result must not be null");
        }
        if (result.data == null) {
            throw new IllegalArgumentException("result.data must not be null");
        }
        if (result.response == null) {
            throw new IllegalArgumentException("result.response must not be null");
        }
        String value = "";
        String value2 = "";
        for (Header header : result.response.getHeaders()) {
            if (TOKEN_HEADER.equals(header.getName())) {
                value = header.getValue();
            } else if (SECRET_HEADER.equals(header.getName())) {
                value2 = header.getValue();
            }
            if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(value2)) {
                break;
            }
        }
        return new DigitsSession(new TwitterAuthToken(value, value2), result.data.id);
    }

    static DigitsSession create(DigitsUser digitsUser) {
        if (digitsUser == null) {
            throw new IllegalArgumentException("result must not be null");
        }
        return new DigitsSession(new TwitterAuthToken(digitsUser.token, digitsUser.secret), digitsUser.userId);
    }

    public static DigitsSession create(TwitterSession twitterSession) {
        return new DigitsSession(twitterSession.getAuthToken(), twitterSession.getUserId());
    }

    public static class Serializer implements SerializationStrategy<DigitsSession> {
        private static final String TAG = "Digits";
        private final Gson gson = new GsonBuilder().registerTypeAdapter(AuthToken.class, new AuthTokenAdapter()).create();

        @Override // io.fabric.sdk.android.services.persistence.SerializationStrategy
        public String serialize(DigitsSession digitsSession) {
            if (digitsSession == null || digitsSession.getAuthToken() == null) {
                return "";
            }
            try {
                return this.gson.toJson(digitsSession);
            } catch (Exception e) {
                Fabric.getLogger().d("Digits", e.getMessage());
                return "";
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // io.fabric.sdk.android.services.persistence.SerializationStrategy
        public DigitsSession deserialize(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            try {
                return (DigitsSession) this.gson.fromJson(str, DigitsSession.class);
            } catch (Exception e) {
                Fabric.getLogger().d("Digits", e.getMessage());
                return null;
            }
        }
    }
}
