package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzn extends com.google.android.gms.internal.games.zzah<Void> {
    private final /* synthetic */ int zzbd;

    zzn(GamesClient gamesClient, int i) {
        this.zzbd = i;
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        zzeVar.zzj(this.zzbd);
        taskCompletionSource.setResult(null);
    }
}
