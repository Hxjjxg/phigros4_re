package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.connection.Connections;

/* JADX INFO: loaded from: classes.dex */
final class zzcx implements Connections.StartAdvertisingResult {
    private final /* synthetic */ Status zzbj;

    zzcx(zzcw zzcwVar, Status status) {
        this.zzbj = status;
    }

    @Override // com.google.android.gms.nearby.connection.Connections.StartAdvertisingResult
    public final String getLocalEndpointName() {
        return null;
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zzbj;
    }
}
