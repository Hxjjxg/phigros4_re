package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzcb extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzcb> CREATOR = new zzcc();
    private final int versionCode;

    @Deprecated
    private String zzff;
    private final zzp zzhh;

    @Deprecated
    private final ClientAppContext zzhi;
    private final zzx zziw;
    public boolean zzix;

    zzcb(int i, IBinder iBinder, IBinder iBinder2, boolean z, String str, ClientAppContext clientAppContext) {
        zzp zzrVar;
        zzx zzzVar;
        this.versionCode = i;
        if (iBinder == null) {
            zzrVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.messages.internal.INearbyMessagesCallback");
            zzrVar = iInterfaceQueryLocalInterface instanceof zzp ? (zzp) iInterfaceQueryLocalInterface : new zzr(iBinder);
        }
        this.zzhh = zzrVar;
        if (iBinder2 == null) {
            zzzVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface2 = iBinder2.queryLocalInterface("com.google.android.gms.nearby.messages.internal.IStatusCallback");
            zzzVar = iInterfaceQueryLocalInterface2 instanceof zzx ? (zzx) iInterfaceQueryLocalInterface2 : new zzz(iBinder2);
        }
        this.zziw = zzzVar;
        this.zzix = z;
        this.zzff = str;
        this.zzhi = ClientAppContext.zza(clientAppContext, null, str, false);
    }

    public zzcb(IBinder iBinder, IBinder iBinder2) {
        this(1, iBinder, iBinder2, false, null, null);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeIBinder(parcel, 2, this.zzhh.asBinder(), false);
        SafeParcelWriter.writeIBinder(parcel, 3, this.zziw.asBinder(), false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzix);
        SafeParcelWriter.writeString(parcel, 5, this.zzff, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzhi, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
