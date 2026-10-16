package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzfr extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfr> CREATOR = new zzfw();
    private final boolean zzhu;

    public zzfr(boolean z) {
        this.zzhu = z;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzhu);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
