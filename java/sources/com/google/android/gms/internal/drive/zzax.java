package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: loaded from: classes.dex */
final class zzax extends zzav {
    private final /* synthetic */ zzj zzei;
    private final /* synthetic */ zzee zzej;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzax(zzaw zzawVar, GoogleApiClient googleApiClient, zzj zzjVar, zzee zzeeVar) {
        super(googleApiClient);
        this.zzei = zzjVar;
        this.zzej = zzeeVar;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo) ((zzaw) anyClient).getService()).zza(this.zzei, this.zzej, (String) null, new zzgs(this));
    }
}
