package com.digits.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.services.common.CommonUtils;
import tds.androidx.recyclerview.widget.ItemTouchHelper;

/* JADX INFO: loaded from: classes.dex */
abstract class DigitsControllerImpl implements DigitsController, TextWatcher {
    public static final int MAX_ERRORS = 5;
    static final long POST_DELAY_MS = 1500;
    final ActivityClassManager activityClassManager;
    final DigitsClient digitsClient;
    final EditText editText;
    private int errorCount = 0;
    final ErrorCodes errors;
    final ResultReceiver resultReceiver;
    final StateButton sendButton;
    final SessionManager<DigitsSession> sessionManager;

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    abstract Uri getTOSUri();

    @Override // com.digits.sdk.android.DigitsController
    public TextWatcher getTextWatcher() {
        return this;
    }

    DigitsControllerImpl(ResultReceiver resultReceiver, StateButton stateButton, EditText editText, DigitsClient digitsClient, ErrorCodes errorCodes, ActivityClassManager activityClassManager, SessionManager<DigitsSession> sessionManager) {
        this.resultReceiver = resultReceiver;
        this.digitsClient = digitsClient;
        this.activityClassManager = activityClassManager;
        this.sendButton = stateButton;
        this.editText = editText;
        this.errors = errorCodes;
        this.sessionManager = sessionManager;
    }

    @Override // com.digits.sdk.android.DigitsController
    public void showTOS(Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(getTOSUri());
        context.startActivity(intent);
    }

    @Override // com.digits.sdk.android.DigitsController
    public void handleError(Context context, DigitsException digitsException) {
        this.errorCount++;
        if (isUnrecoverable(digitsException)) {
            startFallback(context, this.resultReceiver, digitsException);
        } else {
            this.editText.setError(digitsException.getLocalizedMessage());
            this.sendButton.showError();
        }
    }

    private boolean isUnrecoverable(DigitsException digitsException) {
        return this.errorCount == 5 || (digitsException instanceof UnrecoverableException);
    }

    void startActivityForResult(Activity activity, Intent intent) {
        activity.startActivityForResult(intent, TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE);
    }

    @Override // com.digits.sdk.android.DigitsController
    public void startFallback(Context context, ResultReceiver resultReceiver, DigitsException digitsException) {
        Intent intent = new Intent(context, this.activityClassManager.getFailureActivity());
        intent.putExtra(DigitsClient.EXTRA_RESULT_RECEIVER, resultReceiver);
        intent.putExtra(DigitsClient.EXTRA_FALLBACK_REASON, digitsException);
        context.startActivity(intent);
        CommonUtils.finishAffinity(context, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
    }

    @Override // com.digits.sdk.android.DigitsController
    public boolean validateInput(CharSequence charSequence) {
        return !TextUtils.isEmpty(charSequence);
    }

    @Override // com.digits.sdk.android.DigitsController
    public void clearError() {
        this.editText.setError(null);
    }

    @Override // com.digits.sdk.android.DigitsController
    public ErrorCodes getErrors() {
        return this.errors;
    }

    @Override // com.digits.sdk.android.DigitsController
    public int getErrorCount() {
        return this.errorCount;
    }

    @Override // com.digits.sdk.android.DigitsController
    public void onResume() {
        this.sendButton.showStart();
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        clearError();
    }

    Bundle getBundle(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(DigitsClient.EXTRA_PHONE, str);
        return bundle;
    }

    void loginSuccess(final Context context, DigitsSession digitsSession, final String str) {
        this.sessionManager.setActiveSession(digitsSession);
        this.sendButton.showFinish();
        this.editText.postDelayed(new Runnable() { // from class: com.digits.sdk.android.DigitsControllerImpl.1
            @Override // java.lang.Runnable
            public void run() {
                DigitsControllerImpl.this.resultReceiver.send(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, DigitsControllerImpl.this.getBundle(str));
                CommonUtils.finishAffinity((Activity) context, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
            }
        }, POST_DELAY_MS);
    }
}
