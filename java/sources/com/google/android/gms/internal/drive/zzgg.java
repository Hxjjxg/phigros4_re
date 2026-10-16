package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.query.internal.FilterHolder;

/* JADX INFO: loaded from: classes.dex */
public final class zzgg extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzgg> CREATOR = new zzgh();
    private final String zzay;
    private final String[] zzaz;
    private final DriveId zzbb;
    private final FilterHolder zzbc;

    public zzgg(String str, String[] strArr, DriveId driveId, FilterHolder filterHolder) {
        this.zzay = str;
        this.zzaz = strArr;
        this.zzbb = driveId;
        this.zzbc = filterHolder;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzay, false);
        SafeParcelWriter.writeStringArray(parcel, 3, this.zzaz, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzbb, i, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzbc, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
