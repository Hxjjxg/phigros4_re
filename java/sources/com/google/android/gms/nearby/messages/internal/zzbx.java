package com.google.android.gms.nearby.messages.internal;

import com.google.android.gms.internal.nearby.zzha;
import com.google.android.gms.nearby.messages.SubscribeCallback;

/* JADX INFO: loaded from: classes.dex */
final class zzbx extends zzha<SubscribeCallback> {
    zzbx() {
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((SubscribeCallback) obj).onExpired();
    }
}
