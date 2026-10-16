package com.google.android.gms.games.internal;

import android.os.Binder;
import android.view.View;
import com.google.android.gms.common.util.PlatformVersion;

/* JADX INFO: loaded from: classes.dex */
public class zzac {
    protected zze zzjc;
    protected zzae zzjd;

    private zzac(zze zzeVar, int i) {
        this.zzjc = zzeVar;
        zzm(i);
    }

    public static zzac zza(zze zzeVar, int i) {
        return PlatformVersion.isAtLeastHoneycombMR1() ? new zzaf(zzeVar, i) : new zzac(zzeVar, i);
    }

    public void zzb(View view) {
    }

    public void zzbj() {
        this.zzjc.zza(this.zzjd.zzjb, this.zzjd.zzbk());
    }

    protected void zzm(int i) {
        this.zzjd = new zzae(i, new Binder());
    }
}
