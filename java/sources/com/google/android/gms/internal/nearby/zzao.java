package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.nearby.connection.Connections;

/* JADX INFO: loaded from: classes.dex */
final class zzao extends zzds {
    private final ListenerHolder<Connections.EndpointDiscoveryListener> zzbe;

    zzao(ListenerHolder<Connections.EndpointDiscoveryListener> listenerHolder) {
        this.zzbe = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
    }

    @Override // com.google.android.gms.internal.nearby.zzdr
    public final void zza(zzer zzerVar) {
        this.zzbe.notifyListener(new zzap(this, zzerVar));
    }

    @Override // com.google.android.gms.internal.nearby.zzdr
    public final void zza(zzet zzetVar) {
        this.zzbe.notifyListener(new zzaq(this, zzetVar));
    }

    @Override // com.google.android.gms.internal.nearby.zzdr
    public final void zza(zzfd zzfdVar) {
    }
}
