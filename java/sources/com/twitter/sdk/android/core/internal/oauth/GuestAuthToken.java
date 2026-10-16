package com.twitter.sdk.android.core.internal.oauth;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class GuestAuthToken extends OAuth2Token {
    private static final long EXPIRES_IN_MS = 10800000;
    public static final String HEADER_GUEST_TOKEN = "x-guest-token";

    @SerializedName("guest_token")
    private final String guestToken;

    public GuestAuthToken(String str, String str2, String str3) {
        super(str, str2);
        this.guestToken = str3;
    }

    public GuestAuthToken(String str, String str2, String str3, long j) {
        super(str, str2, j);
        this.guestToken = str3;
    }

    public String getGuestToken() {
        return this.guestToken;
    }

    @Override // com.twitter.sdk.android.core.internal.oauth.OAuth2Token, com.twitter.sdk.android.core.AuthToken
    public boolean isExpired() {
        return System.currentTimeMillis() >= this.createdAt + EXPIRES_IN_MS;
    }

    @Override // com.twitter.sdk.android.core.internal.oauth.OAuth2Token, com.twitter.sdk.android.core.internal.oauth.AuthHeaders
    public Map<String, String> getAuthHeaders(TwitterAuthConfig twitterAuthConfig, String str, String str2, Map<String, String> map) {
        Map<String, String> authHeaders = super.getAuthHeaders(twitterAuthConfig, str, str2, map);
        authHeaders.put(HEADER_GUEST_TOKEN, getGuestToken());
        return authHeaders;
    }

    @Override // com.twitter.sdk.android.core.internal.oauth.OAuth2Token
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        String str = this.guestToken;
        String str2 = ((GuestAuthToken) obj).guestToken;
        return str == null ? str2 == null : str.equals(str2);
    }

    @Override // com.twitter.sdk.android.core.internal.oauth.OAuth2Token
    public int hashCode() {
        int iHashCode = super.hashCode() * 31;
        String str = this.guestToken;
        return iHashCode + (str != null ? str.hashCode() : 0);
    }
}
