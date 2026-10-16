package com.google.android.gms.internal.nearby;

import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;

/* JADX INFO: loaded from: classes.dex */
final class zzan extends zzau<EndpointDiscoveryCallback> {
    private final /* synthetic */ String zzbm;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzan(zzak zzakVar, String str) {
        super();
        this.zzbm = str;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((EndpointDiscoveryCallback) obj).onEndpointLost(this.zzbm);
    }
}
