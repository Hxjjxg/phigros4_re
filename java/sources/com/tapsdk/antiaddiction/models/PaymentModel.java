package com.tapsdk.antiaddiction.models;

import com.tapsdk.antiaddiction.AntiAddictionImpl;
import com.tapsdk.antiaddiction.entities.request.PayRequestParams;
import com.tapsdk.antiaddiction.entities.response.CheckPayResult;
import com.tapsdk.antiaddiction.entities.response.SubmitPayResult;
import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.rest.api.AntiAddictionApi;
import com.tapsdk.antiaddiction.skynet.Skynet;

/* JADX INFO: loaded from: classes.dex */
public class PaymentModel {
    public Observable<CheckPayResult> checkPay(long j, String str, String str2, String str3) {
        if (j < 0) {
            return Observable.error(new Throwable("The amount cannot be negative"));
        }
        AntiAddictionApi antiAddictionApi = (AntiAddictionApi) Skynet.getService(Skynet.RETROFIT_FOR_ANTI_ADDICTION, AntiAddictionApi.class);
        if (AntiAddictionImpl.runInFakeEnvironment.booleanValue()) {
            return antiAddictionApi.checkFakePay(str2, str3, j);
        }
        return antiAddictionApi.checkPay(str2, str3, j);
    }

    public Observable<SubmitPayResult> paySuccess(long j, String str, String str2) {
        if (j < 0) {
            return Observable.error(new Throwable("The amount cannot be negative"));
        }
        AntiAddictionApi antiAddictionApi = (AntiAddictionApi) Skynet.getService(Skynet.RETROFIT_FOR_ANTI_ADDICTION, AntiAddictionApi.class);
        PayRequestParams payRequestParams = new PayRequestParams(j);
        if (AntiAddictionImpl.runInFakeEnvironment.booleanValue()) {
            return antiAddictionApi.submitFakePayResult(payRequestParams, str, str2);
        }
        return antiAddictionApi.submitPayResult(payRequestParams, str, str2);
    }
}
