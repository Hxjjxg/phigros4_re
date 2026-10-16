package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzev extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzev> CREATOR = new zzew();
    private String zzat;
    private zzfh zzdk;
    private boolean zzdl;

    private zzev() {
    }

    zzev(String str, zzfh zzfhVar, boolean z) {
        this.zzat = str;
        this.zzdk = zzfhVar;
        this.zzdl = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzev) {
            zzev zzevVar = (zzev) obj;
            if (Objects.equal(this.zzat, zzevVar.zzat) && Objects.equal(this.zzdk, zzevVar.zzdk) && Objects.equal(Boolean.valueOf(this.zzdl), Boolean.valueOf(zzevVar.zzdl))) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzat, this.zzdk, Boolean.valueOf(this.zzdl));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzat, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdk, i, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzdl);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String zzg() {
        return this.zzat;
    }

    public final zzfh zzl() {
        return this.zzdk;
    }

    public final boolean zzm() {
        return this.zzdl;
    }
}
