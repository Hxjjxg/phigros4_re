package com.digits.sdk.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public abstract class DigitsActivity extends Activity {
    static final int REQUEST_CODE = 140;
    static final int RESULT_FINISH_DIGITS = 200;
    DigitsController controller;

    abstract int getLayoutId();

    abstract DigitsController init(Bundle bundle);

    abstract boolean isValid(Bundle bundle);

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        setTheme(Digits.getInstance().getTheme());
        super.onCreate(bundle);
        setContentView(getLayoutId());
        Bundle extras = getIntent().getExtras();
        if (isValid(extras)) {
            this.controller = init(extras);
        } else {
            finish();
            throw new IllegalAccessError("This activity can only be started from Digits");
        }
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        this.controller.onResume();
    }

    void setUpSendButton(StateButton stateButton) {
        stateButton.setOnClickListener(new View.OnClickListener() { // from class: com.digits.sdk.android.DigitsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DigitsActivity.this.controller.clearError();
                DigitsActivity.this.controller.executeRequest(DigitsActivity.this);
            }
        });
    }

    void setUpPhoneEditText(EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.digits.sdk.android.DigitsActivity.2
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != 5) {
                    return false;
                }
                DigitsActivity.this.controller.clearError();
                DigitsActivity.this.controller.executeRequest(DigitsActivity.this);
                return true;
            }
        });
        editText.addTextChangedListener(this.controller.getTextWatcher());
    }

    void setUpTermsText(TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.digits.sdk.android.DigitsActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DigitsActivity.this.controller.clearError();
                DigitsActivity.this.controller.showTOS(DigitsActivity.this);
            }
        });
    }

    void setUpResendText(TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.digits.sdk.android.DigitsActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DigitsActivity.this.finish();
            }
        });
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == 200 && i == 140) {
            finish();
        }
    }
}
