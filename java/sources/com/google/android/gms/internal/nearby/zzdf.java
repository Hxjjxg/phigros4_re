package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class zzdf extends zza implements zzdd {
    zzdf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.nearby.internal.connection.IAdvertisingCallback");
    }

    @Override // com.google.android.gms.internal.nearby.zzdd
    public final void zza(zzej zzejVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzejVar);
        transactOneway(2, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdd
    public final void zza(zzfb zzfbVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzfbVar);
        transactOneway(3, parcelObtainAndWriteInterfaceToken);
    }
}
