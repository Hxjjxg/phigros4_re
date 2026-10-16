package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzbg extends com.google.android.gms.internal.games.zzah<String> {
    private final /* synthetic */ String zzdg;

    zzbg(RealTimeMultiplayerClient realTimeMultiplayerClient, String str) {
        this.zzdg = str;
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<String> taskCompletionSource) throws RemoteException {
        ((com.google.android.gms.games.internal.zzy) zzeVar.getService()).zza(new zzbh(this, taskCompletionSource), this.zzdg);
    }
}
