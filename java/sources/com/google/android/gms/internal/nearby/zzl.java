package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.tasks.OnFailureListener;

/* JADX INFO: loaded from: classes.dex */
final class zzl implements OnFailureListener {
    private final /* synthetic */ RegisterListenerMethod zzap;
    private final /* synthetic */ zzk zzaq;

    zzl(zzk zzkVar, RegisterListenerMethod registerListenerMethod) {
        this.zzaq = zzkVar;
        this.zzap = registerListenerMethod;
    }

    @Override // com.google.android.gms.tasks.OnFailureListener
    public final void onFailure(Exception exc) {
        synchronized (this.zzaq) {
            this.zzaq.zzan.remove(this.zzap.getListenerKey());
        }
    }
}
