package com.digits.sdk.android;

import android.app.Activity;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public interface DigitsActivityDelegate {
    int getLayoutId();

    DigitsController init(Activity activity, Bundle bundle);

    boolean isValid(Bundle bundle);
}
