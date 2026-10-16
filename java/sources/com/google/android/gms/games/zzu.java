package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzu extends com.google.android.gms.internal.games.zzah<Game> {
    zzu(GamesMetadataClient gamesMetadataClient) {
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Game> taskCompletionSource) throws RemoteException {
        taskCompletionSource.setResult(zzeVar.zzt());
    }
}
