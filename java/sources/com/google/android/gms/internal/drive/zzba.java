package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveId;

/* JADX INFO: loaded from: classes.dex */
final class zzba extends zzav {
    private final /* synthetic */ DriveId zzel;
    private final /* synthetic */ int zzem = 1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzba(zzaw zzawVar, GoogleApiClient googleApiClient, DriveId driveId, int i) {
        super(googleApiClient);
        this.zzel = driveId;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo) ((zzaw) anyClient).getService()).zza(new zzgm(this.zzel, this.zzem), (zzes) null, (String) null, new zzgs(this));
    }
}
