package com.google.android.gms.nearby.messages.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.nearby.messages.MessageFilter;
import com.google.android.gms.nearby.messages.Strategy;

/* JADX INFO: loaded from: classes.dex */
public final class SubscribeRequest extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<SubscribeRequest> CREATOR = new zzcd();
    private final int versionCode;

    @Deprecated
    private final String zzff;

    @Deprecated
    private final boolean zzfg;

    @Deprecated
    private final String zzfj;
    private final boolean zzgb;
    private final int zzgc;
    private final int zzhf;
    private final zzp zzhh;

    @Deprecated
    private final ClientAppContext zzhi;
    private final Strategy zzit;

    @Deprecated
    private final boolean zziu;
    private final zzm zziy;
    private final MessageFilter zziz;
    private final PendingIntent zzja;

    @Deprecated
    private final int zzjb;
    private final byte[] zzjc;
    private final zzaa zzjd;

    public SubscribeRequest(int i, IBinder iBinder, Strategy strategy, IBinder iBinder2, MessageFilter messageFilter, PendingIntent pendingIntent, int i2, String str, String str2, byte[] bArr, boolean z, IBinder iBinder3, boolean z2, ClientAppContext clientAppContext, boolean z3, int i3, int i4) {
        zzm zzoVar;
        zzp zzrVar;
        this.versionCode = i;
        zzaa zzacVar = null;
        if (iBinder == null) {
            zzoVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.messages.internal.IMessageListener");
            zzoVar = iInterfaceQueryLocalInterface instanceof zzm ? (zzm) iInterfaceQueryLocalInterface : new zzo(iBinder);
        }
        this.zziy = zzoVar;
        this.zzit = strategy;
        if (iBinder2 == null) {
            zzrVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface2 = iBinder2.queryLocalInterface("com.google.android.gms.nearby.messages.internal.INearbyMessagesCallback");
            zzrVar = iInterfaceQueryLocalInterface2 instanceof zzp ? (zzp) iInterfaceQueryLocalInterface2 : new zzr(iBinder2);
        }
        this.zzhh = zzrVar;
        this.zziz = messageFilter;
        this.zzja = pendingIntent;
        this.zzjb = i2;
        this.zzff = str;
        this.zzfj = str2;
        this.zzjc = bArr;
        this.zziu = z;
        if (iBinder3 != null && iBinder3 != null) {
            IInterface iInterfaceQueryLocalInterface3 = iBinder3.queryLocalInterface("com.google.android.gms.nearby.messages.internal.ISubscribeCallback");
            zzacVar = iInterfaceQueryLocalInterface3 instanceof zzaa ? (zzaa) iInterfaceQueryLocalInterface3 : new zzac(iBinder3);
        }
        this.zzjd = zzacVar;
        this.zzfg = z2;
        this.zzhi = ClientAppContext.zza(clientAppContext, str2, str, z2);
        this.zzgb = z3;
        this.zzgc = i3;
        this.zzhf = i4;
    }

    public SubscribeRequest(IBinder iBinder, Strategy strategy, IBinder iBinder2, MessageFilter messageFilter, PendingIntent pendingIntent, byte[] bArr, IBinder iBinder3, boolean z, int i) {
        this(iBinder, strategy, iBinder2, messageFilter, null, bArr, iBinder3, z, 0, i);
    }

    public SubscribeRequest(IBinder iBinder, Strategy strategy, IBinder iBinder2, MessageFilter messageFilter, PendingIntent pendingIntent, byte[] bArr, IBinder iBinder3, boolean z, int i, int i2) {
        this(3, iBinder, strategy, iBinder2, messageFilter, pendingIntent, 0, null, null, bArr, false, iBinder3, false, null, z, i, i2);
    }

    public final String toString() {
        String string;
        String strValueOf = String.valueOf(this.zziy);
        String strValueOf2 = String.valueOf(this.zzit);
        String strValueOf3 = String.valueOf(this.zzhh);
        String strValueOf4 = String.valueOf(this.zziz);
        String strValueOf5 = String.valueOf(this.zzja);
        byte[] bArr = this.zzjc;
        if (bArr == null) {
            string = null;
        } else {
            string = new StringBuilder(19).append("<").append(bArr.length).append(" bytes>").toString();
        }
        String strValueOf6 = String.valueOf(this.zzjd);
        boolean z = this.zzfg;
        String strValueOf7 = String.valueOf(this.zzhi);
        boolean z2 = this.zzgb;
        String str = this.zzff;
        String str2 = this.zzfj;
        boolean z3 = this.zziu;
        return new StringBuilder(String.valueOf(strValueOf).length() + 291 + String.valueOf(strValueOf2).length() + String.valueOf(strValueOf3).length() + String.valueOf(strValueOf4).length() + String.valueOf(strValueOf5).length() + String.valueOf(string).length() + String.valueOf(strValueOf6).length() + String.valueOf(strValueOf7).length() + String.valueOf(str).length() + String.valueOf(str2).length()).append("SubscribeRequest{messageListener=").append(strValueOf).append(", strategy=").append(strValueOf2).append(", callback=").append(strValueOf3).append(", filter=").append(strValueOf4).append(", pendingIntent=").append(strValueOf5).append(", hint=").append(string).append(", subscribeCallback=").append(strValueOf6).append(", useRealClientApiKey=").append(z).append(", clientAppContext=").append(strValueOf7).append(", isDiscardPendingIntent=").append(z2).append(", zeroPartyPackageName=").append(str).append(", realClientPackageName=").append(str2).append(", isIgnoreNearbyPermission=").append(z3).append(", callingContext=").append(this.zzhf).append("}").toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        zzm zzmVar = this.zziy;
        SafeParcelWriter.writeIBinder(parcel, 2, zzmVar == null ? null : zzmVar.asBinder(), false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzit, i, false);
        zzp zzpVar = this.zzhh;
        SafeParcelWriter.writeIBinder(parcel, 4, zzpVar == null ? null : zzpVar.asBinder(), false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zziz, i, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzja, i, false);
        SafeParcelWriter.writeInt(parcel, 7, this.zzjb);
        SafeParcelWriter.writeString(parcel, 8, this.zzff, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzfj, false);
        SafeParcelWriter.writeByteArray(parcel, 10, this.zzjc, false);
        SafeParcelWriter.writeBoolean(parcel, 11, this.zziu);
        zzaa zzaaVar = this.zzjd;
        SafeParcelWriter.writeIBinder(parcel, 12, zzaaVar != null ? zzaaVar.asBinder() : null, false);
        SafeParcelWriter.writeBoolean(parcel, 13, this.zzfg);
        SafeParcelWriter.writeParcelable(parcel, 14, this.zzhi, i, false);
        SafeParcelWriter.writeBoolean(parcel, 15, this.zzgb);
        SafeParcelWriter.writeInt(parcel, 16, this.zzgc);
        SafeParcelWriter.writeInt(parcel, 17, this.zzhf);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
