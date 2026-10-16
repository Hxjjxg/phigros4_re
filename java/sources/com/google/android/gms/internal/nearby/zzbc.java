package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.nearby.connection.Connections;

/* JADX INFO: loaded from: classes.dex */
final class zzbc extends zzed {
    private final BaseImplementation.ResultHolder<Connections.StartAdvertisingResult> zzcb;

    zzbc(BaseImplementation.ResultHolder<Connections.StartAdvertisingResult> resultHolder) {
        this.zzcb = (BaseImplementation.ResultHolder) Preconditions.checkNotNull(resultHolder);
    }

    @Override // com.google.android.gms.internal.nearby.zzec
    public final void zza(zzez zzezVar) {
        Status statusZza = zzx.zza(zzezVar.getStatusCode());
        if (statusZza.isSuccess()) {
            this.zzcb.setResult(new zzbb(statusZza, zzezVar.getLocalEndpointName()));
        } else {
            this.zzcb.setFailedResult(statusZza);
        }
    }
}
