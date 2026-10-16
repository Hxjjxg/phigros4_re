package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.Contents;

/* JADX INFO: loaded from: classes.dex */
public final class zzfb extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfb> CREATOR = new zzfc();
    final Contents zzeq;
    final boolean zzhf;

    public zzfb(Contents contents, boolean z) {
        this.zzeq = contents;
        this.zzhf = z;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzeq, i, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzhf);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final Contents zzai() {
        return this.zzeq;
    }
}
