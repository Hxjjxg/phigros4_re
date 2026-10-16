package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class zzac extends com.google.android.gms.internal.nearby.zza implements zzaa {
    zzac(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.nearby.messages.internal.ISubscribeCallback");
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzaa
    public final void onExpired() throws RemoteException {
        transactOneway(1, obtainAndWriteInterfaceToken());
    }
}
