package com.digits.sdk.android;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.widget.EditText;
import android.widget.TextView;
import io.fabric.sdk.android.services.common.CommonUtils;

/* JADX INFO: loaded from: classes.dex */
public class LoginCodeActionBarActivity extends DigitsActionBarActivity {
    EditText editText;
    StateButton stateButton;
    TextView termsText;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.digits.sdk.android.DigitsActionBarActivity
    DigitsController init(Bundle bundle) {
        this.editText = (EditText) findViewById(R.id.dgts__confirmationEditText);
        this.stateButton = (StateButton) findViewById(R.id.dgts__createAccount);
        this.termsText = (TextView) findViewById(R.id.dgts__termsTextCreateAccount);
        TextView textView = (TextView) findViewById(R.id.dgts__resendConfirmation);
        this.controller = initController(bundle);
        setUpPhoneEditText(this.editText);
        setUpSendButton(this.stateButton);
        setUpTermsText(this.termsText);
        setUpResendText(textView);
        CommonUtils.openKeyboard(this, this.editText);
        return this.controller;
    }

    DigitsController initController(Bundle bundle) {
        return new LoginCodeController((ResultReceiver) bundle.getParcelable(DigitsClient.EXTRA_RESULT_RECEIVER), this.stateButton, this.editText, bundle.getString(DigitsClient.EXTRA_REQUEST_ID), bundle.getLong(DigitsClient.EXTRA_USER_ID), bundle.getString(DigitsClient.EXTRA_PHONE));
    }

    @Override // com.digits.sdk.android.DigitsActionBarActivity
    void setUpTermsText(TextView textView) {
        textView.setVisibility(8);
    }

    @Override // com.digits.sdk.android.DigitsActionBarActivity
    void setUpSendButton(StateButton stateButton) {
        stateButton.setStatesText(R.string.dgts__sign_in, R.string.dgts__signing_in, R.string.dgts__sign_in);
        stateButton.showStart();
        super.setUpSendButton(stateButton);
    }

    @Override // com.digits.sdk.android.DigitsActionBarActivity
    int getLayoutId() {
        return R.layout.dgts__activity_confirmation;
    }

    @Override // com.digits.sdk.android.DigitsActionBarActivity
    boolean isValid(Bundle bundle) {
        return BundleManager.assertContains(bundle, DigitsClient.EXTRA_RESULT_RECEIVER, DigitsClient.EXTRA_REQUEST_ID, DigitsClient.EXTRA_USER_ID);
    }
}
