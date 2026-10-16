package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.connection.Connections;

/* JADX INFO: loaded from: classes.dex */
final class zzbb implements Connections.StartAdvertisingResult {
    private final String zzcc;
    private final Status zzt;

    zzbb(Status status, String str) {
        this.zzt = status;
        this.zzcc = str;
    }

    @Override // com.google.android.gms.nearby.connection.Connections.StartAdvertisingResult
    public final String getLocalEndpointName() {
        return this.zzcc;
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zzt;
    }
}
