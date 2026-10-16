package com.google.android.gms.internal.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: loaded from: classes.dex */
final class zzar extends zzay {
    private final /* synthetic */ String zzbq;
    private final /* synthetic */ boolean zzjg;
    private final /* synthetic */ int zzjm;
    private final /* synthetic */ int zzjn;
    private final /* synthetic */ int zzjo;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzar(zzam zzamVar, GoogleApiClient googleApiClient, String str, int i, int i2, int i3, boolean z) {
        super(googleApiClient, null);
        this.zzbq = str;
        this.zzjm = i;
        this.zzjn = i2;
        this.zzjo = i3;
        this.zzjg = z;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((com.google.android.gms.games.internal.zze) anyClient).zzb(this, this.zzbq, this.zzjm, this.zzjn, this.zzjo, this.zzjg);
    }
}
