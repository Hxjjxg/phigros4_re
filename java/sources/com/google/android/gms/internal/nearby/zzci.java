package com.google.android.gms.internal.nearby;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.Strategy;

/* JADX INFO: loaded from: classes.dex */
final class zzci extends zzcy {
    private final /* synthetic */ String zzcn;
    private final /* synthetic */ long zzcy;
    private final /* synthetic */ ListenerHolder zzda;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzci(zzca zzcaVar, GoogleApiClient googleApiClient, String str, long j, ListenerHolder listenerHolder) {
        super(googleApiClient, null);
        this.zzcn = str;
        this.zzcy = j;
        this.zzda = listenerHolder;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        String str = this.zzcn;
        long j = this.zzcy;
        ListenerHolder listenerHolder = this.zzda;
        ((zzdu) ((zzx) anyClient).getService()).zza(new zzge().zzf(new zzba(this)).zzk(str).zze(j).zza(new zzao(listenerHolder)).zze(new DiscoveryOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build()).zzw());
    }
}
