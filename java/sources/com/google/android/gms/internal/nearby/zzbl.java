package com.google.android.gms.internal.nearby;

import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class zzbl implements zzbz {
    static final zzbz zzcl = new zzbl();

    private zzbl() {
    }

    @Override // com.google.android.gms.internal.nearby.zzbz
    public final void zzb(zzx zzxVar) throws RemoteException {
        zzxVar.stopAllEndpoints();
    }
}
