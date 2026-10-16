package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.TransferPreferences;
import com.google.android.gms.drive.TransferPreferencesBuilder;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
public final class zzhg extends zzhb<TransferPreferences> {
    public zzhg(TaskCompletionSource<TransferPreferences> taskCompletionSource) {
        super(taskCompletionSource);
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(zzfd zzfdVar) throws RemoteException {
        zzap().setResult(new TransferPreferencesBuilder(zzfdVar.zzaj()).build());
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(zzfu zzfuVar) throws RemoteException {
        zzap().setResult(zzfuVar.zzao());
    }
}
