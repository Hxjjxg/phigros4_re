package com.twitter.sdk.android.core;

import android.os.Parcel;
import android.os.Parcelable;
import com.twitter.sdk.android.core.internal.oauth.OAuth1aService;
import java.net.HttpURLConnection;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TwitterAuthConfig implements Parcelable {
    public static final Parcelable.Creator<TwitterAuthConfig> CREATOR = new Parcelable.Creator<TwitterAuthConfig>() { // from class: com.twitter.sdk.android.core.TwitterAuthConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TwitterAuthConfig createFromParcel(Parcel parcel) {
            return new TwitterAuthConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TwitterAuthConfig[] newArray(int i) {
            return new TwitterAuthConfig[i];
        }
    };
    public static final int DEFAULT_AUTH_REQUEST_CODE = 140;
    private final String consumerKey;
    private final String consumerSecret;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getRequestCode() {
        return DEFAULT_AUTH_REQUEST_CODE;
    }

    public TwitterAuthConfig(String str, String str2) {
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("TwitterAuthConfig must not be created with null consumer key or secret.");
        }
        this.consumerKey = sanitizeAttribute(str);
        this.consumerSecret = sanitizeAttribute(str2);
    }

    private TwitterAuthConfig(Parcel parcel) {
        this.consumerKey = parcel.readString();
        this.consumerSecret = parcel.readString();
    }

    public void signRequest(TwitterAuthToken twitterAuthToken, HttpURLConnection httpURLConnection) {
        OAuth1aService.signRequest(this, twitterAuthToken, httpURLConnection, null);
    }

    public void signRequest(TwitterAuthToken twitterAuthToken, HttpURLConnection httpURLConnection, Map<String, String> map) {
        OAuth1aService.signRequest(this, twitterAuthToken, httpURLConnection, map);
    }

    public String getConsumerKey() {
        return this.consumerKey;
    }

    public String getConsumerSecret() {
        return this.consumerSecret;
    }

    static String sanitizeAttribute(String str) {
        if (str != null) {
            return str.trim();
        }
        return null;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.consumerKey);
        parcel.writeString(this.consumerSecret);
    }
}
