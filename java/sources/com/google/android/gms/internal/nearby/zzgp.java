package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class zzgp extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzgp> CREATOR = new zzgq();
    private final int zzex;
    private final ParcelUuid zzge;
    private final ParcelUuid zzgf;
    private final ParcelUuid zzgg;
    private final byte[] zzgh;
    private final byte[] zzgi;
    private final int zzgj;
    private final byte[] zzgk;
    private final byte[] zzgl;

    zzgp(int i, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, ParcelUuid parcelUuid3, byte[] bArr, byte[] bArr2, int i2, byte[] bArr3, byte[] bArr4) {
        this.zzex = i;
        this.zzge = parcelUuid;
        this.zzgf = parcelUuid2;
        this.zzgg = parcelUuid3;
        this.zzgh = bArr;
        this.zzgi = bArr2;
        this.zzgj = i2;
        this.zzgk = bArr3;
        this.zzgl = bArr4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            zzgp zzgpVar = (zzgp) obj;
            if (this.zzgj == zzgpVar.zzgj && Arrays.equals(this.zzgk, zzgpVar.zzgk) && Arrays.equals(this.zzgl, zzgpVar.zzgl) && Objects.equal(this.zzgg, zzgpVar.zzgg) && Arrays.equals(this.zzgh, zzgpVar.zzgh) && Arrays.equals(this.zzgi, zzgpVar.zzgi) && Objects.equal(this.zzge, zzgpVar.zzge) && Objects.equal(this.zzgf, zzgpVar.zzgf)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzgj), Integer.valueOf(Arrays.hashCode(this.zzgk)), Integer.valueOf(Arrays.hashCode(this.zzgl)), this.zzgg, Integer.valueOf(Arrays.hashCode(this.zzgh)), Integer.valueOf(Arrays.hashCode(this.zzgi)), this.zzge, this.zzgf);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzex);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzge, i, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzgf, i, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzgg, i, false);
        SafeParcelWriter.writeByteArray(parcel, 7, this.zzgh, false);
        SafeParcelWriter.writeByteArray(parcel, 8, this.zzgi, false);
        SafeParcelWriter.writeInt(parcel, 9, this.zzgj);
        SafeParcelWriter.writeByteArray(parcel, 10, this.zzgk, false);
        SafeParcelWriter.writeByteArray(parcel, 11, this.zzgl, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
