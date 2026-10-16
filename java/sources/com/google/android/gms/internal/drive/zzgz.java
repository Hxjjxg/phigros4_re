package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* JADX INFO: loaded from: classes.dex */
public final class zzgz extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzgz> CREATOR = new zzha();
    private final DriveId zzdb;
    private final MetadataBundle zzdc;

    public zzgz(DriveId driveId, MetadataBundle metadataBundle) {
        this.zzdb = driveId;
        this.zzdc = metadataBundle;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdb, i, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzdc, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
