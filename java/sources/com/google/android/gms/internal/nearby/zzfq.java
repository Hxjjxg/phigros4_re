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
public final class zzfq extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfq> CREATOR = new zzft();
    private String name;
    private zzdz zzar;
    private zzdg zzas;
    private String zzat;
    private byte[] zzau;
    private zzdm zzeb;
    private zzdj zzec;

    private zzfq() {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    zzfq(IBinder iBinder, IBinder iBinder2, IBinder iBinder3, String str, String str2, byte[] bArr, IBinder iBinder4) {
        zzdz zzebVar;
        zzdg zzdiVar;
        zzdm zzdoVar;
        zzdj zzdlVar = null;
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
        if (iBinder3 == null) {
            zzdoVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface3 = iBinder3.queryLocalInterface("com.google.android.gms.nearby.internal.connection.IConnectionResponseListener");
            zzdoVar = iInterfaceQueryLocalInterface3 instanceof zzdm ? (zzdm) iInterfaceQueryLocalInterface3 : new zzdo(iBinder3);
        }
        if (iBinder4 != null) {
            IInterface iInterfaceQueryLocalInterface4 = iBinder4.queryLocalInterface("com.google.android.gms.nearby.internal.connection.IConnectionLifecycleListener");
            zzdlVar = iInterfaceQueryLocalInterface4 instanceof zzdj ? (zzdj) iInterfaceQueryLocalInterface4 : new zzdl(iBinder4);
        }
        this(zzebVar, zzdiVar, zzdoVar, str, str2, bArr, zzdlVar);
    }

    private zzfq(zzdz zzdzVar, zzdg zzdgVar, zzdm zzdmVar, String str, String str2, byte[] bArr, zzdj zzdjVar) {
        this.zzar = zzdzVar;
        this.zzas = zzdgVar;
        this.zzeb = zzdmVar;
        this.name = str;
        this.zzat = str2;
        this.zzau = bArr;
        this.zzec = zzdjVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzfq) {
            zzfq zzfqVar = (zzfq) obj;
            if (Objects.equal(this.zzar, zzfqVar.zzar) && Objects.equal(this.zzas, zzfqVar.zzas) && Objects.equal(this.zzeb, zzfqVar.zzeb) && Objects.equal(this.name, zzfqVar.name) && Objects.equal(this.zzat, zzfqVar.zzat) && Arrays.equals(this.zzau, zzfqVar.zzau) && Objects.equal(this.zzec, zzfqVar.zzec)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzar, this.zzas, this.zzeb, this.name, this.zzat, Integer.valueOf(Arrays.hashCode(this.zzau)), this.zzec);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        zzdz zzdzVar = this.zzar;
        SafeParcelWriter.writeIBinder(parcel, 1, zzdzVar == null ? null : zzdzVar.asBinder(), false);
        zzdg zzdgVar = this.zzas;
        SafeParcelWriter.writeIBinder(parcel, 2, zzdgVar == null ? null : zzdgVar.asBinder(), false);
        zzdm zzdmVar = this.zzeb;
        SafeParcelWriter.writeIBinder(parcel, 3, zzdmVar == null ? null : zzdmVar.asBinder(), false);
        SafeParcelWriter.writeString(parcel, 4, this.name, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzat, false);
        SafeParcelWriter.writeByteArray(parcel, 6, this.zzau, false);
        zzdj zzdjVar = this.zzec;
        SafeParcelWriter.writeIBinder(parcel, 7, zzdjVar != null ? zzdjVar.asBinder() : null, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
