package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;

/* JADX INFO: loaded from: classes.dex */
public final class zzj extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzj> CREATOR = new zzk();
    private final com.google.android.gms.drive.events.zze zzbt;
    final int zzcy;
    private final com.google.android.gms.drive.events.zzx zzcz;
    private final com.google.android.gms.drive.events.zzt zzda;
    final DriveId zzk;

    public zzj(int i, DriveId driveId) {
        this((DriveId) Preconditions.checkNotNull(driveId), 1, null, null, null);
    }

    zzj(DriveId driveId, int i, com.google.android.gms.drive.events.zze zzeVar, com.google.android.gms.drive.events.zzx zzxVar, com.google.android.gms.drive.events.zzt zztVar) {
        this.zzk = driveId;
        this.zzcy = i;
        this.zzbt = zzeVar;
        this.zzcz = zzxVar;
        this.zzda = zztVar;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzk, i, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zzcy);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzbt, i, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzcz, i, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzda, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
