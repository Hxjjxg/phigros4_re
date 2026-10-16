package com.google.android.gms.internal.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: loaded from: classes.dex */
final class zzy extends zzab {
    private final /* synthetic */ String zzk;
    private final /* synthetic */ int zzl;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzy(zzv zzvVar, GoogleApiClient googleApiClient, String str, int i) {
        super(googleApiClient, null);
        this.zzk = str;
        this.zzl = i;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((com.google.android.gms.games.internal.zze) anyClient).zza(this.zzk, this.zzl);
    }
}
