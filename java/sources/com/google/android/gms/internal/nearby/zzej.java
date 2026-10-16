package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class zzej extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzej> CREATOR = new zzek();
    private String zzat;
    private byte[] zzau;
    private String zzdj;

    private zzej() {
    }

    zzej(String str, String str2, byte[] bArr) {
        this.zzat = str;
        this.zzdj = str2;
        this.zzau = bArr;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzej) {
            zzej zzejVar = (zzej) obj;
            if (Objects.equal(this.zzat, zzejVar.zzat) && Objects.equal(this.zzdj, zzejVar.zzdj) && Arrays.equals(this.zzau, zzejVar.zzau)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzat, this.zzdj, Integer.valueOf(Arrays.hashCode(this.zzau)));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzat, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzdj, false);
        SafeParcelWriter.writeByteArray(parcel, 3, this.zzau, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String zzg() {
        return this.zzat;
    }

    public final String zzh() {
        return this.zzdj;
    }

    public final byte[] zzj() {
        return this.zzau;
    }
}
