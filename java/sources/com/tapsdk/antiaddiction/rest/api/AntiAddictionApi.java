package com.tapsdk.antiaddiction.rest.api;

import com.tapsdk.antiaddiction.entities.CheckPlayResult;
import com.tapsdk.antiaddiction.entities.request.PayRequestParams;
import com.tapsdk.antiaddiction.entities.request.SessionRequestParam;
import com.tapsdk.antiaddiction.entities.response.CheckPayResult;
import com.tapsdk.antiaddiction.entities.response.RealNameConfig;
import com.tapsdk.antiaddiction.entities.response.SubmitPayResult;
import com.tapsdk.antiaddiction.entities.response.UserAntiConfig;
import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.skynet.retrofit2.Call;
import com.tapsdk.antiaddiction.skynet.retrofit2.http.Body;
import com.tapsdk.antiaddiction.skynet.retrofit2.http.GET;
import com.tapsdk.antiaddiction.skynet.retrofit2.http.Headers;
import com.tapsdk.antiaddiction.skynet.retrofit2.http.POST;
import com.tapsdk.antiaddiction.skynet.retrofit2.http.Query;
import com.tapsdk.antiaddictionui.constant.Constants;

/* JADX INFO: loaded from: classes.dex */
public interface AntiAddictionApi {
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("/anti-addiction/v2/payable?test_mode=1")
    Observable<CheckPayResult> checkFakePay(@Query("client_id") String str, @Query(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER) String str2, @Query("amount") long j);

    @POST("anti-addiction/v2/heartbeat?test_mode=1")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<CheckPlayResult> checkFakePlay(@Query("client_id") String str, @Query(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER) String str2, @Body SessionRequestParam sessionRequestParam);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("/anti-addiction/v2/payable")
    Observable<CheckPayResult> checkPay(@Query("client_id") String str, @Query(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER) String str2, @Query("amount") long j);

    @POST("/anti-addiction/v2/heartbeat")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<CheckPlayResult> checkPlay(@Query("client_id") String str, @Query(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER) String str2, @Body SessionRequestParam sessionRequestParam);

    @GET("/anti-addiction/v2/get-config-by-token?platform=android&test_mode=1")
    Call<UserAntiConfig> fetchFakeUserConfig(@Query("client_id") String str, @Query(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER) String str2);

    @GET("/real-name/v2/get-global-config")
    Call<RealNameConfig> fetchRealNameTipConfig(@Query("client_id") String str, @Query(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER) String str2);

    @GET("/anti-addiction/v2/get-config-by-token?platform=android")
    Call<UserAntiConfig> fetchUserConfig(@Query("client_id") String str, @Query(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER) String str2);

    @POST("anti-addiction/v2/payment-submit?test_mode=1")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<SubmitPayResult> submitFakePayResult(@Body PayRequestParams payRequestParams, @Query("client_id") String str, @Query(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER) String str2);

    @POST("anti-addiction/v2/payment-submit")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<SubmitPayResult> submitPayResult(@Body PayRequestParams payRequestParams, @Query("client_id") String str, @Query(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER) String str2);
}
