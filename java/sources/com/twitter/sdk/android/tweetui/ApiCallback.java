package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiException;
import com.twitter.sdk.android.core.TwitterException;
import io.fabric.sdk.android.Fabric;

/* JADX INFO: loaded from: classes.dex */
abstract class ApiCallback<T> extends Callback<T> {
    private static final String API_CALL_ERROR = "API call failure.";
    private static final String TAG = "TweetUi";
    protected final LoadCallback<T> cb;

    @Override // com.twitter.sdk.android.core.Callback
    public abstract void success(Result<T> result);

    ApiCallback(LoadCallback<T> loadCallback) {
        this.cb = loadCallback;
    }

    @Override // com.twitter.sdk.android.core.Callback
    public void failure(TwitterException twitterException) {
        TwitterApiException twitterApiException = (TwitterApiException) twitterException;
        int errorCode = twitterApiException.getErrorCode();
        Fabric.getLogger().e(TAG, API_CALL_ERROR, twitterApiException);
        if (errorCode == 89 || errorCode == 239) {
            TweetUi.getInstance().clearAppSession(0L);
        }
        LoadCallback<T> loadCallback = this.cb;
        if (loadCallback != null) {
            loadCallback.failure(twitterException);
        }
    }
}
