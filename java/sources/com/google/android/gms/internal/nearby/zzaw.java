package com.google.android.gms.internal.nearby;

import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;

/* JADX INFO: loaded from: classes.dex */
final class zzaw extends zzau<PayloadCallback> {
    private final /* synthetic */ zzev zzbu;
    private final /* synthetic */ Payload zzbx;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaw(zzav zzavVar, zzev zzevVar, Payload payload) {
        super();
        this.zzbu = zzevVar;
        this.zzbx = payload;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((PayloadCallback) obj).onPayloadReceived(this.zzbu.zzg(), this.zzbx);
    }
}
