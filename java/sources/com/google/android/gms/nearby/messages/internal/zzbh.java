package com.google.android.gms.nearby.messages.internal;

import com.google.android.gms.internal.nearby.zzha;
import com.google.android.gms.nearby.messages.SubscribeCallback;

/* JADX INFO: loaded from: classes.dex */
final class zzbh extends zzha<SubscribeCallback> {
    zzbh() {
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((SubscribeCallback) obj).onExpired();
    }
}
