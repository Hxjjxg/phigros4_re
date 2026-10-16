package com.google.android.gms.internal.nearby;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzbq extends RegisterListenerMethod<zzx, EndpointDiscoveryCallback> {
    private final /* synthetic */ String zzcn;
    private final /* synthetic */ ListenerHolder zzco;
    private final /* synthetic */ zzbd zzcq;
    private final /* synthetic */ DiscoveryOptions zzcr;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbq(zzbd zzbdVar, ListenerHolder listenerHolder, String str, ListenerHolder listenerHolder2, DiscoveryOptions discoveryOptions) {
        super(listenerHolder);
        this.zzcq = zzbdVar;
        this.zzcn = str;
        this.zzco = listenerHolder2;
        this.zzcr = discoveryOptions;
    }

    @Override // com.google.android.gms.common.api.internal.RegisterListenerMethod
    protected final /* synthetic */ void registerListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzx) anyClient).zza(new zzby(this.zzcq, taskCompletionSource), this.zzcn, this.zzco, this.zzcr);
    }
}
