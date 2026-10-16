package com.google.android.gms.nearby.messages.internal;

import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzv extends com.google.android.gms.internal.nearby.zzb implements zzu {
    public zzv() {
        super("com.google.android.gms.nearby.messages.internal.IPublishCallback");
    }

    @Override // com.google.android.gms.internal.nearby.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i != 1) {
            return false;
        }
        onExpired();
        return true;
    }
}
