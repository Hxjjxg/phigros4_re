package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzo extends com.google.android.gms.internal.nearby.zza implements zzm {
    zzo(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.nearby.messages.internal.IMessageListener");
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzm
    public final void zza(zzaf zzafVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.nearby.zzc.zza(parcelObtainAndWriteInterfaceToken, zzafVar);
        transactOneway(1, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzm
    public final void zza(List<Update> list) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeTypedList(list);
        transactOneway(4, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzm
    public final void zzb(zzaf zzafVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.nearby.zzc.zza(parcelObtainAndWriteInterfaceToken, zzafVar);
        transactOneway(2, parcelObtainAndWriteInterfaceToken);
    }
}
