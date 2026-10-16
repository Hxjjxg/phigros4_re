package com.google.android.gms.drive.metadata.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.drive.zzhp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class MetadataBundle extends AbstractSafeParcelable implements ReflectedParcelable {
    private final Bundle zzir;
    private static final GmsLogger zzbx = new GmsLogger("MetadataBundle", "");
    public static final Parcelable.Creator<MetadataBundle> CREATOR = new zzj();

    MetadataBundle(Bundle bundle) {
        int i;
        Bundle bundle2 = (Bundle) Preconditions.checkNotNull(bundle);
        this.zzir = bundle2;
        bundle2.setClassLoader(getClass().getClassLoader());
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = bundle2.keySet().iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            String next = it.next();
            if (zzf.zzd(next) == null) {
                arrayList.add(next);
                zzbx.wfmt("MetadataBundle", "Ignored unknown metadata field in bundle: %s", next);
            }
        }
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            this.zzir.remove((String) obj);
        }
    }

    public static <T> MetadataBundle zza(MetadataField<T> metadataField, T t) {
        MetadataBundle metadataBundleZzaw = zzaw();
        metadataBundleZzaw.zzb(metadataField, t);
        return metadataBundleZzaw;
    }

    public static MetadataBundle zzaw() {
        return new MetadataBundle(new Bundle());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        MetadataBundle metadataBundle = (MetadataBundle) obj;
        Set<String> setKeySet = this.zzir.keySet();
        if (!setKeySet.equals(metadataBundle.zzir.keySet())) {
            return false;
        }
        for (String str : setKeySet) {
            if (!Objects.equal(this.zzir.get(str), metadataBundle.zzir.get(str))) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        Iterator<String> it = this.zzir.keySet().iterator();
        int iHashCode = 1;
        while (it.hasNext()) {
            iHashCode = (iHashCode * 31) + this.zzir.get(it.next()).hashCode();
        }
        return iHashCode;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, this.zzir, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final <T> T zza(MetadataField<T> metadataField) {
        return metadataField.zza(this.zzir);
    }

    public final void zza(Context context) {
        BitmapTeleporter bitmapTeleporter = (BitmapTeleporter) zza(zzhp.zzka);
        if (bitmapTeleporter != null) {
            bitmapTeleporter.setTempDir(context.getCacheDir());
        }
    }

    public final MetadataBundle zzax() {
        return new MetadataBundle(new Bundle(this.zzir));
    }

    public final Set<MetadataField<?>> zzay() {
        HashSet hashSet = new HashSet();
        Iterator<String> it = this.zzir.keySet().iterator();
        while (it.hasNext()) {
            hashSet.add(zzf.zzd(it.next()));
        }
        return hashSet;
    }

    public final <T> void zzb(MetadataField<T> metadataField, T t) {
        if (zzf.zzd(metadataField.getName()) == null) {
            String strValueOf = String.valueOf(metadataField.getName());
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Unregistered field: ".concat(strValueOf) : new String("Unregistered field: "));
        }
        metadataField.zza(t, this.zzir);
    }

    public final <T> T zzc(MetadataField<T> metadataField) {
        T t = (T) zza(metadataField);
        this.zzir.remove(metadataField.getName());
        return t;
    }

    public final boolean zzd(MetadataField<?> metadataField) {
        return this.zzir.containsKey(metadataField.getName());
    }
}
