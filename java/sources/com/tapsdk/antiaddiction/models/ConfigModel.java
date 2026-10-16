package com.tapsdk.antiaddiction.models;

import android.content.Context;
import com.tapsdk.antiaddiction.AntiAddictionImpl;
import com.tapsdk.antiaddiction.entities.response.RealNameConfig;
import com.tapsdk.antiaddiction.entities.response.UserAntiConfig;
import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.rest.api.AntiAddictionApi;
import com.tapsdk.antiaddiction.settings.AntiAddictionSettings;
import com.tapsdk.antiaddiction.skynet.Skynet;
import com.tapsdk.antiaddiction.skynet.exceptions.AntiServerException;
import com.tapsdk.antiaddiction.skynet.retrofit2.Call;
import com.tapsdk.antiaddiction.utils.AntiAddictionLogger;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class ConfigModel {
    public RealNameConfig fetchRealNameTipConfig(String str, String str2) {
        try {
            return ((AntiAddictionApi) Skynet.getService(Skynet.RETROFIT_FOR_ANTI_ADDICTION, AntiAddictionApi.class)).fetchRealNameTipConfig(str, str2).execute().body();
        } catch (Exception e) {
            if ((e instanceof AntiServerException) && ((AntiServerException) e).statusCode < 500) {
                throw new RuntimeException(e);
            }
            AntiAddictionLogger.printStackTrace(e);
            return null;
        }
    }

    public Observable<Boolean> fetchAndSetupRealNameTipConfigAsync(final Context context, final String str, final String str2) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() { // from class: com.tapsdk.antiaddiction.models.ConfigModel.1
            @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
            public void call(Subscriber<? super Boolean> subscriber) {
                RealNameConfig realNameConfigFetchRealNameTipConfig = ConfigModel.this.fetchRealNameTipConfig(str, str2);
                if (realNameConfigFetchRealNameTipConfig != null) {
                    AntiAddictionSettings.getInstance().saveRealNameConfig(context, realNameConfigFetchRealNameTipConfig);
                } else {
                    realNameConfigFetchRealNameTipConfig = AntiAddictionSettings.getInstance().getRealNameConfigFromCache(context);
                }
                AntiAddictionSettings.getInstance().setCurrentRealNameConfig(realNameConfigFetchRealNameTipConfig);
                subscriber.onNext(Boolean.valueOf(realNameConfigFetchRealNameTipConfig != null));
            }
        });
    }

    public UserAntiConfig fetchUserAntiConfig(String str, String str2) throws IOException {
        Call<UserAntiConfig> callFetchUserConfig;
        AntiAddictionApi antiAddictionApi = (AntiAddictionApi) Skynet.getService(Skynet.RETROFIT_FOR_ANTI_ADDICTION, AntiAddictionApi.class);
        if (AntiAddictionImpl.runInFakeEnvironment.booleanValue()) {
            callFetchUserConfig = antiAddictionApi.fetchFakeUserConfig(str, str2);
        } else {
            callFetchUserConfig = antiAddictionApi.fetchUserConfig(str, str2);
        }
        return callFetchUserConfig.execute().body();
    }

    public Observable<UserAntiConfig> fetchAndSetupUserAntiConfigAsync(final Context context, final String str, final String str2) {
        return Observable.create(new Observable.OnSubscribe<UserAntiConfig>() { // from class: com.tapsdk.antiaddiction.models.ConfigModel.2
            @Override // com.tapsdk.antiaddiction.reactor.functions.Action1
            public void call(Subscriber<? super UserAntiConfig> subscriber) {
                try {
                    UserAntiConfig userAntiConfigFetchUserAntiConfig = ConfigModel.this.fetchUserAntiConfig(str, str2);
                    AntiAddictionSettings.getInstance().saveUserAntiConfigToCache(context, str2, userAntiConfigFetchUserAntiConfig);
                    AntiAddictionSettings.getInstance().setUserAntiConfig(userAntiConfigFetchUserAntiConfig);
                    subscriber.onNext(userAntiConfigFetchUserAntiConfig);
                } catch (Exception e) {
                    ServerTimeModel.saveRecentLocalTime();
                    if (e instanceof AntiServerException) {
                        AntiServerException antiServerException = (AntiServerException) e;
                        if (antiServerException.isTokenExpired() || antiServerException.statusCode < 500) {
                            subscriber.onError(e);
                        }
                    }
                    UserAntiConfig userAntiConfigFromCache = AntiAddictionSettings.getInstance().getUserAntiConfigFromCache(context, str2);
                    if (userAntiConfigFromCache == null) {
                        subscriber.onError(e);
                    } else {
                        AntiAddictionSettings.getInstance().setUserAntiConfig(userAntiConfigFromCache);
                        subscriber.onNext(userAntiConfigFromCache);
                    }
                }
            }
        });
    }
}
