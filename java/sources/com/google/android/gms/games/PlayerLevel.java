package com.google.android.gms.games;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: loaded from: classes.dex */
public final class PlayerLevel extends com.google.android.gms.games.internal.zzd {
    public static final Parcelable.Creator<PlayerLevel> CREATOR = new zzaq();
    private final int zzcp;
    private final long zzcq;
    private final long zzcr;

    public PlayerLevel(int i, long j, long j2) {
        Preconditions.checkState(j >= 0, "Min XP must be positive!");
        Preconditions.checkState(j2 > j, "Max XP must be more than min XP!");
        this.zzcp = i;
        this.zzcq = j;
        this.zzcr = j2;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof PlayerLevel)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        PlayerLevel playerLevel = (PlayerLevel) obj;
        return Objects.equal(Integer.valueOf(playerLevel.getLevelNumber()), Integer.valueOf(getLevelNumber())) && Objects.equal(Long.valueOf(playerLevel.getMinXp()), Long.valueOf(getMinXp())) && Objects.equal(Long.valueOf(playerLevel.getMaxXp()), Long.valueOf(getMaxXp()));
    }

    public final int getLevelNumber() {
        return this.zzcp;
    }

    public final long getMaxXp() {
        return this.zzcr;
    }

    public final long getMinXp() {
        return this.zzcq;
    }

    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzcp), Long.valueOf(this.zzcq), Long.valueOf(this.zzcr));
    }

    public final String toString() {
        return Objects.toStringHelper(this).add("LevelNumber", Integer.valueOf(getLevelNumber())).add("MinXp", Long.valueOf(getMinXp())).add("MaxXp", Long.valueOf(getMaxXp())).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, getLevelNumber());
        SafeParcelWriter.writeLong(parcel, 2, getMinXp());
        SafeParcelWriter.writeLong(parcel, 3, getMaxXp());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
