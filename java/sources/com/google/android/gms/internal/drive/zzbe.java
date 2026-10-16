package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.TransferPreferences;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzbe extends TaskApiCall<zzaw, Void> {
    private final /* synthetic */ TransferPreferences zzen;

    zzbe(zzbb zzbbVar, TransferPreferences transferPreferences) {
        this.zzen = transferPreferences;
    }

    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        ((zzeo) ((zzaw) anyClient).getService()).zza(new zzgo(new zzei(this.zzen)), new zzhl(taskCompletionSource));
    }
}
