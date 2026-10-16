package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzgb extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzgb> CREATOR = new zzgc();
    private final boolean zzdy;

    public zzgb(boolean z) {
        this.zzdy = z;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzdy);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
