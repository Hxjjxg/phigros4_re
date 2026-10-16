package com.google.android.gms.games;

import android.content.Intent;
import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzbi extends com.google.android.gms.internal.games.zzah<Intent> {
    private final /* synthetic */ int zzdl;
    private final /* synthetic */ int zzdm;
    private final /* synthetic */ boolean zzdn;

    zzbi(RealTimeMultiplayerClient realTimeMultiplayerClient, int i, int i2, boolean z) {
        this.zzdl = i;
        this.zzdm = i2;
        this.zzdn = z;
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Intent> taskCompletionSource) throws RemoteException {
        taskCompletionSource.setResult(zzeVar.zzc(this.zzdl, this.zzdm, this.zzdn));
    }
}
