package com.digits.sdk.android;

import android.os.Bundle;
import androidx.appcompat.app.ActionBarActivity;

/* JADX INFO: loaded from: classes.dex */
public class FailureActionBarActivity extends ActionBarActivity {
    FailureActivityDelegateImpl delegate;

    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle bundle) {
        setTheme(Digits.getInstance().getTheme());
        super.onCreate(bundle);
        FailureActivityDelegateImpl failureActivityDelegateImpl = new FailureActivityDelegateImpl(this);
        this.delegate = failureActivityDelegateImpl;
        failureActivityDelegateImpl.init();
    }
}
