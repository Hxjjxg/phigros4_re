package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzgs extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzgs> CREATOR = new zzgt();
    private static final String zzgu = null;
    public static final zzgs zzgv = new zzgs("", null);
    private final int zzex;
    private final String zzgw;
    private final String zzgx;

    zzgs(int i, String str, String str2) {
        this.zzex = ((Integer) Preconditions.checkNotNull(Integer.valueOf(i))).intValue();
        this.zzgw = str == null ? "" : str;
        this.zzgx = str2;
    }

    private zzgs(String str, String str2) {
        this(1, str, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgs)) {
            return false;
        }
        zzgs zzgsVar = (zzgs) obj;
        return Objects.equal(this.zzgw, zzgsVar.zzgw) && Objects.equal(this.zzgx, zzgsVar.zzgx);
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzgw, this.zzgx);
    }

    public final String toString() {
        String str = this.zzgw;
        String str2 = this.zzgx;
        return new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(str2).length()).append("NearbyDevice{handle=").append(str).append(", bluetoothAddress=").append(str2).append("}").toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 3, this.zzgw, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzgx, false);
        SafeParcelWriter.writeInt(parcel, 1000, this.zzex);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
