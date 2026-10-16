package com.digits.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.widget.EditText;
import android.widget.TextView;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.SessionManager;
import io.fabric.sdk.android.services.common.CommonUtils;
import retrofit.client.Response;

/* JADX INFO: loaded from: classes.dex */
class PhoneNumberController extends DigitsControllerImpl {
    final TextView countryCodeSpinner;
    String phoneNumber;

    PhoneNumberController(ResultReceiver resultReceiver, StateButton stateButton, EditText editText, CountryListSpinner countryListSpinner) {
        this(resultReceiver, stateButton, editText, countryListSpinner, Digits.getInstance().getDigitsClient(), new PhoneNumberErrorCodes(stateButton.getContext().getResources()), Digits.getInstance().getActivityClassManager(), Digits.getSessionManager());
    }

    PhoneNumberController(ResultReceiver resultReceiver, StateButton stateButton, EditText editText, CountryListSpinner countryListSpinner, DigitsClient digitsClient, ErrorCodes errorCodes, ActivityClassManager activityClassManager, SessionManager sessionManager) {
        super(resultReceiver, stateButton, editText, digitsClient, errorCodes, activityClassManager, sessionManager);
        this.countryCodeSpinner = countryListSpinner;
    }

    @Override // com.digits.sdk.android.DigitsController
    public void executeRequest(final Context context) {
        if (validateInput(this.editText.getText())) {
            this.sendButton.showProgress();
            CommonUtils.hideKeyboard(context, this.editText);
            this.phoneNumber = getNumber(((Integer) this.countryCodeSpinner.getTag()).intValue(), this.editText.getText().toString());
            this.digitsClient.registerDevice(context, this, this.phoneNumber, new DigitsCallback<Response>(context, this) { // from class: com.digits.sdk.android.PhoneNumberController.1
                @Override // com.twitter.sdk.android.core.Callback
                public void success(Result<Response> result) {
                    PhoneNumberController.this.sendButton.showFinish();
                    PhoneNumberController.this.editText.postDelayed(new Runnable() { // from class: com.digits.sdk.android.PhoneNumberController.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            PhoneNumberController.this.startNextStep(context);
                        }
                    }, 1500L);
                }
            });
        }
    }

    @Override // com.digits.sdk.android.DigitsControllerImpl, com.digits.sdk.android.DigitsController
    public void handleError(final Context context, DigitsException digitsException) {
        if (digitsException instanceof AlreadyRegisteredException) {
            this.digitsClient.authDevice(this.phoneNumber, new DigitsCallback<AuthResponse>(context, this) { // from class: com.digits.sdk.android.PhoneNumberController.2
                @Override // com.twitter.sdk.android.core.Callback
                public void success(Result<AuthResponse> result) {
                    PhoneNumberController.this.sendButton.showFinish();
                    PhoneNumberController.this.startSignIn(context, result.data.requestId, result.data.userId);
                }
            });
        } else {
            super.handleError(context, digitsException);
        }
    }

    @Override // com.digits.sdk.android.DigitsControllerImpl
    Uri getTOSUri() {
        return DigitsConstants.DIGITS_TOS;
    }

    void startSignIn(Context context, String str, long j) {
        Intent intent = new Intent(context, this.activityClassManager.getLoginCodeActivity());
        Bundle bundle = getBundle();
        bundle.putString(DigitsClient.EXTRA_REQUEST_ID, str);
        bundle.putLong(DigitsClient.EXTRA_USER_ID, j);
        intent.putExtras(bundle);
        startActivityForResult((Activity) context, intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startNextStep(Context context) {
        Intent intent = new Intent(context, this.activityClassManager.getConfirmationActivity());
        intent.putExtras(getBundle());
        startActivityForResult((Activity) context, intent);
    }

    private Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(DigitsClient.EXTRA_PHONE, this.phoneNumber);
        bundle.putParcelable(DigitsClient.EXTRA_RESULT_RECEIVER, this.resultReceiver);
        return bundle;
    }

    private String getNumber(long j, String str) {
        return "+" + String.valueOf(j) + str;
    }
}
