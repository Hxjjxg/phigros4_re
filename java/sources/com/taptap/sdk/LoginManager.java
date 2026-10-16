package com.taptap.sdk;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.CookieManager;
import androidx.core.os.EnvironmentCompat;
import com.taptap.sdk.constant.LoginConstants;
import com.taptap.sdk.net.Api;
import com.taptap.sdk.ui.TapTapActivity;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class LoginManager {
    public static final String KEY_RESPONSE = "com.taptap.sdk.response";
    public static final String RES_KEY_CANCEL = "com.taptap.sdk.response.cancel";
    public static final String RES_KEY_CODE = "com.taptap.sdk.response.code";
    public static final String RES_KEY_CODE_VERIFIER = "com.taptap.sdk.response.codeVerifier";
    public static final String RES_KEY_ERROR = "com.taptap.sdk.response.error";
    public static final String RES_KEY_LOGIN_VERSION = "com.taptap.sdk.response.login_version";
    public static final String RES_KEY_PERMISSIONS = "com.taptap.sdk.response.permissions";
    public static final String RES_KEY_SERVER_URI = "com.taptap.sdk.response.server_uri";
    public static final String RES_KEY_STATE = "com.taptap.sdk.response.state";
    public static final String RES_KEY_TOKEN = "com.taptap.sdk.response.token";
    public static final String RES_KEY_TOKEN_PARCELABLE = "com.taptap.sdk.response.token.parcel";
    private static LoginManager instance;
    private LoginRequest mLastLoginRequest;
    private boolean preApproved = false;

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    private LoginManager() {
    }

    public void registerCallback(CallBackManager callBackManager, final TapTapLoginCallback<LoginResponse> tapTapLoginCallback) {
        if (callBackManager instanceof CallbackManagerImpl) {
            ((CallbackManagerImpl) callBackManager).registerCallback(new CallbackManagerImpl.Callback() { // from class: com.taptap.sdk.LoginManager.1
                @Override // com.taptap.sdk.CallbackManagerImpl.Callback
                public boolean onActivityResult(int i, Intent intent) {
                    return LoginManager.this.onActivityResult(i, intent, tapTapLoginCallback);
                }
            }, 10);
            return;
        }
        throw new IllegalStateException("callbackManager must be instance of  CallbackManagerImpl");
    }

    public void logInWithReadPermissions(Activity activity, LoginRequest loginRequest, String str, String... strArr) {
        Validate.sdkHasInitialized();
        loginRequest.setPreApproved(this.preApproved);
        this.mLastLoginRequest = loginRequest;
        loginRequest.setVersionCode("3.30.3");
        loginRequest.setInfo(LoginRequest.generateInfo(activity, str, this.preApproved));
        Intent intent = new Intent();
        intent.setClass(activity, TapTapActivity.class);
        intent.putExtra("request", loginRequest);
        activity.startActivityForResult(intent, loginRequest.getRequestCode());
    }

    public void logout() {
        Validate.sdkHasInitialized();
        if (AccessToken.getCurrentAccessToken() != null) {
            AccessToken.clear();
        }
        if (Profile.getCurrentProfile() != null) {
            Profile.getCurrentProfile().clear();
        }
        String webHost = TapLoginInnerConfig.getRegionType().getWebHost();
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(webHost);
        if (cookie != null) {
            for (String str : cookie.split(";")) {
                String[] strArrSplit = str.split("=", 2);
                if (strArrSplit.length == 2) {
                    cookieManager.setCookie(webHost, strArrSplit[0].trim() + "=;Max-Age=0");
                }
            }
            cookieManager.flush();
        }
    }

    public void setPreApproved(boolean z) {
        this.preApproved = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onActivityResult(int i, Intent intent, final TapTapLoginCallback<LoginResponse> tapTapLoginCallback) {
        LoginResponse resultFromIntent;
        if (i != -1) {
            if (i == 0) {
                tapTapLoginCallback.onCancel();
            }
            return true;
        }
        final boolean z = intent == null || intent.getBooleanExtra(TapLoginHelperActivity.INTENT_KEY_LOGIN_REQUEST, true);
        try {
            resultFromIntent = LoginResponse.getResultFromIntent(intent);
        } catch (JSONException e) {
            tapTapLoginCallback.onError(e);
            resultFromIntent = null;
        }
        try {
            if (resultFromIntent == null) {
                tapTapLoginCallback.onError(new NullPointerException("result is null"));
                return false;
            }
            if (resultFromIntent.cancel) {
                tapTapLoginCallback.onCancel();
                return false;
            }
            if (resultFromIntent.state != null && resultFromIntent.state.equals(this.mLastLoginRequest.getState())) {
                if (!TextUtils.isEmpty(resultFromIntent.errorMessage)) {
                    if (TextUtils.equals(resultFromIntent.errorMessage, AccountGlobalError.LOGIN_ERROR_ACCESS_DENIED)) {
                        tapTapLoginCallback.onCancel();
                    } else {
                        tapTapLoginCallback.onError(new IllegalArgumentException(resultFromIntent.errorMessage));
                    }
                } else if (resultFromIntent.token == null && resultFromIntent.code == null) {
                    tapTapLoginCallback.onError(new IllegalAccessException("token is null"));
                } else if (LoginConstants.LOGIN_VERSION_RETURN_CODE_1.equals(resultFromIntent.loginVersion)) {
                    TapLoginWithCode.loginWithCode(resultFromIntent.code, TapLoginInnerConfig.codeVerifier, resultFromIntent.state, resultFromIntent.getName(), new TapLoginWithCode.LoginResultCallBack() { // from class: com.taptap.sdk.LoginManager.2
                        @Override // com.taptap.sdk.TapLoginWithCode.LoginResultCallBack
                        public void onLoginResult(LoginResponse loginResponse) {
                            if (!TextUtils.isEmpty(loginResponse.errorMessage)) {
                                if (TextUtils.equals(loginResponse.errorMessage, AccountGlobalError.LOGIN_ERROR_ACCESS_DENIED)) {
                                    tapTapLoginCallback.onCancel();
                                    return;
                                } else {
                                    tapTapLoginCallback.onError(new IllegalArgumentException(loginResponse.errorMessage));
                                    return;
                                }
                            }
                            if (z) {
                                LoginManager.this.loginWithToken(loginResponse, tapTapLoginCallback);
                            } else {
                                tapTapLoginCallback.onSuccess(loginResponse);
                            }
                        }
                    });
                } else if (z) {
                    loginWithToken(resultFromIntent, tapTapLoginCallback);
                } else {
                    tapTapLoginCallback.onSuccess(resultFromIntent);
                }
                return true;
            }
            tapTapLoginCallback.onError(new IllegalStateException("state not equal"));
            return false;
        } catch (Exception e2) {
            tapTapLoginCallback.onError(e2);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loginWithToken(final LoginResponse loginResponse, final TapTapLoginCallback<LoginResponse> tapTapLoginCallback) {
        AccessToken.setCurrentToken(loginResponse.token);
        Profile.fetchProfileForCurrentAccessToken(new Api.ApiCallback<Profile>() { // from class: com.taptap.sdk.LoginManager.3
            @Override // com.taptap.sdk.net.Api.ApiCallback
            public void onSuccess(Profile profile) {
                if (AccessToken.getCurrentAccessToken() == null) {
                    tapTapLoginCallback.onCancel();
                } else {
                    tapTapLoginCallback.onSuccess(loginResponse);
                }
            }

            @Override // com.taptap.sdk.net.Api.ApiCallback
            public void onError(Throwable th) {
                Log.DEBUG_LOG("fetch profile fail error = " + (th == null ? EnvironmentCompat.MEDIA_UNKNOWN : th.getMessage()));
                AccessToken.clear();
                tapTapLoginCallback.onError(th);
            }
        });
    }
}
