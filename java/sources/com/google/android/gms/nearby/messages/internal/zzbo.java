package com.google.android.gms.nearby.messages.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.messages.SubscribeOptions;

/* JADX INFO: loaded from: classes.dex */
final class zzbo extends zzbv {
    private final /* synthetic */ zzbw zzio;
    private final /* synthetic */ SubscribeOptions zzip;
    private final /* synthetic */ PendingIntent zziq;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzbo(zzbi zzbiVar, GoogleApiClient googleApiClient, PendingIntent pendingIntent, zzbw zzbwVar, SubscribeOptions subscribeOptions) {
        super(googleApiClient);
        this.zziq = pendingIntent;
        this.zzio = zzbwVar;
        this.zzip = subscribeOptions;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzah) anyClient).zza(zzah(), this.zziq, this.zzio, this.zzip);
    }
}
