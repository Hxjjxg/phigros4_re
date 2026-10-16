package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzfb extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfb> CREATOR = new zzfc();
    private int zzdn;

    private zzfb() {
    }

    zzfb(int i) {
        this.zzdn = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzfb) {
            return Objects.equal(Integer.valueOf(this.zzdn), Integer.valueOf(((zzfb) obj).zzdn));
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzdn));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzdn);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
