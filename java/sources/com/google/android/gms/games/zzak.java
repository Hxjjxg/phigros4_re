package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzak extends com.google.android.gms.internal.games.zzah<Void> {
    private final /* synthetic */ String zzbq;
    private final /* synthetic */ long zzbt;
    private final /* synthetic */ String zzbu;

    zzak(LeaderboardsClient leaderboardsClient, String str, long j, String str2) {
        this.zzbq = str;
        this.zzbt = j;
        this.zzbu = str2;
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        zzeVar.zza((BaseImplementation.ResultHolder<Leaderboards.SubmitScoreResult>) null, this.zzbq, this.zzbt, this.zzbu);
    }
}
