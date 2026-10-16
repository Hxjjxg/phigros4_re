package com.twitter.sdk.android.tweetui;

import android.net.Uri;
import android.text.TextUtils;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import io.fabric.sdk.android.Fabric;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class TweetUtils {
    static final String LOAD_TWEET_DEBUG = "loadTweet failure for Tweet Id %d.";
    private static final String PERMALINK_FORMAT = "https://twitter.com/%s/status/%d";
    private static final String TAG = "TweetUi";
    private static final String UNKNOWN_SCREEN_NAME = "twitter_unknown";

    private TweetUtils() {
    }

    public static void loadTweet(final long j, final LoadCallback<Tweet> loadCallback) {
        TweetUi.getInstance().getTweetRepository().loadTweet(j, new LoadCallback<Tweet>() { // from class: com.twitter.sdk.android.tweetui.TweetUtils.1
            @Override // com.twitter.sdk.android.tweetui.LoadCallback
            public void success(Tweet tweet) {
                loadCallback.success(tweet);
            }

            @Override // com.twitter.sdk.android.tweetui.LoadCallback
            public void failure(TwitterException twitterException) {
                Fabric.getLogger().d(TweetUtils.TAG, String.format(TweetUtils.LOAD_TWEET_DEBUG, Long.valueOf(j)));
            }
        });
    }

    public static void loadTweets(List<Long> list, LoadCallback<List<Tweet>> loadCallback) {
        TweetUi.getInstance().getTweetRepository().loadTweets(list, loadCallback);
    }

    static boolean isTweetResolvable(Tweet tweet) {
        return (tweet == null || tweet.id <= 0 || tweet.user == null || TextUtils.isEmpty(tweet.user.screenName)) ? false : true;
    }

    static Uri getPermalink(String str, long j) {
        String str2;
        if (j <= 0) {
            return null;
        }
        if (TextUtils.isEmpty(str)) {
            str2 = String.format(Locale.US, PERMALINK_FORMAT, UNKNOWN_SCREEN_NAME, Long.valueOf(j));
        } else {
            str2 = String.format(Locale.US, PERMALINK_FORMAT, str, Long.valueOf(j));
        }
        return Uri.parse(str2);
    }
}
