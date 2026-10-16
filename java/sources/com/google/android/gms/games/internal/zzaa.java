package com.google.android.gms.games.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzaa extends zzd {
    public static final Parcelable.Creator<zzaa> CREATOR = new zzab();
    private final Bundle zzja;
    private final IBinder zzjb;

    zzaa(Bundle bundle, IBinder iBinder) {
        this.zzja = bundle;
        this.zzjb = iBinder;
    }

    public zzaa(zzae zzaeVar) {
        this.zzja = zzaeVar.zzbk();
        this.zzjb = zzaeVar.zzjb;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 1, this.zzja, false);
        SafeParcelWriter.writeIBinder(parcel, 2, this.zzjb, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
