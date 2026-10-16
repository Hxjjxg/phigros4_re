package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzff extends AbstractSafeParcelable {
    private final int status;
    final long zzhi;
    final long zzhj;
    private final List<com.google.android.gms.drive.zzh> zzhk;
    private static final List<com.google.android.gms.drive.zzh> zzhh = Collections.emptyList();
    public static final Parcelable.Creator<zzff> CREATOR = new zzfg();

    public zzff(long j, long j2, int i, List<com.google.android.gms.drive.zzh> list) {
        this.zzhi = j;
        this.zzhj = j2;
        this.status = i;
        this.zzhk = list;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 2, this.zzhi);
        SafeParcelWriter.writeLong(parcel, 3, this.zzhj);
        SafeParcelWriter.writeInt(parcel, 4, this.status);
        SafeParcelWriter.writeTypedList(parcel, 5, this.zzhk, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
