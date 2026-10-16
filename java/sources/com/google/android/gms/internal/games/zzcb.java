package com.google.android.gms.internal.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: loaded from: classes.dex */
final class zzcb extends zzcg {
    private final /* synthetic */ String[] zzjx;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcb(zzca zzcaVar, GoogleApiClient googleApiClient, String[] strArr) {
        super(googleApiClient, null);
        this.zzjx = strArr;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((com.google.android.gms.games.internal.zze) anyClient).zza(this, this.zzjx);
    }
}
