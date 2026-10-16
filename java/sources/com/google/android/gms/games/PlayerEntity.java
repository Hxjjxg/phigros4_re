package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;

/* JADX INFO: loaded from: classes.dex */
public final class PlayerEntity extends GamesDowngradeableSafeParcel implements Player {
    public static final Parcelable.Creator<PlayerEntity> CREATOR = new zza();
    private final String name;
    private final String zzac;
    private final String zzad;
    private String zzby;
    private final long zzbz;
    private final int zzca;
    private final long zzcb;
    private final String zzcc;
    private final com.google.android.gms.games.internal.player.zzb zzcd;
    private final PlayerLevelInfo zzce;
    private final boolean zzcf;
    private final boolean zzcg;
    private final String zzch;
    private final Uri zzci;
    private final String zzcj;
    private final Uri zzck;
    private final String zzcl;
    private final int zzcm;
    private final long zzcn;
    private final boolean zzco;
    private String zzn;
    private final Uri zzr;
    private final Uri zzs;

    static final class zza extends zzap {
        zza() {
        }

        @Override // com.google.android.gms.games.zzap, android.os.Parcelable.Creator
        public final /* synthetic */ PlayerEntity createFromParcel(Parcel parcel) {
            return createFromParcel(parcel);
        }

        @Override // com.google.android.gms.games.zzap
        /* JADX INFO: renamed from: zzc */
        public final PlayerEntity createFromParcel(Parcel parcel) {
            if (PlayerEntity.zzb(PlayerEntity.getUnparcelClientVersion()) || PlayerEntity.canUnparcelSafely(PlayerEntity.class.getCanonicalName())) {
                return super.createFromParcel(parcel);
            }
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            String string4 = parcel.readString();
            return new PlayerEntity(string, string2, string3 == null ? null : Uri.parse(string3), string4 == null ? null : Uri.parse(string4), parcel.readLong(), -1, -1L, null, null, null, null, null, true, false, parcel.readString(), parcel.readString(), null, null, null, null, -1, -1L, false);
        }
    }

    public PlayerEntity(Player player) {
        this(player, true);
    }

    private PlayerEntity(Player player, boolean z) {
        this.zzby = player.getPlayerId();
        this.zzn = player.getDisplayName();
        this.zzr = player.getIconImageUri();
        this.zzac = player.getIconImageUrl();
        this.zzs = player.getHiResImageUri();
        this.zzad = player.getHiResImageUrl();
        long retrievedTimestamp = player.getRetrievedTimestamp();
        this.zzbz = retrievedTimestamp;
        this.zzca = player.zzi();
        this.zzcb = player.getLastPlayedWithTimestamp();
        this.zzcc = player.getTitle();
        this.zzcf = player.zzj();
        com.google.android.gms.games.internal.player.zza zzaVarZzk = player.zzk();
        this.zzcd = zzaVarZzk == null ? null : new com.google.android.gms.games.internal.player.zzb(zzaVarZzk);
        this.zzce = player.getLevelInfo();
        this.zzcg = player.zzh();
        this.zzch = player.zzg();
        this.name = player.getName();
        this.zzci = player.getBannerImageLandscapeUri();
        this.zzcj = player.getBannerImageLandscapeUrl();
        this.zzck = player.getBannerImagePortraitUri();
        this.zzcl = player.getBannerImagePortraitUrl();
        this.zzcm = player.zzl();
        this.zzcn = player.zzm();
        this.zzco = player.isMuted();
        Asserts.checkNotNull(this.zzby);
        Asserts.checkNotNull(this.zzn);
        Asserts.checkState(retrievedTimestamp > 0);
    }

    PlayerEntity(String str, String str2, Uri uri, Uri uri2, long j, int i, long j2, String str3, String str4, String str5, com.google.android.gms.games.internal.player.zzb zzbVar, PlayerLevelInfo playerLevelInfo, boolean z, boolean z2, String str6, String str7, Uri uri3, String str8, Uri uri4, String str9, int i2, long j3, boolean z3) {
        this.zzby = str;
        this.zzn = str2;
        this.zzr = uri;
        this.zzac = str3;
        this.zzs = uri2;
        this.zzad = str4;
        this.zzbz = j;
        this.zzca = i;
        this.zzcb = j2;
        this.zzcc = str5;
        this.zzcf = z;
        this.zzcd = zzbVar;
        this.zzce = playerLevelInfo;
        this.zzcg = z2;
        this.zzch = str6;
        this.name = str7;
        this.zzci = uri3;
        this.zzcj = str8;
        this.zzck = uri4;
        this.zzcl = str9;
        this.zzcm = i2;
        this.zzcn = j3;
        this.zzco = z3;
    }

    static int zza(Player player) {
        return Objects.hashCode(player.getPlayerId(), player.getDisplayName(), Boolean.valueOf(player.zzh()), player.getIconImageUri(), player.getHiResImageUri(), Long.valueOf(player.getRetrievedTimestamp()), player.getTitle(), player.getLevelInfo(), player.zzg(), player.getName(), player.getBannerImageLandscapeUri(), player.getBannerImagePortraitUri(), Integer.valueOf(player.zzl()), Long.valueOf(player.zzm()), Boolean.valueOf(player.isMuted()));
    }

    static boolean zza(Player player, Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        if (player == obj) {
            return true;
        }
        Player player2 = (Player) obj;
        return Objects.equal(player2.getPlayerId(), player.getPlayerId()) && Objects.equal(player2.getDisplayName(), player.getDisplayName()) && Objects.equal(Boolean.valueOf(player2.zzh()), Boolean.valueOf(player.zzh())) && Objects.equal(player2.getIconImageUri(), player.getIconImageUri()) && Objects.equal(player2.getHiResImageUri(), player.getHiResImageUri()) && Objects.equal(Long.valueOf(player2.getRetrievedTimestamp()), Long.valueOf(player.getRetrievedTimestamp())) && Objects.equal(player2.getTitle(), player.getTitle()) && Objects.equal(player2.getLevelInfo(), player.getLevelInfo()) && Objects.equal(player2.zzg(), player.zzg()) && Objects.equal(player2.getName(), player.getName()) && Objects.equal(player2.getBannerImageLandscapeUri(), player.getBannerImageLandscapeUri()) && Objects.equal(player2.getBannerImagePortraitUri(), player.getBannerImagePortraitUri()) && Objects.equal(Integer.valueOf(player2.zzl()), Integer.valueOf(player.zzl())) && Objects.equal(Long.valueOf(player2.zzm()), Long.valueOf(player.zzm())) && Objects.equal(Boolean.valueOf(player2.isMuted()), Boolean.valueOf(player.isMuted()));
    }

    static String zzb(Player player) {
        return Objects.toStringHelper(player).add("PlayerId", player.getPlayerId()).add("DisplayName", player.getDisplayName()).add("HasDebugAccess", Boolean.valueOf(player.zzh())).add("IconImageUri", player.getIconImageUri()).add("IconImageUrl", player.getIconImageUrl()).add("HiResImageUri", player.getHiResImageUri()).add("HiResImageUrl", player.getHiResImageUrl()).add("RetrievedTimestamp", Long.valueOf(player.getRetrievedTimestamp())).add("Title", player.getTitle()).add("LevelInfo", player.getLevelInfo()).add("GamerTag", player.zzg()).add("Name", player.getName()).add("BannerImageLandscapeUri", player.getBannerImageLandscapeUri()).add("BannerImageLandscapeUrl", player.getBannerImageLandscapeUrl()).add("BannerImagePortraitUri", player.getBannerImagePortraitUri()).add("BannerImagePortraitUrl", player.getBannerImagePortraitUrl()).add("GamerFriendStatus", Integer.valueOf(player.zzl())).add("GamerFriendUpdateTimestamp", Long.valueOf(player.zzm())).add("IsMuted", Boolean.valueOf(player.isMuted())).toString();
    }

    public final boolean equals(Object obj) {
        return zza(this, obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.common.data.Freezable
    public final Player freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.Player
    public final Uri getBannerImageLandscapeUri() {
        return this.zzci;
    }

    @Override // com.google.android.gms.games.Player
    public final String getBannerImageLandscapeUrl() {
        return this.zzcj;
    }

    @Override // com.google.android.gms.games.Player
    public final Uri getBannerImagePortraitUri() {
        return this.zzck;
    }

    @Override // com.google.android.gms.games.Player
    public final String getBannerImagePortraitUrl() {
        return this.zzcl;
    }

    @Override // com.google.android.gms.games.Player
    public final String getDisplayName() {
        return this.zzn;
    }

    @Override // com.google.android.gms.games.Player
    public final void getDisplayName(CharArrayBuffer charArrayBuffer) {
        DataUtils.copyStringToBuffer(this.zzn, charArrayBuffer);
    }

    @Override // com.google.android.gms.games.Player
    public final Uri getHiResImageUri() {
        return this.zzs;
    }

    @Override // com.google.android.gms.games.Player
    public final String getHiResImageUrl() {
        return this.zzad;
    }

    @Override // com.google.android.gms.games.Player
    public final Uri getIconImageUri() {
        return this.zzr;
    }

    @Override // com.google.android.gms.games.Player
    public final String getIconImageUrl() {
        return this.zzac;
    }

    @Override // com.google.android.gms.games.Player
    public final long getLastPlayedWithTimestamp() {
        return this.zzcb;
    }

    @Override // com.google.android.gms.games.Player
    public final PlayerLevelInfo getLevelInfo() {
        return this.zzce;
    }

    @Override // com.google.android.gms.games.Player
    public final String getName() {
        return this.name;
    }

    @Override // com.google.android.gms.games.Player
    public final String getPlayerId() {
        return this.zzby;
    }

    @Override // com.google.android.gms.games.Player
    public final long getRetrievedTimestamp() {
        return this.zzbz;
    }

    @Override // com.google.android.gms.games.Player
    public final String getTitle() {
        return this.zzcc;
    }

    @Override // com.google.android.gms.games.Player
    public final void getTitle(CharArrayBuffer charArrayBuffer) {
        DataUtils.copyStringToBuffer(this.zzcc, charArrayBuffer);
    }

    @Override // com.google.android.gms.games.Player
    public final boolean hasHiResImage() {
        return getHiResImageUri() != null;
    }

    @Override // com.google.android.gms.games.Player
    public final boolean hasIconImage() {
        return getIconImageUri() != null;
    }

    public final int hashCode() {
        return zza(this);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final boolean isDataValid() {
        return true;
    }

    @Override // com.google.android.gms.games.Player
    public final boolean isMuted() {
        return this.zzco;
    }

    public final String toString() {
        return zzb(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        if (shouldDowngrade()) {
            parcel.writeString(this.zzby);
            parcel.writeString(this.zzn);
            Uri uri = this.zzr;
            parcel.writeString(uri == null ? null : uri.toString());
            Uri uri2 = this.zzs;
            parcel.writeString(uri2 != null ? uri2.toString() : null);
            parcel.writeLong(this.zzbz);
            return;
        }
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getPlayerId(), false);
        SafeParcelWriter.writeString(parcel, 2, getDisplayName(), false);
        SafeParcelWriter.writeParcelable(parcel, 3, getIconImageUri(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 4, getHiResImageUri(), i, false);
        SafeParcelWriter.writeLong(parcel, 5, getRetrievedTimestamp());
        SafeParcelWriter.writeInt(parcel, 6, this.zzca);
        SafeParcelWriter.writeLong(parcel, 7, getLastPlayedWithTimestamp());
        SafeParcelWriter.writeString(parcel, 8, getIconImageUrl(), false);
        SafeParcelWriter.writeString(parcel, 9, getHiResImageUrl(), false);
        SafeParcelWriter.writeString(parcel, 14, getTitle(), false);
        SafeParcelWriter.writeParcelable(parcel, 15, this.zzcd, i, false);
        SafeParcelWriter.writeParcelable(parcel, 16, getLevelInfo(), i, false);
        SafeParcelWriter.writeBoolean(parcel, 18, this.zzcf);
        SafeParcelWriter.writeBoolean(parcel, 19, this.zzcg);
        SafeParcelWriter.writeString(parcel, 20, this.zzch, false);
        SafeParcelWriter.writeString(parcel, 21, this.name, false);
        SafeParcelWriter.writeParcelable(parcel, 22, getBannerImageLandscapeUri(), i, false);
        SafeParcelWriter.writeString(parcel, 23, getBannerImageLandscapeUrl(), false);
        SafeParcelWriter.writeParcelable(parcel, 24, getBannerImagePortraitUri(), i, false);
        SafeParcelWriter.writeString(parcel, 25, getBannerImagePortraitUrl(), false);
        SafeParcelWriter.writeInt(parcel, 26, this.zzcm);
        SafeParcelWriter.writeLong(parcel, 27, this.zzcn);
        SafeParcelWriter.writeBoolean(parcel, 28, this.zzco);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.games.Player
    public final String zzg() {
        return this.zzch;
    }

    @Override // com.google.android.gms.games.Player
    public final boolean zzh() {
        return this.zzcg;
    }

    @Override // com.google.android.gms.games.Player
    public final int zzi() {
        return this.zzca;
    }

    @Override // com.google.android.gms.games.Player
    public final boolean zzj() {
        return this.zzcf;
    }

    @Override // com.google.android.gms.games.Player
    public final com.google.android.gms.games.internal.player.zza zzk() {
        return this.zzcd;
    }

    @Override // com.google.android.gms.games.Player
    public final int zzl() {
        return this.zzcm;
    }

    @Override // com.google.android.gms.games.Player
    public final long zzm() {
        return this.zzcn;
    }
}
