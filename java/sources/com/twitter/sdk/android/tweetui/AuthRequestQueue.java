package com.twitter.sdk.android.tweetui;

import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
class AuthRequestQueue {
    private final List<SessionManager<? extends Session>> sessionManagers;
    private final TwitterCore twitterCore;
    final Queue<Callback<TwitterApiClient>> queue = new ConcurrentLinkedQueue();
    final AtomicBoolean awaitingSession = new AtomicBoolean(true);

    AuthRequestQueue(TwitterCore twitterCore, List<SessionManager<? extends Session>> list) {
        this.twitterCore = twitterCore;
        this.sessionManagers = list;
    }

    protected synchronized boolean addRequest(Callback<TwitterApiClient> callback) {
        if (callback == null) {
            return false;
        }
        if (!this.awaitingSession.get()) {
            Session validSession = getValidSession();
            if (validSession != null) {
                callback.success(new Result<>(this.twitterCore.getApiClient(validSession), null));
            } else {
                this.queue.add(callback);
                this.awaitingSession.set(true);
                requestAuth();
            }
        } else {
            this.queue.add(callback);
        }
        return true;
    }

    synchronized void sessionRestored(Session session) {
        try {
            if (session != null) {
                flushQueueOnSuccess(this.twitterCore.getApiClient(session));
            } else if (this.queue.size() > 0) {
                requestAuth();
            } else {
                this.awaitingSession.set(false);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    void requestAuth() {
        this.twitterCore.logInGuest(getAppAuthTokenCallback());
    }

    synchronized void flushQueueOnSuccess(TwitterApiClient twitterApiClient) {
        this.awaitingSession.set(false);
        while (!this.queue.isEmpty()) {
            this.queue.poll().success(new Result<>(twitterApiClient, null));
        }
    }

    synchronized void flushQueueOnError(TwitterException twitterException) {
        this.awaitingSession.set(false);
        while (!this.queue.isEmpty()) {
            this.queue.poll().failure(twitterException);
        }
    }

    Session getValidSession() {
        Session activeSession = SessionUtils.getActiveSession(this.sessionManagers);
        if (activeSession == null || activeSession.getAuthToken() == null || activeSession.getAuthToken().isExpired()) {
            return null;
        }
        return activeSession;
    }

    Callback<AppSession> getAppAuthTokenCallback() {
        return new Callback<AppSession>() { // from class: com.twitter.sdk.android.tweetui.AuthRequestQueue.1
            @Override // com.twitter.sdk.android.core.Callback
            public void success(Result<AppSession> result) {
                AuthRequestQueue authRequestQueue = AuthRequestQueue.this;
                authRequestQueue.flushQueueOnSuccess(authRequestQueue.twitterCore.getApiClient(result.data));
            }

            @Override // com.twitter.sdk.android.core.Callback
            public void failure(TwitterException twitterException) {
                AuthRequestQueue.this.flushQueueOnError(twitterException);
            }
        };
    }
}
