package com.google.android.gms.internal.nearby;

import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;

/* JADX INFO: loaded from: classes.dex */
final class zzaf extends zzau<ConnectionLifecycleCallback> {
    private final /* synthetic */ String zzbm;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaf(zzz zzzVar, String str) {
        super();
        this.zzbm = str;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((ConnectionLifecycleCallback) obj).onDisconnected(this.zzbm);
    }
}
