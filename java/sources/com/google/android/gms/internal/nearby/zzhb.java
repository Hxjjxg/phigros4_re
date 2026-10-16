package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.nearby.messages.StatusCallback;

/* JADX INFO: loaded from: classes.dex */
public final class zzhb extends com.google.android.gms.nearby.messages.internal.zzy {
    private final ListenerHolder<StatusCallback> zzjj;

    public zzhb(ListenerHolder<StatusCallback> listenerHolder) {
        this.zzjj = listenerHolder;
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzx
    public final void onPermissionChanged(boolean z) {
        this.zzjj.notifyListener(new zzhc(this, z));
    }
}
