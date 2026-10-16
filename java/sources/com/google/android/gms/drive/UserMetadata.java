package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public class UserMetadata extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<UserMetadata> CREATOR = new zzt();
    private final String zzbm;
    private final String zzbn;
    private final String zzbo;
    private final boolean zzbp;
    private final String zzbq;

    public UserMetadata(String str, String str2, String str3, boolean z, String str4) {
        this.zzbm = str;
        this.zzbn = str2;
        this.zzbo = str3;
        this.zzbp = z;
        this.zzbq = str4;
    }

    public String toString() {
        return String.format("Permission ID: '%s', Display Name: '%s', Picture URL: '%s', Authenticated User: %b, Email: '%s'", this.zzbm, this.zzbn, this.zzbo, Boolean.valueOf(this.zzbp), this.zzbq);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzbm, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzbn, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzbo, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzbp);
        SafeParcelWriter.writeString(parcel, 6, this.zzbq, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
