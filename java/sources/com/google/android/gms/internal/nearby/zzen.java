package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class zzen extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzen> CREATOR = new zzeo();
    private int statusCode;
    private String zzat;
    private byte[] zzau;

    private zzen() {
    }

    zzen(String str, int i, byte[] bArr) {
        this.zzat = str;
        this.statusCode = i;
        this.zzau = bArr;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzen) {
            zzen zzenVar = (zzen) obj;
            if (Objects.equal(this.zzat, zzenVar.zzat) && Objects.equal(Integer.valueOf(this.statusCode), Integer.valueOf(zzenVar.statusCode)) && Arrays.equals(this.zzau, zzenVar.zzau)) {
                return true;
            }
        }
        return false;
    }

    public final int getStatusCode() {
        return this.statusCode;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzat, Integer.valueOf(this.statusCode), Integer.valueOf(Arrays.hashCode(this.zzau)));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzat, false);
        SafeParcelWriter.writeInt(parcel, 2, this.statusCode);
        SafeParcelWriter.writeByteArray(parcel, 3, this.zzau, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String zzg() {
        return this.zzat;
    }
}
