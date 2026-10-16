package com.google.android.gms.nearby.messages.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: loaded from: classes.dex */
final class zzaz<T> extends RegisterListenerMethod<zzah, T> {
    private final /* synthetic */ zzak zzia;
    private final /* synthetic */ zzbd zzid;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaz(zzak zzakVar, ListenerHolder listenerHolder, zzbd zzbdVar) {
        super(listenerHolder);
        this.zzia = zzakVar;
        this.zzid = zzbdVar;
    }

    @Override // com.google.android.gms.common.api.internal.RegisterListenerMethod
    protected final /* synthetic */ void registerListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzid.zza((zzah) anyClient, this.zzia.zza(taskCompletionSource));
    }
}
