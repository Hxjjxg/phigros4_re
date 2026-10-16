package com.digits.sdk.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.internal.TwitterApi;
import com.twitter.sdk.android.core.internal.oauth.OAuth2Service;
import com.twitter.sdk.android.core.internal.oauth.OAuth2Token;
import com.twitter.sdk.android.core.internal.scribe.EventNamespace;
import com.twitter.sdk.android.core.models.User;
import retrofit.client.Response;

/* JADX INFO: loaded from: classes.dex */
public class DigitsClient {
    public static final String EXTRA_FALLBACK_REASON = "fallback_reason";
    public static final String EXTRA_PHONE = "phone_number";
    public static final String EXTRA_REQUEST_ID = "request_id";
    public static final String EXTRA_RESULT_RECEIVER = "receiver";
    public static final String EXTRA_USER_ID = "user_id";
    private static final String SCRIBE_ACTION = "impression";
    private static final String SCRIBE_CLIENT = "android";
    private static final String SCRIBE_COMPONENT = "";
    private static final String SCRIBE_ELEMENT = "";
    private static final String SCRIBE_PAGE = "digits";
    private static final String SCRIBE_SECTION = "";
    public static final String THIRD_PARTY_CONFIRMATION_CODE = "third_party_confirmation_code";
    private final OAuth2Service authService;
    private final Digits digits;
    protected DigitsAPIProvider digitsAPIProvider;
    private final SessionManager<DigitsSession> sessionManager;
    private final TwitterCore twitterCore;

    DigitsClient() {
        this(Digits.getInstance(), TwitterCore.getInstance(), Digits.getSessionManager(), new OAuth2Service(TwitterCore.getInstance(), TwitterCore.getInstance().getSSLSocketFactory(), new TwitterApi()), null);
    }

    DigitsClient(Digits digits, TwitterCore twitterCore, SessionManager<DigitsSession> sessionManager, OAuth2Service oAuth2Service, DigitsAPIProvider digitsAPIProvider) {
        if (twitterCore == null) {
            throw new IllegalArgumentException("twitter must not be null");
        }
        if (digits == null) {
            throw new IllegalArgumentException("digits must not be null");
        }
        if (sessionManager == null) {
            throw new IllegalArgumentException("sessionManager must not be null");
        }
        if (oAuth2Service == null) {
            throw new IllegalArgumentException("authService must not be null");
        }
        this.twitterCore = twitterCore;
        this.digits = digits;
        this.sessionManager = sessionManager;
        this.authService = oAuth2Service;
        this.digitsAPIProvider = digitsAPIProvider;
    }

    protected void registerDevice(Context context, DigitsController digitsController, final String str, final Callback<Response> callback) {
        this.authService.requestGuestOrAppAuthToken(new DigitsCallback<OAuth2Token>(context, digitsController) { // from class: com.digits.sdk.android.DigitsClient.1
            @Override // com.twitter.sdk.android.core.Callback
            public void success(Result<OAuth2Token> result) {
                DigitsSession session = DigitsClient.this.setSession(result);
                DigitsClient.this.digitsAPIProvider = new DigitsAPIProvider(session, DigitsClient.this.twitterCore.getAuthConfig(), DigitsClient.this.twitterCore.getSSLSocketFactory(), DigitsClient.this.digits.getExecutorService());
                DigitsClient.this.digitsAPIProvider.getDeviceService().register(str, DigitsClient.THIRD_PARTY_CONFIRMATION_CODE, true, callback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DigitsSession setSession(Result<OAuth2Token> result) {
        DigitsSession digitsSession = new DigitsSession(result.data);
        this.sessionManager.setSession(0L, digitsSession);
        return digitsSession;
    }

    protected void createAccount(String str, String str2, Callback<User> callback) {
        this.digitsAPIProvider.getSdkService().account(str2, str, callback);
    }

    protected void startSignUp(AuthCallback authCallback) {
        startSignUp(authCallback, this.twitterCore.getContext());
    }

    void startSignUp(AuthCallback authCallback, Context context) {
        scribeImpression();
        DigitsSession digitsSession = (DigitsSession) this.sessionManager.getActiveSession();
        if (digitsSession != null && !digitsSession.isLoggedOutUser()) {
            authCallback.success(digitsSession, null);
        } else {
            startPhoneNumberActivity(authCallback, context);
        }
    }

    private void startPhoneNumberActivity(AuthCallback authCallback, Context context) {
        Intent intent = new Intent(context, this.digits.getActivityClassManager().getPhoneNumberActivity());
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_RESULT_RECEIVER, new LoginResultReceiver(authCallback, this.sessionManager));
        intent.putExtras(bundle);
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    protected void loginDevice(String str, long j, String str2, Callback<DigitsUser> callback) {
        this.digitsAPIProvider.getSdkService().login(str, j, str2, callback);
    }

    protected void authDevice(String str, Callback<AuthResponse> callback) {
        this.digitsAPIProvider.getSdkService().auth(str, callback);
    }

    protected void verifyPin(String str, long j, String str2, Callback<DigitsUser> callback) {
        this.digitsAPIProvider.getSdkService().verifyPin(str, j, str2, callback);
    }

    private void scribeImpression() {
        this.digits.scribe(new EventNamespace.Builder().setClient("android").setPage(SCRIBE_PAGE).setSection("").setComponent("").setElement("").setAction("impression").builder());
    }
}
