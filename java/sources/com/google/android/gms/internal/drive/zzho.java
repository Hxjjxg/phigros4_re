package com.google.android.gms.internal.drive;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class zzho extends zzir<zzho> {
    public long zzac = -1;
    public long zzf = -1;

    public zzho() {
        this.zzmw = null;
        this.zznf = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzho)) {
            return false;
        }
        zzho zzhoVar = (zzho) obj;
        if (this.zzac != zzhoVar.zzac || this.zzf != zzhoVar.zzf) {
            return false;
        }
        if (this.zzmw == null || this.zzmw.isEmpty()) {
            return zzhoVar.zzmw == null || zzhoVar.zzmw.isEmpty();
        }
        return this.zzmw.equals(zzhoVar.zzmw);
    }

    public final int hashCode() {
        int iHashCode = (getClass().getName().hashCode() + 527) * 31;
        long j = this.zzac;
        int i = (iHashCode + ((int) (j ^ (j >>> 32)))) * 31;
        long j2 = this.zzf;
        return ((i + ((int) (j2 ^ (j2 >>> 32)))) * 31) + ((this.zzmw == null || this.zzmw.isEmpty()) ? 0 : this.zzmw.hashCode());
    }

    @Override // com.google.android.gms.internal.drive.zzix
    public final /* synthetic */ zzix zza(zzio zzioVar) throws IOException {
        while (true) {
            int iZzbd = zzioVar.zzbd();
            if (iZzbd == 0) {
                return this;
            }
            if (iZzbd == 8) {
                long jZzbf = zzioVar.zzbf();
                this.zzac = (-(jZzbf & 1)) ^ (jZzbf >>> 1);
            } else if (iZzbd == 16) {
                long jZzbf2 = zzioVar.zzbf();
                this.zzf = (-(jZzbf2 & 1)) ^ (jZzbf2 >>> 1);
            } else if (!super.zza(zzioVar, iZzbd)) {
                return this;
            }
        }
    }

    @Override // com.google.android.gms.internal.drive.zzir, com.google.android.gms.internal.drive.zzix
    public final void zza(zzip zzipVar) throws IOException {
        zzipVar.zza(1, this.zzac);
        zzipVar.zza(2, this.zzf);
        super.zza(zzipVar);
    }

    @Override // com.google.android.gms.internal.drive.zzir, com.google.android.gms.internal.drive.zzix
    protected final int zzaq() {
        return super.zzaq() + zzip.zzb(1, this.zzac) + zzip.zzb(2, this.zzf);
    }
}
