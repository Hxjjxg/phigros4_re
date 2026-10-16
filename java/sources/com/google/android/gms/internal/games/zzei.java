package com.google.android.gms.internal.games;

/* JADX INFO: loaded from: classes.dex */
public final class zzei {
    public static String zzn(int i) {
        if (i == 0) {
            return "DAILY";
        }
        if (i == 1) {
            return "WEEKLY";
        }
        if (i == 2) {
            return "ALL_TIME";
        }
        throw new IllegalArgumentException(new StringBuilder(29).append("Unknown time span ").append(i).toString());
    }
}
