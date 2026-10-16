package com.google.android.gms.drive;

import android.os.Parcel;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzu extends AbstractSafeParcelable {
    private volatile transient boolean zzbr = false;

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Preconditions.checkState(!this.zzbr);
        this.zzbr = true;
        zza(parcel, i);
    }

    protected abstract void zza(Parcel parcel, int i);
}
