package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzcz extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzcz> CREATOR = new zzdc();
    private String zzat;

    private zzcz() {
    }

    zzcz(String str) {
        this.zzat = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzcz) {
            return Objects.equal(this.zzat, ((zzcz) obj).zzat);
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzat);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzat, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
