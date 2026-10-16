package com.digits.sdk.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

/* JADX INFO: loaded from: classes.dex */
class FailureControllerImpl implements FailureController {
    final ActivityClassManager classManager;

    int getFlags() {
        return 268468224;
    }

    public FailureControllerImpl() {
        this(Digits.getInstance().getActivityClassManager());
    }

    public FailureControllerImpl(ActivityClassManager activityClassManager) {
        this.classManager = activityClassManager;
    }

    @Override // com.digits.sdk.android.FailureController
    public void tryAnotherNumber(Activity activity, ResultReceiver resultReceiver) {
        Intent intent = new Intent(activity, this.classManager.getPhoneNumberActivity());
        Bundle bundle = new Bundle();
        bundle.putParcelable(DigitsClient.EXTRA_RESULT_RECEIVER, resultReceiver);
        intent.putExtras(bundle);
        intent.setFlags(getFlags());
        activity.startActivity(intent);
    }

    @Override // com.digits.sdk.android.FailureController
    public void sendFailure(ResultReceiver resultReceiver, Exception exc) {
        Bundle bundle = new Bundle();
        bundle.putString("login_error", exc.getLocalizedMessage());
        resultReceiver.send(400, bundle);
    }
}
