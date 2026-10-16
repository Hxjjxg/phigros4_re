package com.digits.sdk.android;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import io.fabric.sdk.android.services.common.CommonUtils;

/* JADX INFO: loaded from: classes.dex */
public class PhoneNumberActivity extends DigitsActivity {
    @Override // com.digits.sdk.android.DigitsActivity
    int getLayoutId() {
        return R.layout.dgts__activity_phone_number;
    }

    @Override // com.digits.sdk.android.DigitsActivity
    boolean isValid(Bundle bundle) {
        return BundleManager.assertContains(bundle, DigitsClient.EXTRA_RESULT_RECEIVER);
    }

    @Override // com.digits.sdk.android.DigitsActivity
    DigitsController init(Bundle bundle) {
        CountryListSpinner countryListSpinner = (CountryListSpinner) findViewById(R.id.dgts__countryCode);
        StateButton stateButton = (StateButton) findViewById(R.id.dgts__sendCodeButton);
        EditText editText = (EditText) findViewById(R.id.dgts__phoneNumberEditText);
        TextView textView = (TextView) findViewById(R.id.dgts__termsText);
        this.controller = new PhoneNumberController((ResultReceiver) bundle.getParcelable(DigitsClient.EXTRA_RESULT_RECEIVER), stateButton, editText, countryListSpinner);
        setUpPhoneEditText(editText);
        setUpSendButton(stateButton);
        setUpTermsText(textView);
        setUpCountrySpinner(countryListSpinner);
        CommonUtils.openKeyboard(this, editText);
        return this.controller;
    }

    private void setUpCountrySpinner(CountryListSpinner countryListSpinner) {
        countryListSpinner.setOnClickListener(new View.OnClickListener() { // from class: com.digits.sdk.android.PhoneNumberActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PhoneNumberActivity.this.controller.clearError();
            }
        });
    }
}
