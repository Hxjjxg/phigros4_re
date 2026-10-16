package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.nearby.messages.Strategy;

/* JADX INFO: loaded from: classes.dex */
public final class zzbz extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzbz> CREATOR = new zzca();
    private final int versionCode;

    @Deprecated
    private final String zzff;

    @Deprecated
    private final boolean zzfg;

    @Deprecated
    private final String zzfj;
    private final int zzhf;
    private final zzp zzhh;

    @Deprecated
    private final ClientAppContext zzhi;
    private final zzaf zzis;
    private final Strategy zzit;

    @Deprecated
    private final boolean zziu;
    private final zzu zziv;

    zzbz(int i, zzaf zzafVar, Strategy strategy, IBinder iBinder, String str, String str2, boolean z, IBinder iBinder2, boolean z2, ClientAppContext clientAppContext, int i2) {
        zzp zzrVar;
        this.versionCode = i;
        this.zzis = zzafVar;
        this.zzit = strategy;
        zzu zzwVar = null;
        if (iBinder == null) {
            zzrVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.messages.internal.INearbyMessagesCallback");
            zzrVar = iInterfaceQueryLocalInterface instanceof zzp ? (zzp) iInterfaceQueryLocalInterface : new zzr(iBinder);
        }
        this.zzhh = zzrVar;
        this.zzff = str;
        this.zzfj = str2;
        this.zziu = z;
        if (iBinder2 != null && iBinder2 != null) {
            IInterface iInterfaceQueryLocalInterface2 = iBinder2.queryLocalInterface("com.google.android.gms.nearby.messages.internal.IPublishCallback");
            zzwVar = iInterfaceQueryLocalInterface2 instanceof zzu ? (zzu) iInterfaceQueryLocalInterface2 : new zzw(iBinder2);
        }
        this.zziv = zzwVar;
        this.zzfg = z2;
        this.zzhi = ClientAppContext.zza(clientAppContext, str2, str, z2);
        this.zzhf = i2;
    }

    public zzbz(zzaf zzafVar, Strategy strategy, IBinder iBinder, IBinder iBinder2, int i) {
        this(2, zzafVar, strategy, iBinder, null, null, false, iBinder2, false, null, i);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzis, i, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzit, i, false);
        SafeParcelWriter.writeIBinder(parcel, 4, this.zzhh.asBinder(), false);
        SafeParcelWriter.writeString(parcel, 5, this.zzff, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzfj, false);
        SafeParcelWriter.writeBoolean(parcel, 7, this.zziu);
        zzu zzuVar = this.zziv;
        SafeParcelWriter.writeIBinder(parcel, 8, zzuVar == null ? null : zzuVar.asBinder(), false);
        SafeParcelWriter.writeBoolean(parcel, 9, this.zzfg);
        SafeParcelWriter.writeParcelable(parcel, 10, this.zzhi, i, false);
        SafeParcelWriter.writeInt(parcel, 11, this.zzhf);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
