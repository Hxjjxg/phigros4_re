package com.google.android.gms.internal.nearby;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzbn extends TaskApiCall<zzx, Void> {
    private final /* synthetic */ zzbz zzcm;

    zzbn(zzbd zzbdVar, zzbz zzbzVar) {
        this.zzcm = zzbzVar;
    }

    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        this.zzcm.zzb((zzx) anyClient);
        taskCompletionSource.setResult(null);
    }
}
