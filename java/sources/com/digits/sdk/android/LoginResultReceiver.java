package com.digits.sdk.android;

import android.os.Bundle;
import android.os.ResultReceiver;
import com.twitter.sdk.android.core.SessionManager;

/* JADX INFO: loaded from: classes.dex */
class LoginResultReceiver extends ResultReceiver {
    static final String KEY_ERROR = "login_error";
    static final int RESULT_ERROR = 400;
    static final int RESULT_OK = 200;
    final AuthCallback callback;
    final SessionManager<DigitsSession> sessionManager;

    LoginResultReceiver(AuthCallback authCallback, SessionManager<DigitsSession> sessionManager) {
        super(null);
        this.callback = authCallback;
        this.sessionManager = sessionManager;
    }

    @Override // android.os.ResultReceiver
    public void onReceiveResult(int i, Bundle bundle) {
        AuthCallback authCallback = this.callback;
        if (authCallback != null) {
            if (i == 200) {
                authCallback.success((DigitsSession) this.sessionManager.getActiveSession(), bundle.getString(DigitsClient.EXTRA_PHONE));
            } else if (i == RESULT_ERROR) {
                authCallback.failure(new DigitsException(bundle.getString(KEY_ERROR)));
            }
        }
    }
}
