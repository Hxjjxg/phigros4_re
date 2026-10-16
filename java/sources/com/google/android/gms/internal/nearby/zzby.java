package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzby<T> implements BaseImplementation.ResultHolder<T> {
    private final TaskCompletionSource<Void> zzcu;

    zzby(zzbd zzbdVar, TaskCompletionSource<Void> taskCompletionSource) {
        this.zzcu = taskCompletionSource;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder
    public final void setFailedResult(Status status) {
        this.zzcu.setException(new ApiException(status));
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder
    public final void setResult(T t) {
        this.zzcu.setResult(null);
    }
}
