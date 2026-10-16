package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzfn extends com.google.android.gms.drive.zzu {
    public static final Parcelable.Creator<zzfn> CREATOR = new zzfo();
    final boolean zzdy;
    final DataHolder zzhs;

    public zzfn(DataHolder dataHolder, boolean z) {
        this.zzhs = dataHolder;
        this.zzdy = z;
    }

    @Override // com.google.android.gms.drive.zzu
    protected final void zza(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzhs, i, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzdy);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final DataHolder zzal() {
        return this.zzhs;
    }
}
