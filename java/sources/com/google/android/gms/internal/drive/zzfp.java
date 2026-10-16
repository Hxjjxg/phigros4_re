package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzfp extends com.google.android.gms.drive.zzu {
    public static final Parcelable.Creator<zzfp> CREATOR = new zzfq();
    final DataHolder zzht;

    public zzfp(DataHolder dataHolder) {
        this.zzht = dataHolder;
    }

    @Override // com.google.android.gms.drive.zzu
    protected final void zza(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzht, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final DataHolder zzam() {
        return this.zzht;
    }
}
