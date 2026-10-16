package com.tapsdk.antiaddictionui.utils;

import android.app.Activity;
import com.tapsdk.antiaddiction.utils.Utils;
import com.tapsdk.antiaddictionui.widget.AntiToastManager;

/* JADX INFO: loaded from: classes.dex */
public class ToastUtils {
    public static void safeToast(Activity activity, String str) {
        if (ActivityUtils.isActivityAlive(activity)) {
            AntiToastManager.instance().show(activity, str, 0);
        }
    }

    public static void safeToastError(Activity activity, Throwable th) {
        if (ActivityUtils.isActivityAlive(activity)) {
            AntiToastManager.instance().show(activity, Utils.parseErrorMessage(th), 0);
        }
    }
}
