package com.google.android.gms.nearby.messages.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzad extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzad> CREATOR = new zzae();
    private final String namespace;
    private final String type;
    private final int versionCode;

    zzad(int i, String str, String str2) {
        this.versionCode = i;
        this.namespace = str;
        this.type = str2;
    }

    public zzad(String str, String str2) {
        this(1, str, str2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof zzad) && hashCode() == obj.hashCode()) {
            zzad zzadVar = (zzad) obj;
            if (Objects.equal(this.namespace, zzadVar.namespace) && Objects.equal(this.type, zzadVar.type)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.namespace, this.type);
    }

    public final String toString() {
        String str = this.namespace;
        String str2 = this.type;
        return new StringBuilder(String.valueOf(str).length() + 17 + String.valueOf(str2).length()).append("namespace=").append(str).append(", type=").append(str2).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.namespace, false);
        SafeParcelWriter.writeString(parcel, 2, this.type, false);
        SafeParcelWriter.writeInt(parcel, 1000, this.versionCode);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
