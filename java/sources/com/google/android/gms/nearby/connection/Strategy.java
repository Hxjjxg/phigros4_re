package com.google.android.gms.nearby.connection;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class Strategy extends AbstractSafeParcelable {
    private final int zzaj;
    private final int zzak;
    public static final Parcelable.Creator<Strategy> CREATOR = new zzj();
    public static final Strategy P2P_CLUSTER = new Strategy(1, 3);
    public static final Strategy P2P_STAR = new Strategy(1, 2);
    public static final Strategy P2P_POINT_TO_POINT = new Strategy(1, 1);

    Strategy(int i, int i2) {
        this.zzaj = i;
        this.zzak = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Strategy)) {
            return false;
        }
        Strategy strategy = (Strategy) obj;
        return this.zzaj == strategy.zzaj && this.zzak == strategy.zzak;
    }

    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzaj), Integer.valueOf(this.zzak));
    }

    public final String toString() {
        String str;
        Locale locale = Locale.US;
        Object[] objArr = new Object[3];
        if (P2P_CLUSTER.equals(this)) {
            str = "P2P_CLUSTER";
        } else if (P2P_STAR.equals(this)) {
            str = "P2P_STAR";
        } else {
            str = P2P_POINT_TO_POINT.equals(this) ? "P2P_POINT_TO_POINT" : "UNKNOWN";
        }
        objArr[0] = str;
        objArr[1] = Integer.valueOf(this.zzaj);
        objArr[2] = Integer.valueOf(this.zzak);
        return String.format(locale, "Strategy(%s){connectionType=%d, topology=%d}", objArr);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 3, this.zzaj);
        SafeParcelWriter.writeInt(parcel, 4, this.zzak);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
