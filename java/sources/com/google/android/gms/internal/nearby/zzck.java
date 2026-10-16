package com.google.android.gms.internal.nearby;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolder;

/* JADX INFO: loaded from: classes.dex */
final class zzck extends zzcy {
    private final /* synthetic */ String zzcv;
    private final /* synthetic */ byte[] zzdb;
    private final /* synthetic */ ListenerHolder zzdd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzck(zzca zzcaVar, GoogleApiClient googleApiClient, String str, byte[] bArr, ListenerHolder listenerHolder) {
        super(googleApiClient, null);
        this.zzcv = str;
        this.zzdb = bArr;
        this.zzdd = listenerHolder;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        String str = this.zzcv;
        ((zzdu) ((zzx) anyClient).getService()).zza(new zzo().zza(new zzba(this)).zza(str).zza(this.zzdb).zza(new zzar(this.zzdd)).zzb());
    }
}
