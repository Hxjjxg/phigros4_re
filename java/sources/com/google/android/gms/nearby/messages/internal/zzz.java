package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class zzz extends com.google.android.gms.internal.nearby.zza implements zzx {
    zzz(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.nearby.messages.internal.IStatusCallback");
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzx
    public final void onPermissionChanged(boolean z) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.nearby.zzc.zza(parcelObtainAndWriteInterfaceToken, z);
        transactOneway(1, parcelObtainAndWriteInterfaceToken);
    }
}
