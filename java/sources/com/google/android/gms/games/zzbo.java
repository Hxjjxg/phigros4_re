package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzbo extends com.google.android.gms.internal.games.zzah<Void> {
    final /* synthetic */ zzbn zzdr;

    zzbo(zzbn zzbnVar) {
        this.zzdr = zzbnVar;
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        this.zzdr.zzdp.notifyListener(new zzbp(this));
        taskCompletionSource.setResult(null);
    }
}
