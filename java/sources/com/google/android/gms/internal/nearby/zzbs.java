package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;

/* JADX INFO: loaded from: classes.dex */
final class zzbs implements OnFailureListener {
    private final /* synthetic */ String zzbm;
    private final /* synthetic */ zzbd zzcq;

    zzbs(zzbd zzbdVar, String str) {
        this.zzcq = zzbdVar;
        this.zzbm = str;
    }

    @Override // com.google.android.gms.tasks.OnFailureListener
    public final void onFailure(Exception exc) {
        if ((exc instanceof ApiException) && ((ApiException) exc).getStatusCode() == 8003) {
            return;
        }
        this.zzcq.zzc(this.zzbm);
    }
}
