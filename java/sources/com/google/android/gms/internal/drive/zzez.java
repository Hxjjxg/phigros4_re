package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzez extends com.google.android.gms.drive.zzu {
    public static final Parcelable.Creator<zzez> CREATOR = new zzfa();
    private final DataHolder zzhb;
    private final List<DriveId> zzhc;
    private final com.google.android.gms.drive.zza zzhd;
    private final boolean zzhe;

    public zzez(DataHolder dataHolder, List<DriveId> list, com.google.android.gms.drive.zza zzaVar, boolean z) {
        this.zzhb = dataHolder;
        this.zzhc = list;
        this.zzhd = zzaVar;
        this.zzhe = z;
    }

    @Override // com.google.android.gms.drive.zzu
    protected final void zza(Parcel parcel, int i) {
        int i2 = i | 1;
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzhb, i2, false);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zzhc, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzhd, i2, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzhe);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
