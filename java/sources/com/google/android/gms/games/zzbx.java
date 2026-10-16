package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzbx extends com.google.android.gms.internal.games.zzah<Void> {
    private final /* synthetic */ Snapshot zzef;

    zzbx(SnapshotsClient snapshotsClient, Snapshot snapshot) {
        this.zzef = snapshot;
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        zzeVar.zza(this.zzef);
        taskCompletionSource.setResult(null);
    }
}
