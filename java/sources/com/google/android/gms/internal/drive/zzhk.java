package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
public final class zzhk extends zzhb<Boolean> {
    public zzhk(TaskCompletionSource<Boolean> taskCompletionSource) {
        super(taskCompletionSource);
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void onSuccess() throws RemoteException {
        zzap().setResult(true);
    }
}
