package com.tapsdk.antiaddictionui.utils;

import android.app.Activity;
import android.os.Build;
import android.os.Process;

/* JADX INFO: loaded from: classes.dex */
public class ActivityUtils {
    public static boolean isActivityNotAlive(Activity activity) {
        return activity == null || activity.isFinishing();
    }

    public static boolean isActivityAlive(Activity activity) {
        return !isActivityNotAlive(activity);
    }

    public static boolean is64BitProcess() {
        return Process.is64Bit();
    }

    public static boolean support64Bit() {
        return Build.SUPPORTED_64_BIT_ABIS.length > 0;
    }

    public static boolean isProcessMatchCore() {
        return is64BitProcess() == support64Bit();
    }
}
