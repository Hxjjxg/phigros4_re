package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.nearby.connection.AdvertisingOptions;

/* JADX INFO: loaded from: classes.dex */
public final class zzfy extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfy> CREATOR = new zzgb();
    private long durationMillis;
    private String name;
    private zzdj zzec;
    private zzec zzeh;
    private zzdd zzei;
    private AdvertisingOptions zzej;
    private String zzu;

    private zzfy() {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    zzfy(IBinder iBinder, IBinder iBinder2, String str, String str2, long j, AdvertisingOptions advertisingOptions, IBinder iBinder3) {
        zzec zzeeVar;
        zzdd zzdfVar;
        zzdj zzdlVar = null;
        if (iBinder == null) {
            zzeeVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.internal.connection.IStartAdvertisingResultListener");
            zzeeVar = iInterfaceQueryLocalInterface instanceof zzec ? (zzec) iInterfaceQueryLocalInterface : new zzee(iBinder);
        }
        if (iBinder2 == null) {
            zzdfVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface2 = iBinder2.queryLocalInterface("com.google.android.gms.nearby.internal.connection.IAdvertisingCallback");
            zzdfVar = iInterfaceQueryLocalInterface2 instanceof zzdd ? (zzdd) iInterfaceQueryLocalInterface2 : new zzdf(iBinder2);
        }
        if (iBinder3 != null) {
            IInterface iInterfaceQueryLocalInterface3 = iBinder3.queryLocalInterface("com.google.android.gms.nearby.internal.connection.IConnectionLifecycleListener");
            zzdlVar = iInterfaceQueryLocalInterface3 instanceof zzdj ? (zzdj) iInterfaceQueryLocalInterface3 : new zzdl(iBinder3);
        }
        this(zzeeVar, zzdfVar, str, str2, j, advertisingOptions, zzdlVar);
    }

    private zzfy(zzec zzecVar, zzdd zzddVar, String str, String str2, long j, AdvertisingOptions advertisingOptions, zzdj zzdjVar) {
        this.zzeh = zzecVar;
        this.zzei = zzddVar;
        this.name = str;
        this.zzu = str2;
        this.durationMillis = j;
        this.zzej = advertisingOptions;
        this.zzec = zzdjVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzfy) {
            zzfy zzfyVar = (zzfy) obj;
            if (Objects.equal(this.zzeh, zzfyVar.zzeh) && Objects.equal(this.zzei, zzfyVar.zzei) && Objects.equal(this.name, zzfyVar.name) && Objects.equal(this.zzu, zzfyVar.zzu) && Objects.equal(Long.valueOf(this.durationMillis), Long.valueOf(zzfyVar.durationMillis)) && Objects.equal(this.zzej, zzfyVar.zzej) && Objects.equal(this.zzec, zzfyVar.zzec)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzeh, this.zzei, this.name, this.zzu, Long.valueOf(this.durationMillis), this.zzej, this.zzec);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        zzec zzecVar = this.zzeh;
        SafeParcelWriter.writeIBinder(parcel, 1, zzecVar == null ? null : zzecVar.asBinder(), false);
        zzdd zzddVar = this.zzei;
        SafeParcelWriter.writeIBinder(parcel, 2, zzddVar == null ? null : zzddVar.asBinder(), false);
        SafeParcelWriter.writeString(parcel, 3, this.name, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzu, false);
        SafeParcelWriter.writeLong(parcel, 5, this.durationMillis);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzej, i, false);
        zzdj zzdjVar = this.zzec;
        SafeParcelWriter.writeIBinder(parcel, 7, zzdjVar != null ? zzdjVar.asBinder() : null, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
