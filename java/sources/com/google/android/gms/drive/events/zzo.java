package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzo extends com.google.android.gms.drive.zzu implements DriveEvent {
    public static final Parcelable.Creator<zzo> CREATOR = new zzp();
    private final DataHolder zzat;
    private final boolean zzco;
    private final int zzcp;

    public zzo(DataHolder dataHolder, boolean z, int i) {
        this.zzat = dataHolder;
        this.zzco = z;
        this.zzcp = i;
    }

    @Override // com.google.android.gms.drive.events.DriveEvent
    public final int getType() {
        return 3;
    }

    @Override // com.google.android.gms.drive.zzu
    public final void zza(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzat, i, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzco);
        SafeParcelWriter.writeInt(parcel, 4, this.zzcp);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final int zzaa() {
        return this.zzcp;
    }

    public final DataHolder zzy() {
        return this.zzat;
    }

    public final boolean zzz() {
        return this.zzco;
    }
}
