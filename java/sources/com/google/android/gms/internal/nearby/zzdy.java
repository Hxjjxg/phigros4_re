package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class zzdy extends zza implements zzdw {
    zzdy(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.nearby.internal.connection.IPayloadListener");
    }

    @Override // com.google.android.gms.internal.nearby.zzdw
    public final void zza(zzev zzevVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzevVar);
        transactOneway(2, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdw
    public final void zza(zzex zzexVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzexVar);
        transactOneway(3, parcelObtainAndWriteInterfaceToken);
    }
}
