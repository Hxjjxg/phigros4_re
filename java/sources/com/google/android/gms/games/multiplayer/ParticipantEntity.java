package com.google.android.gms.games.multiplayer;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;

/* JADX INFO: loaded from: classes.dex */
public final class ParticipantEntity extends GamesDowngradeableSafeParcel implements Participant {
    public static final Parcelable.Creator<ParticipantEntity> CREATOR = new zza();
    private final int status;
    private final String zzac;
    private final String zzad;
    private final PlayerEntity zzfh;
    private final String zzhl;
    private final String zzn;
    private final String zzoh;
    private final boolean zzoi;
    private final int zzoj;
    private final ParticipantResult zzok;
    private final Uri zzr;
    private final Uri zzs;

    static final class zza extends zzc {
        zza() {
        }

        @Override // com.google.android.gms.games.multiplayer.zzc, android.os.Parcelable.Creator
        public final /* synthetic */ ParticipantEntity createFromParcel(Parcel parcel) {
            return createFromParcel(parcel);
        }

        @Override // com.google.android.gms.games.multiplayer.zzc
        /* JADX INFO: renamed from: zze */
        public final ParticipantEntity createFromParcel(Parcel parcel) {
            if (ParticipantEntity.zzb(ParticipantEntity.getUnparcelClientVersion()) || ParticipantEntity.canUnparcelSafely(ParticipantEntity.class.getCanonicalName())) {
                return super.createFromParcel(parcel);
            }
            String string = parcel.readString();
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            Uri uri = string3 == null ? null : Uri.parse(string3);
            String string4 = parcel.readString();
            return new ParticipantEntity(string, string2, uri, string4 == null ? null : Uri.parse(string4), parcel.readInt(), parcel.readString(), parcel.readInt() > 0, parcel.readInt() > 0 ? PlayerEntity.CREATOR.createFromParcel(parcel) : null, 7, null, null, null);
        }
    }

    public ParticipantEntity(Participant participant) {
        this.zzhl = participant.getParticipantId();
        this.zzn = participant.getDisplayName();
        this.zzr = participant.getIconImageUri();
        this.zzs = participant.getHiResImageUri();
        this.status = participant.getStatus();
        this.zzoh = participant.zzcg();
        this.zzoi = participant.isConnectedToRoom();
        Player player = participant.getPlayer();
        this.zzfh = player == null ? null : new PlayerEntity(player);
        this.zzoj = participant.getCapabilities();
        this.zzok = participant.getResult();
        this.zzac = participant.getIconImageUrl();
        this.zzad = participant.getHiResImageUrl();
    }

    ParticipantEntity(String str, String str2, Uri uri, Uri uri2, int i, String str3, boolean z, PlayerEntity playerEntity, int i2, ParticipantResult participantResult, String str4, String str5) {
        this.zzhl = str;
        this.zzn = str2;
        this.zzr = uri;
        this.zzs = uri2;
        this.status = i;
        this.zzoh = str3;
        this.zzoi = z;
        this.zzfh = playerEntity;
        this.zzoj = i2;
        this.zzok = participantResult;
        this.zzac = str4;
        this.zzad = str5;
    }

    static int zza(Participant participant) {
        return Objects.hashCode(participant.getPlayer(), Integer.valueOf(participant.getStatus()), participant.zzcg(), Boolean.valueOf(participant.isConnectedToRoom()), participant.getDisplayName(), participant.getIconImageUri(), participant.getHiResImageUri(), Integer.valueOf(participant.getCapabilities()), participant.getResult(), participant.getParticipantId());
    }

    static boolean zza(Participant participant, Object obj) {
        if (!(obj instanceof Participant)) {
            return false;
        }
        if (participant == obj) {
            return true;
        }
        Participant participant2 = (Participant) obj;
        return Objects.equal(participant2.getPlayer(), participant.getPlayer()) && Objects.equal(Integer.valueOf(participant2.getStatus()), Integer.valueOf(participant.getStatus())) && Objects.equal(participant2.zzcg(), participant.zzcg()) && Objects.equal(Boolean.valueOf(participant2.isConnectedToRoom()), Boolean.valueOf(participant.isConnectedToRoom())) && Objects.equal(participant2.getDisplayName(), participant.getDisplayName()) && Objects.equal(participant2.getIconImageUri(), participant.getIconImageUri()) && Objects.equal(participant2.getHiResImageUri(), participant.getHiResImageUri()) && Objects.equal(Integer.valueOf(participant2.getCapabilities()), Integer.valueOf(participant.getCapabilities())) && Objects.equal(participant2.getResult(), participant.getResult()) && Objects.equal(participant2.getParticipantId(), participant.getParticipantId());
    }

    static String zzb(Participant participant) {
        return Objects.toStringHelper(participant).add("ParticipantId", participant.getParticipantId()).add("Player", participant.getPlayer()).add("Status", Integer.valueOf(participant.getStatus())).add("ClientAddress", participant.zzcg()).add("ConnectedToRoom", Boolean.valueOf(participant.isConnectedToRoom())).add("DisplayName", participant.getDisplayName()).add("IconImage", participant.getIconImageUri()).add("IconImageUrl", participant.getIconImageUrl()).add("HiResImage", participant.getHiResImageUri()).add("HiResImageUrl", participant.getHiResImageUrl()).add("Capabilities", Integer.valueOf(participant.getCapabilities())).add("Result", participant.getResult()).toString();
    }

    public final boolean equals(Object obj) {
        return zza(this, obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.common.data.Freezable
    public final Participant freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final int getCapabilities() {
        return this.zzoj;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String getDisplayName() {
        PlayerEntity playerEntity = this.zzfh;
        return playerEntity == null ? this.zzn : playerEntity.getDisplayName();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final void getDisplayName(CharArrayBuffer charArrayBuffer) {
        PlayerEntity playerEntity = this.zzfh;
        if (playerEntity == null) {
            DataUtils.copyStringToBuffer(this.zzn, charArrayBuffer);
        } else {
            playerEntity.getDisplayName(charArrayBuffer);
        }
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final Uri getHiResImageUri() {
        PlayerEntity playerEntity = this.zzfh;
        return playerEntity == null ? this.zzs : playerEntity.getHiResImageUri();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String getHiResImageUrl() {
        PlayerEntity playerEntity = this.zzfh;
        return playerEntity == null ? this.zzad : playerEntity.getHiResImageUrl();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final Uri getIconImageUri() {
        PlayerEntity playerEntity = this.zzfh;
        return playerEntity == null ? this.zzr : playerEntity.getIconImageUri();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String getIconImageUrl() {
        PlayerEntity playerEntity = this.zzfh;
        return playerEntity == null ? this.zzac : playerEntity.getIconImageUrl();
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String getParticipantId() {
        return this.zzhl;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final Player getPlayer() {
        return this.zzfh;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final ParticipantResult getResult() {
        return this.zzok;
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final int getStatus() {
        return this.status;
    }

    public final int hashCode() {
        return zza(this);
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final boolean isConnectedToRoom() {
        return this.zzoi;
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final boolean isDataValid() {
        return true;
    }

    @Override // com.google.android.gms.common.internal.DowngradeableSafeParcel
    public final void setShouldDowngrade(boolean z) {
        super.setShouldDowngrade(z);
        PlayerEntity playerEntity = this.zzfh;
        if (playerEntity != null) {
            playerEntity.setShouldDowngrade(z);
        }
    }

    public final String toString() {
        return zzb(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        if (shouldDowngrade()) {
            parcel.writeString(this.zzhl);
            parcel.writeString(this.zzn);
            Uri uri = this.zzr;
            parcel.writeString(uri == null ? null : uri.toString());
            Uri uri2 = this.zzs;
            parcel.writeString(uri2 != null ? uri2.toString() : null);
            parcel.writeInt(this.status);
            parcel.writeString(this.zzoh);
            parcel.writeInt(this.zzoi ? 1 : 0);
            parcel.writeInt(this.zzfh == null ? 0 : 1);
            PlayerEntity playerEntity = this.zzfh;
            if (playerEntity != null) {
                playerEntity.writeToParcel(parcel, i);
                return;
            }
            return;
        }
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getParticipantId(), false);
        SafeParcelWriter.writeString(parcel, 2, getDisplayName(), false);
        SafeParcelWriter.writeParcelable(parcel, 3, getIconImageUri(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 4, getHiResImageUri(), i, false);
        SafeParcelWriter.writeInt(parcel, 5, getStatus());
        SafeParcelWriter.writeString(parcel, 6, this.zzoh, false);
        SafeParcelWriter.writeBoolean(parcel, 7, isConnectedToRoom());
        SafeParcelWriter.writeParcelable(parcel, 8, getPlayer(), i, false);
        SafeParcelWriter.writeInt(parcel, 9, this.zzoj);
        SafeParcelWriter.writeParcelable(parcel, 10, getResult(), i, false);
        SafeParcelWriter.writeString(parcel, 11, getIconImageUrl(), false);
        SafeParcelWriter.writeString(parcel, 12, getHiResImageUrl(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.games.multiplayer.Participant
    public final String zzcg() {
        return this.zzoh;
    }
}
