package com.google.android.gms.internal.nearby;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolder;

/* JADX INFO: loaded from: classes.dex */
final class zzcj extends zzcy {
    private final /* synthetic */ String val$name;
    private final /* synthetic */ String zzcv;
    private final /* synthetic */ byte[] zzdb;
    private final /* synthetic */ ListenerHolder zzdc;
    private final /* synthetic */ ListenerHolder zzdd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcj(zzca zzcaVar, GoogleApiClient googleApiClient, String str, String str2, byte[] bArr, ListenerHolder listenerHolder, ListenerHolder listenerHolder2) {
        super(googleApiClient, null);
        this.val$name = str;
        this.zzcv = str2;
        this.zzdb = bArr;
        this.zzdc = listenerHolder;
        this.zzdd = listenerHolder2;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        String str = this.val$name;
        String str2 = this.zzcv;
        byte[] bArr = this.zzdb;
        ListenerHolder listenerHolder = this.zzdc;
        ((zzdu) ((zzx) anyClient).getService()).zza(new zzfs().zzd(new zzba(this)).zzg(str).zzh(str2).zzc(bArr).zzb(new zzar(this.zzdd)).zza(new zzai(listenerHolder)).zzt());
    }
}
