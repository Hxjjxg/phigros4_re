package com.google.android.gms.nearby.messages.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolder;

/* JADX INFO: loaded from: classes.dex */
final class zzbs extends zzbv {
    private final /* synthetic */ ListenerHolder zzik;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbs(zzbi zzbiVar, GoogleApiClient googleApiClient, ListenerHolder listenerHolder) {
        super(googleApiClient);
        this.zzik = listenerHolder;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzah) anyClient).zzb(zzah(), this.zzik);
    }
}
