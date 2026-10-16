package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzez extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzez> CREATOR = new zzfa();
    private int statusCode;
    private String zzcc;

    private zzez() {
    }

    zzez(int i, String str) {
        this.statusCode = i;
        this.zzcc = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzez) {
            zzez zzezVar = (zzez) obj;
            if (Objects.equal(Integer.valueOf(this.statusCode), Integer.valueOf(zzezVar.statusCode)) && Objects.equal(this.zzcc, zzezVar.zzcc)) {
                return true;
            }
        }
        return false;
    }

    public final String getLocalEndpointName() {
        return this.zzcc;
    }

    public final int getStatusCode() {
        return this.statusCode;
    }

    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.statusCode), this.zzcc);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.statusCode);
        SafeParcelWriter.writeString(parcel, 2, this.zzcc, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
