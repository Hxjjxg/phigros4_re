package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.connection.Connections;

/* JADX INFO: loaded from: classes.dex */
abstract class zzcw extends zzcv<Connections.StartAdvertisingResult> {
    private zzcw(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzcw(GoogleApiClient googleApiClient, zzcb zzcbVar) {
        this(googleApiClient);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public /* synthetic */ Result createFailedResult(Status status) {
        return new zzcx(this, status);
    }
}
