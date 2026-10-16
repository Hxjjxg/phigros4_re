package com.google.android.gms.nearby.messages;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.games.quest.Quests;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class Strategy extends AbstractSafeParcelable {
    public static final Strategy BLE_ONLY;
    public static final Parcelable.Creator<Strategy> CREATOR = new zzg();
    public static final Strategy DEFAULT = new Builder().build();
    public static final int DISCOVERY_MODE_BROADCAST = 1;
    public static final int DISCOVERY_MODE_DEFAULT = 3;
    public static final int DISCOVERY_MODE_SCAN = 2;
    public static final int DISTANCE_TYPE_DEFAULT = 0;
    public static final int DISTANCE_TYPE_EARSHOT = 1;
    public static final int TTL_SECONDS_DEFAULT = 300;
    public static final int TTL_SECONDS_INFINITE = Integer.MAX_VALUE;
    public static final int TTL_SECONDS_MAX = 86400;

    @Deprecated
    private static final Strategy zzfm;
    private final int zzex;

    @Deprecated
    private final int zzfn;
    private final int zzfo;
    private final int zzfp;

    @Deprecated
    private final boolean zzfq;
    private final int zzfr;
    private final int zzfs;
    private final int zzft;

    public static class Builder {
        private int zzfu = 3;
        private int zzfv = 300;
        private int zzfw = 0;
        private int zzfx = -1;
        private int zzfy = 0;

        public Strategy build() {
            if (this.zzfx == 2 && this.zzfw == 1) {
                throw new IllegalStateException("Cannot set EARSHOT with BLE only mode.");
            }
            return new Strategy(2, 0, this.zzfv, this.zzfw, false, this.zzfx, this.zzfu, 0);
        }

        public Builder setDiscoveryMode(int i) {
            this.zzfu = i;
            return this;
        }

        public Builder setDistanceType(int i) {
            this.zzfw = i;
            return this;
        }

        public Builder setTtlSeconds(int i) {
            Preconditions.checkArgument(i == Integer.MAX_VALUE || (i > 0 && i <= 86400), "mTtlSeconds(%d) must either be TTL_SECONDS_INFINITE, or it must be between 1 and TTL_SECONDS_MAX(%d) inclusive", Integer.valueOf(i), 86400);
            this.zzfv = i;
            return this;
        }

        public final Builder zze(int i) {
            this.zzfx = 2;
            return this;
        }
    }

    static {
        Strategy strategyBuild = new Builder().zze(2).setTtlSeconds(Integer.MAX_VALUE).build();
        BLE_ONLY = strategyBuild;
        zzfm = strategyBuild;
    }

    /* JADX WARN: Code duplicated, block: B:4:0x000b A[PHI: r8
  0x000b: PHI (r8v2 int) = (r8v0 int), (r8v1 int) binds: [B:3:0x0009, B:7:0x0011] A[DONT_GENERATE, DONT_INLINE]] */
    Strategy(int i, int i2, int i3, int i4, boolean z, int i5, int i6, int i7) {
        this.zzex = i;
        this.zzfn = i2;
        if (i2 == 0) {
            this.zzfs = i6;
        } else if (i2 != 2) {
            i6 = 3;
            if (i2 != 3) {
                this.zzfs = i6;
            } else {
                this.zzfs = 2;
            }
        } else {
            this.zzfs = 1;
        }
        this.zzfp = i4;
        this.zzfq = z;
        if (z) {
            this.zzfr = 2;
            this.zzfo = Integer.MAX_VALUE;
        } else {
            this.zzfo = i3;
            if (i5 == -1 || i5 == 0 || i5 == 1 || i5 == 6) {
                this.zzfr = -1;
            } else {
                this.zzfr = i5;
            }
        }
        this.zzft = i7;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Strategy)) {
            return false;
        }
        Strategy strategy = (Strategy) obj;
        return this.zzex == strategy.zzex && this.zzfs == strategy.zzfs && this.zzfo == strategy.zzfo && this.zzfp == strategy.zzfp && this.zzfr == strategy.zzfr && this.zzft == strategy.zzft;
    }

    public int hashCode() {
        return (((((((((this.zzex * 31) + this.zzfs) * 31) + this.zzfo) * 31) + this.zzfp) * 31) + this.zzfr) * 31) + this.zzft;
    }

    public String toString() {
        String string;
        String string2;
        String string3;
        int i = this.zzfo;
        int i2 = this.zzfp;
        String string4 = "DEFAULT";
        if (i2 != 0) {
            string = i2 != 1 ? new StringBuilder(19).append("UNKNOWN:").append(i2).toString() : "EARSHOT";
        } else {
            string = "DEFAULT";
        }
        int i3 = this.zzfr;
        if (i3 == -1) {
            string2 = "DEFAULT";
        } else {
            ArrayList arrayList = new ArrayList();
            if ((i3 & 4) > 0) {
                arrayList.add("ULTRASOUND");
            }
            if ((i3 & 2) > 0) {
                arrayList.add("BLE");
            }
            string2 = arrayList.isEmpty() ? new StringBuilder(19).append("UNKNOWN:").append(i3).toString() : arrayList.toString();
        }
        int i4 = this.zzfs;
        if (i4 == 3) {
            string3 = "DEFAULT";
        } else {
            ArrayList arrayList2 = new ArrayList();
            if ((i4 & 1) > 0) {
                arrayList2.add("BROADCAST");
            }
            if ((i4 & 2) > 0) {
                arrayList2.add("SCAN");
            }
            string3 = arrayList2.isEmpty() ? new StringBuilder(19).append("UNKNOWN:").append(i4).toString() : arrayList2.toString();
        }
        int i5 = this.zzft;
        if (i5 != 0) {
            string4 = i5 != 1 ? new StringBuilder(20).append("UNKNOWN: ").append(i5).toString() : "ALWAYS_ON";
        }
        return new StringBuilder(String.valueOf(string).length() + Quests.SELECT_ENDING_SOON + String.valueOf(string2).length() + String.valueOf(string3).length() + String.valueOf(string4).length()).append("Strategy{ttlSeconds=").append(i).append(", distanceType=").append(string).append(", discoveryMedium=").append(string2).append(", discoveryMode=").append(string3).append(", backgroundScanMode=").append(string4).append('}').toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzfn);
        SafeParcelWriter.writeInt(parcel, 2, this.zzfo);
        SafeParcelWriter.writeInt(parcel, 3, this.zzfp);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzfq);
        SafeParcelWriter.writeInt(parcel, 5, this.zzfr);
        SafeParcelWriter.writeInt(parcel, 6, this.zzfs);
        SafeParcelWriter.writeInt(parcel, 7, this.zzft);
        SafeParcelWriter.writeInt(parcel, 1000, this.zzex);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final int zzae() {
        return this.zzft;
    }
}
