package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class zzee extends zza implements zzec {
    zzee(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.nearby.internal.connection.IStartAdvertisingResultListener");
    }

    @Override // com.google.android.gms.internal.nearby.zzec
    public final void zza(zzez zzezVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzezVar);
        transactOneway(2, parcelObtainAndWriteInterfaceToken);
    }
}
