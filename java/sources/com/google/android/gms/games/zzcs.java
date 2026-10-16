package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzcs extends com.google.android.gms.internal.games.zzah<Void> {
    private final /* synthetic */ String zzdj;

    zzcs(TurnBasedMultiplayerClient turnBasedMultiplayerClient, String str) {
        this.zzdj = str;
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        zzeVar.zzb(this.zzdj, 1);
        taskCompletionSource.setResult(null);
    }
}
