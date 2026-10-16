package com.google.android.gms.internal.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: loaded from: classes.dex */
final class zzbp extends zzbt {
    private final /* synthetic */ String zzjt;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbp(zzbo zzboVar, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient, null);
        this.zzjt = str;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((com.google.android.gms.games.internal.zze) anyClient).zzh(this, this.zzjt);
    }
}
