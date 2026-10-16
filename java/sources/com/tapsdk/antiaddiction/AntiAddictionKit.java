package com.tapsdk.antiaddiction;

import android.content.Context;
import com.tapsdk.antiaddiction.entities.response.CheckPayResult;
import com.tapsdk.antiaddiction.entities.response.IdentifyState;
import com.tapsdk.antiaddiction.entities.response.SubmitPayResult;

/* JADX INFO: loaded from: classes.dex */
public class AntiAddictionKit {
    private static final IAntiAddiction antiAddiction = new AntiAddictionImpl();

    public static void init(Context context, Config config, AntiAddictionCallback antiAddictionCallback) {
        antiAddiction.init(context, config, antiAddictionCallback);
    }

    public static boolean initialized() {
        return antiAddiction.initialized();
    }

    public static void getRealNameConfig(Context context, String str, Callback<Void> callback) {
        antiAddiction.getRealNameConfig(context, str, callback);
    }

    public static void setAntiAddictionCallback(AntiAddictionCallback antiAddictionCallback) {
        antiAddiction.setAntiAddictionCallback(antiAddictionCallback);
    }

    public static void login(String str, String str2, AntiAddictionLoginCallback antiAddictionLoginCallback) {
        antiAddiction.login(str, str2, antiAddictionLoginCallback);
    }

    public static void checkPlayableWhenEnterByUI(String str, String str2) {
        antiAddiction.checkPlayableWhenEnterByUI(str, str2);
    }

    public static void logout() {
        antiAddiction.logout();
    }

    public static void enterGame() {
        antiAddiction.enterGame();
    }

    public static void leaveGame() {
        antiAddiction.leaveGame();
    }

    public static String getCurrentSession() {
        return antiAddiction.getSessionId();
    }

    public static void checkPayLimit(long j, Callback<CheckPayResult> callback) {
        antiAddiction.checkPayLimit(j, callback);
    }

    public static void submitPayResult(long j, Callback<SubmitPayResult> callback) {
        antiAddiction.submitPayResult(j, callback);
    }

    public static String currentToken() {
        return antiAddiction.currentToken();
    }

    public static boolean isNotAdult() {
        return antiAddiction.isNotAdult();
    }

    public static int currentUserAgeLimit() {
        return antiAddiction.currentAgeLimit();
    }

    public static int currentUserRemainingTime() {
        return antiAddiction.currentUserRemainTime();
    }

    public static void setUnityVersion(String str) {
        antiAddiction.setUnityVersion(str);
    }

    public static void switchAccount() {
        antiAddiction.switchAccount();
    }

    public static boolean showSwitchAccount() {
        return antiAddiction.showSwitchAccount();
    }

    public static void checkStandaloneCacheAndFetchUserIdentifyState(String str, String str2, Callback<IdentifyState> callback) {
        antiAddiction.checkStandaloneCacheAndFetchUserIdentifyState(str, str2, callback);
    }

    public static void setTestEnvironment(boolean z) {
        AntiAddictionImpl.setTestEnvironment(z);
    }
}
