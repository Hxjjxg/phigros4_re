package com.digits.sdk.android;

import android.content.Context;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterException;
import io.fabric.sdk.android.Fabric;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public abstract class DigitsCallback<T> extends Callback<T> {
    private final WeakReference<Context> context;
    final DigitsController controller;

    DigitsCallback(Context context, DigitsController digitsController) {
        this.context = new WeakReference<>(context);
        this.controller = digitsController;
    }

    @Override // com.twitter.sdk.android.core.Callback
    public void failure(TwitterException twitterException) {
        DigitsException digitsExceptionCreate = DigitsException.create(this.controller.getErrors(), twitterException);
        StringBuilder sb = new StringBuilder("HTTP Error: ");
        sb.append(twitterException.getMessage()).append(", API Error: ").append(digitsExceptionCreate.getErrorCode()).append(", User Message: ").append(digitsExceptionCreate.getMessage());
        Fabric.getLogger().e(Digits.TAG, sb.toString());
        this.controller.handleError(this.context.get(), digitsExceptionCreate);
    }
}
