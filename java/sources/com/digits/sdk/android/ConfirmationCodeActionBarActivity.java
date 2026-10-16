package com.digits.sdk.android;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.widget.EditText;
import android.widget.TextView;
import io.fabric.sdk.android.services.common.CommonUtils;

/* JADX INFO: loaded from: classes.dex */
public class ConfirmationCodeActionBarActivity extends DigitsActionBarActivity {
    EditText editText;
    private TextView resendText;
    StateButton stateButton;
    TextView termsText;

    @Override // com.digits.sdk.android.DigitsActionBarActivity
    int getLayoutId() {
        return R.layout.dgts__activity_confirmation;
    }

    @Override // com.digits.sdk.android.DigitsActionBarActivity
    boolean isValid(Bundle bundle) {
        return BundleManager.assertContains(bundle, DigitsClient.EXTRA_RESULT_RECEIVER, DigitsClient.EXTRA_PHONE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.digits.sdk.android.DigitsActionBarActivity
    DigitsController init(Bundle bundle) {
        this.editText = (EditText) findViewById(R.id.dgts__confirmationEditText);
        this.stateButton = (StateButton) findViewById(R.id.dgts__createAccount);
        this.termsText = (TextView) findViewById(R.id.dgts__termsTextCreateAccount);
        this.resendText = (TextView) findViewById(R.id.dgts__resendConfirmation);
        this.controller = initController(bundle);
        setUpPhoneEditText(this.editText);
        setUpSendButton(this.stateButton);
        setUpTermsText(this.termsText);
        setUpResendText(this.resendText);
        CommonUtils.openKeyboard(this, this.editText);
        return this.controller;
    }

    DigitsController initController(Bundle bundle) {
        return new ConfirmationCodeController((ResultReceiver) bundle.getParcelable(DigitsClient.EXTRA_RESULT_RECEIVER), this.stateButton, this.editText, bundle.getString(DigitsClient.EXTRA_PHONE));
    }
}
