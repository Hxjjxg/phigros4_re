package com.google.android.gms.nearby.connection;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class DiscoveryOptions extends AbstractSafeParcelable {
    public static final Parcelable.Creator<DiscoveryOptions> CREATOR = new zzg();
    private Strategy zzh;
    private boolean zzk;
    private boolean zzl;
    private boolean zzw;

    public static final class Builder {
        private final DiscoveryOptions zzx;

        public Builder() {
            this.zzx = new DiscoveryOptions();
        }

        public Builder(DiscoveryOptions discoveryOptions) {
            DiscoveryOptions discoveryOptions2 = new DiscoveryOptions();
            this.zzx = discoveryOptions2;
            discoveryOptions2.zzh = discoveryOptions.zzh;
            discoveryOptions2.zzw = discoveryOptions.zzw;
            discoveryOptions2.zzk = discoveryOptions.zzk;
            discoveryOptions2.zzl = discoveryOptions.zzl;
        }

        public final DiscoveryOptions build() {
            return this.zzx;
        }

        public final Builder setStrategy(Strategy strategy) {
            this.zzx.zzh = strategy;
            return this;
        }
    }

    private DiscoveryOptions() {
        this.zzw = false;
        this.zzk = true;
        this.zzl = true;
    }

    @Deprecated
    public DiscoveryOptions(Strategy strategy) {
        this.zzw = false;
        this.zzk = true;
        this.zzl = true;
        this.zzh = strategy;
    }

    DiscoveryOptions(Strategy strategy, boolean z, boolean z2, boolean z3) {
        this.zzh = strategy;
        this.zzw = z;
        this.zzk = z2;
        this.zzl = z3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DiscoveryOptions) {
            DiscoveryOptions discoveryOptions = (DiscoveryOptions) obj;
            if (Objects.equal(this.zzh, discoveryOptions.zzh) && Objects.equal(Boolean.valueOf(this.zzw), Boolean.valueOf(discoveryOptions.zzw)) && Objects.equal(Boolean.valueOf(this.zzk), Boolean.valueOf(discoveryOptions.zzk)) && Objects.equal(Boolean.valueOf(this.zzl), Boolean.valueOf(discoveryOptions.zzl))) {
                return true;
            }
        }
        return false;
    }

    public final Strategy getStrategy() {
        return this.zzh;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzh, Boolean.valueOf(this.zzw), Boolean.valueOf(this.zzk), Boolean.valueOf(this.zzl));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getStrategy(), i, false);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzw);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzk);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzl);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
