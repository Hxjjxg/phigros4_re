package com.google.android.gms.internal.nearby;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolder;

/* JADX INFO: loaded from: classes.dex */
final class zzct extends zzcy {
    private final /* synthetic */ String zzcv;
    private final /* synthetic */ ListenerHolder zzdh;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzct(zzca zzcaVar, GoogleApiClient googleApiClient, String str, ListenerHolder listenerHolder) {
        super(googleApiClient, null);
        this.zzcv = str;
        this.zzdh = listenerHolder;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzx) anyClient).zza(this, this.zzcv, this.zzdh);
    }
}
