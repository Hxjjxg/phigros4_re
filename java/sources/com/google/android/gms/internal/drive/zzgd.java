package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;

/* JADX INFO: loaded from: classes.dex */
public final class zzgd extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzgd> CREATOR = new zzge();
    private final int mode;
    private final DriveId zzdb;
    private final int zzhz;

    public zzgd(DriveId driveId, int i, int i2) {
        this.zzdb = driveId;
        this.mode = i;
        this.zzhz = i2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdb, i, false);
        SafeParcelWriter.writeInt(parcel, 3, this.mode);
        SafeParcelWriter.writeInt(parcel, 4, this.zzhz);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
