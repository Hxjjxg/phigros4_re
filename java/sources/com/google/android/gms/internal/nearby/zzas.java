package com.google.android.gms.internal.nearby;

import android.util.Log;
import com.google.android.gms.nearby.connection.Connections;
import com.google.android.gms.nearby.connection.Payload;

/* JADX INFO: loaded from: classes.dex */
final class zzas extends zzau<Connections.MessageListener> {
    private final /* synthetic */ zzev zzbu;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzas(zzar zzarVar, zzev zzevVar) {
        super();
        this.zzbu = zzevVar;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        Connections.MessageListener messageListener = (Connections.MessageListener) obj;
        Payload payloadZza = zzfl.zza(this.zzbu.zzl());
        if (payloadZza == null) {
            Log.w("NearbyConnectionsClient", String.format("Failed to convert incoming ParcelablePayload %d to Payload.", Long.valueOf(this.zzbu.zzl().getId())));
        } else if (payloadZza.getType() == 1) {
            messageListener.onMessageReceived(this.zzbu.zzg(), payloadZza.asBytes(), this.zzbu.zzm());
        }
    }
}
