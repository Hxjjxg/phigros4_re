package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
public final class zzhj extends zzhb<Metadata> {
    public zzhj(TaskCompletionSource<Metadata> taskCompletionSource) {
        super(taskCompletionSource);
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(zzfs zzfsVar) throws RemoteException {
        zzap().setResult(new zzaa(zzfsVar.zzan()));
    }
}
