package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.nearby.connection.DiscoveryOptions;

/* JADX INFO: loaded from: classes.dex */
public final class zzgc extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzgc> CREATOR = new zzgf();
    private long durationMillis;
    private zzdz zzar;
    private zzdp zzel;
    private DiscoveryOptions zzem;
    private zzdr zzen;
    private String zzu;

    private zzgc() {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    zzgc(IBinder iBinder, IBinder iBinder2, String str, long j, DiscoveryOptions discoveryOptions, IBinder iBinder3) {
        zzdz zzebVar;
        zzdp zzdqVar;
        zzdr zzdtVar = null;
        if (iBinder == null) {
            zzebVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.internal.connection.IResultListener");
            zzebVar = iInterfaceQueryLocalInterface instanceof zzdz ? (zzdz) iInterfaceQueryLocalInterface : new zzeb(iBinder);
        }
        if (iBinder2 == null) {
            zzdqVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface2 = iBinder2.queryLocalInterface("com.google.android.gms.nearby.internal.connection.IDiscoveryCallback");
            zzdqVar = iInterfaceQueryLocalInterface2 instanceof zzdp ? (zzdp) iInterfaceQueryLocalInterface2 : new zzdq(iBinder2);
        }
        if (iBinder3 != null) {
            IInterface iInterfaceQueryLocalInterface3 = iBinder3.queryLocalInterface("com.google.android.gms.nearby.internal.connection.IDiscoveryListener");
            zzdtVar = iInterfaceQueryLocalInterface3 instanceof zzdr ? (zzdr) iInterfaceQueryLocalInterface3 : new zzdt(iBinder3);
        }
        this(zzebVar, zzdqVar, str, j, discoveryOptions, zzdtVar);
    }

    private zzgc(zzdz zzdzVar, zzdp zzdpVar, String str, long j, DiscoveryOptions discoveryOptions, zzdr zzdrVar) {
        this.zzar = zzdzVar;
        this.zzel = zzdpVar;
        this.zzu = str;
        this.durationMillis = j;
        this.zzem = discoveryOptions;
        this.zzen = zzdrVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzgc) {
            zzgc zzgcVar = (zzgc) obj;
            if (Objects.equal(this.zzar, zzgcVar.zzar) && Objects.equal(this.zzel, zzgcVar.zzel) && Objects.equal(this.zzu, zzgcVar.zzu) && Objects.equal(Long.valueOf(this.durationMillis), Long.valueOf(zzgcVar.durationMillis)) && Objects.equal(this.zzem, zzgcVar.zzem) && Objects.equal(this.zzen, zzgcVar.zzen)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzar, this.zzel, this.zzu, Long.valueOf(this.durationMillis), this.zzem, this.zzen);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        zzdz zzdzVar = this.zzar;
        SafeParcelWriter.writeIBinder(parcel, 1, zzdzVar == null ? null : zzdzVar.asBinder(), false);
        zzdp zzdpVar = this.zzel;
        SafeParcelWriter.writeIBinder(parcel, 2, zzdpVar == null ? null : zzdpVar.asBinder(), false);
        SafeParcelWriter.writeString(parcel, 3, this.zzu, false);
        SafeParcelWriter.writeLong(parcel, 4, this.durationMillis);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzem, i, false);
        zzdr zzdrVar = this.zzen;
        SafeParcelWriter.writeIBinder(parcel, 6, zzdrVar != null ? zzdrVar.asBinder() : null, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
