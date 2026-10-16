package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzde extends zzb implements zzdd {
    public zzde() {
        super("com.google.android.gms.nearby.internal.connection.IAdvertisingCallback");
    }

    @Override // com.google.android.gms.internal.nearby.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 2) {
            zza((zzej) zzc.zza(parcel, zzej.CREATOR));
            return true;
        }
        if (i != 3) {
            return false;
        }
        zza((zzfb) zzc.zza(parcel, zzfb.CREATOR));
        return true;
    }
}
