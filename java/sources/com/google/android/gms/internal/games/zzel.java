package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.internal.zal$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzel {
    private final AtomicReference<zzej> zzkw = new AtomicReference<>();

    public final void flush() {
        zzej zzejVar = this.zzkw.get();
        if (zzejVar != null) {
            zzejVar.flush();
        }
    }

    public final void zza(String str, int i) {
        zzej zzejVarZzbe = this.zzkw.get();
        if (zzejVarZzbe == null) {
            zzejVarZzbe = zzbe();
            if (!zal$$ExternalSyntheticBackportWithForwarding0.m(this.zzkw, null, zzejVarZzbe)) {
                zzejVarZzbe = this.zzkw.get();
            }
        }
        zzejVarZzbe.zzg(str, i);
    }

    protected abstract zzej zzbe();
}
