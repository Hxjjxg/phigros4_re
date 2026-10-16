package com.google.android.gms.internal.nearby;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzbv extends TaskApiCall<zzx, Void> {
    private final /* synthetic */ zzbd zzcq;
    private final /* synthetic */ zzbw zzcs;

    zzbv(zzbd zzbdVar, zzbw zzbwVar) {
        this.zzcq = zzbdVar;
        this.zzcs = zzbwVar;
    }

    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        this.zzcs.zza((zzx) anyClient, new zzby(this.zzcq, taskCompletionSource));
    }
}
