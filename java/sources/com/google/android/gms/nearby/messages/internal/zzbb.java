package com.google.android.gms.nearby.messages.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzbb extends TaskApiCall<zzah, Void> {
    private final /* synthetic */ zzak zzia;
    private final /* synthetic */ zzbd zzif;

    zzbb(zzak zzakVar, zzbd zzbdVar) {
        this.zzia = zzakVar;
        this.zzif = zzbdVar;
    }

    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        this.zzif.zza((zzah) anyClient, this.zzia.zza((TaskCompletionSource) taskCompletionSource));
    }
}
