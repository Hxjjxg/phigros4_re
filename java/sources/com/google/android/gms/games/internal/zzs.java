package com.google.android.gms.games.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzs<L> extends RegisterListenerMethod<zze, L> {
    protected zzs(ListenerHolder<L> listenerHolder) {
        super(listenerHolder);
    }

    @Override // com.google.android.gms.common.api.internal.RegisterListenerMethod
    protected /* synthetic */ void registerListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        try {
            zzb((zze) anyClient, taskCompletionSource);
        } catch (SecurityException e) {
            taskCompletionSource.trySetException(e);
        }
    }

    protected abstract void zzb(zze zzeVar, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException;
}
