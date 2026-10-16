package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzf extends com.google.android.gms.internal.games.zzah<Void> {
    private final /* synthetic */ String zzk;
    private final /* synthetic */ int zzl;

    zzf(EventsClient eventsClient, String str, int i) {
        this.zzk = str;
        this.zzl = i;
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        zzeVar.zza(this.zzk, this.zzl);
    }
}
