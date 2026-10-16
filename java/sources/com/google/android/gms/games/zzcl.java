package com.google.android.gms.games;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class zzcl implements com.google.android.gms.games.internal.zzr {
    zzcl() {
    }

    @Override // com.google.android.gms.games.internal.zzr
    public final boolean zza(Status status) {
        return status.isSuccess() || status.getStatusCode() == 5;
    }
}
