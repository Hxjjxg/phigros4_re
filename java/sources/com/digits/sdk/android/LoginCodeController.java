package com.digits.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.widget.EditText;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.SessionManager;
import io.fabric.sdk.android.services.common.CommonUtils;

/* JADX INFO: loaded from: classes.dex */
class LoginCodeController extends DigitsControllerImpl {
    private final String phoneNumber;
    private final String requestId;
    private final long userId;

    LoginCodeController(ResultReceiver resultReceiver, StateButton stateButton, EditText editText, String str, long j, String str2) {
        this(resultReceiver, stateButton, editText, Digits.getSessionManager(), Digits.getInstance().getDigitsClient(), str, j, str2, new ConfirmationErrorCodes(stateButton.getContext().getResources()), Digits.getInstance().getActivityClassManager());
    }

    LoginCodeController(ResultReceiver resultReceiver, StateButton stateButton, EditText editText, SessionManager<DigitsSession> sessionManager, DigitsClient digitsClient, String str, long j, String str2, ErrorCodes errorCodes, ActivityClassManager activityClassManager) {
        super(resultReceiver, stateButton, editText, digitsClient, errorCodes, activityClassManager, sessionManager);
        this.requestId = str;
        this.userId = j;
        this.phoneNumber = str2;
    }

    @Override // com.digits.sdk.android.DigitsController
    public void executeRequest(final Context context) {
        if (validateInput(this.editText.getText())) {
            this.sendButton.showProgress();
            CommonUtils.hideKeyboard(context, this.editText);
            this.digitsClient.loginDevice(this.requestId, this.userId, this.editText.getText().toString(), new DigitsCallback<DigitsUser>(context, this) { // from class: com.digits.sdk.android.LoginCodeController.1
                @Override // com.twitter.sdk.android.core.Callback
                public void success(Result<DigitsUser> result) {
                    if (result.data.isEmpty()) {
                        LoginCodeController.this.startPinCodeActivity(context);
                        return;
                    }
                    DigitsSession digitsSessionCreate = DigitsSession.create(result.data);
                    LoginCodeController loginCodeController = LoginCodeController.this;
                    loginCodeController.loginSuccess(context, digitsSessionCreate, loginCodeController.phoneNumber);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPinCodeActivity(Context context) {
        Intent intent = new Intent(context, this.activityClassManager.getPinCodeActivity());
        Bundle bundle = getBundle(this.phoneNumber);
        bundle.putParcelable(DigitsClient.EXTRA_RESULT_RECEIVER, this.resultReceiver);
        bundle.putString(DigitsClient.EXTRA_REQUEST_ID, this.requestId);
        bundle.putLong(DigitsClient.EXTRA_USER_ID, this.userId);
        intent.putExtras(bundle);
        startActivityForResult((Activity) context, intent);
    }

    @Override // com.digits.sdk.android.DigitsControllerImpl
    Uri getTOSUri() {
        return DigitsConstants.TWITTER_TOS;
    }
}
