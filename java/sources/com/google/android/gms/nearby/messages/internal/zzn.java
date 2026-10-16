package com.google.android.gms.nearby.messages.internal;

import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzn extends com.google.android.gms.internal.nearby.zzb implements zzm {
    public zzn() {
        super("com.google.android.gms.nearby.messages.internal.IMessageListener");
    }

    @Override // com.google.android.gms.internal.nearby.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 1) {
            zza((zzaf) com.google.android.gms.internal.nearby.zzc.zza(parcel, zzaf.CREATOR));
        } else if (i == 2) {
            zzb((zzaf) com.google.android.gms.internal.nearby.zzc.zza(parcel, zzaf.CREATOR));
        } else {
            if (i != 4) {
                return false;
            }
            zza(parcel.createTypedArrayList(Update.CREATOR));
        }
        return true;
    }
}
