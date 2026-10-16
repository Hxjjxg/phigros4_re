package com.google.android.gms.nearby.messages.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.nearby.messages.Distance;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class zze extends AbstractSafeParcelable implements Distance {
    public static final Parcelable.Creator<zze> CREATOR = new zzf();
    private final int accuracy;
    private final int versionCode;
    private final double zzhg;

    public zze(int i, double d) {
        this(1, 1, Double.NaN);
    }

    zze(int i, int i2, double d) {
        this.versionCode = i;
        this.accuracy = i2;
        this.zzhg = d;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.lang.Comparable
    public final int compareTo(Distance distance) {
        if (Double.isNaN(getMeters()) && Double.isNaN(distance.getMeters())) {
            return 0;
        }
        return Double.compare(getMeters(), distance.getMeters());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zze)) {
            return false;
        }
        zze zzeVar = (zze) obj;
        return getAccuracy() == zzeVar.getAccuracy() && compareTo((Distance) zzeVar) == 0;
    }

    @Override // com.google.android.gms.nearby.messages.Distance
    public final int getAccuracy() {
        return this.accuracy;
    }

    @Override // com.google.android.gms.nearby.messages.Distance
    public final double getMeters() {
        return this.zzhg;
    }

    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(getAccuracy()), Double.valueOf(getMeters()));
    }

    public final String toString() {
        Locale locale = Locale.US;
        Object[] objArr = new Object[2];
        objArr[0] = Double.valueOf(this.zzhg);
        objArr[1] = this.accuracy != 1 ? "UNKNOWN" : "LOW";
        return String.format(locale, "(%.1fm, %s)", objArr);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeInt(parcel, 2, this.accuracy);
        SafeParcelWriter.writeDouble(parcel, 3, this.zzhg);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
