package com.google.android.gms.nearby.messages.internal;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.collection.ArraySet;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.nearby.zzgr;
import com.google.android.gms.internal.nearby.zzgs;
import com.google.android.gms.nearby.messages.Message;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class Update extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<Update> CREATOR = new zzci();
    private final int versionCode;
    public final Message zzhk;
    private final int zzje;
    public final zze zzjf;
    public final zza zzjg;
    public final zzgs zzjh;
    private final byte[] zzji;

    Update(int i, int i2, Message message, zze zzeVar, zza zzaVar, zzgs zzgsVar, byte[] bArr) {
        this.versionCode = i;
        if (zza(i2, 2)) {
            zzeVar = null;
            i2 = 2;
            zzaVar = null;
            zzgsVar = null;
            bArr = null;
        }
        this.zzje = i2;
        this.zzhk = message;
        this.zzjf = zzeVar;
        this.zzjg = zzaVar;
        this.zzjh = zzgsVar;
        this.zzji = bArr;
    }

    private static boolean zza(int i, int i2) {
        return (i & i2) != 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Update)) {
            return false;
        }
        Update update = (Update) obj;
        return this.zzje == update.zzje && Objects.equal(this.zzhk, update.zzhk) && Objects.equal(this.zzjf, update.zzjf) && Objects.equal(this.zzjg, update.zzjg) && Objects.equal(this.zzjh, update.zzjh) && Arrays.equals(this.zzji, update.zzji);
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzje), this.zzhk, this.zzjf, this.zzjg, this.zzjh, this.zzji);
    }

    public String toString() {
        ArraySet arraySet = new ArraySet();
        if (zzg(1)) {
            arraySet.add("FOUND");
        }
        if (zzg(2)) {
            arraySet.add("LOST");
        }
        if (zzg(4)) {
            arraySet.add("DISTANCE");
        }
        if (zzg(8)) {
            arraySet.add("BLE_SIGNAL");
        }
        if (zzg(16)) {
            arraySet.add("DEVICE");
        }
        if (zzg(32)) {
            arraySet.add("BLE_RECORD");
        }
        String strValueOf = String.valueOf(arraySet);
        String strValueOf2 = String.valueOf(this.zzhk);
        String strValueOf3 = String.valueOf(this.zzjf);
        String strValueOf4 = String.valueOf(this.zzjg);
        String strValueOf5 = String.valueOf(this.zzjh);
        String strValueOf6 = String.valueOf(zzgr.zzd(this.zzji));
        return new StringBuilder(String.valueOf(strValueOf).length() + 68 + String.valueOf(strValueOf2).length() + String.valueOf(strValueOf3).length() + String.valueOf(strValueOf4).length() + String.valueOf(strValueOf5).length() + String.valueOf(strValueOf6).length()).append("Update{types=").append(strValueOf).append(", message=").append(strValueOf2).append(", distance=").append(strValueOf3).append(", bleSignal=").append(strValueOf4).append(", device=").append(strValueOf5).append(", bleRecord=").append(strValueOf6).append("}").toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeInt(parcel, 2, this.zzje);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzhk, i, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzjf, i, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzjg, i, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzjh, i, false);
        SafeParcelWriter.writeByteArray(parcel, 7, this.zzji, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final boolean zzg(int i) {
        return zza(this.zzje, i);
    }
}
