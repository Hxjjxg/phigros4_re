package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.Contents;

/* JADX INFO: loaded from: classes.dex */
public final class zzo extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzo> CREATOR = new zzp();
    private final Contents zzdd;
    private final int zzdf;
    private final Boolean zzdh;

    public zzo(int i, boolean z) {
        this(null, false, i);
    }

    zzo(Contents contents, Boolean bool, int i) {
        this.zzdd = contents;
        this.zzdh = bool;
        this.zzdf = i;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdd, i, false);
        SafeParcelWriter.writeBooleanObject(parcel, 3, this.zzdh, false);
        SafeParcelWriter.writeInt(parcel, 4, this.zzdf);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
