package com.google.android.gms.internal.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: loaded from: classes.dex */
final class zzbs extends zzbx {
    private final /* synthetic */ boolean zzjg;
    private final /* synthetic */ String[] zzjw;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbs(zzbo zzboVar, GoogleApiClient googleApiClient, boolean z, String[] strArr) {
        super(googleApiClient, null);
        this.zzjg = z;
        this.zzjw = strArr;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((com.google.android.gms.games.internal.zze) anyClient).zzb(this, this.zzjg, this.zzjw);
    }
}
