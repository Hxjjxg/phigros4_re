package com.twitter.sdk.android.tweetui;

import android.content.Context;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.BaseTweetView;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TweetViewFetchAdapter<T extends BaseTweetView> extends TweetViewAdapter<T> {
    public TweetViewFetchAdapter(Context context) {
        super(context);
    }

    public TweetViewFetchAdapter(Context context, List<Long> list) {
        this(context, list, null);
    }

    public TweetViewFetchAdapter(Context context, List<Long> list, LoadCallback<List<Tweet>> loadCallback) {
        super(context);
        setTweetIds(list, loadCallback);
    }

    public void setTweetIds(List<Long> list) {
        setTweetIds(list, null);
    }

    public void setTweetIds(List<Long> list, final LoadCallback<List<Tweet>> loadCallback) {
        TweetUi.getInstance().getTweetRepository().loadTweets(list, new LoadCallback<List<Tweet>>() { // from class: com.twitter.sdk.android.tweetui.TweetViewFetchAdapter.1
            @Override // com.twitter.sdk.android.tweetui.LoadCallback
            public void success(List<Tweet> list2) {
                TweetViewFetchAdapter.this.setTweets(list2);
                LoadCallback loadCallback2 = loadCallback;
                if (loadCallback2 != null) {
                    loadCallback2.success(list2);
                }
            }

            @Override // com.twitter.sdk.android.tweetui.LoadCallback
            public void failure(TwitterException twitterException) {
                LoadCallback loadCallback2 = loadCallback;
                if (loadCallback2 != null) {
                    loadCallback2.failure(twitterException);
                }
            }
        });
    }
}
