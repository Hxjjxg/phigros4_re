package com.twitter.sdk.android.tweetui;

import android.os.Handler;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes.dex */
abstract class Repository {
    protected final ExecutorService executorService;
    protected final Handler mainHandler;
    protected final AuthRequestQueue queue;
    protected final TweetUi tweetUiKit;

    public Repository(TweetUi tweetUi, ExecutorService executorService, Handler handler, AuthRequestQueue authRequestQueue) {
        this.tweetUiKit = tweetUi;
        this.executorService = executorService;
        this.mainHandler = handler;
        this.queue = authRequestQueue;
    }
}
