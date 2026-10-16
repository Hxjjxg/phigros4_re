package com.google.android.gms.nearby.messages.internal;

import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzy extends com.google.android.gms.internal.nearby.zzb implements zzx {
    public zzy() {
        super("com.google.android.gms.nearby.messages.internal.IStatusCallback");
    }

    @Override // com.google.android.gms.internal.nearby.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i != 1) {
            return false;
        }
        onPermissionChanged(com.google.android.gms.internal.nearby.zzc.zza(parcel));
        return true;
    }
}
