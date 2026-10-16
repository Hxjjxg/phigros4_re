package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.nearby.connection.Connections;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
final class zzar extends zzdh {
    private final ListenerHolder<Connections.MessageListener> zzbt;

    zzar(ListenerHolder<Connections.MessageListener> listenerHolder) {
        this.zzbt = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
    }

    @Override // com.google.android.gms.internal.nearby.zzdg
    public final void zza(zzep zzepVar) {
        this.zzbt.notifyListener(new zzat(this, zzepVar));
    }

    @Override // com.google.android.gms.internal.nearby.zzdg
    public final void zza(zzev zzevVar) {
        this.zzbt.notifyListener(new zzas(this, zzevVar));
    }

    @Override // com.google.android.gms.internal.nearby.zzdg
    public final void zza(zzex zzexVar) {
    }
}
