package com.google.android.gms.internal.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: loaded from: classes.dex */
final class zzbr extends zzbx {
    private final /* synthetic */ boolean zzjg;
    private final /* synthetic */ int zzjl;
    private final /* synthetic */ int[] zzjv;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbr(zzbo zzboVar, GoogleApiClient googleApiClient, int[] iArr, int i, boolean z) {
        super(googleApiClient, null);
        this.zzjv = iArr;
        this.zzjl = i;
        this.zzjg = z;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((com.google.android.gms.games.internal.zze) anyClient).zza(this, this.zzjv, this.zzjl, this.zzjg);
    }
}
