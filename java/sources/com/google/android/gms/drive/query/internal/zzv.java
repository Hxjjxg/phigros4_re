package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.query.Filter;

/* JADX INFO: loaded from: classes.dex */
public final class zzv extends zza {
    public static final Parcelable.Creator<zzv> CREATOR = new zzw();
    private final FilterHolder zzlz;

    public zzv(Filter filter) {
        this(new FilterHolder(filter));
    }

    zzv(FilterHolder filterHolder) {
        this.zzlz = filterHolder;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzlz, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.drive.query.Filter
    public final <T> T zza(zzj<T> zzjVar) {
        return (T) zzjVar.zza(this.zzlz.getFilter().zza(zzjVar));
    }
}
