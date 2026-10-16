package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;

/* JADX INFO: loaded from: classes.dex */
public final class zzgm extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzgm> CREATOR = new zzgn();
    private final int zzcy;
    private final com.google.android.gms.drive.events.zzt zzda;
    private final DriveId zzk;

    public zzgm(DriveId driveId, int i) {
        this(driveId, i, null);
    }

    zzgm(DriveId driveId, int i, com.google.android.gms.drive.events.zzt zztVar) {
        this.zzk = driveId;
        this.zzcy = i;
        this.zzda = zztVar;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzk, i, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zzcy);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzda, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
