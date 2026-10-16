package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
public final class zzhh extends zzhb<MetadataBuffer> {
    public zzhh(TaskCompletionSource<MetadataBuffer> taskCompletionSource) {
        super(taskCompletionSource);
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(zzfn zzfnVar) throws RemoteException {
        zzap().setResult(new MetadataBuffer(zzfnVar.zzal()));
    }
}
