package com.tapsdk.antiaddictionui.widget;

import android.app.Activity;
import com.tapsdk.antiaddiction.Callback;
import com.tapsdk.antiaddiction.entities.response.IdentifyState;
import com.tapsdk.antiaddiction.models.IdentifyModel;
import com.tapsdk.antiaddiction.models.TapTapModel;
import com.tapsdk.antiaddiction.skynet.exceptions.AntiServerException;
import com.tapsdk.antiaddiction.utils.AntiAddictionLogger;
import com.tapsdk.antiaddictionui.utils.ActivityUtils;
import com.tapsdk.antiaddictionui.utils.ToastUtils;
import com.taptap.sdk.AccessToken;
import com.taptap.sdk.AccountGlobalError;
import com.taptap.sdk.TapLoginHelper;

/* JADX INFO: loaded from: classes.dex */
public class TapAuthorizationService {
    private TapAuthorizationCallback authorizationCallback;
    private String clientId;
    private final IdentifyModel identifyModel;
    private final TapTapModel tapTapModel;
    private boolean useAgeRange;
    private String userIdentifier;

    public interface TapAuthorizationCallback {
        void navigateToRealName();

        void navigateToUnderReview();

        void onCancel();

        void onFail(Throwable th);

        void onSuccess(IdentifyState identifyState);
    }

    private TapAuthorizationService() {
        this.tapTapModel = new TapTapModel();
        this.identifyModel = new IdentifyModel();
    }

    public static TapAuthorizationService getInstance() {
        return Single.INSTANCE.service;
    }

    public void setUseAgeRange(boolean z) {
        this.useAgeRange = z;
    }

    public void authorizeFromTapTap(final Activity activity, String str, String str2, TapAuthorizationCallback tapAuthorizationCallback) {
        if (ActivityUtils.isActivityNotAlive(activity)) {
            return;
        }
        this.clientId = str;
        this.userIdentifier = str2;
        this.authorizationCallback = tapAuthorizationCallback;
        try {
            AntiToastManager.instance().showLoading(activity);
            this.tapTapModel.getTapIdentifyToken(activity, this.useAgeRange, new TapLoginHelper.TapLoginResultCallback() { // from class: com.tapsdk.antiaddictionui.widget.TapAuthorizationService.1
                @Override // com.taptap.sdk.TapLoginHelper.TapLoginResultCallback
                public void onLoginSuccess(AccessToken accessToken) {
                    TapAuthorizationService.this.uploadTapTapAuthorizationInfo(activity, accessToken);
                }

                @Override // com.taptap.sdk.TapLoginHelper.TapLoginResultCallback
                public void onLoginCancel() {
                    AntiToastManager.instance().dismissLoading();
                    ToastUtils.safeToast(activity, "授权取消");
                    if (TapAuthorizationService.this.authorizationCallback != null) {
                        TapAuthorizationService.this.authorizationCallback.onCancel();
                    }
                }

                @Override // com.taptap.sdk.TapLoginHelper.TapLoginResultCallback
                public void onLoginError(AccountGlobalError accountGlobalError) {
                    AntiToastManager.instance().dismissLoading();
                    ToastUtils.safeToast(activity, "授权失败");
                    TapAuthorizationService.this.navigateToManualIdentify();
                }
            });
        } catch (Exception unused) {
            AntiToastManager.instance().dismissLoading();
            navigateToManualIdentify();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadTapTapAuthorizationInfo(final Activity activity, AccessToken accessToken) {
        this.identifyModel.getTokenByTapToken(this.clientId, this.userIdentifier, this.useAgeRange, accessToken, new Callback<IdentifyState>() { // from class: com.tapsdk.antiaddictionui.widget.TapAuthorizationService.2
            @Override // com.tapsdk.antiaddiction.Callback
            public void onSuccess(IdentifyState identifyState) {
                AntiToastManager.instance().dismissLoading();
                TapAuthorizationService.this.processIdentifyResult(identifyState, activity);
            }

            @Override // com.tapsdk.antiaddiction.Callback
            public void onError(Throwable th) {
                String message;
                AntiToastManager.instance().dismissLoading();
                boolean z = th instanceof AntiServerException;
                if (z && ((AntiServerException) th).statusCode >= 500) {
                    AntiAddictionLogger.d("upload tap to server fail ServerError  ");
                } else {
                    if (z) {
                        message = ((AntiServerException) th).errorDescription;
                    } else {
                        message = th.getMessage();
                    }
                    AntiAddictionLogger.d("upload tap to server fail error : " + message);
                }
                TapAuthorizationService.this.navigateToManualIdentify();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processIdentifyResult(IdentifyState identifyState, Activity activity) {
        if (identifyState == null) {
            TapAuthorizationCallback tapAuthorizationCallback = this.authorizationCallback;
            if (tapAuthorizationCallback != null) {
                tapAuthorizationCallback.onFail(new RuntimeException("invalid identifyState"));
                return;
            }
            return;
        }
        if (identifyState.identifyState.equals(IdentifyState.STATUS_PASS)) {
            TapAuthorizationCallback tapAuthorizationCallback2 = this.authorizationCallback;
            if (tapAuthorizationCallback2 != null) {
                tapAuthorizationCallback2.onSuccess(identifyState);
                return;
            }
            return;
        }
        if (identifyState.identifyState.equals(IdentifyState.STATUS_WAITING)) {
            TapAuthorizationCallback tapAuthorizationCallback3 = this.authorizationCallback;
            if (tapAuthorizationCallback3 != null) {
                tapAuthorizationCallback3.navigateToUnderReview();
                return;
            }
            return;
        }
        TapAuthorizationCallback tapAuthorizationCallback4 = this.authorizationCallback;
        if (tapAuthorizationCallback4 != null) {
            tapAuthorizationCallback4.navigateToRealName();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void navigateToManualIdentify() {
        TapAuthorizationCallback tapAuthorizationCallback = this.authorizationCallback;
        if (tapAuthorizationCallback != null) {
            tapAuthorizationCallback.navigateToRealName();
        }
    }

    enum Single {
        INSTANCE;

        TapAuthorizationService service = new TapAuthorizationService();

        Single() {
        }
    }
}
