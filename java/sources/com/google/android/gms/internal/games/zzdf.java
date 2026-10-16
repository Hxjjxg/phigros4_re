package com.google.android.gms.internal.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: loaded from: classes.dex */
final class zzdf extends zzdo {
    private final /* synthetic */ String zzdj;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdf(zzdb zzdbVar, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient, null);
        this.zzdj = str;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((com.google.android.gms.games.internal.zze) anyClient).zzd(this, this.zzdj);
    }
}
