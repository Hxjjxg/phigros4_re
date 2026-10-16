package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import io.fabric.sdk.android.services.concurrency.PriorityRunnable;

/* JADX INFO: loaded from: classes.dex */
public class SafeToast extends Toast {
    public SafeToast(Context context) {
        super(context);
    }

    @Override // android.widget.Toast
    public void show() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.show();
        } else {
            new Handler(Looper.getMainLooper()).post(new PriorityRunnable() { // from class: io.fabric.sdk.android.services.common.SafeToast.1
                @Override // java.lang.Runnable
                public void run() {
                    SafeToast.super.show();
                }
            });
        }
    }

    public static Toast makeText(Context context, CharSequence charSequence, int i) {
        Toast toastMakeText = Toast.makeText(context, charSequence, i);
        SafeToast safeToast = new SafeToast(context);
        safeToast.setView(toastMakeText.getView());
        safeToast.setDuration(toastMakeText.getDuration());
        return safeToast;
    }

    public static Toast makeText(Context context, int i, int i2) throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(i), i2);
    }
}
