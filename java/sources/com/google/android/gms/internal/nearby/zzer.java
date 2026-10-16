package com.google.android.gms.internal.nearby;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class zzer extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzer> CREATOR = new zzes();
    private String zzca;
    private String zzq;
    private String zzu;
    private BluetoothDevice zzv;

    private zzer() {
    }

    zzer(String str, String str2, String str3, BluetoothDevice bluetoothDevice) {
        this.zzca = str;
        this.zzu = str2;
        this.zzq = str3;
        this.zzv = bluetoothDevice;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzer) {
            zzer zzerVar = (zzer) obj;
            if (Objects.equal(this.zzca, zzerVar.zzca) && Objects.equal(this.zzu, zzerVar.zzu) && Objects.equal(this.zzq, zzerVar.zzq) && Objects.equal(this.zzv, zzerVar.zzv)) {
                return true;
            }
        }
        return false;
    }

    public final String getEndpointName() {
        return this.zzq;
    }

    public final String getServiceId() {
        return this.zzu;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzca, this.zzu, this.zzq, this.zzv);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzca, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzu, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzq, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzv, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String zze() {
        return this.zzca;
    }

    public final BluetoothDevice zzk() {
        return this.zzv;
    }
}
