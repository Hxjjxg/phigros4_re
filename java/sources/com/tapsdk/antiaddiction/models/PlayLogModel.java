package com.tapsdk.antiaddiction.models;

import android.content.Context;
import com.tapsdk.antiaddiction.AntiAddictionImpl;
import com.tapsdk.antiaddiction.entities.CheckPlayResult;
import com.tapsdk.antiaddiction.entities.ThreeTuple;
import com.tapsdk.antiaddiction.entities.TwoTuple;
import com.tapsdk.antiaddiction.entities.request.SessionRequestParam;
import com.tapsdk.antiaddiction.entities.response.UserAntiConfig;
import com.tapsdk.antiaddiction.rest.api.AntiAddictionApi;
import com.tapsdk.antiaddiction.settings.AntiAddictionSettings;
import com.tapsdk.antiaddiction.skynet.Skynet;
import com.tapsdk.antiaddiction.skynet.exceptions.AntiServerException;
import com.tapsdk.antiaddiction.skynet.retrofit2.Response;
import com.tapsdk.antiaddiction.utils.AntiAddictionLogger;
import com.tapsdk.antiaddiction.utils.TimeUtil;

/* JADX INFO: loaded from: classes.dex */
public class PlayLogModel {
    static PlayLogModel instance;
    Context context;
    String sessionId;
    UserModel userModel;

    public static PlayLogModel getInstance() {
        if (instance == null) {
            instance = new PlayLogModel();
        }
        return instance;
    }

    public void init(Context context, UserModel userModel) {
        this.context = context.getApplicationContext();
        this.userModel = userModel;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public TwoTuple<Boolean, CheckPlayResult> checkUserStateSync(String str, String str2) {
        UserAntiConfig userAntiConfig = AntiAddictionSettings.getInstance().getUserAntiConfig();
        if (userAntiConfig.policy.model.equals("local") && userAntiConfig.policy.active.equals(AntiAddictionSettings.TIME_RANGE_MODE)) {
            return localCheckPlayable();
        }
        return uploadPlayLogSync(str, str2);
    }

    public ThreeTuple<Boolean, CheckPlayResult, Boolean> localCheckPlayable() {
        int timeToNightStrict;
        UserAntiConfig.TipDesc tipDesc;
        AntiAddictionLogger.d("check sync Local play time");
        UserAntiConfig.Local local = AntiAddictionSettings.getInstance().getUserAntiConfig().localConfig;
        if (this.userModel.getCurrentUser().isAdult()) {
            timeToNightStrict = 9999;
        } else {
            long recentServerTime = ServerTimeModel.getRecentServerTime() * 1000;
            timeToNightStrict = TimeUtil.isHoliday(recentServerTime) ? TimeUtil.getTimeToNightStrict(local.timeRangeConfig.timeStart, local.timeRangeConfig.timeEnd, recentServerTime) : 0;
        }
        this.userModel.getCurrentUser().resetRemainTime(timeToNightStrict);
        CheckPlayResult checkPlayResult = new CheckPlayResult();
        checkPlayResult.remainTime = timeToNightStrict;
        if (timeToNightStrict > 0) {
            tipDesc = local.timeRangeConfig.uiTipText.allow;
        } else {
            tipDesc = local.timeRangeConfig.uiTipText.reject;
        }
        checkPlayResult.title = tipDesc.tipTitle;
        checkPlayResult.description = TimeUtil.getRestTime(tipDesc.tipDescription, timeToNightStrict);
        if (this.userModel.getCurrentUser().isAdult()) {
            AntiAddictionLogger.d("player's type is adult remainTime = " + checkPlayResult.remainTime);
            return ThreeTuple.create(false, checkPlayResult, true);
        }
        AntiAddictionLogger.d("player's type is others remainTime = " + checkPlayResult.remainTime);
        return ThreeTuple.create(true, checkPlayResult, true);
    }

    public ThreeTuple<Boolean, CheckPlayResult, Boolean> uploadPlayLogSync(String str, String str2) {
        Response<CheckPlayResult> responseExecute;
        try {
            AntiAddictionApi antiAddictionApi = (AntiAddictionApi) Skynet.getService(Skynet.RETROFIT_FOR_ANTI_ADDICTION, AntiAddictionApi.class);
            SessionRequestParam sessionRequestParam = new SessionRequestParam();
            sessionRequestParam.sessionId = this.sessionId;
            if (AntiAddictionImpl.runInFakeEnvironment.booleanValue()) {
                responseExecute = antiAddictionApi.checkFakePlay(str, str2, sessionRequestParam).execute();
            } else {
                responseExecute = antiAddictionApi.checkPlay(str, str2, sessionRequestParam).execute();
            }
            this.userModel.getCurrentUser().resetRemainTime(responseExecute.body().remainTime);
            AntiAddictionLogger.d("player left time:" + responseExecute.body().remainTime);
            if (this.userModel.getCurrentUser().isAdult()) {
                AntiAddictionLogger.d("player's type is adult");
                return ThreeTuple.create(false, responseExecute.body(), false);
            }
            AntiAddictionLogger.d("player's type is others");
            return ThreeTuple.create(true, responseExecute.body(), false);
        } catch (Exception e) {
            AntiAddictionLogger.printStackTrace(e);
            if (e instanceof AntiServerException) {
                AntiServerException antiServerException = (AntiServerException) e;
                if (antiServerException.isTokenExpired()) {
                    throw new RuntimeException(antiServerException);
                }
                if (antiServerException.statusCode < 500) {
                    throw new RuntimeException(antiServerException);
                }
            }
            if (AntiAddictionSettings.getInstance().getUserAntiConfig().policy.active.equals(AntiAddictionSettings.TIME_RANGE_MODE)) {
                return localCheckPlayable();
            }
            throw new RuntimeException(e);
        }
    }
}
