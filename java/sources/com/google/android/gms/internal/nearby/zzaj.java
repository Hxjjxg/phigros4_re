package com.google.android.gms.internal.nearby;

import com.google.android.gms.nearby.connection.Connections;

/* JADX INFO: loaded from: classes.dex */
final class zzaj extends zzau<Connections.ConnectionResponseCallback> {
    private final /* synthetic */ zzel zzbp;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaj(zzai zzaiVar, zzel zzelVar) {
        super();
        this.zzbp = zzelVar;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((Connections.ConnectionResponseCallback) obj).onConnectionResponse(this.zzbp.zzg(), zzx.zza(this.zzbp.getStatusCode()), this.zzbp.zzj());
    }
}
