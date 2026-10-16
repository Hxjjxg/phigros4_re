package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;

/* JADX INFO: loaded from: classes.dex */
final class zzgz extends zzha<BaseImplementation.ResultHolder<Status>> {
    private final /* synthetic */ Status zzbj;

    zzgz(zzgy zzgyVar, Status status) {
        this.zzbj = status;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        BaseImplementation.ResultHolder resultHolder = (BaseImplementation.ResultHolder) obj;
        if (this.zzbj.isSuccess()) {
            resultHolder.setResult(this.zzbj);
        } else {
            resultHolder.setFailedResult(this.zzbj);
        }
    }
}
