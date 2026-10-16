package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzt extends com.google.android.gms.internal.games.zzah<Integer> {
    zzt(GamesClient gamesClient) {
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Integer> taskCompletionSource) throws RemoteException {
        taskCompletionSource.setResult(Integer.valueOf(zzeVar.zzak()));
    }
}
