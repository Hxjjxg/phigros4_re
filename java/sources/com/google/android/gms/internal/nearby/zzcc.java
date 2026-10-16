package com.google.android.gms.internal.nearby;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.nearby.connection.Payload;

/* JADX INFO: loaded from: classes.dex */
final class zzcc extends zzcy {
    private final /* synthetic */ Payload zzbx;
    private final /* synthetic */ String zzcv;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcc(zzca zzcaVar, GoogleApiClient googleApiClient, String str, Payload payload) {
        super(googleApiClient, null);
        this.zzcv = str;
        this.zzbx = payload;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzx) anyClient).zza((BaseImplementation.ResultHolder<Status>) this, new String[]{this.zzcv}, this.zzbx, false);
    }
}
