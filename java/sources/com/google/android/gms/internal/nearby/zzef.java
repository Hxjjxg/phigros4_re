package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzef extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzef> CREATOR = new zzeg();
    private int quality;
    private String zzat;

    private zzef() {
    }

    zzef(String str, int i) {
        this.zzat = str;
        this.quality = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzef) {
            zzef zzefVar = (zzef) obj;
            if (Objects.equal(this.zzat, zzefVar.zzat) && Objects.equal(Integer.valueOf(this.quality), Integer.valueOf(zzefVar.quality))) {
                return true;
            }
        }
        return false;
    }

    public final int getQuality() {
        return this.quality;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzat, Integer.valueOf(this.quality));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzat, false);
        SafeParcelWriter.writeInt(parcel, 2, this.quality);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String zzg() {
        return this.zzat;
    }
}
