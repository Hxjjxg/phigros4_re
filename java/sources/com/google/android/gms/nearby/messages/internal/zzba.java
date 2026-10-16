package com.google.android.gms.nearby.messages.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: loaded from: classes.dex */
final class zzba<T> extends UnregisterListenerMethod<zzah, T> {
    private final /* synthetic */ zzak zzia;
    private final /* synthetic */ zzbd zzie;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzba(zzak zzakVar, ListenerHolder.ListenerKey listenerKey, zzbd zzbdVar) {
        super(listenerKey);
        this.zzia = zzakVar;
        this.zzie = zzbdVar;
    }

    @Override // com.google.android.gms.common.api.internal.UnregisterListenerMethod
    protected final /* synthetic */ void unregisterListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzie.zza((zzah) anyClient, this.zzia.zza(taskCompletionSource));
    }
}
