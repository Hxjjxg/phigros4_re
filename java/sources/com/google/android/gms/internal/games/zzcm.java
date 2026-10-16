package com.google.android.gms.internal.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.snapshot.SnapshotMetadata;

/* JADX INFO: loaded from: classes.dex */
final class zzcm extends zzcq {
    private final /* synthetic */ SnapshotMetadata zzke;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcm(zzci zzciVar, GoogleApiClient googleApiClient, SnapshotMetadata snapshotMetadata) {
        super(googleApiClient, null);
        this.zzke = snapshotMetadata;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((com.google.android.gms.games.internal.zze) anyClient).zzi(this, this.zzke.getSnapshotId());
    }
}
