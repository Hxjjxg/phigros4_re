package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzet extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzet> CREATOR = new zzeu();
    private String zzca;

    private zzet() {
    }

    zzet(String str) {
        this.zzca = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzet) {
            return Objects.equal(this.zzca, ((zzet) obj).zzca);
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzca);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzca, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String zze() {
        return this.zzca;
    }
}
