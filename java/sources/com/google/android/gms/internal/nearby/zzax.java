package com.google.android.gms.internal.nearby;

import com.google.android.gms.nearby.connection.PayloadCallback;

/* JADX INFO: loaded from: classes.dex */
final class zzax extends zzau<PayloadCallback> {
    private final /* synthetic */ zzex zzby;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzax(zzav zzavVar, zzex zzexVar) {
        super();
        this.zzby = zzexVar;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((PayloadCallback) obj).onPayloadTransferUpdate(this.zzby.zzg(), this.zzby.zzn());
    }
}
