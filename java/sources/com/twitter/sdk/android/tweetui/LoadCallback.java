package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.TwitterException;

/* JADX INFO: loaded from: classes.dex */
public interface LoadCallback<T> {
    void failure(TwitterException twitterException);

    void success(T t);
}
