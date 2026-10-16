package com.google.android.gms.internal.nearby;

import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;

/* JADX INFO: loaded from: classes.dex */
final class zzaa extends zzau<ConnectionLifecycleCallback> {
    private final /* synthetic */ zzeh zzbh;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaa(zzz zzzVar, zzeh zzehVar) {
        super();
        this.zzbh = zzehVar;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((ConnectionLifecycleCallback) obj).onConnectionInitiated(this.zzbh.zzg(), new ConnectionInfo(this.zzbh.zzh(), this.zzbh.getAuthenticationToken(), this.zzbh.zzi()));
    }
}
