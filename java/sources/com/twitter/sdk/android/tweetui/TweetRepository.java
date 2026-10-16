package com.twitter.sdk.android.tweetui;

import android.os.Handler;
import android.text.TextUtils;
import androidx.collection.LruCache;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import io.fabric.sdk.android.Fabric;
import java.util.List;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes.dex */
class TweetRepository extends Repository {
    private static final String AUTH_ERROR = "Auth could not be obtained.";
    private static final int DEFAULT_CACHE_SIZE = 20;
    private static final String TAG = "TweetUi";
    final LruCache<Long, FormattedTweetText> formatCache;
    final LruCache<Long, Tweet> tweetCache;

    TweetRepository(TweetUi tweetUi, ExecutorService executorService, Handler handler, AuthRequestQueue authRequestQueue) {
        super(tweetUi, executorService, handler, authRequestQueue);
        this.tweetCache = new LruCache<>(20);
        this.formatCache = new LruCache<>(20);
    }

    FormattedTweetText formatTweetText(Tweet tweet) {
        if (tweet == null) {
            return null;
        }
        FormattedTweetText formattedTweetText = this.formatCache.get(Long.valueOf(tweet.id));
        if (formattedTweetText != null) {
            return formattedTweetText;
        }
        FormattedTweetText tweetText = TweetTextUtils.formatTweetText(tweet);
        if (tweetText != null && !TextUtils.isEmpty(tweetText.text)) {
            this.formatCache.put(Long.valueOf(tweet.id), tweetText);
        }
        return tweetText;
    }

    void loadTweet(final long j, final LoadCallback<Tweet> loadCallback) {
        Tweet tweet = this.tweetCache.get(Long.valueOf(j));
        if (tweet != null) {
            deliverTweet(tweet, loadCallback);
        } else {
            this.queue.addRequest(new Callback<TwitterApiClient>() { // from class: com.twitter.sdk.android.tweetui.TweetRepository.1
                @Override // com.twitter.sdk.android.core.Callback
                public void success(Result<TwitterApiClient> result) {
                    result.data.getStatusesService().show(Long.valueOf(j), null, null, null, TweetRepository.this.new TweetApiCallback(loadCallback));
                }

                @Override // com.twitter.sdk.android.core.Callback
                public void failure(TwitterException twitterException) {
                    Fabric.getLogger().e(TweetRepository.TAG, TweetRepository.AUTH_ERROR, twitterException);
                    LoadCallback loadCallback2 = loadCallback;
                    if (loadCallback2 != null) {
                        loadCallback2.failure(twitterException);
                    }
                }
            });
        }
    }

    void loadTweets(final List<Long> list, final LoadCallback<List<Tweet>> loadCallback) {
        this.queue.addRequest(new Callback<TwitterApiClient>() { // from class: com.twitter.sdk.android.tweetui.TweetRepository.2
            @Override // com.twitter.sdk.android.core.Callback
            public void success(Result<TwitterApiClient> result) {
                result.data.getStatusesService().lookup(TextUtils.join(",", list), null, null, null, TweetRepository.this.new TweetsApiCallback(list, loadCallback));
            }

            @Override // com.twitter.sdk.android.core.Callback
            public void failure(TwitterException twitterException) {
                Fabric.getLogger().e(TweetRepository.TAG, TweetRepository.AUTH_ERROR, twitterException);
                LoadCallback loadCallback2 = loadCallback;
                if (loadCallback2 != null) {
                    loadCallback2.failure(twitterException);
                }
            }
        });
    }

    protected void updateCache(Tweet tweet) {
        this.tweetCache.put(Long.valueOf(tweet.id), tweet);
    }

    private void deliverTweet(final Tweet tweet, final LoadCallback<Tweet> loadCallback) {
        if (loadCallback == null) {
            return;
        }
        this.mainHandler.post(new Runnable() { // from class: com.twitter.sdk.android.tweetui.TweetRepository.3
            @Override // java.lang.Runnable
            public void run() {
                loadCallback.success(tweet);
            }
        });
    }

    class TweetApiCallback extends ApiCallback<Tweet> {
        TweetApiCallback(LoadCallback<Tweet> loadCallback) {
            super(loadCallback);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // com.twitter.sdk.android.tweetui.ApiCallback, com.twitter.sdk.android.core.Callback
        public void success(Result<Tweet> result) {
            Tweet tweet = result.data;
            TweetRepository.this.updateCache(tweet);
            if (this.cb != 0) {
                this.cb.success((T) tweet);
            }
        }
    }

    class TweetsApiCallback extends ApiCallback<List<Tweet>> {
        List<Long> tweetIds;

        TweetsApiCallback(List<Long> list, LoadCallback<List<Tweet>> loadCallback) {
            super(loadCallback);
            this.tweetIds = list;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // com.twitter.sdk.android.tweetui.ApiCallback, com.twitter.sdk.android.core.Callback
        public void success(Result<List<Tweet>> result) {
            if (this.cb != 0) {
                this.cb.success((T) Utils.orderTweets(this.tweetIds, result.data));
            }
        }
    }
}
