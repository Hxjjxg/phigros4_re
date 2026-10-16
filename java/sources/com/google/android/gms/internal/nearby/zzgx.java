package com.google.android.gms.internal.nearby;

import com.google.android.gms.nearby.messages.MessageListener;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class zzgx extends zzha<MessageListener> {
    private final /* synthetic */ List zzjk;

    zzgx(zzgw zzgwVar, List list) {
        this.zzjk = list;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final /* synthetic */ void notifyListener(Object obj) {
        zzgw.zza(this.zzjk, (MessageListener) obj);
    }
}
