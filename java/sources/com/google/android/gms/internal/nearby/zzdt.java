package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class zzdt extends zza implements zzdr {
    zzdt(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.nearby.internal.connection.IDiscoveryListener");
    }

    @Override // com.google.android.gms.internal.nearby.zzdr
    public final void zza(zzer zzerVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzerVar);
        transactOneway(2, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdr
    public final void zza(zzet zzetVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzetVar);
        transactOneway(3, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdr
    public final void zza(zzfd zzfdVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzfdVar);
        transactOneway(4, parcelObtainAndWriteInterfaceToken);
    }
}
