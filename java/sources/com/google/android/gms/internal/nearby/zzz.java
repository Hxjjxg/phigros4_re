package com.google.android.gms.internal.nearby;

import androidx.collection.ArraySet;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
final class zzz extends zzdk {
    private final ListenerHolder<ConnectionLifecycleCallback> zzbe;
    private final Set<String> zzbf = new ArraySet();
    private final Set<String> zzbg = new ArraySet();

    zzz(ListenerHolder<ConnectionLifecycleCallback> listenerHolder) {
        this.zzbe = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
    }

    final synchronized void shutdown() {
        Iterator<String> it = this.zzbf.iterator();
        while (it.hasNext()) {
            this.zzbe.notifyListener(new zzae(this, it.next()));
        }
        this.zzbf.clear();
        Iterator<String> it2 = this.zzbg.iterator();
        while (it2.hasNext()) {
            this.zzbe.notifyListener(new zzaf(this, it2.next()));
        }
        this.zzbg.clear();
    }

    @Override // com.google.android.gms.internal.nearby.zzdj
    public final void zza(zzef zzefVar) {
        this.zzbe.notifyListener(new zzad(this, zzefVar));
    }

    @Override // com.google.android.gms.internal.nearby.zzdj
    public final synchronized void zza(zzeh zzehVar) {
        this.zzbf.add(zzehVar.zzg());
        this.zzbe.notifyListener(new zzaa(this, zzehVar));
    }

    @Override // com.google.android.gms.internal.nearby.zzdj
    public final synchronized void zza(zzen zzenVar) {
        this.zzbf.remove(zzenVar.zzg());
        Status statusZza = zzx.zza(zzenVar.getStatusCode());
        if (statusZza.isSuccess()) {
            this.zzbg.add(zzenVar.zzg());
        }
        this.zzbe.notifyListener(new zzab(this, zzenVar, statusZza));
    }

    @Override // com.google.android.gms.internal.nearby.zzdj
    public final synchronized void zza(zzep zzepVar) {
        this.zzbg.remove(zzepVar.zzg());
        this.zzbe.notifyListener(new zzac(this, zzepVar));
    }
}
