package com.google.android.gms.internal.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: loaded from: classes.dex */
final class zzcd extends zzce {
    private final /* synthetic */ int zzjl;
    private final /* synthetic */ int zzjy;
    private final /* synthetic */ int zzjz;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcd(zzca zzcaVar, GoogleApiClient googleApiClient, int i, int i2, int i3) {
        super(googleApiClient, null);
        this.zzjy = i;
        this.zzjz = i2;
        this.zzjl = i3;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((com.google.android.gms.games.internal.zze) anyClient).zza(this, this.zzjy, this.zzjz, this.zzjl);
    }
}
