package com.google.android.gms.games;

import android.content.Intent;
import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzai extends com.google.android.gms.internal.games.zzah<Intent> {
    private final /* synthetic */ String zzbq;
    private final /* synthetic */ int zzbr;
    private final /* synthetic */ int zzbs;

    zzai(LeaderboardsClient leaderboardsClient, String str, int i, int i2) {
        this.zzbq = str;
        this.zzbr = i;
        this.zzbs = i2;
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Intent> taskCompletionSource) throws RemoteException {
        taskCompletionSource.setResult(zzeVar.zza(this.zzbq, this.zzbr, this.zzbs));
    }
}
