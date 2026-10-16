package com.twitter.sdk.android.core.identity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.digits.sdk.android.DigitsClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.internal.oauth.OAuth1aService;
import com.twitter.sdk.android.core.internal.oauth.OAuthConstants;
import com.twitter.sdk.android.core.internal.oauth.OAuthResponse;
import io.fabric.sdk.android.Fabric;

/* JADX INFO: loaded from: classes.dex */
class OAuthController implements OAuthWebViewClient.Listener {
    private final TwitterAuthConfig authConfig;
    final Listener listener;
    private final OAuth1aService oAuth1aService;
    TwitterAuthToken requestToken;
    private final ProgressBar spinner;
    private final WebView webView;

    interface Listener {
        void onComplete(int i, Intent intent);
    }

    OAuthController(ProgressBar progressBar, WebView webView, TwitterAuthConfig twitterAuthConfig, OAuth1aService oAuth1aService, Listener listener) {
        this.spinner = progressBar;
        this.webView = webView;
        this.authConfig = twitterAuthConfig;
        this.oAuth1aService = oAuth1aService;
        this.listener = listener;
    }

    void startAuth() {
        Fabric.getLogger().d(TwitterCore.TAG, "Obtaining request token to start the sign in flow");
        this.oAuth1aService.requestTempToken(newRequestTempTokenCallback());
    }

    Callback<OAuthResponse> newRequestTempTokenCallback() {
        return new Callback<OAuthResponse>() { // from class: com.twitter.sdk.android.core.identity.OAuthController.1
            @Override // com.twitter.sdk.android.core.Callback
            public void success(Result<OAuthResponse> result) {
                OAuthController.this.requestToken = result.data.authToken;
                String authorizeUrl = OAuthController.this.oAuth1aService.getAuthorizeUrl(OAuthController.this.requestToken);
                Fabric.getLogger().d(TwitterCore.TAG, "Redirecting user to web view to complete authorization flow");
                OAuthController oAuthController = OAuthController.this;
                oAuthController.setUpWebView(oAuthController.webView, new OAuthWebViewClient(OAuthController.this.oAuth1aService.buildCallbackUrl(OAuthController.this.authConfig), OAuthController.this), authorizeUrl, new OAuthWebChromeClient());
            }

            @Override // com.twitter.sdk.android.core.Callback
            public void failure(TwitterException twitterException) {
                Fabric.getLogger().e(TwitterCore.TAG, "Failed to get request token", twitterException);
                OAuthController.this.handleAuthError(1, new TwitterAuthException("Failed to get request token"));
            }
        };
    }

    protected void handleAuthError(int i, TwitterAuthException twitterAuthException) {
        Intent intent = new Intent();
        intent.putExtra("auth_error", twitterAuthException);
        this.listener.onComplete(i, intent);
    }

    void setUpWebView(WebView webView, WebViewClient webViewClient, String str, WebChromeClient webChromeClient) {
        WebSettings settings = webView.getSettings();
        settings.setAllowFileAccess(false);
        settings.setJavaScriptEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(str);
        webView.setVisibility(4);
        webView.setWebChromeClient(webChromeClient);
    }

    private void handleWebViewSuccess(Bundle bundle) {
        String string;
        Fabric.getLogger().d(TwitterCore.TAG, "OAuth web view completed successfully");
        if (bundle != null && (string = bundle.getString(OAuthConstants.PARAM_VERIFIER)) != null) {
            Fabric.getLogger().d(TwitterCore.TAG, "Converting the request token to an access token.");
            this.oAuth1aService.requestAccessToken(newRequestAccessTokenCallback(), this.requestToken, string);
        } else {
            Fabric.getLogger().e(TwitterCore.TAG, "Failed to get authorization, bundle incomplete " + bundle, null);
            handleAuthError(1, new TwitterAuthException("Failed to get authorization, bundle incomplete"));
        }
    }

    Callback<OAuthResponse> newRequestAccessTokenCallback() {
        return new Callback<OAuthResponse>() { // from class: com.twitter.sdk.android.core.identity.OAuthController.2
            @Override // com.twitter.sdk.android.core.Callback
            public void success(Result<OAuthResponse> result) {
                Intent intent = new Intent();
                OAuthResponse oAuthResponse = result.data;
                intent.putExtra("screen_name", oAuthResponse.userName);
                intent.putExtra(DigitsClient.EXTRA_USER_ID, oAuthResponse.userId);
                intent.putExtra("tk", oAuthResponse.authToken.token);
                intent.putExtra("ts", oAuthResponse.authToken.secret);
                OAuthController.this.listener.onComplete(-1, intent);
            }

            @Override // com.twitter.sdk.android.core.Callback
            public void failure(TwitterException twitterException) {
                Fabric.getLogger().e(TwitterCore.TAG, "Failed to get access token", twitterException);
                OAuthController.this.handleAuthError(1, new TwitterAuthException("Failed to get access token"));
            }
        };
    }

    private void handleWebViewError(WebViewException webViewException) {
        Fabric.getLogger().e(TwitterCore.TAG, "OAuth web view completed with an error", webViewException);
        handleAuthError(1, new TwitterAuthException("OAuth web view completed with an error"));
    }

    private void dismissWebView() {
        this.webView.stopLoading();
        dismissSpinner();
    }

    private void dismissSpinner() {
        this.spinner.setVisibility(8);
    }

    @Override // com.twitter.sdk.android.core.identity.OAuthWebViewClient.Listener
    public void onPageFinished(WebView webView, String str) {
        dismissSpinner();
        webView.setVisibility(0);
    }

    @Override // com.twitter.sdk.android.core.identity.OAuthWebViewClient.Listener
    public void onSuccess(Bundle bundle) {
        handleWebViewSuccess(bundle);
        dismissWebView();
    }

    @Override // com.twitter.sdk.android.core.identity.OAuthWebViewClient.Listener
    public void onError(WebViewException webViewException) {
        handleWebViewError(webViewException);
        dismissWebView();
    }
}
