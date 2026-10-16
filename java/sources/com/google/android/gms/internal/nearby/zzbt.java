package com.google.android.gms.internal.nearby;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzbt extends RegisterListenerMethod<zzx, String> {
    zzbt(zzbd zzbdVar, ListenerHolder listenerHolder) {
        super(listenerHolder);
    }

    @Override // com.google.android.gms.common.api.internal.RegisterListenerMethod
    protected final /* synthetic */ void registerListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        taskCompletionSource.setResult(null);
    }
}
