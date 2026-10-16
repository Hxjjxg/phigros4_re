package com.google.android.gms.drive.query.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface zzj<F> {
    <T> F zza(com.google.android.gms.drive.metadata.zzb<T> zzbVar, T t);

    <T> F zza(zzx zzxVar, MetadataField<T> metadataField, T t);

    F zza(zzx zzxVar, List<F> list);

    F zza(F f);

    F zzbb();

    F zzbc();

    <T> F zzc(MetadataField<T> metadataField, T t);

    F zze(MetadataField<?> metadataField);

    F zzg(String str);
}
