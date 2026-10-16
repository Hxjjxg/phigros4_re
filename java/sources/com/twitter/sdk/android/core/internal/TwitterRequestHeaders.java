package com.twitter.sdk.android.core.internal;

import android.text.TextUtils;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TwitterRequestHeaders {
    public static final String HEADER_USER_AGENT = "User-Agent";
    private final TwitterAuthConfig authConfig;
    private final String method;
    private final Map<String, String> postParams;
    private final Session session;
    private final String url;
    private final String userAgent;

    public TwitterRequestHeaders(String str, String str2, TwitterAuthConfig twitterAuthConfig, Session session, String str3, Map<String, String> map) {
        this.method = str;
        this.url = str2;
        this.authConfig = twitterAuthConfig;
        this.session = session;
        this.userAgent = str3;
        this.postParams = map;
    }

    public final Map<String, String> getHeaders() {
        HashMap map = new HashMap();
        map.putAll(getExtraHeaders());
        if (!TextUtils.isEmpty(this.userAgent)) {
            map.put("User-Agent", this.userAgent);
        }
        map.putAll(getAuthHeaders());
        return map;
    }

    protected Map<String, String> getExtraHeaders() {
        return Collections.emptyMap();
    }

    public Map<String, String> getAuthHeaders() {
        Session session = this.session;
        if (session != null && session.getAuthToken() != null) {
            return this.session.getAuthToken().getAuthHeaders(this.authConfig, getMethod(), this.url, getPostParams());
        }
        return Collections.emptyMap();
    }

    protected String getMethod() {
        return this.method;
    }

    protected Map<String, String> getPostParams() {
        return this.postParams;
    }
}
