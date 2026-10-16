package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class zzeh extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzeh> CREATOR = new zzei();
    private String zzat;
    private byte[] zzau;
    private String zzdj;
    private String zzr;
    private boolean zzs;

    private zzeh() {
    }

    zzeh(String str, String str2, String str3, boolean z, byte[] bArr) {
        this.zzat = str;
        this.zzdj = str2;
        this.zzr = str3;
        this.zzs = z;
        this.zzau = bArr;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzeh) {
            zzeh zzehVar = (zzeh) obj;
            if (Objects.equal(this.zzat, zzehVar.zzat) && Objects.equal(this.zzdj, zzehVar.zzdj) && Objects.equal(this.zzr, zzehVar.zzr) && Objects.equal(Boolean.valueOf(this.zzs), Boolean.valueOf(zzehVar.zzs)) && Arrays.equals(this.zzau, zzehVar.zzau)) {
                return true;
            }
        }
        return false;
    }

    public final String getAuthenticationToken() {
        return this.zzr;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzat, this.zzdj, this.zzr, Boolean.valueOf(this.zzs), Integer.valueOf(Arrays.hashCode(this.zzau)));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzat, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzdj, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzr, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzs);
        SafeParcelWriter.writeByteArray(parcel, 5, this.zzau, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String zzg() {
        return this.zzat;
    }

    public final String zzh() {
        return this.zzdj;
    }

    public final boolean zzi() {
        return this.zzs;
    }
}
