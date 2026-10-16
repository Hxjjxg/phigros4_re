package com.digits.sdk.android;

/* JADX INFO: loaded from: classes.dex */
public interface AuthCallback {
    void failure(DigitsException digitsException);

    void success(DigitsSession digitsSession, String str);
}
