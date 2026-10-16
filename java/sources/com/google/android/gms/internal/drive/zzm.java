package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* JADX INFO: loaded from: classes.dex */
public final class zzm extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzm> CREATOR = new zzn();
    private final String zzal;
    private final boolean zzam;
    private final boolean zzar;
    private final DriveId zzdb;
    private final MetadataBundle zzdc;
    private final Contents zzdd;
    private final int zzde;
    private final int zzdf;
    private final boolean zzdg;

    public zzm(DriveId driveId, MetadataBundle metadataBundle, int i, boolean z, com.google.android.gms.drive.zzn zznVar) {
        this(driveId, metadataBundle, null, zznVar.zzl(), zznVar.zzk(), zznVar.zzm(), i, z, zznVar.zzo());
    }

    zzm(DriveId driveId, MetadataBundle metadataBundle, Contents contents, boolean z, String str, int i, int i2, boolean z2, boolean z3) {
        this.zzdb = driveId;
        this.zzdc = metadataBundle;
        this.zzdd = contents;
        this.zzam = z;
        this.zzal = str;
        this.zzde = i;
        this.zzdf = i2;
        this.zzdg = z2;
        this.zzar = z3;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdb, i, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzdc, i, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzdd, i, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzam);
        SafeParcelWriter.writeString(parcel, 6, this.zzal, false);
        SafeParcelWriter.writeInt(parcel, 7, this.zzde);
        SafeParcelWriter.writeInt(parcel, 8, this.zzdf);
        SafeParcelWriter.writeBoolean(parcel, 9, this.zzdg);
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzar);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
