package com.google.android.gms.nearby.messages.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: loaded from: classes.dex */
final class zzax implements BaseImplementation.ResultHolder<Status> {
    private final /* synthetic */ TaskCompletionSource zzib;

    zzax(zzak zzakVar, TaskCompletionSource taskCompletionSource) {
        this.zzib = taskCompletionSource;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder
    public final void setFailedResult(Status status) {
        this.zzib.setException(new ApiException(status));
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder
    public final /* synthetic */ void setResult(Status status) {
        this.zzib.setResult(null);
    }
}
