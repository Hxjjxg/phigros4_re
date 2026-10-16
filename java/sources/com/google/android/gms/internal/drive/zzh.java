package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;

/* JADX INFO: loaded from: classes.dex */
public final class zzh extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzh> CREATOR = new zzi();
    final int status;
    final int zzcr;
    final long zzcu;
    final long zzcv;
    final DriveId zzk;

    public zzh(int i, DriveId driveId, int i2, long j, long j2) {
        this.zzcr = i;
        this.zzk = driveId;
        this.status = i2;
        this.zzcu = j;
        this.zzcv = j2;
    }

    public final boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (obj == this) {
                return true;
            }
            zzh zzhVar = (zzh) obj;
            if (this.zzcr == zzhVar.zzcr && Objects.equal(this.zzk, zzhVar.zzk) && this.status == zzhVar.status && this.zzcu == zzhVar.zzcu && this.zzcv == zzhVar.zzcv) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzcr), this.zzk, Integer.valueOf(this.status), Long.valueOf(this.zzcu), Long.valueOf(this.zzcv));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzcr);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzk, i, false);
        SafeParcelWriter.writeInt(parcel, 4, this.status);
        SafeParcelWriter.writeLong(parcel, 5, this.zzcu);
        SafeParcelWriter.writeLong(parcel, 6, this.zzcv);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
