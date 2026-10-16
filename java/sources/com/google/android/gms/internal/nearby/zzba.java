package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: loaded from: classes.dex */
final class zzba extends zzea {
    private final BaseImplementation.ResultHolder<Status> zzcb;

    zzba(BaseImplementation.ResultHolder<Status> resultHolder) {
        this.zzcb = (BaseImplementation.ResultHolder) Preconditions.checkNotNull(resultHolder);
    }

    @Override // com.google.android.gms.internal.nearby.zzdz
    public final void zzc(int i) {
        Status statusZza = zzx.zza(i);
        if (statusZza.isSuccess()) {
            this.zzcb.setResult(statusZza);
        } else {
            this.zzcb.setFailedResult(statusZza);
        }
    }
}
