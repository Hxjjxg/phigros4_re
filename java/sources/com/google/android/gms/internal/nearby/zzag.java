package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.nearby.connection.Connections;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
final class zzag extends zzde {
    private final ListenerHolder<Connections.ConnectionRequestListener> zzbe;

    zzag(ListenerHolder<Connections.ConnectionRequestListener> listenerHolder) {
        this.zzbe = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
    }

    @Override // com.google.android.gms.internal.nearby.zzdd
    public final void zza(zzej zzejVar) {
        this.zzbe.notifyListener(new zzah(this, zzejVar));
    }

    @Override // com.google.android.gms.internal.nearby.zzdd
    public final void zza(zzfb zzfbVar) {
    }
}
