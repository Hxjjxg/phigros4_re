package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class zzeb extends zza implements zzdz {
    zzeb(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.nearby.internal.connection.IResultListener");
    }

    @Override // com.google.android.gms.internal.nearby.zzdz
    public final void zzc(int i) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        transactOneway(2, parcelObtainAndWriteInterfaceToken);
    }
}
