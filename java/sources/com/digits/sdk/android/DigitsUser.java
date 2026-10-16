package com.digits.sdk.android;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.internal.oauth.OAuthConstants;

/* JADX INFO: loaded from: classes.dex */
class DigitsUser {

    @SerializedName("screen_name")
    public String screenName;

    @SerializedName(OAuthConstants.PARAM_TOKEN_SECRET)
    public String secret;

    @SerializedName(OAuthConstants.PARAM_TOKEN)
    public String token;

    @SerializedName(DigitsClient.EXTRA_USER_ID)
    public long userId;

    DigitsUser() {
    }

    public boolean isEmpty() {
        return this.token == null && this.secret == null && this.screenName == null && this.userId == 0;
    }
}
