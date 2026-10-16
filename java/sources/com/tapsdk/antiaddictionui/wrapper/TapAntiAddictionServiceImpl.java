package com.tapsdk.antiaddictionui.wrapper;

import android.app.Activity;
import android.text.TextUtils;
import com.tapsdk.antiaddiction.Config;
import com.tapsdk.antiaddiction.constants.Constants;
import com.tapsdk.antiaddiction.entities.response.CheckPayResult;
import com.tapsdk.antiaddiction.entities.response.SubmitPayResult;
import com.tapsdk.antiaddictionui.AntiAddictionUICallback;
import com.tapsdk.antiaddictionui.AntiAddictionUIKit;
import com.tapsdk.antiaddictionui.Callback;
import com.tds.common.bridge.BridgeCallback;
import com.tds.common.bridge.utils.BridgeJsonHelper;
import com.tds.common.log.Logger;
import com.tds.common.log.constants.BusinessType;
import com.tds.common.tracker.model.NetworkStateModel;
import com.voxelbusters.nativeplugins.defines.Keys;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TapAntiAddictionServiceImpl implements TapAntiAddictionService {
    private static final String TAG = "TapAntiAddictionServiceImpl";
    private final Logger logger = Logger.get(BusinessType.COMMON_LOG);

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void init(Activity activity, String str, boolean z, boolean z2) {
        this.logger.i(TAG, "initWithoutCallback(gameIdentifier:" + str + ", showSwitchAccount:" + z + " , useAgeRange = " + z2);
        AntiAddictionUIKit.init(activity, new Config.Builder().withClientId(str).showSwitchAccount(z).useAgeRange(z2).build());
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void setAntiAddictionCallback(final BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "setAntiAddictionCallback");
        AntiAddictionUIKit.setAntiAddictionCallback(new AntiAddictionUICallback() { // from class: com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionServiceImpl.1
            @Override // com.tapsdk.antiaddictionui.AntiAddictionUICallback
            public void onCallback(int i, Map<String, Object> map) {
                TapAntiAddictionServiceImpl.this.sendCallbackDataToEngine(i, map, bridgeCallback);
            }
        });
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void startup(Activity activity, boolean z, String str) {
        this.logger.i(TAG, "startup(useTapLogin:" + z + ", userIdentifier:" + str);
        AntiAddictionUIKit.startup(activity, z, str);
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void startup(Activity activity, String str, boolean z) {
        this.logger.i(TAG, "startup(userIdentifier:" + str + ", isTapUser:" + z);
        AntiAddictionUIKit.startup(activity, str, z);
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void startupWithTapTap(Activity activity, String str) {
        this.logger.i(TAG, "startupWithTapTap(userIdentifier:" + str);
        AntiAddictionUIKit.startupWithTapTap(activity, str);
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void checkPayLimit(Activity activity, long j, final BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "checkPayLimit(amount:" + j);
        AntiAddictionUIKit.checkPayLimit(activity, j, new Callback<CheckPayResult>() { // from class: com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionServiceImpl.2
            @Override // com.tapsdk.antiaddictionui.Callback
            public void onSuccess(CheckPayResult checkPayResult) {
                HashMap map = new HashMap(4);
                map.put(Keys.Billing.Validation.SUCCESS, 0);
                map.put("status", Integer.valueOf(checkPayResult.status ? 1 : 0));
                map.put("title", checkPayResult.title);
                map.put(Constants.MsgExtraParams.DESCRIPTION, checkPayResult.description);
                bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
            }

            @Override // com.tapsdk.antiaddictionui.Callback
            public void onError(Throwable th) {
                String string;
                if (!TextUtils.isEmpty(th.getMessage())) {
                    string = th.getMessage();
                } else {
                    string = th.toString();
                }
                HashMap map = new HashMap(2);
                map.put(Keys.Billing.Validation.SUCCESS, -1);
                map.put(Constants.MsgExtraParams.DESCRIPTION, string);
                bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
            }
        });
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void submitPayResult(long j, final BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "submitPayResult(amount:" + j);
        AntiAddictionUIKit.submitPayResult(j, new com.tapsdk.antiaddiction.Callback<SubmitPayResult>() { // from class: com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionServiceImpl.3
            @Override // com.tapsdk.antiaddiction.Callback
            public void onSuccess(SubmitPayResult submitPayResult) {
                HashMap map = new HashMap(1);
                map.put(Keys.Billing.Validation.SUCCESS, 0);
                bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
            }

            @Override // com.tapsdk.antiaddiction.Callback
            public void onError(Throwable th) {
                String string;
                if (!TextUtils.isEmpty(th.getMessage())) {
                    string = th.getMessage();
                } else {
                    string = th.toString();
                }
                HashMap map = new HashMap(2);
                map.put(Keys.Billing.Validation.SUCCESS, -1);
                map.put(Constants.MsgExtraParams.DESCRIPTION, string);
                bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
            }
        });
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void logout() {
        this.logger.i(TAG, "logout");
        AntiAddictionUIKit.logout();
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    @Deprecated
    public void getCurrentUserAgeLimit(BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "getCurrentUserAgeLimit");
        HashMap map = new HashMap(1);
        map.put("ageLimit", -1);
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void getCurrentUserRemainTime(BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "getCurrentUserRemainTime");
        HashMap map = new HashMap(1);
        map.put("remainTime", Integer.valueOf(AntiAddictionUIKit.getRemainingTime()));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void enterGame() {
        this.logger.i(TAG, "enterGame");
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void leaveGame() {
        this.logger.i(TAG, "leaveGame");
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void setUnityVersion(String str) {
        this.logger.i(TAG, "setUnityVersion(version:" + str);
        AntiAddictionUIKit.setUnityVersion(str);
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void getCurrentToken(BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "getCurrentToken");
        HashMap map = new HashMap(1);
        map.put("token", AntiAddictionUIKit.currentToken());
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void setRndEnvironment(String str) {
        this.logger.i(TAG, "setRndEnvironment(newKey:" + str);
    }

    @Override // com.tapsdk.antiaddictionui.wrapper.TapAntiAddictionService
    public void setTestEnvironment(Activity activity, boolean z) {
        this.logger.i(TAG, "setTestEnvironment(isTest:" + z);
        AntiAddictionUIKit.setTestEnvironment(activity, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendCallbackDataToEngine(int i, Map<String, Object> map, BridgeCallback bridgeCallback) {
        HashMap map2 = new HashMap(2);
        map2.put(NetworkStateModel.PARAM_CODE, Integer.valueOf(i));
        map2.put("extras", map);
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map2));
    }
}
