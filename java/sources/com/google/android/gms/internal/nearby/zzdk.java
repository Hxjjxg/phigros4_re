package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzdk extends zzb implements zzdj {
    public zzdk() {
        super("com.google.android.gms.nearby.internal.connection.IConnectionLifecycleListener");
    }

    @Override // com.google.android.gms.internal.nearby.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 2) {
            zza((zzeh) zzc.zza(parcel, zzeh.CREATOR));
            return true;
        }
        if (i == 3) {
            zza((zzen) zzc.zza(parcel, zzen.CREATOR));
            return true;
        }
        if (i == 4) {
            zza((zzep) zzc.zza(parcel, zzep.CREATOR));
            return true;
        }
        if (i != 5) {
            return false;
        }
        zza((zzef) zzc.zza(parcel, zzef.CREATOR));
        return true;
    }
}
