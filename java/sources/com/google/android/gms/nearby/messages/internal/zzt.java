package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class zzt extends com.google.android.gms.internal.nearby.zza implements zzs {
    zzt(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.nearby.messages.internal.INearbyMessagesService");
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzs
    public final void zza(SubscribeRequest subscribeRequest) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.nearby.zzc.zza(parcelObtainAndWriteInterfaceToken, subscribeRequest);
        transactOneway(3, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzs
    public final void zza(zzbz zzbzVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.nearby.zzc.zza(parcelObtainAndWriteInterfaceToken, zzbzVar);
        transactOneway(1, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzs
    public final void zza(zzcb zzcbVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.nearby.zzc.zza(parcelObtainAndWriteInterfaceToken, zzcbVar);
        transactOneway(8, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzs
    public final void zza(zzce zzceVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.nearby.zzc.zza(parcelObtainAndWriteInterfaceToken, zzceVar);
        transactOneway(2, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzs
    public final void zza(zzcg zzcgVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.nearby.zzc.zza(parcelObtainAndWriteInterfaceToken, zzcgVar);
        transactOneway(4, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzs
    public final void zza(zzh zzhVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.nearby.zzc.zza(parcelObtainAndWriteInterfaceToken, zzhVar);
        transactOneway(7, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzs
    public final void zza(zzj zzjVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        com.google.android.gms.internal.nearby.zzc.zza(parcelObtainAndWriteInterfaceToken, zzjVar);
        transactOneway(9, parcelObtainAndWriteInterfaceToken);
    }
}
