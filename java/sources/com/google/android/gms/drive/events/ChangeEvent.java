package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class ChangeEvent extends AbstractSafeParcelable implements ResourceEvent {
    public static final Parcelable.Creator<ChangeEvent> CREATOR = new zza();
    private final int zzbs;
    private final DriveId zzk;

    public ChangeEvent(DriveId driveId, int i) {
        this.zzk = driveId;
        this.zzbs = i;
    }

    @Override // com.google.android.gms.drive.events.ResourceEvent
    public final DriveId getDriveId() {
        return this.zzk;
    }

    @Override // com.google.android.gms.drive.events.DriveEvent
    public final int getType() {
        return 1;
    }

    public final boolean hasBeenDeleted() {
        return (this.zzbs & 4) != 0;
    }

    public final boolean hasContentChanged() {
        return (this.zzbs & 2) != 0;
    }

    public final boolean hasMetadataChanged() {
        return (this.zzbs & 1) != 0;
    }

    public final String toString() {
        return String.format(Locale.US, "ChangeEvent [id=%s,changeFlags=%x]", this.zzk, Integer.valueOf(this.zzbs));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzk, i, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zzbs);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
