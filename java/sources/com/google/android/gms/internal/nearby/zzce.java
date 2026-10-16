package com.google.android.gms.internal.nearby;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: loaded from: classes.dex */
final class zzce extends zzcy {
    private final /* synthetic */ long zzcx;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzce(zzca zzcaVar, GoogleApiClient googleApiClient, long j) {
        super(googleApiClient, null);
        this.zzcx = j;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzx) anyClient).zza(this, this.zzcx);
    }
}
