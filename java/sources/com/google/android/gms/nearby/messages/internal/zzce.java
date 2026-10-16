package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzce extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzce> CREATOR = new zzcf();
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
    private final zzaf zzis;

    zzce(int i, zzaf zzafVar, IBinder iBinder, String str, String str2, boolean z, ClientAppContext clientAppContext) {
        zzp zzrVar;
        this.versionCode = i;
        this.zzis = zzafVar;
        if (iBinder == null) {
            zzrVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.messages.internal.INearbyMessagesCallback");
            zzrVar = iInterfaceQueryLocalInterface instanceof zzp ? (zzp) iInterfaceQueryLocalInterface : new zzr(iBinder);
        }
        this.zzhh = zzrVar;
        this.zzff = str;
        this.zzfj = str2;
        this.zzfg = z;
        this.zzhi = ClientAppContext.zza(clientAppContext, str2, str, z);
    }

    public zzce(zzaf zzafVar, IBinder iBinder) {
        this(1, zzafVar, iBinder, null, null, false, null);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzis, i, false);
        SafeParcelWriter.writeIBinder(parcel, 3, this.zzhh.asBinder(), false);
        SafeParcelWriter.writeString(parcel, 4, this.zzff, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzfj, false);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzfg);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzhi, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
