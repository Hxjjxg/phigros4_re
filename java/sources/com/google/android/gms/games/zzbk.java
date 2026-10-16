package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzbk extends com.google.android.gms.games.internal.zzt<com.google.android.gms.games.multiplayer.realtime.zzh> {
    zzbk(RealTimeMultiplayerClient realTimeMultiplayerClient, ListenerHolder.ListenerKey listenerKey) {
        super(listenerKey);
    }

    @Override // com.google.android.gms.games.internal.zzt
    protected final void zzc(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Boolean> taskCompletionSource) throws RemoteException, SecurityException {
        taskCompletionSource.setResult(true);
    }
}
