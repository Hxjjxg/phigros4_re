package com.twitter.sdk.android.core.internal.oauth;

import android.net.Uri;
import com.tds.common.tracker.constants.CommonParam;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.internal.TwitterApi;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.UrlUtils;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.TreeMap;
import javax.net.ssl.SSLSocketFactory;
import retrofit.client.Response;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/* JADX INFO: loaded from: classes.dex */
public class OAuth1aService extends OAuthService {
    private static final String CALLBACK_URL = "twittersdk://callback";
    private static final String PARAM_SCREEN_NAME = "screen_name";
    private static final String PARAM_USER_ID = "user_id";
    private static final String RESOURCE_OAUTH = "oauth";
    OAuthApi api;

    interface OAuthApi {
        @POST("/oauth/access_token")
        void getAccessToken(@Header("Authorization") String str, @Query(OAuthConstants.PARAM_VERIFIER) String str2, Callback<Response> callback);

        @POST("/oauth/request_token")
        void getTempToken(@Header("Authorization") String str, Callback<Response> callback);
    }

    public OAuth1aService(TwitterCore twitterCore, SSLSocketFactory sSLSocketFactory, TwitterApi twitterApi) {
        super(twitterCore, sSLSocketFactory, twitterApi);
        this.api = (OAuthApi) getApiAdapter().create(OAuthApi.class);
    }

    public void requestTempToken(Callback<OAuthResponse> callback) {
        TwitterAuthConfig authConfig = getTwitterCore().getAuthConfig();
        this.api.getTempToken(getAuthorizationHeader(authConfig, null, buildCallbackUrl(authConfig), HttpRequest.METHOD_POST, getTempTokenUrl(), null), getCallbackWrapper(callback));
    }

    String getTempTokenUrl() {
        return getApi().getBaseHostUrl() + "/oauth/request_token";
    }

    public String buildCallbackUrl(TwitterAuthConfig twitterAuthConfig) {
        return Uri.parse(CALLBACK_URL).buildUpon().appendQueryParameter(CommonParam.VERSION, getTwitterCore().getVersion()).appendQueryParameter(SettingsJsonConstants.APP_KEY, twitterAuthConfig.getConsumerKey()).build().toString();
    }

    public void requestAccessToken(Callback<OAuthResponse> callback, TwitterAuthToken twitterAuthToken, String str) {
        this.api.getAccessToken(getAuthorizationHeader(getTwitterCore().getAuthConfig(), twitterAuthToken, null, HttpRequest.METHOD_POST, getAccessTokenUrl(), null), str, getCallbackWrapper(callback));
    }

    String getAccessTokenUrl() {
        return getApi().getBaseHostUrl() + "/oauth/access_token";
    }

    public String getAuthorizeUrl(TwitterAuthToken twitterAuthToken) {
        return getApi().buildUponBaseHostUrl(RESOURCE_OAUTH, "authorize").appendQueryParameter(OAuthConstants.PARAM_TOKEN, twitterAuthToken.token).build().toString();
    }

    public static void signRequest(TwitterAuthConfig twitterAuthConfig, TwitterAuthToken twitterAuthToken, HttpURLConnection httpURLConnection, Map<String, String> map) {
        httpURLConnection.setRequestProperty("Authorization", getAuthorizationHeader(twitterAuthConfig, twitterAuthToken, null, HttpMethod.valueOf(httpURLConnection.getRequestMethod()).name(), httpURLConnection.getURL().toString(), map));
    }

    public static String getAuthorizationHeader(TwitterAuthConfig twitterAuthConfig, TwitterAuthToken twitterAuthToken, String str, String str2, String str3, Map<String, String> map) {
        return new OAuth1aParameters(twitterAuthConfig, twitterAuthToken, str, str2, str3, map).getAuthorizationHeader();
    }

    public static OAuthResponse parseAuthResponse(String str) {
        TreeMap<String, String> queryParams = UrlUtils.getQueryParams(str, false);
        String str2 = queryParams.get(OAuthConstants.PARAM_TOKEN);
        String str3 = queryParams.get(OAuthConstants.PARAM_TOKEN_SECRET);
        String str4 = queryParams.get(PARAM_SCREEN_NAME);
        long j = queryParams.containsKey("user_id") ? Long.parseLong(queryParams.get("user_id")) : 0L;
        if (str2 == null || str3 == null) {
            return null;
        }
        return new OAuthResponse(new TwitterAuthToken(str2, str3), str4, j);
    }

    Callback<Response> getCallbackWrapper(final Callback<OAuthResponse> callback) {
        return new Callback<Response>() { // from class: com.twitter.sdk.android.core.internal.oauth.OAuth1aService.1
            @Override // com.twitter.sdk.android.core.Callback
            public void success(Result<Response> result) throws Throwable {
                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = null;
                try {
                    try {
                        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(result.data.getBody().in()));
                        while (true) {
                            try {
                                String line = bufferedReader2.readLine();
                                if (line == null) {
                                    break;
                                } else {
                                    sb.append(line);
                                }
                            } catch (Throwable th) {
                                th = th;
                                bufferedReader = bufferedReader2;
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                throw th;
                            }
                        }
                        bufferedReader2.close();
                        String string = sb.toString();
                        OAuthResponse authResponse = OAuth1aService.parseAuthResponse(string);
                        if (authResponse == null) {
                            callback.failure(new TwitterAuthException("Failed to parse auth response: " + string));
                        } else {
                            callback.success(new Result(authResponse, null));
                        }
                    } catch (IOException e) {
                        callback.failure(new TwitterAuthException(e.getMessage(), e));
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }

            @Override // com.twitter.sdk.android.core.Callback
            public void failure(TwitterException twitterException) {
                callback.failure(twitterException);
            }
        };
    }
}
