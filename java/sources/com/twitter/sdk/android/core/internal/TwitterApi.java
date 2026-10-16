package com.twitter.sdk.android.core.internal;

import android.net.Uri;
import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class TwitterApi {
    public static final String BASE_HOST = "api.twitter.com";
    public static final String BASE_HOST_URL = "https://api.twitter.com";
    private final String baseHostUrl;

    public TwitterApi() {
        this("https://api.twitter.com");
    }

    public TwitterApi(String str) {
        this.baseHostUrl = str;
    }

    public String getBaseHostUrl() {
        return this.baseHostUrl;
    }

    public Uri.Builder buildUponBaseHostUrl(String... strArr) {
        Uri.Builder builderBuildUpon = Uri.parse(getBaseHostUrl()).buildUpon();
        if (strArr != null) {
            for (String str : strArr) {
                builderBuildUpon.appendPath(str);
            }
        }
        return builderBuildUpon;
    }

    public static String buildUserAgent(String str, String str2) {
        return str + '/' + str2 + ' ' + Build.MODEL + '/' + Build.VERSION.RELEASE + " (" + Build.MANUFACTURER + ';' + Build.MODEL + ';' + Build.BRAND + ';' + Build.PRODUCT + ')';
    }
}
