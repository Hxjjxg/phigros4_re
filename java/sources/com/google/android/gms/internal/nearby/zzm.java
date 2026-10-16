package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class zzm extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzm> CREATOR = new zzp();
    private zzdz zzar;
    private zzdg zzas;
    private String zzat;
    private byte[] zzau;
    private zzdw zzav;

    private zzm() {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    zzm(IBinder iBinder, IBinder iBinder2, String str, byte[] bArr, IBinder iBinder3) {
        zzdz zzebVar;
        zzdg zzdiVar;
        zzdw zzdyVar = null;
        if (iBinder == null) {
            zzebVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.internal.connection.IResultListener");
            zzebVar = iInterfaceQueryLocalInterface instanceof zzdz ? (zzdz) iInterfaceQueryLocalInterface : new zzeb(iBinder);
        }
        if (iBinder2 == null) {
            zzdiVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface2 = iBinder2.queryLocalInterface("com.google.android.gms.nearby.internal.connection.IConnectionEventListener");
            zzdiVar = iInterfaceQueryLocalInterface2 instanceof zzdg ? (zzdg) iInterfaceQueryLocalInterface2 : new zzdi(iBinder2);
        }
        if (iBinder3 != null) {
            IInterface iInterfaceQueryLocalInterface3 = iBinder3.queryLocalInterface("com.google.android.gms.nearby.internal.connection.IPayloadListener");
            zzdyVar = iInterfaceQueryLocalInterface3 instanceof zzdw ? (zzdw) iInterfaceQueryLocalInterface3 : new zzdy(iBinder3);
        }
        this(zzebVar, zzdiVar, str, bArr, zzdyVar);
    }

    private zzm(zzdz zzdzVar, zzdg zzdgVar, String str, byte[] bArr, zzdw zzdwVar) {
        this.zzar = zzdzVar;
        this.zzas = zzdgVar;
        this.zzat = str;
        this.zzau = bArr;
        this.zzav = zzdwVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzm) {
            zzm zzmVar = (zzm) obj;
            if (Objects.equal(this.zzar, zzmVar.zzar) && Objects.equal(this.zzas, zzmVar.zzas) && Objects.equal(this.zzat, zzmVar.zzat) && Arrays.equals(this.zzau, zzmVar.zzau) && Objects.equal(this.zzav, zzmVar.zzav)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzar, this.zzas, this.zzat, Integer.valueOf(Arrays.hashCode(this.zzau)), this.zzav);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        zzdz zzdzVar = this.zzar;
        SafeParcelWriter.writeIBinder(parcel, 1, zzdzVar == null ? null : zzdzVar.asBinder(), false);
        zzdg zzdgVar = this.zzas;
        SafeParcelWriter.writeIBinder(parcel, 2, zzdgVar == null ? null : zzdgVar.asBinder(), false);
        SafeParcelWriter.writeString(parcel, 3, this.zzat, false);
        SafeParcelWriter.writeByteArray(parcel, 4, this.zzau, false);
        zzdw zzdwVar = this.zzav;
        SafeParcelWriter.writeIBinder(parcel, 5, zzdwVar != null ? zzdwVar.asBinder() : null, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
