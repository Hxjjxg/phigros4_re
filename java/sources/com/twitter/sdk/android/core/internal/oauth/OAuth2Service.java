package com.twitter.sdk.android.core.internal.oauth;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.internal.TwitterApi;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.UrlUtils;
import javax.net.ssl.SSLSocketFactory;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;

/* JADX INFO: loaded from: classes.dex */
public class OAuth2Service extends OAuthService {
    OAuth2Api api;

    interface OAuth2Api {
        @POST("/oauth2/token")
        @Headers({"Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
        @FormUrlEncoded
        void getAppAuthToken(@Header("Authorization") String str, @Field(OAuthConstants.PARAM_GRANT_TYPE) String str2, Callback<OAuth2Token> callback);

        @POST("/1.1/guest/activate.json")
        void getGuestToken(@Header("Authorization") String str, Callback<GuestTokenResponse> callback);
    }

    public OAuth2Service(TwitterCore twitterCore, SSLSocketFactory sSLSocketFactory, TwitterApi twitterApi) {
        super(twitterCore, sSLSocketFactory, twitterApi);
        this.api = (OAuth2Api) getApiAdapter().create(OAuth2Api.class);
    }

    public void requestGuestOrAppAuthToken(final Callback<OAuth2Token> callback) {
        requestAppAuthToken(new Callback<OAuth2Token>() { // from class: com.twitter.sdk.android.core.internal.oauth.OAuth2Service.1
            @Override // com.twitter.sdk.android.core.Callback
            public void success(Result<OAuth2Token> result) {
                final OAuth2Token oAuth2Token = result.data;
                OAuth2Service.this.requestGuestToken(new Callback<GuestTokenResponse>() { // from class: com.twitter.sdk.android.core.internal.oauth.OAuth2Service.1.1
                    @Override // com.twitter.sdk.android.core.Callback
                    public void success(Result<GuestTokenResponse> result2) {
                        callback.success(new Result(new GuestAuthToken(oAuth2Token.getTokenType(), oAuth2Token.getAccessToken(), result2.data.guestToken), null));
                    }

                    @Override // com.twitter.sdk.android.core.Callback
                    public void failure(TwitterException twitterException) {
                        Fabric.getLogger().e(TwitterCore.TAG, "Your app may be rate limited. Please talk to us regarding upgrading your consumer key.", twitterException);
                        callback.success(new Result(oAuth2Token, null));
                    }
                }, oAuth2Token);
            }

            @Override // com.twitter.sdk.android.core.Callback
            public void failure(TwitterException twitterException) {
                Fabric.getLogger().e(TwitterCore.TAG, "Failed to get app auth token", twitterException);
                Callback callback2 = callback;
                if (callback2 != null) {
                    callback2.failure(twitterException);
                }
            }
        });
    }

    public void requestAppAuthToken(Callback<OAuth2Token> callback) {
        this.api.getAppAuthToken(getAuthHeader(), OAuthConstants.GRANT_TYPE_CLIENT_CREDENTIALS, callback);
    }

    public void requestGuestToken(Callback<GuestTokenResponse> callback, OAuth2Token oAuth2Token) {
        this.api.getGuestToken(getAuthorizationHeader(oAuth2Token), callback);
    }

    public static String getAuthorizationHeader(OAuth2Token oAuth2Token) {
        return "Bearer " + oAuth2Token.getAccessToken();
    }

    private String getAuthHeader() {
        TwitterAuthConfig authConfig = getTwitterCore().getAuthConfig();
        return "Basic " + HttpRequest.Base64.encode(UrlUtils.percentEncode(authConfig.getConsumerKey()) + ":" + UrlUtils.percentEncode(authConfig.getConsumerSecret()));
    }
}
