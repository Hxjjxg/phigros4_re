package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: loaded from: classes.dex */
final class zzbm extends zzav {
    private final /* synthetic */ zzbi zzet;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbm(zzbi zzbiVar, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzet = zzbiVar;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo) ((zzaw) anyClient).getService()).zza(new zzo(this.zzet.zzeq.getRequestId(), false), new zzgs(this));
    }
}
