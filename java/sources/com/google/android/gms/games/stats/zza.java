package com.google.android.gms.games.stats;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.games.internal.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class zza extends zzd implements PlayerStats {
    public static final Parcelable.Creator<zza> CREATOR = new zzb();
    private final float zzre;
    private final float zzrf;
    private final int zzrg;
    private final int zzrh;
    private final int zzri;
    private final float zzrj;
    private final float zzrk;
    private final Bundle zzrl;
    private final float zzrm;
    private final float zzrn;
    private final float zzro;

    zza(float f, float f2, int i, int i2, int i3, float f3, float f4, Bundle bundle, float f5, float f6, float f7) {
        this.zzre = f;
        this.zzrf = f2;
        this.zzrg = i;
        this.zzrh = i2;
        this.zzri = i3;
        this.zzrj = f3;
        this.zzrk = f4;
        this.zzrl = bundle;
        this.zzrm = f5;
        this.zzrn = f6;
        this.zzro = f7;
    }

    public zza(PlayerStats playerStats) {
        this.zzre = playerStats.getAverageSessionLength();
        this.zzrf = playerStats.getChurnProbability();
        this.zzrg = playerStats.getDaysSinceLastPlayed();
        this.zzrh = playerStats.getNumberOfPurchases();
        this.zzri = playerStats.getNumberOfSessions();
        this.zzrj = playerStats.getSessionPercentile();
        this.zzrk = playerStats.getSpendPercentile();
        this.zzrm = playerStats.getSpendProbability();
        this.zzrn = playerStats.getHighSpenderProbability();
        this.zzro = playerStats.getTotalSpendNext28Days();
        this.zzrl = playerStats.zzcn();
    }

    static int zza(PlayerStats playerStats) {
        return Objects.hashCode(Float.valueOf(playerStats.getAverageSessionLength()), Float.valueOf(playerStats.getChurnProbability()), Integer.valueOf(playerStats.getDaysSinceLastPlayed()), Integer.valueOf(playerStats.getNumberOfPurchases()), Integer.valueOf(playerStats.getNumberOfSessions()), Float.valueOf(playerStats.getSessionPercentile()), Float.valueOf(playerStats.getSpendPercentile()), Float.valueOf(playerStats.getSpendProbability()), Float.valueOf(playerStats.getHighSpenderProbability()), Float.valueOf(playerStats.getTotalSpendNext28Days()));
    }

    static boolean zza(PlayerStats playerStats, Object obj) {
        if (!(obj instanceof PlayerStats)) {
            return false;
        }
        if (playerStats == obj) {
            return true;
        }
        PlayerStats playerStats2 = (PlayerStats) obj;
        return Objects.equal(Float.valueOf(playerStats2.getAverageSessionLength()), Float.valueOf(playerStats.getAverageSessionLength())) && Objects.equal(Float.valueOf(playerStats2.getChurnProbability()), Float.valueOf(playerStats.getChurnProbability())) && Objects.equal(Integer.valueOf(playerStats2.getDaysSinceLastPlayed()), Integer.valueOf(playerStats.getDaysSinceLastPlayed())) && Objects.equal(Integer.valueOf(playerStats2.getNumberOfPurchases()), Integer.valueOf(playerStats.getNumberOfPurchases())) && Objects.equal(Integer.valueOf(playerStats2.getNumberOfSessions()), Integer.valueOf(playerStats.getNumberOfSessions())) && Objects.equal(Float.valueOf(playerStats2.getSessionPercentile()), Float.valueOf(playerStats.getSessionPercentile())) && Objects.equal(Float.valueOf(playerStats2.getSpendPercentile()), Float.valueOf(playerStats.getSpendPercentile())) && Objects.equal(Float.valueOf(playerStats2.getSpendProbability()), Float.valueOf(playerStats.getSpendProbability())) && Objects.equal(Float.valueOf(playerStats2.getHighSpenderProbability()), Float.valueOf(playerStats.getHighSpenderProbability())) && Objects.equal(Float.valueOf(playerStats2.getTotalSpendNext28Days()), Float.valueOf(playerStats.getTotalSpendNext28Days()));
    }

    static String zzb(PlayerStats playerStats) {
        return Objects.toStringHelper(playerStats).add("AverageSessionLength", Float.valueOf(playerStats.getAverageSessionLength())).add("ChurnProbability", Float.valueOf(playerStats.getChurnProbability())).add("DaysSinceLastPlayed", Integer.valueOf(playerStats.getDaysSinceLastPlayed())).add("NumberOfPurchases", Integer.valueOf(playerStats.getNumberOfPurchases())).add("NumberOfSessions", Integer.valueOf(playerStats.getNumberOfSessions())).add("SessionPercentile", Float.valueOf(playerStats.getSessionPercentile())).add("SpendPercentile", Float.valueOf(playerStats.getSpendPercentile())).add("SpendProbability", Float.valueOf(playerStats.getSpendProbability())).add("HighSpenderProbability", Float.valueOf(playerStats.getHighSpenderProbability())).add("TotalSpendNext28Days", Float.valueOf(playerStats.getTotalSpendNext28Days())).toString();
    }

    public final boolean equals(Object obj) {
        return zza(this, obj);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final /* bridge */ /* synthetic */ PlayerStats freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final float getAverageSessionLength() {
        return this.zzre;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final float getChurnProbability() {
        return this.zzrf;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final int getDaysSinceLastPlayed() {
        return this.zzrg;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final float getHighSpenderProbability() {
        return this.zzrn;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final int getNumberOfPurchases() {
        return this.zzrh;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final int getNumberOfSessions() {
        return this.zzri;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final float getSessionPercentile() {
        return this.zzrj;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final float getSpendPercentile() {
        return this.zzrk;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final float getSpendProbability() {
        return this.zzrm;
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final float getTotalSpendNext28Days() {
        return this.zzro;
    }

    public final int hashCode() {
        return zza(this);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final boolean isDataValid() {
        return true;
    }

    public final String toString() {
        return zzb(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeFloat(parcel, 1, getAverageSessionLength());
        SafeParcelWriter.writeFloat(parcel, 2, getChurnProbability());
        SafeParcelWriter.writeInt(parcel, 3, getDaysSinceLastPlayed());
        SafeParcelWriter.writeInt(parcel, 4, getNumberOfPurchases());
        SafeParcelWriter.writeInt(parcel, 5, getNumberOfSessions());
        SafeParcelWriter.writeFloat(parcel, 6, getSessionPercentile());
        SafeParcelWriter.writeFloat(parcel, 7, getSpendPercentile());
        SafeParcelWriter.writeBundle(parcel, 8, this.zzrl, false);
        SafeParcelWriter.writeFloat(parcel, 9, getSpendProbability());
        SafeParcelWriter.writeFloat(parcel, 10, getHighSpenderProbability());
        SafeParcelWriter.writeFloat(parcel, 11, getTotalSpendNext28Days());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.games.stats.PlayerStats
    public final Bundle zzcn() {
        return this.zzrl;
    }
}
