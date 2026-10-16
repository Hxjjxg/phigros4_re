package com.digits.sdk.android;

import android.content.Context;
import android.net.Uri;
import android.os.ResultReceiver;
import android.widget.EditText;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.models.User;
import io.fabric.sdk.android.services.common.CommonUtils;

/* JADX INFO: loaded from: classes.dex */
class ConfirmationCodeController extends DigitsControllerImpl {
    private final String phoneNumber;

    ConfirmationCodeController(ResultReceiver resultReceiver, StateButton stateButton, EditText editText, String str) {
        this(resultReceiver, stateButton, editText, str, Digits.getSessionManager(), Digits.getInstance().getDigitsClient(), new ConfirmationErrorCodes(stateButton.getContext().getResources()), Digits.getInstance().getActivityClassManager());
    }

    ConfirmationCodeController(ResultReceiver resultReceiver, StateButton stateButton, EditText editText, String str, SessionManager<DigitsSession> sessionManager, DigitsClient digitsClient, ErrorCodes errorCodes, ActivityClassManager activityClassManager) {
        super(resultReceiver, stateButton, editText, digitsClient, errorCodes, activityClassManager, sessionManager);
        this.phoneNumber = str;
    }

    @Override // com.digits.sdk.android.DigitsController
    public void executeRequest(final Context context) {
        if (validateInput(this.editText.getText())) {
            this.sendButton.showProgress();
            CommonUtils.hideKeyboard(context, this.editText);
            this.digitsClient.createAccount(this.editText.getText().toString(), this.phoneNumber, new DigitsCallback<User>(context, this) { // from class: com.digits.sdk.android.ConfirmationCodeController.1
                @Override // com.twitter.sdk.android.core.Callback
                public void success(Result<User> result) {
                    DigitsSession digitsSessionCreate = DigitsSession.create(result);
                    ConfirmationCodeController confirmationCodeController = ConfirmationCodeController.this;
                    confirmationCodeController.loginSuccess(context, digitsSessionCreate, confirmationCodeController.phoneNumber);
                }
            });
        }
    }

    @Override // com.digits.sdk.android.DigitsControllerImpl
    Uri getTOSUri() {
        return DigitsConstants.TWITTER_TOS;
    }
}
