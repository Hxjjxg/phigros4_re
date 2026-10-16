package com.tapsdk.antiaddiction.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class AntiAddictionLogger {
    private static final String TAG = "AntiAddiction";
    private static boolean debuggable = false;

    public static void enableDebug(boolean z) {
        debuggable = z;
    }

    public static void d(String str) {
        if (debuggable) {
            Log.d(TAG, "debug ----- message: " + str);
        }
    }

    public static void e(String str) {
        Log.e(TAG, "error ----- message: " + str);
    }

    public static void w(String str) {
        Log.w(TAG, "warning ----- message: " + str);
    }

    public static void printStackTrace(Exception exc, boolean z) {
        if (exc != null) {
            if (z) {
                w(exc.getMessage());
            } else {
                e(exc.getMessage());
            }
        }
    }

    public static void printStackTrace(Exception exc) {
        if (exc != null) {
            printStackTrace(exc, false);
        }
    }

    public static void printStackTrace(Throwable th) {
        if (th != null) {
            printStackTrace(new Exception(th));
        }
    }
}
