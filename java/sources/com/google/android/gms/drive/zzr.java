package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzr extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzr> CREATOR = new zzs();
    private int accountType;
    private String zzbe;
    private String zzbf;
    private String zzbg;
    private int zzbh;
    private boolean zzbi;

    public zzr(String str, int i, String str2, String str3, int i2, boolean z) {
        this.zzbe = str;
        this.accountType = i;
        this.zzbf = str2;
        this.zzbg = str3;
        this.zzbh = i2;
        this.zzbi = z;
    }

    private static boolean zzb(int i) {
        switch (i) {
            case 256:
            case 257:
            case 258:
                return true;
            default:
                return false;
        }
    }

    public final boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (obj == this) {
                return true;
            }
            zzr zzrVar = (zzr) obj;
            if (Objects.equal(this.zzbe, zzrVar.zzbe) && this.accountType == zzrVar.accountType && this.zzbh == zzrVar.zzbh && this.zzbi == zzrVar.zzbi) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzbe, Integer.valueOf(this.accountType), Integer.valueOf(this.zzbh), Boolean.valueOf(this.zzbi));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, !zzb(this.accountType) ? null : this.zzbe, false);
        SafeParcelWriter.writeInt(parcel, 3, !zzb(this.accountType) ? -1 : this.accountType);
        SafeParcelWriter.writeString(parcel, 4, this.zzbf, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzbg, false);
        int i2 = this.zzbh;
        SafeParcelWriter.writeInt(parcel, 6, i2 == 0 || i2 == 1 || i2 == 2 || i2 == 3 ? i2 : -1);
        SafeParcelWriter.writeBoolean(parcel, 7, this.zzbi);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
