package com.twitter.sdk.android.core.identity;

import android.app.Activity;
import android.content.Intent;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

/* JADX INFO: loaded from: classes.dex */
public abstract class AuthHandler {
    static final String EXTRA_AUTH_ERROR = "auth_error";
    static final String EXTRA_SCREEN_NAME = "screen_name";
    static final String EXTRA_TOKEN = "tk";
    static final String EXTRA_TOKEN_SECRET = "ts";
    static final String EXTRA_USER_ID = "user_id";
    static final int RESULT_CODE_ERROR = 1;
    private final Callback<TwitterSession> callback;
    private final TwitterAuthConfig config;
    protected final int requestCode;

    public abstract boolean authorize(Activity activity);

    AuthHandler(TwitterAuthConfig twitterAuthConfig, Callback<TwitterSession> callback, int i) {
        this.config = twitterAuthConfig;
        this.callback = callback;
        this.requestCode = i;
    }

    TwitterAuthConfig getAuthConfig() {
        return this.config;
    }

    Callback<TwitterSession> getCallback() {
        return this.callback;
    }

    public boolean handleOnActivityResult(int i, int i2, Intent intent) {
        if (this.requestCode != i) {
            return false;
        }
        Callback<TwitterSession> callback = getCallback();
        if (callback == null) {
            return true;
        }
        if (i2 == -1) {
            String stringExtra = intent.getStringExtra(EXTRA_TOKEN);
            String stringExtra2 = intent.getStringExtra(EXTRA_TOKEN_SECRET);
            String stringExtra3 = intent.getStringExtra(EXTRA_SCREEN_NAME);
            callback.success(new TwitterSession(new TwitterAuthToken(stringExtra, stringExtra2), intent.getLongExtra("user_id", 0L), stringExtra3), null);
            return true;
        }
        if (intent != null && intent.hasExtra(EXTRA_AUTH_ERROR)) {
            callback.failure((TwitterAuthException) intent.getSerializableExtra(EXTRA_AUTH_ERROR));
            return true;
        }
        callback.failure(new TwitterAuthException("Authorize failed."));
        return true;
    }
}
