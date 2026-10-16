package com.google.android.gms.internal.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: loaded from: classes.dex */
final class zzap extends zzaw {
    private final /* synthetic */ String zzbq;
    private final /* synthetic */ int zzjm;
    private final /* synthetic */ int zzjn;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzap(zzam zzamVar, GoogleApiClient googleApiClient, String str, int i, int i2) {
        super(googleApiClient, null);
        this.zzbq = str;
        this.zzjm = i;
        this.zzjn = i2;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((com.google.android.gms.games.internal.zze) anyClient).zza(this, (String) null, this.zzbq, this.zzjm, this.zzjn);
    }
}
