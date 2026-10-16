package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzz extends com.google.android.gms.games.internal.zzt<OnInvitationReceivedListener> {
    zzz(InvitationsClient invitationsClient, ListenerHolder.ListenerKey listenerKey) {
        super(listenerKey);
    }

    @Override // com.google.android.gms.games.internal.zzt
    protected final void zzc(com.google.android.gms.games.internal.zze zzeVar, TaskCompletionSource<Boolean> taskCompletionSource) throws RemoteException, SecurityException {
        zzeVar.zzaa();
        taskCompletionSource.setResult(true);
    }
}
