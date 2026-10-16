package com.google.android.gms.nearby.messages.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.messages.Message;

/* JADX INFO: loaded from: classes.dex */
final class zzbm extends zzbv {
    private final /* synthetic */ Message zzil;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbm(zzbi zzbiVar, GoogleApiClient googleApiClient, Message message) {
        super(googleApiClient);
        this.zzil = message;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzah) anyClient).zza(zzah(), zzaf.zza(this.zzil));
    }
}
