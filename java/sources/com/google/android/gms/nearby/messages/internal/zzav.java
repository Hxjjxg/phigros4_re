package com.google.android.gms.nearby.messages.internal;

import com.google.android.gms.common.api.internal.ListenerHolder;

/* JADX INFO: loaded from: classes.dex */
final class zzav extends zzbe {
    private final /* synthetic */ ListenerHolder zzhz;
    private final /* synthetic */ zzak zzia;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzav(zzak zzakVar, ListenerHolder listenerHolder, ListenerHolder listenerHolder2) {
        super(listenerHolder);
        this.zzia = zzakVar;
        this.zzhz = listenerHolder2;
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzbe, com.google.android.gms.nearby.messages.internal.zzu
    public final void onExpired() {
        this.zzia.doUnregisterEventListener(this.zzhz.getListenerKey());
        super.onExpired();
    }
}
