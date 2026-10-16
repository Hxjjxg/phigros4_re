package com.google.android.gms.nearby.messages.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzj extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzj> CREATOR = new zzk();
    private final int versionCode;

    @Deprecated
    private final ClientAppContext zzhi;
    private final int zzhj;

    public zzj(int i) {
        this(1, null, i);
    }

    zzj(int i, ClientAppContext clientAppContext, int i2) {
        this.versionCode = i;
        this.zzhi = clientAppContext;
        this.zzhj = i2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzhi, i, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zzhj);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
