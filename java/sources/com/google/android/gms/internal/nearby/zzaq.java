package com.google.android.gms.internal.nearby;

import com.google.android.gms.nearby.connection.Connections;

/* JADX INFO: loaded from: classes.dex */
final class zzaq extends zzau<Connections.EndpointDiscoveryListener> {
    private final /* synthetic */ zzet zzbs;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaq(zzao zzaoVar, zzet zzetVar) {
        super();
        this.zzbs = zzetVar;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((Connections.EndpointDiscoveryListener) obj).onEndpointLost(this.zzbs.zze());
    }
}
