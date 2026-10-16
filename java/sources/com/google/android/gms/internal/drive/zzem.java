package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzem extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzem> CREATOR = new zzen();
    private final int responseCode;
    private final List<com.google.android.gms.drive.zzr> zzgz;

    public zzem(List<com.google.android.gms.drive.zzr> list, int i) {
        this.zzgz = list;
        this.responseCode = i;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzgz, false);
        SafeParcelWriter.writeInt(parcel, 3, this.responseCode);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
