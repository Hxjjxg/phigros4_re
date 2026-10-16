package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.query.Query;

/* JADX INFO: loaded from: classes.dex */
final class zzag extends zzar {
    private final /* synthetic */ Query zzds;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzag(zzaf zzafVar, GoogleApiClient googleApiClient, Query query) {
        super(googleApiClient);
        this.zzds = query;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo) ((zzaw) anyClient).getService()).zza(new zzgk(this.zzds), new zzas(this));
    }
}
