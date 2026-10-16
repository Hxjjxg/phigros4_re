package com.twitter.sdk.android.core;

import com.twitter.sdk.android.core.internal.oauth.OAuth2Service;
import com.twitter.sdk.android.core.internal.oauth.OAuth2Token;

/* JADX INFO: loaded from: classes.dex */
class GuestAuthClient {
    private final OAuth2Service service;

    GuestAuthClient(OAuth2Service oAuth2Service) {
        if (oAuth2Service == null) {
            throw new IllegalArgumentException("OAuth2Service must not be null");
        }
        this.service = oAuth2Service;
    }

    void authorize(SessionManager<AppSession> sessionManager, Callback<AppSession> callback) {
        if (sessionManager == null) {
            throw new IllegalArgumentException("SessionManager must not be null");
        }
        this.service.requestGuestOrAppAuthToken(new CallbackWrapper(sessionManager, callback));
    }

    class CallbackWrapper extends Callback<OAuth2Token> {
        private final SessionManager<AppSession> appSessionManager;
        private final Callback<AppSession> callback;

        CallbackWrapper(SessionManager<AppSession> sessionManager, Callback<AppSession> callback) {
            this.appSessionManager = sessionManager;
            this.callback = callback;
        }

        @Override // com.twitter.sdk.android.core.Callback
        public void success(Result<OAuth2Token> result) {
            AppSession appSession = new AppSession(result.data);
            this.appSessionManager.setSession(appSession.getId(), appSession);
            Callback<AppSession> callback = this.callback;
            if (callback != null) {
                callback.success(new Result<>(appSession, result.response));
            }
        }

        @Override // com.twitter.sdk.android.core.Callback
        public void failure(TwitterException twitterException) {
            Callback<AppSession> callback = this.callback;
            if (callback != null) {
                callback.failure(twitterException);
            }
        }
    }
}
