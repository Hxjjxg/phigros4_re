package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public final class zzgu extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzgu> CREATOR = new zzgv();
    private final int zzex;
    private final int zzgy;
    private final byte[] zzgz;
    private final boolean zzha;

    zzgu(int i, int i2, byte[] bArr, boolean z) {
        this.zzex = i;
        this.zzgy = i2;
        this.zzgz = bArr;
        this.zzha = z;
    }

    private zzgu(int i, byte[] bArr) {
        this(1, i, bArr, false);
    }

    public static zzgu zza(UUID uuid, Short sh, Short sh2) {
        return new zzgu(3, new com.google.android.gms.nearby.messages.internal.zzl(uuid, sh, sh2).getBytes());
    }

    public static zzgu zzb(String str, String str2) {
        String strValueOf = String.valueOf(str);
        if (str2 == null) {
            str2 = "";
        }
        String strValueOf2 = String.valueOf(str2);
        return new zzgu(2, new com.google.android.gms.nearby.messages.internal.zzg(strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf)).getBytes());
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzgy);
        SafeParcelWriter.writeByteArray(parcel, 2, this.zzgz, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzha);
        SafeParcelWriter.writeInt(parcel, 1000, this.zzex);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
