package com.google.android.gms.internal.nearby;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.nearby.connection.Payload;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class zzcn extends zzcy {
    private final /* synthetic */ List zzcw;
    private final /* synthetic */ byte[] zzde;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcn(zzca zzcaVar, GoogleApiClient googleApiClient, List list, byte[] bArr) {
        super(googleApiClient, null);
        this.zzcw = list;
        this.zzde = bArr;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzx) anyClient).zza((BaseImplementation.ResultHolder<Status>) this, (String[]) this.zzcw.toArray(new String[0]), Payload.fromBytes(this.zzde), true);
    }
}
