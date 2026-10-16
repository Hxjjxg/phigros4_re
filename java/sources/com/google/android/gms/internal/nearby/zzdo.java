package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class zzdo extends zza implements zzdm {
    zzdo(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.nearby.internal.connection.IConnectionResponseListener");
    }

    @Override // com.google.android.gms.internal.nearby.zzdm
    public final void zza(zzel zzelVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzelVar);
        transactOneway(2, parcelObtainAndWriteInterfaceToken);
    }
}
