package com.twitter.sdk.android.core.identity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.tds.common.tracker.model.NetworkStateModel;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.internal.scribe.DefaultScribeClient;
import com.twitter.sdk.android.core.internal.scribe.EventNamespace;
import com.twitter.sdk.android.core.internal.scribe.TwitterCoreScribeClientHolder;
import io.fabric.sdk.android.Fabric;

/* JADX INFO: loaded from: classes.dex */
public class TwitterAuthClient {
    private static final String SCRIBE_ACTION = "impression";
    private static final String SCRIBE_CLIENT = "android";
    private static final String SCRIBE_COMPONENT = "";
    private static final String SCRIBE_ELEMENT = "";
    private static final String SCRIBE_LOGIN_PAGE = "login";
    private static final String SCRIBE_SECTION = "";
    private static final String SCRIBE_SHARE_EMAIL_PAGE = "shareemail";
    private final TwitterAuthConfig authConfig;
    final AuthState authState;
    private final Context context;
    final SessionManager<TwitterSession> sessionManager;

    private static class AuthStateLazyHolder {
        private static final AuthState INSTANCE = new AuthState();

        private AuthStateLazyHolder() {
        }
    }

    public int getRequestCode() {
        return this.authConfig.getRequestCode();
    }

    public TwitterAuthClient() {
        this(TwitterCore.getInstance().getContext(), TwitterCore.getInstance().getAuthConfig(), TwitterCore.getInstance().getSessionManager(), AuthStateLazyHolder.INSTANCE);
    }

    TwitterAuthClient(Context context, TwitterAuthConfig twitterAuthConfig, SessionManager<TwitterSession> sessionManager, AuthState authState) {
        this.authState = authState;
        this.context = context;
        this.authConfig = twitterAuthConfig;
        this.sessionManager = sessionManager;
    }

    public void authorize(Activity activity, Callback<TwitterSession> callback) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity must not be null.");
        }
        if (callback == null) {
            throw new IllegalArgumentException("Callback must not be null.");
        }
        if (activity.isFinishing()) {
            Fabric.getLogger().e(TwitterCore.TAG, "Cannot authorize, activity is finishing.", null);
        } else {
            handleAuthorize(activity, callback);
        }
    }

    private void handleAuthorize(Activity activity, Callback<TwitterSession> callback) {
        scribeAuthorizeImpression();
        CallbackWrapper callbackWrapper = new CallbackWrapper(this.sessionManager, callback);
        if (authorizeUsingSSO(activity, callbackWrapper) || authorizeUsingOAuth(activity, callbackWrapper)) {
            return;
        }
        callbackWrapper.failure(new TwitterAuthException("Authorize failed."));
    }

    private boolean authorizeUsingSSO(Activity activity, CallbackWrapper callbackWrapper) {
        if (!SSOAuthHandler.isAvailable(activity)) {
            return false;
        }
        Fabric.getLogger().d(TwitterCore.TAG, "Using SSO");
        AuthState authState = this.authState;
        TwitterAuthConfig twitterAuthConfig = this.authConfig;
        return authState.beginAuthorize(activity, new SSOAuthHandler(twitterAuthConfig, callbackWrapper, twitterAuthConfig.getRequestCode()));
    }

    private boolean authorizeUsingOAuth(Activity activity, CallbackWrapper callbackWrapper) {
        Fabric.getLogger().d(TwitterCore.TAG, "Using OAuth");
        AuthState authState = this.authState;
        TwitterAuthConfig twitterAuthConfig = this.authConfig;
        return authState.beginAuthorize(activity, new OAuthHandler(twitterAuthConfig, callbackWrapper, twitterAuthConfig.getRequestCode()));
    }

    private void scribeAuthorizeImpression() {
        DefaultScribeClient scribeClient = getScribeClient();
        if (scribeClient == null) {
            return;
        }
        scribeClient.scribeSyndicatedSdkImpressionEvents(new EventNamespace.Builder().setClient("android").setPage(SCRIBE_LOGIN_PAGE).setSection("").setComponent("").setElement("").setAction("impression").builder());
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        Fabric.getLogger().d(TwitterCore.TAG, "onActivityResult called with " + i + " " + i2);
        if (!this.authState.isAuthorizeInProgress()) {
            Fabric.getLogger().e(TwitterCore.TAG, "Authorize not in progress", null);
            return;
        }
        AuthHandler authHandler = this.authState.getAuthHandler();
        if (authHandler == null || !authHandler.handleOnActivityResult(i, i2, intent)) {
            return;
        }
        this.authState.endAuthorize();
    }

    public void requestEmail(TwitterSession twitterSession, Callback<String> callback) {
        if (twitterSession == null) {
            throw new IllegalArgumentException("Session must not be null.");
        }
        if (callback == null) {
            throw new IllegalArgumentException("Callback must not be null.");
        }
        scribeRequestEmail();
        this.context.startActivity(newShareEmailIntent(twitterSession, callback));
    }

    protected DefaultScribeClient getScribeClient() {
        return TwitterCoreScribeClientHolder.getScribeClient();
    }

    private void scribeRequestEmail() {
        DefaultScribeClient scribeClient = getScribeClient();
        if (scribeClient == null) {
            return;
        }
        scribeClient.scribeSyndicatedSdkImpressionEvents(new EventNamespace.Builder().setClient("android").setPage(SCRIBE_SHARE_EMAIL_PAGE).setSection("").setComponent("").setElement("").setAction("impression").builder());
    }

    Intent newShareEmailIntent(TwitterSession twitterSession, Callback<String> callback) {
        return new Intent(this.context, (Class<?>) ShareEmailActivity.class).setFlags(268435456).putExtra(NetworkStateModel.PARAM_SESSION_ID, twitterSession.getId()).putExtra("result_receiver", new ShareEmailResultReceiver(callback));
    }

    static class CallbackWrapper extends Callback<TwitterSession> {
        private final Callback<TwitterSession> callback;
        private final SessionManager<TwitterSession> sessionManager;

        public CallbackWrapper(SessionManager<TwitterSession> sessionManager, Callback<TwitterSession> callback) {
            this.sessionManager = sessionManager;
            this.callback = callback;
        }

        @Override // com.twitter.sdk.android.core.Callback
        public void success(Result<TwitterSession> result) {
            Fabric.getLogger().d(TwitterCore.TAG, "Authorization completed successfully");
            this.sessionManager.setActiveSession(result.data);
            this.callback.success(result);
        }

        @Override // com.twitter.sdk.android.core.Callback
        public void failure(TwitterException twitterException) {
            Fabric.getLogger().e(TwitterCore.TAG, "Authorization completed with an error", twitterException);
            this.callback.failure(twitterException);
        }
    }
}
