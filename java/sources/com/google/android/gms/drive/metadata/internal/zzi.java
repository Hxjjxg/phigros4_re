package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.util.GmsVersion;

/* JADX INFO: loaded from: classes.dex */
public class zzi extends com.google.android.gms.drive.metadata.zza<Long> {
    public zzi(String str, int i) {
        super(str, GmsVersion.VERSION_JARLSBERG);
    }

    @Override // com.google.android.gms.drive.metadata.zza
    protected final /* synthetic */ void zza(Bundle bundle, Long l) {
        bundle.putLong(getName(), l.longValue());
    }

    @Override // com.google.android.gms.drive.metadata.zza
    protected final /* synthetic */ Long zzb(Bundle bundle) {
        return Long.valueOf(bundle.getLong(getName()));
    }

    @Override // com.google.android.gms.drive.metadata.zza
    protected final /* synthetic */ Long zzc(DataHolder dataHolder, int i, int i2) {
        return Long.valueOf(dataHolder.getLong(getName(), i, i2));
    }
}
