package com.unity3d.player;

import android.os.Build;
import java.util.Arrays;

/* JADX INFO: renamed from: com.unity3d.player.g0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0040g0 implements Thread.UncaughtExceptionHandler {
    private volatile Thread.UncaughtExceptionHandler a;
    private String b;

    C0040g0() {
    }

    static void a(String str) {
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler instanceof C0040g0) {
            C0040g0 c0040g0 = (C0040g0) defaultUncaughtExceptionHandler;
            int i = -1;
            int i2 = -1;
            while (true) {
                int iIndexOf = str.indexOf(47, i + 1);
                if (iIndexOf == -1) {
                    break;
                }
                i2 = i;
                i = iIndexOf;
            }
            c0040g0.b = i2 < 0 ? "Unknown" : str.substring(i2 + 1);
        }
    }

    final synchronized void a() {
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler == this) {
            return;
        }
        this.a = defaultUncaughtExceptionHandler;
        this.b = "Unknown";
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final synchronized void uncaughtException(Thread thread, Throwable th) {
        try {
            Error error = new Error(String.format("FATAL EXCEPTION [%s]\n", thread.getName()) + String.format("Unity version     : %s\n", "2022.3.62f2") + String.format("Device model      : %s %s\n", Build.MANUFACTURER, Build.MODEL) + String.format("Device fingerprint: %s\n", Build.FINGERPRINT) + String.format("CPU supported ABI : %s\n", Arrays.toString(Build.SUPPORTED_ABIS)) + String.format("Build Type        : %s\n", "Release") + String.format("Scripting Backend : %s\n", "IL2CPP") + String.format("Libs loaded from  : %s\n", this.b) + String.format("Strip Engine Code : %s\n", Boolean.TRUE));
            error.setStackTrace(new StackTraceElement[0]);
            error.initCause(th);
            this.a.uncaughtException(thread, error);
        } catch (Throwable unused) {
            this.a.uncaughtException(thread, th);
        }
    }
}
