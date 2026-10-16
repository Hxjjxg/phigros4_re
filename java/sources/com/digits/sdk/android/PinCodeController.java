package com.digits.sdk.android;

import android.content.Context;
import android.net.Uri;
import android.os.ResultReceiver;
import android.widget.EditText;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.SessionManager;
import io.fabric.sdk.android.services.common.CommonUtils;

/* JADX INFO: loaded from: classes.dex */
class PinCodeController extends DigitsControllerImpl {
    private final String phoneNumber;
    private final String requestId;
    private final long userId;

    @Override // com.digits.sdk.android.DigitsControllerImpl
    Uri getTOSUri() {
        return null;
    }

    @Override // com.digits.sdk.android.DigitsControllerImpl, com.digits.sdk.android.DigitsController
    public void showTOS(Context context) {
    }

    PinCodeController(ResultReceiver resultReceiver, StateButton stateButton, EditText editText, String str, long j, String str2) {
        this(resultReceiver, stateButton, editText, Digits.getSessionManager(), Digits.getInstance().getDigitsClient(), str, j, str2, new ConfirmationErrorCodes(stateButton.getContext().getResources()), Digits.getInstance().getActivityClassManager());
    }

    PinCodeController(ResultReceiver resultReceiver, StateButton stateButton, EditText editText, SessionManager<DigitsSession> sessionManager, DigitsClient digitsClient, String str, long j, String str2, ErrorCodes errorCodes, ActivityClassManager activityClassManager) {
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
            this.digitsClient.verifyPin(this.requestId, this.userId, this.editText.getText().toString(), new DigitsCallback<DigitsUser>(context, this) { // from class: com.digits.sdk.android.PinCodeController.1
                @Override // com.twitter.sdk.android.core.Callback
                public void success(Result<DigitsUser> result) {
                    DigitsSession digitsSessionCreate = DigitsSession.create(result.data);
                    PinCodeController pinCodeController = PinCodeController.this;
                    pinCodeController.loginSuccess(context, digitsSessionCreate, pinCodeController.phoneNumber);
                }
            });
        }
    }
}
