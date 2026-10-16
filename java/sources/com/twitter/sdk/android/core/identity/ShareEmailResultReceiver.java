package com.twitter.sdk.android.core.identity;

import android.os.Bundle;
import android.os.ResultReceiver;
import androidx.core.app.NotificationCompat;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

/* JADX INFO: loaded from: classes.dex */
class ShareEmailResultReceiver extends ResultReceiver {
    private final Callback<String> callback;

    public ShareEmailResultReceiver(Callback<String> callback) {
        super(null);
        if (callback == null) {
            throw new IllegalArgumentException("Callback must not be null");
        }
        this.callback = callback;
    }

    @Override // android.os.ResultReceiver
    public void onReceiveResult(int i, Bundle bundle) {
        if (i == -1) {
            this.callback.success(new Result<>(bundle.getString("email"), null));
        } else if (i == 0) {
            this.callback.failure(new TwitterException(bundle.getString(NotificationCompat.CATEGORY_MESSAGE)));
        } else {
            if (i == 1) {
                this.callback.failure((TwitterException) bundle.getSerializable("error"));
                return;
            }
            throw new IllegalArgumentException("Invalid result code " + i);
        }
    }
}
