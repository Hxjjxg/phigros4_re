package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* JADX INFO: loaded from: classes.dex */
public final class zzw extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzw> CREATOR = new zzx();
    private final String zzal;
    private final Contents zzdd;
    private final MetadataBundle zzdl;
    private final Integer zzdm;
    private final DriveId zzdn;
    private final boolean zzdo;
    private final int zzdp;
    private final int zzdq;

    public zzw(DriveId driveId, MetadataBundle metadataBundle, int i, int i2, ExecutionOptions executionOptions) {
        this(driveId, metadataBundle, null, i2, executionOptions.zzl(), executionOptions.zzk(), executionOptions.zzm(), i);
    }

    zzw(DriveId driveId, MetadataBundle metadataBundle, Contents contents, int i, boolean z, String str, int i2, int i3) {
        if (contents != null && i3 != 0) {
            Preconditions.checkArgument(contents.getRequestId() == i3, "inconsistent contents reference");
        }
        if (i == 0 && contents == null && i3 == 0) {
            throw new IllegalArgumentException("Need a valid contents");
        }
        this.zzdn = (DriveId) Preconditions.checkNotNull(driveId);
        this.zzdl = (MetadataBundle) Preconditions.checkNotNull(metadataBundle);
        this.zzdd = contents;
        this.zzdm = Integer.valueOf(i);
        this.zzal = str;
        this.zzdp = i2;
        this.zzdo = z;
        this.zzdq = i3;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdn, i, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzdl, i, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzdd, i, false);
        SafeParcelWriter.writeIntegerObject(parcel, 5, this.zzdm, false);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzdo);
        SafeParcelWriter.writeString(parcel, 7, this.zzal, false);
        SafeParcelWriter.writeInt(parcel, 8, this.zzdp);
        SafeParcelWriter.writeInt(parcel, 9, this.zzdq);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
