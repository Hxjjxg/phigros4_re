package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* JADX INFO: loaded from: classes.dex */
public final class zzu extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzu> CREATOR = new zzv();
    private final String zzay;
    private final DriveId zzbb;
    private final MetadataBundle zzdl;
    private final Integer zzdm;
    private final int zzj;

    public zzu(MetadataBundle metadataBundle, int i, String str, DriveId driveId, Integer num) {
        this.zzdl = metadataBundle;
        this.zzj = i;
        this.zzay = str;
        this.zzbb = driveId;
        this.zzdm = num;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdl, i, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zzj);
        SafeParcelWriter.writeString(parcel, 4, this.zzay, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzbb, i, false);
        SafeParcelWriter.writeIntegerObject(parcel, 6, this.zzdm, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
