package com.google.android.gms.internal.nearby;

import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;

/* JADX INFO: loaded from: classes.dex */
final class zzae extends zzau<ConnectionLifecycleCallback> {
    private final /* synthetic */ String zzbm;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzae(zzz zzzVar, String str) {
        super();
        this.zzbm = str;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((ConnectionLifecycleCallback) obj).onConnectionResult(this.zzbm, new ConnectionResolution(zzx.zza(13)));
    }
}
