package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.drive.zzhm;
import com.google.android.gms.internal.drive.zzix;

/* JADX INFO: loaded from: classes.dex */
public class zza extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zza> CREATOR = new zzb();
    private final long zze;
    private final long zzf;
    private final long zzg;
    private volatile String zzh = null;

    public zza(long j, long j2, long j3) {
        Preconditions.checkArgument(j != -1);
        Preconditions.checkArgument(j2 != -1);
        Preconditions.checkArgument(j3 != -1);
        this.zze = j;
        this.zzf = j2;
        this.zzg = j3;
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == zza.class) {
            zza zzaVar = (zza) obj;
            if (zzaVar.zzf == this.zzf && zzaVar.zzg == this.zzg && zzaVar.zze == this.zze) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String strValueOf = String.valueOf(this.zze);
        String strValueOf2 = String.valueOf(this.zzf);
        String strValueOf3 = String.valueOf(this.zzg);
        return new StringBuilder(String.valueOf(strValueOf).length() + String.valueOf(strValueOf2).length() + String.valueOf(strValueOf3).length()).append(strValueOf).append(strValueOf2).append(strValueOf3).toString().hashCode();
    }

    public String toString() {
        if (this.zzh == null) {
            zzhm zzhmVar = new zzhm();
            zzhmVar.versionCode = 1;
            zzhmVar.zze = this.zze;
            zzhmVar.zzf = this.zzf;
            zzhmVar.zzg = this.zzg;
            String strValueOf = String.valueOf(Base64.encodeToString(zzix.zza(zzhmVar), 10));
            this.zzh = strValueOf.length() != 0 ? "ChangeSequenceNumber:".concat(strValueOf) : new String("ChangeSequenceNumber:");
        }
        return this.zzh;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 2, this.zze);
        SafeParcelWriter.writeLong(parcel, 3, this.zzf);
        SafeParcelWriter.writeLong(parcel, 4, this.zzg);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
