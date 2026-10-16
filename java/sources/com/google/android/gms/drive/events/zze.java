package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveSpace;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zze extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zze> CREATOR = new zzf();
    private final int zzbu;
    private final boolean zzbv;
    private final List<DriveSpace> zzbw;

    zze(int i, boolean z, List<DriveSpace> list) {
        this.zzbu = i;
        this.zzbv = z;
        this.zzbw = list;
    }

    public final boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (obj == this) {
                return true;
            }
            zze zzeVar = (zze) obj;
            if (Objects.equal(this.zzbw, zzeVar.zzbw) && this.zzbu == zzeVar.zzbu && this.zzbv == zzeVar.zzbv) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzbw, Integer.valueOf(this.zzbu), Boolean.valueOf(this.zzbv));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzbu);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzbv);
        SafeParcelWriter.writeTypedList(parcel, 4, this.zzbw, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
