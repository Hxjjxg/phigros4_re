package com.google.android.gms.nearby.messages.internal;

import com.google.android.gms.internal.nearby.zzha;
import com.google.android.gms.nearby.messages.PublishCallback;

/* JADX INFO: loaded from: classes.dex */
final class zzbf extends zzha<PublishCallback> {
    zzbf() {
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        ((PublishCallback) obj).onExpired();
    }
}
