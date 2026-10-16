package com.tapsdk.antiaddictionui;

import android.app.Activity;
import com.tapsdk.antiaddiction.AntiAddictionKit;
import com.tapsdk.antiaddiction.Config;
import com.tapsdk.antiaddiction.entities.response.CheckPayResult;
import com.tapsdk.antiaddiction.entities.response.SubmitPayResult;

/* JADX INFO: loaded from: classes.dex */
public class AntiAddictionUIKit {
    private static IAntiAddictionUI antiAddictionUIImpl = new AntiAddictionUIImpl();

    @Deprecated
    public static void enterGame() {
    }

    @Deprecated
    public static void leaveGame() {
    }

    public static boolean isInitialized() {
        return antiAddictionUIImpl.isInitialized();
    }

    @Deprecated
    public static void init(Activity activity, Config config, AntiAddictionUICallback antiAddictionUICallback) {
        antiAddictionUIImpl.init(activity, config, antiAddictionUICallback);
    }

    public static void init(Activity activity, Config config) {
        antiAddictionUIImpl.init(activity, config);
    }

    public static void setAntiAddictionCallback(AntiAddictionUICallback antiAddictionUICallback) {
        antiAddictionUIImpl.setAntiAddictionCallback(antiAddictionUICallback);
    }

    @Deprecated
    public static void startup(Activity activity, boolean z, String str) {
        antiAddictionUIImpl.startup(activity, z, str);
    }

    @Deprecated
    public static void startup(Activity activity, String str) {
        antiAddictionUIImpl.startup(activity, str);
    }

    @Deprecated
    public static void startup(Activity activity, String str, boolean z) {
        antiAddictionUIImpl.startup(activity, str, z);
    }

    public static void startupWithTapTap(Activity activity, String str) {
        antiAddictionUIImpl.startupWithTapTap(activity, str);
    }

    public static void checkPayLimit(Activity activity, long j, Callback<CheckPayResult> callback) {
        antiAddictionUIImpl.checkPayLimit(activity, j, callback);
    }

    public static void submitPayResult(long j, com.tapsdk.antiaddiction.Callback<SubmitPayResult> callback) {
        antiAddictionUIImpl.submitPayResult(j, callback);
    }

    @Deprecated
    public static void logout() {
        AntiAddictionKit.logout();
    }

    public static void exit() {
        AntiAddictionKit.logout();
    }

    private static boolean isReady() {
        return antiAddictionUIImpl.isReady();
    }

    @Deprecated
    public static int currentUserAgeLimit() {
        return AntiAddictionKit.currentUserAgeLimit();
    }

    public static int getAgeRange() {
        return AntiAddictionKit.currentUserAgeLimit();
    }

    @Deprecated
    public static int currentUserRemainingTime() {
        return AntiAddictionKit.currentUserRemainingTime();
    }

    public static int getRemainingTime() {
        return AntiAddictionKit.currentUserRemainingTime();
    }

    public static int getRemainingTimeInMinutes() {
        int iCurrentUserRemainingTime = AntiAddictionKit.currentUserRemainingTime();
        if (iCurrentUserRemainingTime == 0) {
            return 0;
        }
        if (iCurrentUserRemainingTime <= 0 || iCurrentUserRemainingTime > 60) {
            return (int) Math.ceil((((double) AntiAddictionKit.currentUserRemainingTime()) * 1.0d) / 60.0d);
        }
        return 1;
    }

    public static String currentToken() {
        return AntiAddictionKit.currentToken();
    }

    public static void setUnityVersion(String str) {
        AntiAddictionKit.setUnityVersion(str);
    }

    public static void setTestEnvironment(Activity activity, boolean z) {
        AntiAddictionKit.setTestEnvironment(z);
        antiAddictionUIImpl.setTestFloatingWindowEnabled(activity, z);
    }
}
