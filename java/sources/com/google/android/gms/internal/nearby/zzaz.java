package com.google.android.gms.internal.nearby;

import com.google.android.gms.common.internal.Objects;

/* JADX INFO: loaded from: classes.dex */
final class zzaz {
    private final long zzaf;
    private final String zzca;

    zzaz(String str, long j) {
        this.zzca = str;
        this.zzaf = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzaz) {
            zzaz zzazVar = (zzaz) obj;
            if (Objects.equal(this.zzca, zzazVar.zzca) && Objects.equal(Long.valueOf(this.zzaf), Long.valueOf(zzazVar.zzaf))) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzca, Long.valueOf(this.zzaf));
    }

    public final String zze() {
        return this.zzca;
    }
}
