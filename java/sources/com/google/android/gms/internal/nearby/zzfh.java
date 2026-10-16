package com.google.android.gms.internal.nearby;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class zzfh extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfh> CREATOR = new zzfk();
    private long id;
    private int type;
    private ParcelFileDescriptor zzdv;
    private String zzdw;
    private long zzdx;
    private ParcelFileDescriptor zzdy;
    private byte[] zzy;

    private zzfh() {
        this.zzdx = -1L;
    }

    zzfh(long j, int i, byte[] bArr, ParcelFileDescriptor parcelFileDescriptor, String str, long j2, ParcelFileDescriptor parcelFileDescriptor2) {
        this.id = j;
        this.type = i;
        this.zzy = bArr;
        this.zzdv = parcelFileDescriptor;
        this.zzdw = str;
        this.zzdx = j2;
        this.zzdy = parcelFileDescriptor2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzfh) {
            zzfh zzfhVar = (zzfh) obj;
            if (Objects.equal(Long.valueOf(this.id), Long.valueOf(zzfhVar.id)) && Objects.equal(Integer.valueOf(this.type), Integer.valueOf(zzfhVar.type)) && Arrays.equals(this.zzy, zzfhVar.zzy) && Objects.equal(this.zzdv, zzfhVar.zzdv) && Objects.equal(this.zzdw, zzfhVar.zzdw) && Objects.equal(Long.valueOf(this.zzdx), Long.valueOf(zzfhVar.zzdx)) && Objects.equal(this.zzdy, zzfhVar.zzdy)) {
                return true;
            }
        }
        return false;
    }

    public final byte[] getBytes() {
        return this.zzy;
    }

    public final long getId() {
        return this.id;
    }

    public final int getType() {
        return this.type;
    }

    public final int hashCode() {
        return Objects.hashCode(Long.valueOf(this.id), Integer.valueOf(this.type), Integer.valueOf(Arrays.hashCode(this.zzy)), this.zzdv, this.zzdw, Long.valueOf(this.zzdx), this.zzdy);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 1, this.id);
        SafeParcelWriter.writeInt(parcel, 2, this.type);
        SafeParcelWriter.writeByteArray(parcel, 3, this.zzy, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzdv, i, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzdw, false);
        SafeParcelWriter.writeLong(parcel, 6, this.zzdx);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzdy, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final ParcelFileDescriptor zzo() {
        return this.zzdv;
    }

    public final String zzp() {
        return this.zzdw;
    }

    public final long zzq() {
        return this.zzdx;
    }
}
