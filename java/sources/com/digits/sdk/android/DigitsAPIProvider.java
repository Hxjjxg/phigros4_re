package com.digits.sdk.android;

import com.twitter.sdk.android.core.AuthenticatedClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.internal.TwitterApi;
import com.twitter.sdk.android.core.models.User;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import javax.net.ssl.SSLSocketFactory;
import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/* JADX INFO: loaded from: classes.dex */
class DigitsAPIProvider {
    private final RestAdapter restAdapter;
    private final ConcurrentHashMap<Class, Object> services = new ConcurrentHashMap<>();

    protected interface DeviceService {
        @POST("/1.1/device/register.json")
        @FormUrlEncoded
        void register(@Field("raw_phone_number") String str, @Field("text_key") String str2, @Field("send_numeric_pin") Boolean bool, Callback<Response> callback);
    }

    protected interface SdkService {
        @POST("/1.1/sdk/account.json")
        @FormUrlEncoded
        void account(@Field(DigitsClient.EXTRA_PHONE) String str, @Field("numeric_pin") String str2, Callback<User> callback);

        @POST("/auth/1/xauth_phone_number.json")
        @FormUrlEncoded
        void auth(@Field("x_auth_phone_number") String str, Callback<AuthResponse> callback);

        @POST("/auth/1/xauth_challenge.json")
        @FormUrlEncoded
        void login(@Field("login_verification_request_id") String str, @Field("login_verification_user_id") long j, @Field("login_verification_challenge_response") String str2, Callback<DigitsUser> callback);

        @POST("/auth/1/xauth_pin.json")
        @FormUrlEncoded
        void verifyPin(@Field("login_verification_request_id") String str, @Field("login_verification_user_id") long j, @Field("pin") String str2, Callback<DigitsUser> callback);
    }

    DigitsAPIProvider(DigitsSession digitsSession, TwitterAuthConfig twitterAuthConfig, SSLSocketFactory sSLSocketFactory, ExecutorService executorService) {
        this.restAdapter = new RestAdapter.Builder().setEndpoint(new TwitterApi().getBaseHostUrl()).setExecutors(executorService, new MainThreadExecutor()).setClient(new AuthenticatedClient(twitterAuthConfig, digitsSession, sSLSocketFactory)).build();
    }

    public SdkService getSdkService() {
        return (SdkService) getService(SdkService.class);
    }

    public DeviceService getDeviceService() {
        return (DeviceService) getService(DeviceService.class);
    }

    private <T> T getService(Class<T> cls) {
        if (!this.services.containsKey(cls)) {
            this.services.put(cls, this.restAdapter.create(cls));
        }
        return (T) this.services.get(cls);
    }
}
