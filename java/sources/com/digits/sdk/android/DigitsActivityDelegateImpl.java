package com.digits.sdk.android;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public abstract class DigitsActivityDelegateImpl implements DigitsActivityDelegate {
    public void setUpSendButton(final Activity activity, final DigitsController digitsController, StateButton stateButton) {
        stateButton.setOnClickListener(new View.OnClickListener() { // from class: com.digits.sdk.android.DigitsActivityDelegateImpl.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                digitsController.clearError();
                digitsController.executeRequest(activity);
            }
        });
    }

    public void setUpEditText(final Activity activity, final DigitsController digitsController, EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.digits.sdk.android.DigitsActivityDelegateImpl.2
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != 5) {
                    return false;
                }
                digitsController.clearError();
                digitsController.executeRequest(activity);
                return true;
            }
        });
        editText.addTextChangedListener(digitsController.getTextWatcher());
    }

    public void setUpTermsText(final Activity activity, final DigitsController digitsController, TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.digits.sdk.android.DigitsActivityDelegateImpl.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                digitsController.clearError();
                digitsController.showTOS(activity);
            }
        });
    }
}
