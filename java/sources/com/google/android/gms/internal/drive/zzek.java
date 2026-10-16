package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;

/* JADX INFO: loaded from: classes.dex */
public final class zzek extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzek> CREATOR = new zzel();
    private final DriveId zzdb;
    private final boolean zzgy;

    public zzek(DriveId driveId, boolean z) {
        this.zzdb = driveId;
        this.zzgy = z;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdb, i, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzgy);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
