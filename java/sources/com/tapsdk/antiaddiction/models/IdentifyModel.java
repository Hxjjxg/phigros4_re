package com.tapsdk.antiaddiction.models;

import android.net.Uri;
import android.text.TextUtils;
import com.tapsdk.antiaddiction.Callback;
import com.tapsdk.antiaddiction.entities.request.IdentifyDataRequestParams;
import com.tapsdk.antiaddiction.entities.response.IdentifyState;
import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.reactor.rxandroid.schedulers.AndroidSchedulers;
import com.tapsdk.antiaddiction.reactor.schedulers.Schedulers;
import com.tapsdk.antiaddiction.rest.api.IdentificationApi;
import com.tapsdk.antiaddiction.skynet.Skynet;
import com.tapsdk.antiaddiction.skynet.exceptions.AntiServerException;
import com.tapsdk.antiaddiction.utils.TapTapSignUtil;
import com.tapsdk.antiaddictionui.constant.Constants;
import com.taptap.sdk.AccessToken;
import com.tds.common.net.util.HostReplaceUtil;
import io.fabric.sdk.android.services.network.HttpRequest;

/* JADX INFO: loaded from: classes.dex */
public class IdentifyModel {
    private static int retryTimesForTapAuth = 1;

    static /* synthetic */ int access$010() {
        int i = retryTimesForTapAuth;
        retryTimesForTapAuth = i - 1;
        return i;
    }

    public void inquireIdentifyState(String str, String str2, final Callback<IdentifyState> callback) {
        ((IdentificationApi) Skynet.getService(Skynet.RETROFIT_FOR_ANTI_ADDICTION, IdentificationApi.class)).inquireIdentifyState(str, str2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<IdentifyState>() { // from class: com.tapsdk.antiaddiction.models.IdentifyModel.1
            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onCompleted() {
            }

            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onError(Throwable th) {
                callback.onError(th);
            }

            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onNext(IdentifyState identifyState) {
                callback.onSuccess(identifyState);
            }
        });
    }

    public void getTokenByTapToken(final String str, final String str2, final boolean z, AccessToken accessToken, final Callback<IdentifyState> callback) {
        if (accessToken == null) {
            accessToken = AccessToken.getCurrentAccessToken();
        }
        final AccessToken accessToken2 = accessToken;
        if (!TapTapModel.hasComplianceAuthInLocal(accessToken2, z)) {
            callback.onError(null);
            return;
        }
        IdentificationApi identificationApi = (IdentificationApi) Skynet.getService(Skynet.RETROFIT_FOR_TAPTAP_SERVICE, IdentificationApi.class);
        String ua = TapTapSignUtil.getUA();
        Uri.Builder builderBuildUpon = Uri.parse(HostReplaceUtil.getInstance().getReplacedHost("https://tds-tapsdk.cn.tapapis.com") + "/real-name/v2/anti-addiction-token-taptap").buildUpon();
        builderBuildUpon.appendQueryParameter("client_id", str);
        builderBuildUpon.appendQueryParameter(Constants.ExtraBundleKey.KEY_USER_IDENTIFIER, str2);
        identificationApi.getTokenByTapToken(str, str2, ua, TapTapSignUtil.getAuthorization(builderBuildUpon.build().toString(), HttpRequest.METHOD_GET, accessToken2.kid, accessToken2.mac_key)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<IdentifyState>() { // from class: com.tapsdk.antiaddiction.models.IdentifyModel.2
            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onCompleted() {
            }

            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onError(Throwable th) {
                if (th instanceof AntiServerException) {
                    AntiServerException antiServerException = (AntiServerException) th;
                    if (antiServerException.statusCode < 500) {
                        String str3 = antiServerException.errorType;
                        if (!TextUtils.isEmpty(str3) && str3.equals(com.tapsdk.antiaddiction.constants.Constants.ServerErrorType.INVALID_TIME) && IdentifyModel.retryTimesForTapAuth > 0) {
                            IdentifyModel.access$010();
                            IdentifyModel.this.getTokenByTapToken(str, str2, z, accessToken2, callback);
                            return;
                        }
                    }
                }
                int unused = IdentifyModel.retryTimesForTapAuth = 1;
                callback.onError(th);
            }

            @Override // com.tapsdk.antiaddiction.reactor.Observer
            public void onNext(IdentifyState identifyState) {
                int unused = IdentifyModel.retryTimesForTapAuth = 1;
                callback.onSuccess(identifyState);
            }
        });
    }

    public Observable<IdentifyState> identifyUserManually(String str, String str2, String str3, String str4) {
        IdentificationApi identificationApi = (IdentificationApi) Skynet.getService(Skynet.RETROFIT_FOR_ANTI_ADDICTION, IdentificationApi.class);
        IdentifyDataRequestParams identifyDataRequestParams = new IdentifyDataRequestParams();
        identifyDataRequestParams.idCard = str4;
        identifyDataRequestParams.name = str3;
        return identificationApi.identifyUserManual(str, str2, identifyDataRequestParams);
    }
}
