package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Collection;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public final class zzp<T> extends zza {
    public static final zzq CREATOR = new zzq();
    private final MetadataBundle zzlk;
    private final com.google.android.gms.drive.metadata.zzb<T> zzlx;

    public zzp(SearchableCollectionMetadataField<T> searchableCollectionMetadataField, T t) {
        this(MetadataBundle.zza(searchableCollectionMetadataField, Collections.singleton(t)));
    }

    zzp(MetadataBundle metadataBundle) {
        this.zzlk = metadataBundle;
        this.zzlx = (com.google.android.gms.drive.metadata.zzb) zzi.zza(metadataBundle);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzlk, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.drive.query.Filter
    public final <F> F zza(zzj<F> zzjVar) {
        com.google.android.gms.drive.metadata.zzb<T> zzbVar = this.zzlx;
        return zzjVar.zza(zzbVar, ((Collection) this.zzlk.zza(zzbVar)).iterator().next());
    }
}
