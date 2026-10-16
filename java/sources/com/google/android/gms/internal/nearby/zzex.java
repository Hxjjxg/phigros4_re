package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;

/* JADX INFO: loaded from: classes.dex */
public final class zzex extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzex> CREATOR = new zzey();
    private String zzat;
    private PayloadTransferUpdate zzdm;

    private zzex() {
    }

    zzex(String str, PayloadTransferUpdate payloadTransferUpdate) {
        this.zzat = str;
        this.zzdm = payloadTransferUpdate;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzex) {
            zzex zzexVar = (zzex) obj;
            if (Objects.equal(this.zzat, zzexVar.zzat) && Objects.equal(this.zzdm, zzexVar.zzdm)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzat, this.zzdm);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzat, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdm, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String zzg() {
        return this.zzat;
    }

    public final PayloadTransferUpdate zzn() {
        return this.zzdm;
    }
}
