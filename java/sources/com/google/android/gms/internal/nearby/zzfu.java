package com.google.android.gms.internal.nearby;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class zzfu extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfu> CREATOR = new zzfx();
    private zzdz zzar;
    private zzfh zzdk;
    private String[] zzee;
    private boolean zzef;

    private zzfu() {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    zzfu(IBinder iBinder, String[] strArr, zzfh zzfhVar, boolean z) {
        zzdz zzebVar;
        if (iBinder == null) {
            zzebVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.internal.connection.IResultListener");
            zzebVar = iInterfaceQueryLocalInterface instanceof zzdz ? (zzdz) iInterfaceQueryLocalInterface : new zzeb(iBinder);
        }
        this(zzebVar, strArr, zzfhVar, z);
    }

    private zzfu(zzdz zzdzVar, String[] strArr, zzfh zzfhVar, boolean z) {
        this.zzar = zzdzVar;
        this.zzee = strArr;
        this.zzdk = zzfhVar;
        this.zzef = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzfu) {
            zzfu zzfuVar = (zzfu) obj;
            if (Objects.equal(this.zzar, zzfuVar.zzar) && Arrays.equals(this.zzee, zzfuVar.zzee) && Objects.equal(this.zzdk, zzfuVar.zzdk) && Objects.equal(Boolean.valueOf(this.zzef), Boolean.valueOf(zzfuVar.zzef))) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzar, Integer.valueOf(Arrays.hashCode(this.zzee)), this.zzdk, Boolean.valueOf(this.zzef));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        zzdz zzdzVar = this.zzar;
        SafeParcelWriter.writeIBinder(parcel, 1, zzdzVar == null ? null : zzdzVar.asBinder(), false);
        SafeParcelWriter.writeStringArray(parcel, 2, this.zzee, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzdk, i, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzef);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
