package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
public final class zzr extends com.google.android.gms.internal.nearby.zza implements zzp {
    zzr(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.nearby.messages.internal.INearbyMessagesCallback");
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzp
    public final void zza(Status status) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.nearby.zzc.zza(parcelObtainAndWriteInterfaceToken, status);
        transactOneway(1, parcelObtainAndWriteInterfaceToken);
    }
}
