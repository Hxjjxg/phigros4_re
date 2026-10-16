package com.google.android.gms.internal.nearby;

import com.google.android.gms.nearby.connection.Connections;

/* JADX INFO: loaded from: classes.dex */
final class zzat extends zzau<Connections.MessageListener> {
    private final /* synthetic */ zzep zzbk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzat(zzar zzarVar, zzep zzepVar) {
        super();
        this.zzbk = zzepVar;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((Connections.MessageListener) obj).onDisconnected(this.zzbk.zzg());
    }
}
