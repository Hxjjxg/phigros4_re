package com.google.android.gms.internal.nearby;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.nearby.connection.Payload;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class zzcd extends zzcy {
    private final /* synthetic */ Payload zzbx;
    private final /* synthetic */ List zzcw;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcd(zzca zzcaVar, GoogleApiClient googleApiClient, List list, Payload payload) {
        super(googleApiClient, null);
        this.zzcw = list;
        this.zzbx = payload;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzx) anyClient).zza((BaseImplementation.ResultHolder<Status>) this, (String[]) this.zzcw.toArray(new String[0]), this.zzbx, false);
    }
}
