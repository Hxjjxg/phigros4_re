package com.digits.sdk.android;

import android.app.Activity;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class FailureActivity extends Activity {
    FailureActivityDelegateImpl delegate;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        setTheme(Digits.getInstance().getTheme());
        super.onCreate(bundle);
        FailureActivityDelegateImpl failureActivityDelegateImpl = new FailureActivityDelegateImpl(this);
        this.delegate = failureActivityDelegateImpl;
        failureActivityDelegateImpl.init();
    }
}
