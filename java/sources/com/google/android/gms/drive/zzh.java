package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzh extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzh> CREATOR = new zzi();
    private final long zzaa;
    private final long zzz;

    public zzh(long j, long j2) {
        this.zzz = j;
        this.zzaa = j2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 2, this.zzz);
        SafeParcelWriter.writeLong(parcel, 3, this.zzaa);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
