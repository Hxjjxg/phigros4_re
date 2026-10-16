package com.google.android.gms.internal.nearby;

import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;

/* JADX INFO: loaded from: classes.dex */
final class zzac extends zzau<ConnectionLifecycleCallback> {
    private final /* synthetic */ zzep zzbk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzac(zzz zzzVar, zzep zzepVar) {
        super();
        this.zzbk = zzepVar;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((ConnectionLifecycleCallback) obj).onDisconnected(this.zzbk.zzg());
    }
}
