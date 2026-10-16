package com.tapsdk.antiaddictionui.wrapper;

import android.app.Activity;
import com.tds.common.bridge.BridgeCallback;
import com.tds.common.bridge.IBridgeService;
import com.tds.common.bridge.annotation.BridgeMethod;
import com.tds.common.bridge.annotation.BridgeParam;
import com.tds.common.bridge.annotation.BridgeService;

/* JADX INFO: loaded from: classes.dex */
@BridgeService("TapAntiAddictionService")
public interface TapAntiAddictionService extends IBridgeService {
    @BridgeMethod("checkPayLimit")
    void checkPayLimit(Activity activity, @BridgeParam("amount") long j, BridgeCallback bridgeCallback);

    @BridgeMethod("enterGame")
    void enterGame();

    @BridgeMethod("getCurrentToken")
    void getCurrentToken(BridgeCallback bridgeCallback);

    @BridgeMethod("getCurrentUserAgeLimit")
    @Deprecated
    void getCurrentUserAgeLimit(BridgeCallback bridgeCallback);

    @BridgeMethod("getCurrentUserRemainTime")
    void getCurrentUserRemainTime(BridgeCallback bridgeCallback);

    @BridgeMethod("init")
    void init(Activity activity, @BridgeParam("gameIdentifier") String str, @BridgeParam("showSwitchAccount") boolean z, @BridgeParam("useAgeRange") boolean z2);

    @BridgeMethod("leaveGame")
    void leaveGame();

    @BridgeMethod("logout")
    void logout();

    @BridgeMethod("setAntiAddictionCallback")
    void setAntiAddictionCallback(BridgeCallback bridgeCallback);

    @BridgeMethod("setRndEnvironment")
    void setRndEnvironment(@BridgeParam("newKey") String str);

    @BridgeMethod("setTestEnvironment")
    void setTestEnvironment(Activity activity, @BridgeParam("setTestEnvironment") boolean z);

    @BridgeMethod("setUnityVersion")
    @Deprecated
    void setUnityVersion(@BridgeParam("setUnityVersion") String str);

    @BridgeMethod("startupWithUserType")
    @Deprecated
    void startup(Activity activity, @BridgeParam("userIdentifier") String str, @BridgeParam("isTapUser") boolean z);

    @BridgeMethod("startup")
    @Deprecated
    void startup(Activity activity, @BridgeParam("useTapLogin") boolean z, @BridgeParam("userIdentifier") String str);

    @BridgeMethod("startupWithTapTap")
    void startupWithTapTap(Activity activity, @BridgeParam("startupWithTapTap") String str);

    @BridgeMethod("submitPayResult")
    void submitPayResult(@BridgeParam("submitAmount") long j, BridgeCallback bridgeCallback);
}
