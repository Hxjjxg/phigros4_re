package com.google.android.gms.nearby.messages.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.PublishOptions;

/* JADX INFO: loaded from: classes.dex */
final class zzbl extends zzbv {
    private final /* synthetic */ Message zzil;
    private final /* synthetic */ zzbt zzim;
    private final /* synthetic */ PublishOptions zzin;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbl(zzbi zzbiVar, GoogleApiClient googleApiClient, Message message, zzbt zzbtVar, PublishOptions publishOptions) {
        super(googleApiClient);
        this.zzil = message;
        this.zzim = zzbtVar;
        this.zzin = publishOptions;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzah) anyClient).zza(zzah(), zzaf.zza(this.zzil), this.zzim, this.zzin);
    }
}
