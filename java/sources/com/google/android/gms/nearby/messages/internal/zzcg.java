package com.google.android.gms.nearby.messages.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzcg extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzcg> CREATOR = new zzch();
    private final int versionCode;

    @Deprecated
    private final String zzff;

    @Deprecated
    private final boolean zzfg;

    @Deprecated
    private final String zzfj;
    private final zzp zzhh;

    @Deprecated
    private final ClientAppContext zzhi;
    private final zzm zziy;
    private final PendingIntent zzja;

    @Deprecated
    private final int zzjb;

    public zzcg(int i, IBinder iBinder, IBinder iBinder2, PendingIntent pendingIntent, int i2, String str, String str2, boolean z, ClientAppContext clientAppContext) {
        zzm zzoVar;
        this.versionCode = i;
        zzp zzrVar = null;
        if (iBinder == null) {
            zzoVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.messages.internal.IMessageListener");
            zzoVar = iInterfaceQueryLocalInterface instanceof zzm ? (zzm) iInterfaceQueryLocalInterface : new zzo(iBinder);
        }
        this.zziy = zzoVar;
        if (iBinder2 != null) {
            IInterface iInterfaceQueryLocalInterface2 = iBinder2.queryLocalInterface("com.google.android.gms.nearby.messages.internal.INearbyMessagesCallback");
            zzrVar = iInterfaceQueryLocalInterface2 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface2 : new zzr(iBinder2);
        }
        this.zzhh = zzrVar;
        this.zzja = pendingIntent;
        this.zzjb = i2;
        this.zzff = str;
        this.zzfj = str2;
        this.zzfg = z;
        this.zzhi = ClientAppContext.zza(clientAppContext, str2, str, z);
    }

    public zzcg(IBinder iBinder, IBinder iBinder2, PendingIntent pendingIntent) {
        this(1, iBinder, iBinder2, pendingIntent, 0, null, null, false, null);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        zzm zzmVar = this.zziy;
        SafeParcelWriter.writeIBinder(parcel, 2, zzmVar == null ? null : zzmVar.asBinder(), false);
        SafeParcelWriter.writeIBinder(parcel, 3, this.zzhh.asBinder(), false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzja, i, false);
        SafeParcelWriter.writeInt(parcel, 5, this.zzjb);
        SafeParcelWriter.writeString(parcel, 6, this.zzff, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzfj, false);
        SafeParcelWriter.writeBoolean(parcel, 8, this.zzfg);
        SafeParcelWriter.writeParcelable(parcel, 9, this.zzhi, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
