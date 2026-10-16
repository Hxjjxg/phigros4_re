package com.google.android.gms.nearby.messages.internal;

import com.google.android.gms.common.api.internal.ListenerHolder;

/* JADX INFO: loaded from: classes.dex */
final class zzaw extends zzbg {
    private final /* synthetic */ ListenerHolder zzco;
    private final /* synthetic */ zzak zzia;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaw(zzak zzakVar, ListenerHolder listenerHolder, ListenerHolder listenerHolder2) {
        super(listenerHolder);
        this.zzia = zzakVar;
        this.zzco = listenerHolder2;
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzbg, com.google.android.gms.nearby.messages.internal.zzaa
    public final void onExpired() {
        this.zzia.doUnregisterEventListener(this.zzco.getListenerKey());
        super.onExpired();
    }
}
