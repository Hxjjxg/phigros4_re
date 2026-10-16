package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class zzdi extends zza implements zzdg {
    zzdi(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.nearby.internal.connection.IConnectionEventListener");
    }

    @Override // com.google.android.gms.internal.nearby.zzdg
    public final void zza(zzep zzepVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzepVar);
        transactOneway(3, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdg
    public final void zza(zzev zzevVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzevVar);
        transactOneway(2, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdg
    public final void zza(zzex zzexVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzexVar);
        transactOneway(4, parcelObtainAndWriteInterfaceToken);
    }
}
