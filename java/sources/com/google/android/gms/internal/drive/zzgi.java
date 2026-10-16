package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.TransferPreferences;

/* JADX INFO: loaded from: classes.dex */
public final class zzgi extends AbstractSafeParcelable implements TransferPreferences {
    public static final Parcelable.Creator<zzgi> CREATOR = new zzgj();
    private final boolean zzbk;
    private final int zzbl;
    private final int zzgw;

    zzgi(int i, int i2, boolean z) {
        this.zzgw = i;
        this.zzbl = i2;
        this.zzbk = z;
    }

    @Override // com.google.android.gms.drive.TransferPreferences
    public final int getBatteryUsagePreference() {
        return this.zzbl;
    }

    @Override // com.google.android.gms.drive.TransferPreferences
    public final int getNetworkPreference() {
        return this.zzgw;
    }

    @Override // com.google.android.gms.drive.TransferPreferences
    public final boolean isRoamingAllowed() {
        return this.zzbk;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzgw);
        SafeParcelWriter.writeInt(parcel, 3, this.zzbl);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzbk);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
