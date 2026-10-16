package com.google.android.gms.nearby.connection;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class AdvertisingOptions extends AbstractSafeParcelable {
    public static final Parcelable.Creator<AdvertisingOptions> CREATOR = new zzb();
    private Strategy zzh;
    private boolean zzi;
    private boolean zzj;
    private boolean zzk;
    private boolean zzl;
    private byte[] zzm;

    public static final class Builder {
        private final AdvertisingOptions zzn;

        public Builder() {
            this.zzn = new AdvertisingOptions();
        }

        public Builder(AdvertisingOptions advertisingOptions) {
            AdvertisingOptions advertisingOptions2 = new AdvertisingOptions();
            this.zzn = advertisingOptions2;
            advertisingOptions2.zzh = advertisingOptions.zzh;
            advertisingOptions2.zzi = advertisingOptions.zzi;
            advertisingOptions2.zzj = advertisingOptions.zzj;
            advertisingOptions2.zzk = advertisingOptions.zzk;
            advertisingOptions2.zzl = advertisingOptions.zzl;
            advertisingOptions2.zzm = advertisingOptions.zzm;
        }

        public final AdvertisingOptions build() {
            return this.zzn;
        }

        public final Builder setStrategy(Strategy strategy) {
            this.zzn.zzh = strategy;
            return this;
        }
    }

    private AdvertisingOptions() {
        this.zzi = true;
        this.zzj = true;
        this.zzk = true;
        this.zzl = true;
    }

    @Deprecated
    public AdvertisingOptions(Strategy strategy) {
        this.zzi = true;
        this.zzj = true;
        this.zzk = true;
        this.zzl = true;
        this.zzh = strategy;
    }

    AdvertisingOptions(Strategy strategy, boolean z, boolean z2, boolean z3, boolean z4, byte[] bArr) {
        this.zzh = strategy;
        this.zzi = z;
        this.zzj = z2;
        this.zzk = z3;
        this.zzl = z4;
        this.zzm = bArr;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AdvertisingOptions) {
            AdvertisingOptions advertisingOptions = (AdvertisingOptions) obj;
            if (Objects.equal(this.zzh, advertisingOptions.zzh) && Objects.equal(Boolean.valueOf(this.zzi), Boolean.valueOf(advertisingOptions.zzi)) && Objects.equal(Boolean.valueOf(this.zzj), Boolean.valueOf(advertisingOptions.zzj)) && Objects.equal(Boolean.valueOf(this.zzk), Boolean.valueOf(advertisingOptions.zzk)) && Objects.equal(Boolean.valueOf(this.zzl), Boolean.valueOf(advertisingOptions.zzl)) && Arrays.equals(this.zzm, advertisingOptions.zzm)) {
                return true;
            }
        }
        return false;
    }

    public final Strategy getStrategy() {
        return this.zzh;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzh, Boolean.valueOf(this.zzi), Boolean.valueOf(this.zzj), Boolean.valueOf(this.zzk), Boolean.valueOf(this.zzl), Integer.valueOf(Arrays.hashCode(this.zzm)));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getStrategy(), i, false);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzi);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzj);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzk);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzl);
        SafeParcelWriter.writeByteArray(parcel, 6, this.zzm, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
