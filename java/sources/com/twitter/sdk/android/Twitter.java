package com.twitter.sdk.android;

import android.app.Activity;
import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.twitter.sdk.android.tweetui.TweetUi;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitGroup;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public class Twitter extends Kit implements KitGroup {
    public final TwitterCore core;
    public final Digits digits;
    public final Collection<? extends Kit> kits;
    public final TweetComposer tweetComposer;
    public final TweetUi tweetUi;

    @Override // io.fabric.sdk.android.Kit
    protected Object doInBackground() {
        return null;
    }

    @Override // io.fabric.sdk.android.Kit
    public String getIdentifier() {
        return "com.twitter.sdk.android:twitter";
    }

    @Override // io.fabric.sdk.android.Kit
    public String getVersion() {
        return "1.3.033";
    }

    public static Twitter getInstance() {
        return (Twitter) Fabric.getKit(Twitter.class);
    }

    private static void checkInitialized() {
        if (getInstance() == null) {
            throw new IllegalStateException("Must start Twitter Kit with Fabric.with() first");
        }
    }

    public Twitter(TwitterAuthConfig twitterAuthConfig) {
        TwitterCore twitterCore = new TwitterCore(twitterAuthConfig);
        this.core = twitterCore;
        TweetUi tweetUi = new TweetUi();
        this.tweetUi = tweetUi;
        TweetComposer tweetComposer = new TweetComposer();
        this.tweetComposer = tweetComposer;
        Digits digits = new Digits();
        this.digits = digits;
        this.kits = Collections.unmodifiableCollection(Arrays.asList(twitterCore, tweetUi, tweetComposer, digits));
    }

    @Override // io.fabric.sdk.android.KitGroup
    public Collection<? extends Kit> getKits() {
        return this.kits;
    }

    public static void logIn(Activity activity, Callback<TwitterSession> callback) {
        checkInitialized();
        getInstance().core.logIn(activity, callback);
    }

    public static void logOut() {
        checkInitialized();
        getInstance().core.logOut();
    }

    public static SessionManager<TwitterSession> getSessionManager() {
        checkInitialized();
        return getInstance().core.getSessionManager();
    }

    public static TwitterApiClient getApiClient() {
        checkInitialized();
        return getInstance().core.getApiClient();
    }

    public static TwitterApiClient getApiClient(Session session) {
        checkInitialized();
        return getInstance().core.getApiClient(session);
    }
}
