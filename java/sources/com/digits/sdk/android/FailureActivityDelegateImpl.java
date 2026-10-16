package com.digits.sdk.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import io.fabric.sdk.android.services.common.CommonUtils;
import tds.androidx.recyclerview.widget.ItemTouchHelper;

/* JADX INFO: loaded from: classes.dex */
class FailureActivityDelegateImpl implements FailureActivityDelegate {
    final Activity activity;
    final FailureController controller;

    public FailureActivityDelegateImpl(Activity activity) {
        this(activity, new FailureControllerImpl());
    }

    public FailureActivityDelegateImpl(Activity activity, FailureController failureController) {
        this.activity = activity;
        this.controller = failureController;
    }

    @Override // com.digits.sdk.android.FailureActivityDelegate
    public void init() {
        if (isBundleValid(this.activity.getIntent().getExtras())) {
            setContentView();
            setUpViews();
            setErrorText();
            return;
        }
        throw new IllegalAccessError("This activity can only be started from Digits");
    }

    protected boolean isBundleValid(Bundle bundle) {
        return BundleManager.assertContains(bundle, DigitsClient.EXTRA_RESULT_RECEIVER);
    }

    protected void setErrorText() {
        if (getBundleException().getErrorCode() == 269) {
            setErrorText(R.string.dgts__associated_with_twitter_error, R.string.dgts__associated_with_twitter_error_alternative);
        }
    }

    protected void setErrorText(int i, int i2) {
        TextView textView = (TextView) this.activity.findViewById(R.id.dgts__error_title);
        TextView textView2 = (TextView) this.activity.findViewById(R.id.dgts__error_text);
        textView.setText(i);
        textView2.setText(i2);
    }

    protected void setContentView() {
        this.activity.setContentView(R.layout.dgts__activity_failure);
    }

    protected void setUpViews() {
        Button button = (Button) this.activity.findViewById(R.id.dgts__dismiss_button);
        TextView textView = (TextView) this.activity.findViewById(R.id.dgts__try_another_phone);
        setUpDismissButton(button);
        setUpTryAnotherPhoneButton(textView);
    }

    protected void setUpDismissButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() { // from class: com.digits.sdk.android.FailureActivityDelegateImpl.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CommonUtils.finishAffinity(FailureActivityDelegateImpl.this.activity, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                FailureActivityDelegateImpl.this.controller.sendFailure(FailureActivityDelegateImpl.this.getBundleResultReceiver(), FailureActivityDelegateImpl.this.getBundleException());
            }
        });
    }

    protected void setUpTryAnotherPhoneButton(TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.digits.sdk.android.FailureActivityDelegateImpl.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FailureActivityDelegateImpl.this.controller.tryAnotherNumber(FailureActivityDelegateImpl.this.activity, FailureActivityDelegateImpl.this.getBundleResultReceiver());
                FailureActivityDelegateImpl.this.activity.finish();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ResultReceiver getBundleResultReceiver() {
        return (ResultReceiver) this.activity.getIntent().getExtras().getParcelable(DigitsClient.EXTRA_RESULT_RECEIVER);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DigitsException getBundleException() {
        return (DigitsException) this.activity.getIntent().getExtras().getSerializable(DigitsClient.EXTRA_FALLBACK_REASON);
    }
}
