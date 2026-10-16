package com.twitter.sdk.android.core;

import com.twitter.sdk.android.core.internal.oauth.OAuth1aService;
import io.fabric.sdk.android.services.network.HttpMethod;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class OAuthSigning {
    static final String HEADER_AUTH_CREDENTIALS = "X-Verify-Credentials-Authorization";
    static final String HEADER_AUTH_SERVICE_PROVIDER = "X-Auth-Service-Provider";
    static final String VERIFY_CREDENTIALS_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";
    private final TwitterAuthConfig authConfig;
    private final TwitterAuthToken authToken;

    public OAuthSigning(TwitterAuthConfig twitterAuthConfig, TwitterAuthToken twitterAuthToken) {
        if (twitterAuthConfig == null) {
            throw new IllegalArgumentException("authConfig must not be null");
        }
        if (twitterAuthToken == null) {
            throw new IllegalArgumentException("authToken must not be null");
        }
        this.authConfig = twitterAuthConfig;
        this.authToken = twitterAuthToken;
    }

    public Map<String, String> getOAuthEchoHeaders(String str, String str2, Map<String, String> map) {
        return getOAuthEchoHeaders(this.authConfig, this.authToken, null, str, str2, map);
    }

    public Map<String, String> getOAuthEchoHeadersForVerifyCredentials() {
        return getOAuthEchoHeaders(this.authConfig, this.authToken, null, HttpMethod.GET.name(), VERIFY_CREDENTIALS_URL, null);
    }

    Map<String, String> getOAuthEchoHeaders(TwitterAuthConfig twitterAuthConfig, TwitterAuthToken twitterAuthToken, String str, String str2, String str3, Map<String, String> map) {
        HashMap map2 = new HashMap(2);
        map2.put(HEADER_AUTH_CREDENTIALS, OAuth1aService.getAuthorizationHeader(twitterAuthConfig, twitterAuthToken, str, str2, str3, map));
        map2.put(HEADER_AUTH_SERVICE_PROVIDER, str3);
        return map2;
    }
}
