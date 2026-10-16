package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzau extends com.google.android.gms.internal.games.zzah<Player> {
    zzau(PlayersClient playersClient) {
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Player> taskCompletionSource) throws RemoteException {
        taskCompletionSource.setResult(zzeVar.zzr());
    }
}
