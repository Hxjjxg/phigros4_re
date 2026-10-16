package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzfz extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfz> CREATOR = new zzga();
    private final ParcelFileDescriptor zzhx;
    private final IBinder zzhy;
    private final String zzm;

    zzfz(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String str) {
        this.zzhx = parcelFileDescriptor;
        this.zzhy = iBinder;
        this.zzm = str;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzhx, i | 1, false);
        SafeParcelWriter.writeIBinder(parcel, 3, this.zzhy, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzm, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
