package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzbr extends com.google.android.gms.internal.games.zzah<Integer> {
    private final /* synthetic */ byte[] zzdf;
    private final /* synthetic */ String zzdg;
    private final /* synthetic */ String zzdh;
    private final /* synthetic */ ListenerHolder zzdt;

    zzbr(RealTimeMultiplayerClient realTimeMultiplayerClient, ListenerHolder listenerHolder, byte[] bArr, String str, String str2) {
        this.zzdt = listenerHolder;
        this.zzdf = bArr;
        this.zzdg = str;
        this.zzdh = str2;
    }

    @Override // com.google.android.gms.internal.games.zzah
    protected final void zza(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Integer> taskCompletionSource) throws RemoteException {
        Integer numValueOf = Integer.valueOf(zzeVar.zza(this.zzdt, this.zzdf, this.zzdg, this.zzdh));
        if (numValueOf.intValue() == -1) {
            taskCompletionSource.trySetException(ApiExceptionUtil.fromStatus(GamesClientStatusCodes.zza(GamesClientStatusCodes.REAL_TIME_MESSAGE_SEND_FAILED)));
        } else {
            taskCompletionSource.setResult(numValueOf);
        }
    }
}
