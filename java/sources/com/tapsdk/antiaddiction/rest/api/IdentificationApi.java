package com.tapsdk.antiaddiction.rest.api;

import com.tapsdk.antiaddiction.entities.request.IdentifyDataRequestParams;
import com.tapsdk.antiaddiction.entities.response.IdentifyState;
import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.skynet.retrofit2.http.Body;
import com.tapsdk.antiaddiction.skynet.retrofit2.http.GET;
import com.tapsdk.antiaddiction.skynet.retrofit2.http.Header;
import com.tapsdk.antiaddiction.skynet.retrofit2.http.POST;
import com.tapsdk.antiaddiction.skynet.retrofit2.http.Query;
import com.tapsdk.antiaddictionui.constant.Constants;

/* JADX INFO: loaded from: classes.dex */
public interface IdentificationApi {
    @GET("/real-name/v2/anti-addiction-token-taptap")
    Observable<IdentifyState> getTokenByTapToken(@Query("client_id") String str, @Query(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER) String str2, @Header("User-Agent") String str3, @Header("Authorization") String str4);

    @POST("/real-name/v2/anti-addiction-token-manual")
    Observable<IdentifyState> identifyUserManual(@Query("client_id") String str, @Query(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER) String str2, @Body IdentifyDataRequestParams identifyDataRequestParams);

    @GET("/real-name/v2/anti-addiction-token")
    Observable<IdentifyState> inquireIdentifyState(@Query("client_id") String str, @Query(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER) String str2);
}
