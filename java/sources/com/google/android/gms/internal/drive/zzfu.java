package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.TransferPreferences;

/* JADX INFO: loaded from: classes.dex */
public final class zzfu extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfu> CREATOR = new zzfv();
    private final zzgi zzhv;

    zzfu(zzgi zzgiVar) {
        this.zzhv = zzgiVar;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzhv, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final TransferPreferences zzao() {
        return this.zzhv;
    }
}
