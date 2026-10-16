package com.google.android.gms.internal.nearby;

import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;

/* JADX INFO: loaded from: classes.dex */
final class zzal extends zzau<EndpointDiscoveryCallback> {
    private final /* synthetic */ zzer zzbr;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzal(zzak zzakVar, zzer zzerVar) {
        super();
        this.zzbr = zzerVar;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        String strZze;
        DiscoveredEndpointInfo discoveredEndpointInfo;
        EndpointDiscoveryCallback endpointDiscoveryCallback = (EndpointDiscoveryCallback) obj;
        if ("__UNRECOGNIZED_BLUETOOTH_DEVICE__".equals(this.zzbr.zze())) {
            strZze = this.zzbr.zze();
            discoveredEndpointInfo = new DiscoveredEndpointInfo(this.zzbr.getServiceId(), this.zzbr.zzk());
        } else {
            strZze = this.zzbr.zze();
            discoveredEndpointInfo = new DiscoveredEndpointInfo(this.zzbr.getServiceId(), this.zzbr.getEndpointName());
        }
        endpointDiscoveryCallback.onEndpointFound(strZze, discoveredEndpointInfo);
    }
}
