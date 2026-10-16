package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.games.GamesStatusCodes;

/* JADX INFO: loaded from: classes.dex */
public final class zzdv extends zza implements zzdu {
    zzdv(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
    }

    @Override // com.google.android.gms.internal.nearby.zzdu
    public final void zza(zzcz zzczVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzczVar);
        transactAndReadExceptionReturnVoid(2009, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdu
    public final void zza(zzfm zzfmVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzfmVar);
        transactAndReadExceptionReturnVoid(2007, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdu
    public final void zza(zzfq zzfqVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzfqVar);
        transactAndReadExceptionReturnVoid(2005, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdu
    public final void zza(zzfu zzfuVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzfuVar);
        transactAndReadExceptionReturnVoid(2008, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdu
    public final void zza(zzfy zzfyVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzfyVar);
        transactAndReadExceptionReturnVoid(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdu
    public final void zza(zzgc zzgcVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzgcVar);
        transactAndReadExceptionReturnVoid(2003, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdu
    public final void zza(zzgg zzggVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzggVar);
        transactAndReadExceptionReturnVoid(GamesStatusCodes.STATUS_REQUEST_TOO_MANY_RECIPIENTS, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdu
    public final void zza(zzgj zzgjVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzgjVar);
        transactAndReadExceptionReturnVoid(2010, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdu
    public final void zza(zzgm zzgmVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzgmVar);
        transactAndReadExceptionReturnVoid(2004, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdu
    public final void zza(zzm zzmVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzmVar);
        transactAndReadExceptionReturnVoid(2006, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdu
    public final void zza(zzq zzqVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzqVar);
        transactAndReadExceptionReturnVoid(2012, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.nearby.zzdu
    public final void zza(zzu zzuVar) throws RemoteException {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, zzuVar);
        transactAndReadExceptionReturnVoid(2011, parcelObtainAndWriteInterfaceToken);
    }
}
