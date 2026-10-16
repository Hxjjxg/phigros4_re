package com.tapsdk.antiaddiction;

import android.content.Context;
import com.tapsdk.antiaddiction.entities.response.CheckPayResult;
import com.tapsdk.antiaddiction.entities.response.IdentifyState;
import com.tapsdk.antiaddiction.entities.response.SubmitPayResult;

/* JADX INFO: loaded from: classes.dex */
public interface IAntiAddiction {
    void checkPayLimit(long j, Callback<CheckPayResult> callback);

    void checkPlayableWhenEnterByUI(String str, String str2);

    void checkStandaloneCacheAndFetchUserIdentifyState(String str, String str2, Callback<IdentifyState> callback);

    int currentAgeLimit();

    String currentToken();

    int currentUserRemainTime();

    void enterGame();

    void getRealNameConfig(Context context, String str, Callback<Void> callback);

    String getSessionId();

    void init(Context context, Config config, AntiAddictionCallback antiAddictionCallback);

    @Deprecated
    void init(Context context, String str, AntiAddictionCallback antiAddictionCallback);

    boolean initialized();

    boolean isNotAdult();

    void leaveGame();

    void login(String str, String str2, AntiAddictionLoginCallback antiAddictionLoginCallback);

    void logout();

    void reStartTiming();

    void setAntiAddictionCallback(AntiAddictionCallback antiAddictionCallback);

    void setUnityVersion(String str);

    boolean showSwitchAccount();

    void submitPayResult(long j, Callback<SubmitPayResult> callback);

    void switchAccount();
}
