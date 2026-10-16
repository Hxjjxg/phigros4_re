package com.google.android.gms.nearby.messages.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.nearby.Nearby;

/* JADX INFO: loaded from: classes.dex */
abstract class zzbv extends BaseImplementation.ApiMethodImpl<Status, zzah> {
    private final ListenerHolder<BaseImplementation.ResultHolder<Status>> zzir;

    public zzbv(GoogleApiClient googleApiClient) {
        super(Nearby.MESSAGES_API, googleApiClient);
        this.zzir = googleApiClient.registerListener(this);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public /* synthetic */ Result createFailedResult(Status status) {
        return status;
    }

    final ListenerHolder<BaseImplementation.ResultHolder<Status>> zzah() {
        return this.zzir;
    }
}
