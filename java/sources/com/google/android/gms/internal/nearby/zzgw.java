package com.google.android.gms.internal.nearby;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.internal.Update;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzgw extends com.google.android.gms.nearby.messages.internal.zzn {
    private final ListenerHolder<MessageListener> zzjj;

    public zzgw(ListenerHolder<MessageListener> listenerHolder) {
        this.zzjj = listenerHolder;
    }

    public static void zza(Intent intent, MessageListener messageListener) {
        Bundle bundleExtra = intent.getBundleExtra("com.google.android.gms.nearby.messages.UPDATES");
        zza((Iterable<Update>) (bundleExtra == null ? Collections.emptyList() : bundleExtra.getParcelableArrayList("com.google.android.gms.nearby.messages.UPDATES")), messageListener);
    }

    public static void zza(Iterable<Update> iterable, MessageListener messageListener) {
        for (Update update : iterable) {
            if (update.zzg(1)) {
                messageListener.onFound(update.zzhk);
            }
            if (update.zzg(2)) {
                messageListener.onLost(update.zzhk);
            }
            if (update.zzg(4)) {
                messageListener.onDistanceChanged(update.zzhk, update.zzjf);
            }
            if (update.zzg(8)) {
                messageListener.onBleSignalChanged(update.zzhk, update.zzjg);
            }
            if (update.zzg(16)) {
                Message message = update.zzhk;
                zzgs zzgsVar = update.zzjh;
            }
        }
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzm
    public final void zza(com.google.android.gms.nearby.messages.internal.zzaf zzafVar) {
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzm
    public final void zza(List<Update> list) throws RemoteException {
        this.zzjj.notifyListener(new zzgx(this, list));
    }

    @Override // com.google.android.gms.nearby.messages.internal.zzm
    public final void zzb(com.google.android.gms.nearby.messages.internal.zzaf zzafVar) {
    }
}
