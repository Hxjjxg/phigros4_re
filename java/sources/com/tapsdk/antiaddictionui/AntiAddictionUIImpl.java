package com.tapsdk.antiaddictionui;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.tapsdk.antiaddiction.AntiAddictionCallback;
import com.tapsdk.antiaddiction.AntiAddictionKit;
import com.tapsdk.antiaddiction.AntiAddictionLoginCallback;
import com.tapsdk.antiaddiction.Config;
import com.tapsdk.antiaddiction.constants.Constants;
import com.tapsdk.antiaddiction.entities.response.CheckPayResult;
import com.tapsdk.antiaddiction.entities.response.IdentifyState;
import com.tapsdk.antiaddiction.entities.response.SubmitPayResult;
import com.tapsdk.antiaddiction.models.TapTapModel;
import com.tapsdk.antiaddiction.settings.AntiAddictionSettings;
import com.tapsdk.antiaddiction.skynet.okhttp3.Interceptor;
import com.tapsdk.antiaddiction.utils.AntiAddictionLogger;
import com.tapsdk.antiaddiction.utils.FileUtils;
import com.tapsdk.antiaddictionui.utils.ActivityUtils;
import com.tapsdk.antiaddictionui.utils.ToastUtils;
import com.tapsdk.antiaddictionui.utils.UIUtil;
import com.tapsdk.antiaddictionui.widget.AntiToastManager;
import com.tapsdk.antiaddictionui.widget.DialogGameHealthReminder;
import com.tapsdk.antiaddictionui.widget.DialogGameRealName;
import com.tapsdk.antiaddictionui.widget.DialogTapAuthorization;
import com.tapsdk.antiaddictionui.widget.FloatingWindow;
import com.tapsdk.antiaddictionui.widget.TapAuthorizationService;
import com.taptap.sdk.TapLoginHelper;
import com.tds.common.tracker.SdkDurationStatistics;
import com.tds.common.tracker.constants.CommonParam;
import com.tds.common.widgets.TdsActivityManager;
import com.tds.common.widgets.toast.TapToastManager;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AntiAddictionUIImpl implements IAntiAddictionUI {
    private Config config;
    WeakReference<DialogGameHealthReminder> currentChildRemainTipDialog;
    private AntiAddictionUICallback mAntiAddictionUICallback;
    private AntiAddictionUICallback mUserAntiAddictionUICallback;
    private boolean initialized = false;
    private String clientId = "";
    private String currentUserIdentifier = "";
    private boolean isReady = false;
    private volatile boolean isCheckingUser = false;
    WeakReference<Activity> currentActivityWeakReference = null;

    @Override // com.tapsdk.antiaddictionui.IAntiAddictionUI
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override // com.tapsdk.antiaddictionui.IAntiAddictionUI
    public void init(Activity activity, Config config, AntiAddictionUICallback antiAddictionUICallback) {
        init(activity, config);
        setAntiAddictionCallback(antiAddictionUICallback);
    }

    @Override // com.tapsdk.antiaddictionui.IAntiAddictionUI
    public void init(Activity activity, Config config) {
        if (this.initialized) {
            return;
        }
        if (config == null) {
            throw new RuntimeException("AntiAddiction init fail(config can't be null)");
        }
        this.clientId = config.clientId;
        this.config = config;
        if (config.useAgeRange) {
            TapLoginHelper.appendPermission("compliance");
        } else {
            TapLoginHelper.appendPermission("compliance_basic");
        }
        TdsActivityManager.getInstance().setRootActivity(activity);
        TapAuthorizationService.getInstance().setUseAgeRange(config.useAgeRange);
        WeakReference<Activity> weakReference = this.currentActivityWeakReference;
        if (weakReference == null || weakReference.get() != activity) {
            this.currentActivityWeakReference = new WeakReference<>(activity);
        }
        if (!AntiAddictionKit.initialized()) {
            AntiAddictionUICallback antiAddictionUICallback = new AntiAddictionUICallback() { // from class: com.tapsdk.antiaddictionui.AntiAddictionUIImpl.1
                @Override // com.tapsdk.antiaddictionui.AntiAddictionUICallback
                public void onCallback(int i, Map<String, Object> map) {
                    if (i == 500 || i == 9002 || i == 1000 || i == 1100 || i == 1001 || i == 1200) {
                        AntiAddictionUIImpl.this.isCheckingUser = false;
                    }
                    if (AntiAddictionUIImpl.this.mUserAntiAddictionUICallback != null) {
                        AntiAddictionUIImpl.this.mUserAntiAddictionUICallback.onCallback(i, map);
                    }
                }
            };
            this.mAntiAddictionUICallback = antiAddictionUICallback;
            AntiAddictionKit.init(activity, config, makeAntiAddictionCallback(antiAddictionUICallback));
        }
        AntiAddictionLogger.d("init success");
        this.initialized = true;
    }

    @Override // com.tapsdk.antiaddictionui.IAntiAddictionUI
    public void setAntiAddictionCallback(AntiAddictionUICallback antiAddictionUICallback) {
        this.mUserAntiAddictionUICallback = antiAddictionUICallback;
    }

    private AntiAddictionCallback makeAntiAddictionCallback(final AntiAddictionUICallback antiAddictionUICallback) {
        if (antiAddictionUICallback == null) {
            return null;
        }
        return new AntiAddictionCallback() { // from class: com.tapsdk.antiaddictionui.AntiAddictionUIImpl.2
            @Override // com.tapsdk.antiaddiction.AntiAddictionCallback
            public void onCallback(int i, Map<String, Object> map) {
                boolean z;
                int i2;
                String str;
                String str2;
                String str3;
                AntiAddictionLogger.d("AntiAddiction result:" + i + "," + (map != null ? map.toString() : ""));
                boolean z2 = true;
                if (i == 500) {
                    antiAddictionUICallback.onCallback(500, null);
                    AntiAddictionUIImpl.sendDurationExtraParams(AntiAddictionUIImpl.this.currentUserIdentifier);
                    AntiAddictionUIImpl.this.isReady = true;
                    AntiAddictionKit.enterGame();
                    AntiAddictionUIImpl.this.showRealNameToast();
                    return;
                }
                final boolean zBooleanValue = false;
                if (i == 1030) {
                    z = true;
                } else if (i == 1050) {
                    z = false;
                } else {
                    if (i != 1095) {
                        if (i == 1100) {
                            antiAddictionUICallback.onCallback(Constants.ANTI_ADDICTION_CALLBACK_CODE.AGE_RESTRICT, null);
                            return;
                        }
                        if (i == 1200) {
                            antiAddictionUICallback.onCallback(i, map);
                            return;
                        }
                        if (i == 9002) {
                            antiAddictionUICallback.onCallback(9002, null);
                            return;
                        }
                        if (i == 1000) {
                            antiAddictionUICallback.onCallback(1000, null);
                            AntiAddictionUIImpl.sendDurationExtraParams(null);
                            return;
                        } else {
                            if (i != 1001) {
                                return;
                            }
                            antiAddictionUICallback.onCallback(1001, null);
                            AntiAddictionUIImpl.sendDurationExtraParams(null);
                            return;
                        }
                    }
                    z = false;
                    z2 = false;
                }
                if (z) {
                    str = "退出游戏";
                    i2 = 1003;
                } else if (z2) {
                    str = "退出游戏";
                    i2 = 1001;
                } else {
                    i2 = 1004;
                    str = "进入游戏";
                }
                if (map != null) {
                    String str4 = map.containsKey("title") ? (String) map.get("title") : "";
                    String str5 = map.containsKey(Constants.MsgExtraParams.DESCRIPTION) ? (String) map.get(Constants.MsgExtraParams.DESCRIPTION) : "";
                    zBooleanValue = map.containsKey(Constants.MsgExtraParams.IS_LOGIN_TIP) ? ((Boolean) map.get(Constants.MsgExtraParams.IS_LOGIN_TIP)).booleanValue() : false;
                    str2 = str4;
                    str3 = str5;
                } else {
                    str2 = "";
                    str3 = str2;
                }
                if (AntiAddictionUIImpl.this.currentActivityWeakReference == null) {
                    return;
                }
                AntiAddictionUIImpl antiAddictionUIImpl = AntiAddictionUIImpl.this;
                DialogGameHealthReminder dialogGameHealthReminderOpenReminderDialog = antiAddictionUIImpl.openReminderDialog(antiAddictionUIImpl.currentActivityWeakReference.get(), AntiAddictionUIImpl.this.currentUserIdentifier, i2, str2, str3, "", str, new DialogGameHealthReminder.Callback() { // from class: com.tapsdk.antiaddictionui.AntiAddictionUIImpl.2.1
                    @Override // com.tapsdk.antiaddictionui.widget.DialogGameHealthReminder.Callback
                    public void checkPending() {
                    }

                    @Override // com.tapsdk.antiaddictionui.widget.DialogGameHealthReminder.Callback
                    public void confirmNotice() {
                    }

                    @Override // com.tapsdk.antiaddictionui.widget.DialogGameHealthReminder.Callback
                    public void enterGame() {
                        if (zBooleanValue) {
                            AntiAddictionKit.checkPlayableWhenEnterByUI(AntiAddictionUIImpl.this.clientId, AntiAddictionUIImpl.this.currentUserIdentifier);
                        }
                    }
                });
                if (i != 1095) {
                    antiAddictionUICallback.onCallback(i, map);
                    if (AntiAddictionUIImpl.this.currentChildRemainTipDialog == null || AntiAddictionUIImpl.this.currentChildRemainTipDialog.get() == null || !AntiAddictionUIImpl.this.currentChildRemainTipDialog.get().isAdded()) {
                        return;
                    }
                    AntiAddictionUIImpl.this.currentChildRemainTipDialog.get().dismissAllowingStateLoss();
                    AntiAddictionUIImpl.this.currentChildRemainTipDialog = null;
                    return;
                }
                AntiAddictionUIImpl.this.currentChildRemainTipDialog = new WeakReference<>(dialogGameHealthReminderOpenReminderDialog);
                AntiAddictionUIImpl.this.showRealNameToast();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sendDurationExtraParams(String str) {
        String currentSession;
        HashMap map = new HashMap();
        if (TextUtils.isEmpty(str)) {
            str = "";
            currentSession = "";
        } else {
            currentSession = AntiAddictionKit.getCurrentSession();
        }
        map.put(CommonParam.ANTI_ADDICTION_USER_ID, str);
        map.put(CommonParam.ANTI_ADDICTION_SESSION_ID, currentSession);
        SdkDurationStatistics.setExtraCommonMessage(map);
    }

    private static String getMockResponse(Context context, Interceptor.Chain chain) {
        String string = chain.request().url().url().toString();
        AntiAddictionLogger.d("getMockResponse " + string);
        return string.contains("/account/idcard/v1") ? FileUtils.getJsonFromAssetsFile(context, "mock_taptap_identify_info.json") : "";
    }

    @Override // com.tapsdk.antiaddictionui.IAntiAddictionUI
    @Deprecated
    public void startup(Activity activity, String str) {
        startup(activity, str, false);
    }

    @Override // com.tapsdk.antiaddictionui.IAntiAddictionUI
    @Deprecated
    public void startup(Activity activity, boolean z, String str) {
        startup(activity, str, false);
    }

    @Override // com.tapsdk.antiaddictionui.IAntiAddictionUI
    public void startup(Activity activity, final String str, boolean z) {
        this.currentUserIdentifier = str;
        if (TextUtils.isEmpty(str)) {
            ToastUtils.safeToast(activity, "userIdentifier is empty");
            return;
        }
        if (this.isCheckingUser) {
            AntiAddictionLogger.d("current user is checking so just return");
            return;
        }
        this.isCheckingUser = true;
        WeakReference<Activity> weakReference = this.currentActivityWeakReference;
        if (weakReference == null || weakReference.get() != activity) {
            this.currentActivityWeakReference = new WeakReference<>(activity);
        }
        AntiToastManager.instance().showLoading(this.currentActivityWeakReference.get());
        AntiAddictionKit.getRealNameConfig(this.currentActivityWeakReference.get(), str, new com.tapsdk.antiaddiction.Callback<Void>() { // from class: com.tapsdk.antiaddictionui.AntiAddictionUIImpl.3
            @Override // com.tapsdk.antiaddiction.Callback
            public void onSuccess(Void r3) {
                AntiToastManager.instance().dismissLoading();
                AntiAddictionUIImpl antiAddictionUIImpl = AntiAddictionUIImpl.this;
                antiAddictionUIImpl.checkUserAuthIdentify(antiAddictionUIImpl.currentActivityWeakReference.get(), str);
            }

            @Override // com.tapsdk.antiaddiction.Callback
            public void onError(Throwable th) {
                AntiToastManager.instance().dismissLoading();
                AntiAddictionLogger.d("AntiAddiction fetch realNameConfig fail ");
                th.printStackTrace();
            }
        });
    }

    @Override // com.tapsdk.antiaddictionui.IAntiAddictionUI
    public void startupWithTapTap(Activity activity, String str) {
        startup(activity, str, true);
    }

    @Override // com.tapsdk.antiaddictionui.IAntiAddictionUI
    public void checkPayLimit(final Activity activity, long j, final Callback<CheckPayResult> callback) {
        WeakReference<Activity> weakReference = this.currentActivityWeakReference;
        if (weakReference == null || weakReference.get() != activity) {
            this.currentActivityWeakReference = new WeakReference<>(activity);
        }
        AntiAddictionKit.checkPayLimit(j, new com.tapsdk.antiaddiction.Callback<CheckPayResult>() { // from class: com.tapsdk.antiaddictionui.AntiAddictionUIImpl.4
            @Override // com.tapsdk.antiaddiction.Callback
            public void onSuccess(final CheckPayResult checkPayResult) {
                if (checkPayResult.status) {
                    callback.onSuccess(checkPayResult);
                    return;
                }
                callback.onSuccess(checkPayResult);
                if (AntiAddictionUIImpl.this.currentActivityWeakReference == null || TextUtils.isEmpty(checkPayResult.title) || TextUtils.isEmpty(checkPayResult.description)) {
                    return;
                }
                AntiAddictionUIImpl antiAddictionUIImpl = AntiAddictionUIImpl.this;
                DialogGameHealthReminder dialogGameHealthReminderOpenReminderDialog = antiAddictionUIImpl.openReminderDialog(antiAddictionUIImpl.currentActivityWeakReference.get(), AntiAddictionUIImpl.this.currentUserIdentifier, 1005, checkPayResult.title, checkPayResult.description, "", activity.getString(R.string.antiaddictionui_return_game), new DialogGameHealthReminder.Callback() { // from class: com.tapsdk.antiaddictionui.AntiAddictionUIImpl.4.1
                    @Override // com.tapsdk.antiaddictionui.widget.DialogGameHealthReminder.Callback
                    public void checkPending() {
                    }

                    @Override // com.tapsdk.antiaddictionui.widget.DialogGameHealthReminder.Callback
                    public void enterGame() {
                    }

                    @Override // com.tapsdk.antiaddictionui.widget.DialogGameHealthReminder.Callback
                    public void confirmNotice() {
                        callback.onSuccess(checkPayResult);
                    }
                });
                AntiAddictionUIImpl.this.currentChildRemainTipDialog = new WeakReference<>(dialogGameHealthReminderOpenReminderDialog);
            }

            @Override // com.tapsdk.antiaddiction.Callback
            public void onError(Throwable th) {
                callback.onError(th);
            }
        });
    }

    @Override // com.tapsdk.antiaddictionui.IAntiAddictionUI
    public void submitPayResult(long j, com.tapsdk.antiaddiction.Callback<SubmitPayResult> callback) {
        AntiAddictionKit.submitPayResult(j, callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryLoginAntiAddiction(String str, String str2) {
        AntiAddictionKit.login(str, str2, new AntiAddictionLoginCallback() { // from class: com.tapsdk.antiaddictionui.AntiAddictionUIImpl.5
            @Override // com.tapsdk.antiaddiction.AntiAddictionLoginCallback
            public void onSuccess() {
            }

            @Override // com.tapsdk.antiaddiction.AntiAddictionLoginCallback
            public void onError(Throwable th) {
                AntiAddictionLogger.d("try login failed");
                th.printStackTrace();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkUserAuthIdentify(Activity activity, final String str) {
        if (ActivityUtils.isActivityNotAlive(activity)) {
            return;
        }
        WeakReference<Activity> weakReference = this.currentActivityWeakReference;
        if (weakReference == null || weakReference.get() != activity) {
            this.currentActivityWeakReference = new WeakReference<>(activity);
        }
        AntiAddictionKit.checkStandaloneCacheAndFetchUserIdentifyState(this.clientId, str, new com.tapsdk.antiaddiction.Callback<IdentifyState>() { // from class: com.tapsdk.antiaddictionui.AntiAddictionUIImpl.6
            @Override // com.tapsdk.antiaddiction.Callback
            public void onSuccess(IdentifyState identifyState) {
                if (identifyState.identifyState.equals(IdentifyState.STATUS_PASS)) {
                    AntiAddictionUIImpl.this.tryLoginAntiAddiction(str, identifyState.antiAddictionToken);
                    return;
                }
                if (identifyState.identifyState.equals(IdentifyState.STATUS_WAITING)) {
                    if (AntiAddictionUIImpl.this.currentActivityWeakReference == null) {
                        return;
                    }
                    AntiAddictionUIImpl antiAddictionUIImpl = AntiAddictionUIImpl.this;
                    antiAddictionUIImpl.openReminderDialog(antiAddictionUIImpl.currentActivityWeakReference.get(), str, 1002, "", "", "", "", new DialogGameHealthReminder.Callback() { // from class: com.tapsdk.antiaddictionui.AntiAddictionUIImpl.6.1
                        @Override // com.tapsdk.antiaddictionui.widget.DialogGameHealthReminder.Callback
                        public void confirmNotice() {
                        }

                        @Override // com.tapsdk.antiaddictionui.widget.DialogGameHealthReminder.Callback
                        public void enterGame() {
                        }

                        @Override // com.tapsdk.antiaddictionui.widget.DialogGameHealthReminder.Callback
                        public void checkPending() {
                            if (AntiAddictionUIImpl.this.mAntiAddictionUICallback != null) {
                                AntiAddictionUIImpl.this.mAntiAddictionUICallback.onCallback(9002, null);
                            }
                        }
                    });
                    return;
                }
                if (AntiAddictionUIImpl.this.currentActivityWeakReference == null) {
                    return;
                }
                AntiAddictionUIImpl antiAddictionUIImpl2 = AntiAddictionUIImpl.this;
                antiAddictionUIImpl2.navigateToTapTapFastCertification(antiAddictionUIImpl2.currentActivityWeakReference.get(), str, AntiAddictionSettings.getInstance().getRealNameConfig().useManual);
            }

            @Override // com.tapsdk.antiaddiction.Callback
            public void onError(Throwable th) {
                AntiAddictionLogger.e("checkUserAuthIdentify: onError:" + th.getMessage());
                if (AntiAddictionUIImpl.this.currentActivityWeakReference == null || ActivityUtils.isActivityNotAlive(AntiAddictionUIImpl.this.currentActivityWeakReference.get())) {
                    return;
                }
                AntiAddictionUIImpl antiAddictionUIImpl = AntiAddictionUIImpl.this;
                antiAddictionUIImpl.navigateToTapTapFastCertification(antiAddictionUIImpl.currentActivityWeakReference.get(), str, AntiAddictionSettings.getInstance().getRealNameConfig().useManual);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void navigateToTapTapFastCertification(final Activity activity, final String str, boolean z) {
        if (ActivityUtils.isActivityNotAlive(activity)) {
            return;
        }
        DialogTapAuthorization dialogTapAuthorization = DialogTapAuthorization.getInstance(this.clientId, str, TapTapModel.isTapLogged() && !TapTapModel.hasComplianceAuthInLocal(null, this.config.useAgeRange), z, new DialogTapAuthorization.AuthorizationCallback() { // from class: com.tapsdk.antiaddictionui.AntiAddictionUIImpl.7
            @Override // com.tapsdk.antiaddictionui.widget.DialogTapAuthorization.AuthorizationCallback
            public void navigateToRealName(boolean z2) {
                AntiAddictionUIImpl.this.navigateToRealName(activity, str, z2);
            }

            @Override // com.tapsdk.antiaddictionui.widget.DialogTapAuthorization.AuthorizationCallback
            public void onRealNameSuccess(IdentifyState identifyState) {
                AntiAddictionUIImpl.this.tryLoginAntiAddiction(str, identifyState.antiAddictionToken);
            }

            @Override // com.tapsdk.antiaddictionui.widget.DialogTapAuthorization.AuthorizationCallback
            public void navigateToUnderReview() {
                AntiAddictionUIImpl.this.navigateToUnderReview(activity, str);
            }

            @Override // com.tapsdk.antiaddictionui.widget.DialogTapAuthorization.AuthorizationCallback
            public void onCloseRealName() {
                if (AntiAddictionUIImpl.this.mAntiAddictionUICallback != null) {
                    AntiAddictionUIImpl.this.mAntiAddictionUICallback.onCallback(9002, null);
                }
            }
        });
        if (dialogTapAuthorization.isAdded()) {
            return;
        }
        dialogTapAuthorization.show(activity.getFragmentManager(), DialogTapAuthorization.TAG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void navigateToRealName(Activity activity, final String str, boolean z) {
        DialogGameRealName dialogGameRealName = DialogGameRealName.getInstance(this.clientId, str, false, z, new DialogGameRealName.RealNameResultCallback() { // from class: com.tapsdk.antiaddictionui.AntiAddictionUIImpl.8
            @Override // com.tapsdk.antiaddictionui.widget.DialogGameRealName.RealNameResultCallback
            public void onRealNameSuccess(IdentifyState identifyState) {
                AntiAddictionUIImpl.this.tryLoginAntiAddiction(str, identifyState.antiAddictionToken);
            }

            @Override // com.tapsdk.antiaddictionui.widget.DialogGameRealName.RealNameResultCallback
            public void onCloseRealName() {
                if (AntiAddictionUIImpl.this.mAntiAddictionUICallback != null) {
                    AntiAddictionUIImpl.this.mAntiAddictionUICallback.onCallback(9002, null);
                }
            }
        });
        if (dialogGameRealName.isAdded()) {
            return;
        }
        dialogGameRealName.show(activity.getFragmentManager(), DialogGameRealName.TAG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void navigateToUnderReview(final Activity activity, String str) {
        if (ActivityUtils.isActivityNotAlive(activity)) {
            return;
        }
        DialogGameHealthReminder dialogGameHealthReminder = DialogGameHealthReminder.getInstance(this.clientId, str, 1002, "", "", "", "", new DialogGameHealthReminder.Callback() { // from class: com.tapsdk.antiaddictionui.AntiAddictionUIImpl.9
            @Override // com.tapsdk.antiaddictionui.widget.DialogGameHealthReminder.Callback
            public void enterGame() {
            }

            @Override // com.tapsdk.antiaddictionui.widget.DialogGameHealthReminder.Callback
            public void checkPending() {
                if (AntiAddictionUIImpl.this.mAntiAddictionUICallback != null) {
                    AntiAddictionUIImpl.this.mAntiAddictionUICallback.onCallback(9002, null);
                }
            }

            @Override // com.tapsdk.antiaddictionui.widget.DialogGameHealthReminder.Callback
            public void confirmNotice() {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        if (dialogGameHealthReminder.isAdded()) {
            return;
        }
        dialogGameHealthReminder.show(activity.getFragmentManager(), DialogGameRealName.TAG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DialogGameHealthReminder openReminderDialog(Activity activity, String str, int i, String str2, String str3, String str4, String str5, DialogGameHealthReminder.Callback callback) {
        if (ActivityUtils.isActivityNotAlive(activity)) {
            return null;
        }
        DialogGameHealthReminder dialogGameHealthReminder = DialogGameHealthReminder.getInstance(this.clientId, str, i, str2, str3, str4, str5, callback);
        if (!dialogGameHealthReminder.isAdded()) {
            dialogGameHealthReminder.show(activity.getFragmentManager(), DialogGameRealName.TAG);
        }
        return dialogGameHealthReminder;
    }

    @Override // com.tapsdk.antiaddictionui.IAntiAddictionUI
    public boolean isReady() {
        return this.isReady;
    }

    @Override // com.tapsdk.antiaddictionui.IAntiAddictionUI
    public void setTestFloatingWindowEnabled(final Activity activity, final boolean z) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tapsdk.antiaddictionui.AntiAddictionUIImpl.10
            @Override // java.lang.Runnable
            public void run() {
                if (z) {
                    FloatingWindow.getInstance().showFloatingWindow(activity, "当前处于防沉迷调试模式");
                } else {
                    FloatingWindow.getInstance().removeWindow();
                }
            }
        });
    }

    public void showRealNameToast() {
        WeakReference<Activity> weakReference = this.currentActivityWeakReference;
        if (weakReference == null || weakReference.get() == null || AntiAddictionSettings.getInstance().hasShowRealNameToast(this.currentActivityWeakReference.get(), this.currentUserIdentifier)) {
            return;
        }
        TapToastManager.instance().show(this.currentActivityWeakReference.get(), "已通过防沉迷校验，祝您游戏愉快！", 1, 80, UIUtil.dp2px(this.currentActivityWeakReference.get(), 50.0f));
        AntiAddictionSettings.getInstance().updateShowRealNameToast(this.currentActivityWeakReference.get(), this.currentUserIdentifier);
    }
}
