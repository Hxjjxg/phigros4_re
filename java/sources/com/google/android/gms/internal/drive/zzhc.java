package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
public final class zzhc extends zzhb<DriveContents> {
    public zzhc(TaskCompletionSource<DriveContents> taskCompletionSource) {
        super(taskCompletionSource);
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(zzfb zzfbVar) throws RemoteException {
        zzap().setResult(new zzbi(zzfbVar.zzai()));
    }
}
