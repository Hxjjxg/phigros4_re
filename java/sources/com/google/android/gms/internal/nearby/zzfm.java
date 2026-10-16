package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzfm extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfm> CREATOR = new zzfp();
    private zzdz zzar;
    private String zzat;

    private zzfm() {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    zzfm(IBinder iBinder, String str) {
        zzdz zzebVar;
        if (iBinder == null) {
            zzebVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.internal.connection.IResultListener");
            zzebVar = iInterfaceQueryLocalInterface instanceof zzdz ? (zzdz) iInterfaceQueryLocalInterface : new zzeb(iBinder);
        }
        this(zzebVar, str);
    }

    private zzfm(zzdz zzdzVar, String str) {
        this.zzar = zzdzVar;
        this.zzat = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzfm) {
            zzfm zzfmVar = (zzfm) obj;
            if (Objects.equal(this.zzar, zzfmVar.zzar) && Objects.equal(this.zzat, zzfmVar.zzat)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzar, this.zzat);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        zzdz zzdzVar = this.zzar;
        SafeParcelWriter.writeIBinder(parcel, 1, zzdzVar == null ? null : zzdzVar.asBinder(), false);
        SafeParcelWriter.writeString(parcel, 2, this.zzat, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
