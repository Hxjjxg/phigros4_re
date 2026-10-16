package com.twitter.sdk.android.core.identity;

import android.app.Activity;
import android.content.Intent;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterSession;

/* JADX INFO: loaded from: classes.dex */
class OAuthHandler extends AuthHandler {
    public OAuthHandler(TwitterAuthConfig twitterAuthConfig, Callback<TwitterSession> callback, int i) {
        super(twitterAuthConfig, callback, i);
    }

    @Override // com.twitter.sdk.android.core.identity.AuthHandler
    public boolean authorize(Activity activity) {
        activity.startActivityForResult(newIntent(activity), this.requestCode);
        return true;
    }

    Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, (Class<?>) OAuthActivity.class);
        intent.putExtra("auth_config", getAuthConfig());
        return intent;
    }
}
