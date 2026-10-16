package com.google.android.gms.nearby.messages.internal;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.internal.nearby.zzha;
import com.google.android.gms.nearby.messages.SubscribeCallback;

/* JADX INFO: loaded from: classes.dex */
class zzbg extends zzab {
    private static final zzha<SubscribeCallback> zzih = new zzbh();
    private final ListenerHolder<SubscribeCallback> zzii;

    public zzbg(ListenerHolder<SubscribeCallback> listenerHolder) {
        this.zzii = listenerHolder;
    }

    public void onExpired() {
        ListenerHolder<SubscribeCallback> listenerHolder = this.zzii;
        if (listenerHolder != null) {
            listenerHolder.notifyListener(zzih);
        }
    }
}
