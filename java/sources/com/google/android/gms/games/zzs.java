package com.google.android.gms.games;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzs extends com.google.android.gms.internal.games.zzah<Bundle> {
    zzs(GamesClient gamesClient) {
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Bundle> taskCompletionSource) throws RemoteException {
        taskCompletionSource.setResult(zzeVar.zzo());
    }
}
