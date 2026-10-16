package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzao extends com.google.android.gms.internal.games.zzah<Void> {
    private final /* synthetic */ int zzbx;

    zzao(NotificationsClient notificationsClient, int i) {
        this.zzbx = i;
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        zzeVar.zzk(this.zzbx);
        taskCompletionSource.setResult(null);
    }
}
