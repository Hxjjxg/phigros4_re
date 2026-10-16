package com.google.android.gms.nearby.messages.internal;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.internal.nearby.zzha;
import com.google.android.gms.nearby.messages.PublishCallback;

/* JADX INFO: loaded from: classes.dex */
class zzbe extends zzv {
    private static final zzha<PublishCallback> zzih = new zzbf();
    private final ListenerHolder<PublishCallback> zzii;

    public zzbe(ListenerHolder<PublishCallback> listenerHolder) {
        this.zzii = listenerHolder;
    }

    public void onExpired() {
        ListenerHolder<PublishCallback> listenerHolder = this.zzii;
        if (listenerHolder != null) {
            listenerHolder.notifyListener(zzih);
        }
    }
}
