package com.google.android.gms.common.util;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;

/* JADX INFO: loaded from: classes.dex */
public final class zza {
    private static long zzgt;
    private static final IntentFilter filter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static float zzgu = Float.NaN;

    /* JADX WARN: Multi-variable type inference failed */
    public static int zzg(Context context) {
        if (context == null || context.getApplicationContext() == null) {
            return -1;
        }
        Intent intentRegisterReceiver = context.getApplicationContext().registerReceiver(null, filter);
        int i = ((intentRegisterReceiver == null ? 0 : intentRegisterReceiver.getIntExtra("plugged", 0)) & 7) != 0 ? 1 : 0;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager == null) {
            return -1;
        }
        return ((PlatformVersion.isAtLeastKitKatWatch() ? powerManager.isInteractive() : powerManager.isScreenOn()) << 1) | i;
    }

    public static synchronized float zzh(Context context) {
        if (SystemClock.elapsedRealtime() - zzgt < 60000 && !Float.isNaN(zzgu)) {
            return zzgu;
        }
        Intent intentRegisterReceiver = context.getApplicationContext().registerReceiver(null, filter);
        if (intentRegisterReceiver != null) {
            zzgu = intentRegisterReceiver.getIntExtra("level", -1) / intentRegisterReceiver.getIntExtra("scale", -1);
        }
        zzgt = SystemClock.elapsedRealtime();
        return zzgu;
    }
}
