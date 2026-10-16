package com.google.android.gms.drive.metadata.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class AppVisibleCustomProperties extends AbstractSafeParcelable implements ReflectedParcelable, Iterable<zzc> {
    public static final Parcelable.Creator<AppVisibleCustomProperties> CREATOR = new com.google.android.gms.drive.metadata.internal.zza();
    public static final AppVisibleCustomProperties zzil = new zza().zzat();
    private final List<zzc> zzim;

    public static class zza {
        private final Map<CustomPropertyKey, zzc> zzin = new HashMap();

        public final zza zza(CustomPropertyKey customPropertyKey, String str) {
            Preconditions.checkNotNull(customPropertyKey, "key");
            this.zzin.put(customPropertyKey, new zzc(customPropertyKey, str));
            return this;
        }

        public final zza zza(zzc zzcVar) {
            Preconditions.checkNotNull(zzcVar, "property");
            this.zzin.put(zzcVar.zzio, zzcVar);
            return this;
        }

        public final AppVisibleCustomProperties zzat() {
            return new AppVisibleCustomProperties(this.zzin.values());
        }
    }

    AppVisibleCustomProperties(Collection<zzc> collection) {
        Preconditions.checkNotNull(collection);
        this.zzim = new ArrayList(collection);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        return zzas().equals(((AppVisibleCustomProperties) obj).zzas());
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzim);
    }

    @Override // java.lang.Iterable
    public final Iterator<zzc> iterator() {
        return this.zzim.iterator();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzim, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final Map<CustomPropertyKey, String> zzas() {
        HashMap map = new HashMap(this.zzim.size());
        for (zzc zzcVar : this.zzim) {
            map.put(zzcVar.zzio, zzcVar.value);
        }
        return Collections.unmodifiableMap(map);
    }
}
