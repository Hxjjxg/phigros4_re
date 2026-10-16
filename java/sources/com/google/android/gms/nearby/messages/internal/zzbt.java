package com.google.android.gms.nearby.messages.internal;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.internal.nearby.zzha;
import com.google.android.gms.nearby.messages.PublishCallback;

/* JADX INFO: loaded from: classes.dex */
final class zzbt extends zzv {
    private static final zzha<PublishCallback> zzih = new zzbu();
    private final ListenerHolder<PublishCallback> zzii;

    public zzbt(ListenerHolder<PublishCallback> listenerHolder) {
        this.zzii = listenerHolder;
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzu
    public final void onExpired() {
        this.zzii.notifyListener(zzih);
    }
}
