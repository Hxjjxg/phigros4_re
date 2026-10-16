package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class zzds extends zzav {
    private final /* synthetic */ List zzfz;
    private final /* synthetic */ zzdp zzgo;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzds(zzdp zzdpVar, GoogleApiClient googleApiClient, List list) {
        super(googleApiClient);
        this.zzgo = zzdpVar;
        this.zzfz = list;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo) ((zzaw) anyClient).getService()).zza(new zzgq(this.zzgo.zzk, this.zzfz), new zzgs(this));
    }
}
