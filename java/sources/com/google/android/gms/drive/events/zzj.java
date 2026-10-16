package com.google.android.gms.drive.events;

import com.google.android.gms.drive.DriveId;

/* JADX INFO: loaded from: classes.dex */
public final class zzj {
    public static boolean zza(int i, DriveId driveId) {
        if (i != 1) {
            if (i == 4 || i == 7) {
                return driveId == null;
            }
            if (i != 8) {
                return false;
            }
        }
        return driveId != null;
    }
}
