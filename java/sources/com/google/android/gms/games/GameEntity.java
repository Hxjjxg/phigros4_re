package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;

/* JADX INFO: loaded from: classes.dex */
public final class GameEntity extends GamesDowngradeableSafeParcel implements Game {
    public static final Parcelable.Creator<GameEntity> CREATOR = new zza();
    private final String description;
    private final boolean zzaa;
    private final boolean zzab;
    private final String zzac;
    private final String zzad;
    private final String zzae;
    private final boolean zzaf;
    private final boolean zzag;
    private final boolean zzah;
    private final String zzai;
    private final boolean zzaj;
    private final String zzm;
    private final String zzn;
    private final String zzo;
    private final String zzp;
    private final String zzq;
    private final Uri zzr;
    private final Uri zzs;
    private final Uri zzt;
    private final boolean zzu;
    private final boolean zzv;
    private final String zzw;
    private final int zzx;
    private final int zzy;
    private final int zzz;

    static final class zza extends zzh {
        zza() {
        }

        @Override // com.google.android.gms.games.zzh, android.os.Parcelable.Creator
        public final /* synthetic */ GameEntity createFromParcel(Parcel parcel) {
            return createFromParcel(parcel);
        }

        @Override // com.google.android.gms.games.zzh
        /* JADX INFO: renamed from: zzb */
        public final GameEntity createFromParcel(Parcel parcel) {
            if (GameEntity.zzb(GameEntity.getUnparcelClientVersion()) || GameEntity.canUnparcelSafely(GameEntity.class.getCanonicalName())) {
                return super.createFromParcel(parcel);
            }
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            String string4 = parcel.readString();
            String string5 = parcel.readString();
            String string6 = parcel.readString();
            String string7 = parcel.readString();
            Uri uri = string7 == null ? null : Uri.parse(string7);
            String string8 = parcel.readString();
            Uri uri2 = string8 == null ? null : Uri.parse(string8);
            String string9 = parcel.readString();
            return new GameEntity(string, string2, string3, string4, string5, string6, uri, uri2, string9 == null ? null : Uri.parse(string9), parcel.readInt() > 0, parcel.readInt() > 0, parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), false, false, null, null, null, false, false, false, null, false);
        }
    }

    public GameEntity(Game game) {
        this.zzm = game.getApplicationId();
        this.zzo = game.getPrimaryCategory();
        this.zzp = game.getSecondaryCategory();
        this.description = game.getDescription();
        this.zzq = game.getDeveloperName();
        this.zzn = game.getDisplayName();
        this.zzr = game.getIconImageUri();
        this.zzac = game.getIconImageUrl();
        this.zzs = game.getHiResImageUri();
        this.zzad = game.getHiResImageUrl();
        this.zzt = game.getFeaturedImageUri();
        this.zzae = game.getFeaturedImageUrl();
        this.zzu = game.zza();
        this.zzv = game.zzc();
        this.zzw = game.zzd();
        this.zzx = 1;
        this.zzy = game.getAchievementTotalCount();
        this.zzz = game.getLeaderboardCount();
        this.zzaa = game.isRealTimeMultiplayerEnabled();
        this.zzab = game.isTurnBasedMultiplayerEnabled();
        this.zzaf = game.isMuted();
        this.zzag = game.zzb();
        this.zzah = game.areSnapshotsEnabled();
        this.zzai = game.getThemeColor();
        this.zzaj = game.hasGamepadSupport();
    }

    GameEntity(String str, String str2, String str3, String str4, String str5, String str6, Uri uri, Uri uri2, Uri uri3, boolean z, boolean z2, String str7, int i, int i2, int i3, boolean z3, boolean z4, String str8, String str9, String str10, boolean z5, boolean z6, boolean z7, String str11, boolean z8) {
        this.zzm = str;
        this.zzn = str2;
        this.zzo = str3;
        this.zzp = str4;
        this.description = str5;
        this.zzq = str6;
        this.zzr = uri;
        this.zzac = str8;
        this.zzs = uri2;
        this.zzad = str9;
        this.zzt = uri3;
        this.zzae = str10;
        this.zzu = z;
        this.zzv = z2;
        this.zzw = str7;
        this.zzx = i;
        this.zzy = i2;
        this.zzz = i3;
        this.zzaa = z3;
        this.zzab = z4;
        this.zzaf = z5;
        this.zzag = z6;
        this.zzah = z7;
        this.zzai = str11;
        this.zzaj = z8;
    }

    static int zza(Game game) {
        return Objects.hashCode(game.getApplicationId(), game.getDisplayName(), game.getPrimaryCategory(), game.getSecondaryCategory(), game.getDescription(), game.getDeveloperName(), game.getIconImageUri(), game.getHiResImageUri(), game.getFeaturedImageUri(), Boolean.valueOf(game.zza()), Boolean.valueOf(game.zzc()), game.zzd(), Integer.valueOf(game.getAchievementTotalCount()), Integer.valueOf(game.getLeaderboardCount()), Boolean.valueOf(game.isRealTimeMultiplayerEnabled()), Boolean.valueOf(game.isTurnBasedMultiplayerEnabled()), Boolean.valueOf(game.isMuted()), Boolean.valueOf(game.zzb()), Boolean.valueOf(game.areSnapshotsEnabled()), game.getThemeColor(), Boolean.valueOf(game.hasGamepadSupport()));
    }

    static boolean zza(Game game, Object obj) {
        if (!(obj instanceof Game)) {
            return false;
        }
        if (game == obj) {
            return true;
        }
        Game game2 = (Game) obj;
        return Objects.equal(game2.getApplicationId(), game.getApplicationId()) && Objects.equal(game2.getDisplayName(), game.getDisplayName()) && Objects.equal(game2.getPrimaryCategory(), game.getPrimaryCategory()) && Objects.equal(game2.getSecondaryCategory(), game.getSecondaryCategory()) && Objects.equal(game2.getDescription(), game.getDescription()) && Objects.equal(game2.getDeveloperName(), game.getDeveloperName()) && Objects.equal(game2.getIconImageUri(), game.getIconImageUri()) && Objects.equal(game2.getHiResImageUri(), game.getHiResImageUri()) && Objects.equal(game2.getFeaturedImageUri(), game.getFeaturedImageUri()) && Objects.equal(Boolean.valueOf(game2.zza()), Boolean.valueOf(game.zza())) && Objects.equal(Boolean.valueOf(game2.zzc()), Boolean.valueOf(game.zzc())) && Objects.equal(game2.zzd(), game.zzd()) && Objects.equal(Integer.valueOf(game2.getAchievementTotalCount()), Integer.valueOf(game.getAchievementTotalCount())) && Objects.equal(Integer.valueOf(game2.getLeaderboardCount()), Integer.valueOf(game.getLeaderboardCount())) && Objects.equal(Boolean.valueOf(game2.isRealTimeMultiplayerEnabled()), Boolean.valueOf(game.isRealTimeMultiplayerEnabled())) && Objects.equal(Boolean.valueOf(game2.isTurnBasedMultiplayerEnabled()), Boolean.valueOf(game.isTurnBasedMultiplayerEnabled())) && Objects.equal(Boolean.valueOf(game2.isMuted()), Boolean.valueOf(game.isMuted())) && Objects.equal(Boolean.valueOf(game2.zzb()), Boolean.valueOf(game.zzb())) && Objects.equal(Boolean.valueOf(game2.areSnapshotsEnabled()), Boolean.valueOf(game.areSnapshotsEnabled())) && Objects.equal(game2.getThemeColor(), game.getThemeColor()) && Objects.equal(Boolean.valueOf(game2.hasGamepadSupport()), Boolean.valueOf(game.hasGamepadSupport()));
    }

    static String zzb(Game game) {
        return Objects.toStringHelper(game).add("ApplicationId", game.getApplicationId()).add("DisplayName", game.getDisplayName()).add("PrimaryCategory", game.getPrimaryCategory()).add("SecondaryCategory", game.getSecondaryCategory()).add("Description", game.getDescription()).add("DeveloperName", game.getDeveloperName()).add("IconImageUri", game.getIconImageUri()).add("IconImageUrl", game.getIconImageUrl()).add("HiResImageUri", game.getHiResImageUri()).add("HiResImageUrl", game.getHiResImageUrl()).add("FeaturedImageUri", game.getFeaturedImageUri()).add("FeaturedImageUrl", game.getFeaturedImageUrl()).add("PlayEnabledGame", Boolean.valueOf(game.zza())).add("InstanceInstalled", Boolean.valueOf(game.zzc())).add("InstancePackageName", game.zzd()).add("AchievementTotalCount", Integer.valueOf(game.getAchievementTotalCount())).add("LeaderboardCount", Integer.valueOf(game.getLeaderboardCount())).add("RealTimeMultiplayerEnabled", Boolean.valueOf(game.isRealTimeMultiplayerEnabled())).add("TurnBasedMultiplayerEnabled", Boolean.valueOf(game.isTurnBasedMultiplayerEnabled())).add("AreSnapshotsEnabled", Boolean.valueOf(game.areSnapshotsEnabled())).add("ThemeColor", game.getThemeColor()).add("HasGamepadSupport", Boolean.valueOf(game.hasGamepadSupport())).toString();
    }

    @Override // com.google.android.gms.games.Game
    public final boolean areSnapshotsEnabled() {
        return this.zzah;
    }

    public final boolean equals(Object obj) {
        return zza(this, obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.common.data.Freezable
    public final Game freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.Game
    public final int getAchievementTotalCount() {
        return this.zzy;
    }

    @Override // com.google.android.gms.games.Game
    public final String getApplicationId() {
        return this.zzm;
    }

    @Override // com.google.android.gms.games.Game
    public final String getDescription() {
        return this.description;
    }

    @Override // com.google.android.gms.games.Game
    public final void getDescription(CharArrayBuffer charArrayBuffer) {
        DataUtils.copyStringToBuffer(this.description, charArrayBuffer);
    }

    @Override // com.google.android.gms.games.Game
    public final String getDeveloperName() {
        return this.zzq;
    }

    @Override // com.google.android.gms.games.Game
    public final void getDeveloperName(CharArrayBuffer charArrayBuffer) {
        DataUtils.copyStringToBuffer(this.zzq, charArrayBuffer);
    }

    @Override // com.google.android.gms.games.Game
    public final String getDisplayName() {
        return this.zzn;
    }

    @Override // com.google.android.gms.games.Game
    public final void getDisplayName(CharArrayBuffer charArrayBuffer) {
        DataUtils.copyStringToBuffer(this.zzn, charArrayBuffer);
    }

    @Override // com.google.android.gms.games.Game
    public final Uri getFeaturedImageUri() {
        return this.zzt;
    }

    @Override // com.google.android.gms.games.Game
    public final String getFeaturedImageUrl() {
        return this.zzae;
    }

    @Override // com.google.android.gms.games.Game
    public final Uri getHiResImageUri() {
        return this.zzs;
    }

    @Override // com.google.android.gms.games.Game
    public final String getHiResImageUrl() {
        return this.zzad;
    }

    @Override // com.google.android.gms.games.Game
    public final Uri getIconImageUri() {
        return this.zzr;
    }

    @Override // com.google.android.gms.games.Game
    public final String getIconImageUrl() {
        return this.zzac;
    }

    @Override // com.google.android.gms.games.Game
    public final int getLeaderboardCount() {
        return this.zzz;
    }

    @Override // com.google.android.gms.games.Game
    public final String getPrimaryCategory() {
        return this.zzo;
    }

    @Override // com.google.android.gms.games.Game
    public final String getSecondaryCategory() {
        return this.zzp;
    }

    @Override // com.google.android.gms.games.Game
    public final String getThemeColor() {
        return this.zzai;
    }

    @Override // com.google.android.gms.games.Game
    public final boolean hasGamepadSupport() {
        return this.zzaj;
    }

    public final int hashCode() {
        return zza(this);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final boolean isDataValid() {
        return true;
    }

    @Override // com.google.android.gms.games.Game
    public final boolean isMuted() {
        return this.zzaf;
    }

    @Override // com.google.android.gms.games.Game
    public final boolean isRealTimeMultiplayerEnabled() {
        return this.zzaa;
    }

    @Override // com.google.android.gms.games.Game
    public final boolean isTurnBasedMultiplayerEnabled() {
        return this.zzab;
    }

    public final String toString() {
        return zzb(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        if (shouldDowngrade()) {
            parcel.writeString(this.zzm);
            parcel.writeString(this.zzn);
            parcel.writeString(this.zzo);
            parcel.writeString(this.zzp);
            parcel.writeString(this.description);
            parcel.writeString(this.zzq);
            Uri uri = this.zzr;
            parcel.writeString(uri == null ? null : uri.toString());
            Uri uri2 = this.zzs;
            parcel.writeString(uri2 == null ? null : uri2.toString());
            Uri uri3 = this.zzt;
            parcel.writeString(uri3 != null ? uri3.toString() : null);
            parcel.writeInt(this.zzu ? 1 : 0);
            parcel.writeInt(this.zzv ? 1 : 0);
            parcel.writeString(this.zzw);
            parcel.writeInt(this.zzx);
            parcel.writeInt(this.zzy);
            parcel.writeInt(this.zzz);
            return;
        }
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getApplicationId(), false);
        SafeParcelWriter.writeString(parcel, 2, getDisplayName(), false);
        SafeParcelWriter.writeString(parcel, 3, getPrimaryCategory(), false);
        SafeParcelWriter.writeString(parcel, 4, getSecondaryCategory(), false);
        SafeParcelWriter.writeString(parcel, 5, getDescription(), false);
        SafeParcelWriter.writeString(parcel, 6, getDeveloperName(), false);
        SafeParcelWriter.writeParcelable(parcel, 7, getIconImageUri(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 8, getHiResImageUri(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 9, getFeaturedImageUri(), i, false);
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzu);
        SafeParcelWriter.writeBoolean(parcel, 11, this.zzv);
        SafeParcelWriter.writeString(parcel, 12, this.zzw, false);
        SafeParcelWriter.writeInt(parcel, 13, this.zzx);
        SafeParcelWriter.writeInt(parcel, 14, getAchievementTotalCount());
        SafeParcelWriter.writeInt(parcel, 15, getLeaderboardCount());
        SafeParcelWriter.writeBoolean(parcel, 16, isRealTimeMultiplayerEnabled());
        SafeParcelWriter.writeBoolean(parcel, 17, isTurnBasedMultiplayerEnabled());
        SafeParcelWriter.writeString(parcel, 18, getIconImageUrl(), false);
        SafeParcelWriter.writeString(parcel, 19, getHiResImageUrl(), false);
        SafeParcelWriter.writeString(parcel, 20, getFeaturedImageUrl(), false);
        SafeParcelWriter.writeBoolean(parcel, 21, this.zzaf);
        SafeParcelWriter.writeBoolean(parcel, 22, this.zzag);
        SafeParcelWriter.writeBoolean(parcel, 23, areSnapshotsEnabled());
        SafeParcelWriter.writeString(parcel, 24, getThemeColor(), false);
        SafeParcelWriter.writeBoolean(parcel, 25, hasGamepadSupport());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.games.Game
    public final boolean zza() {
        return this.zzu;
    }

    @Override // com.google.android.gms.games.Game
    public final boolean zzb() {
        return this.zzag;
    }

    @Override // com.google.android.gms.games.Game
    public final boolean zzc() {
        return this.zzv;
    }

    @Override // com.google.android.gms.games.Game
    public final String zzd() {
        return this.zzw;
    }
}
