package com.google.android.gms.games.multiplayer.turnbased;

import android.database.CharArrayBuffer;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class TurnBasedMatchEntity extends com.google.android.gms.games.internal.zzd implements TurnBasedMatch {
    public static final Parcelable.Creator<TurnBasedMatchEntity> CREATOR = new zzc();
    private final byte[] data;
    private final String description;
    private final int version;
    private final long zzfk;
    private final String zzhg;
    private final GameEntity zzky;
    private final long zzoa;
    private final ArrayList<ParticipantEntity> zzod;
    private final int zzoe;
    private final Bundle zzoz;
    private final String zzpc;
    private final String zzpl;
    private final String zzpm;
    private final int zzpn;
    private final String zzpo;
    private final byte[] zzpp;
    private final int zzpq;
    private final int zzpr;
    private final boolean zzps;
    private final String zzpt;

    TurnBasedMatchEntity(GameEntity gameEntity, String str, String str2, long j, String str3, long j2, String str4, int i, int i2, int i3, byte[] bArr, ArrayList<ParticipantEntity> arrayList, String str5, byte[] bArr2, int i4, Bundle bundle, int i5, boolean z, String str6, String str7) {
        this.zzky = gameEntity;
        this.zzhg = str;
        this.zzpc = str2;
        this.zzoa = j;
        this.zzpl = str3;
        this.zzfk = j2;
        this.zzpm = str4;
        this.zzpn = i;
        this.zzpr = i5;
        this.zzoe = i2;
        this.version = i3;
        this.data = bArr;
        this.zzod = arrayList;
        this.zzpo = str5;
        this.zzpp = bArr2;
        this.zzpq = i4;
        this.zzoz = bundle;
        this.zzps = z;
        this.description = str6;
        this.zzpt = str7;
    }

    public TurnBasedMatchEntity(TurnBasedMatch turnBasedMatch) {
        this.zzky = new GameEntity(turnBasedMatch.getGame());
        this.zzhg = turnBasedMatch.getMatchId();
        this.zzpc = turnBasedMatch.getCreatorId();
        this.zzoa = turnBasedMatch.getCreationTimestamp();
        this.zzpl = turnBasedMatch.getLastUpdaterId();
        this.zzfk = turnBasedMatch.getLastUpdatedTimestamp();
        this.zzpm = turnBasedMatch.getPendingParticipantId();
        this.zzpn = turnBasedMatch.getStatus();
        this.zzpr = turnBasedMatch.getTurnStatus();
        this.zzoe = turnBasedMatch.getVariant();
        this.version = turnBasedMatch.getVersion();
        this.zzpo = turnBasedMatch.getRematchId();
        this.zzpq = turnBasedMatch.getMatchNumber();
        this.zzoz = turnBasedMatch.getAutoMatchCriteria();
        this.zzps = turnBasedMatch.isLocallyModified();
        this.description = turnBasedMatch.getDescription();
        this.zzpt = turnBasedMatch.getDescriptionParticipantId();
        byte[] data = turnBasedMatch.getData();
        if (data == null) {
            this.data = null;
        } else {
            byte[] bArr = new byte[data.length];
            this.data = bArr;
            System.arraycopy(data, 0, bArr, 0, data.length);
        }
        byte[] previousMatchData = turnBasedMatch.getPreviousMatchData();
        if (previousMatchData == null) {
            this.zzpp = null;
        } else {
            byte[] bArr2 = new byte[previousMatchData.length];
            this.zzpp = bArr2;
            System.arraycopy(previousMatchData, 0, bArr2, 0, previousMatchData.length);
        }
        ArrayList<Participant> participants = turnBasedMatch.getParticipants();
        int size = participants.size();
        this.zzod = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.zzod.add((ParticipantEntity) participants.get(i).freeze());
        }
    }

    static int zza(TurnBasedMatch turnBasedMatch) {
        return Objects.hashCode(turnBasedMatch.getGame(), turnBasedMatch.getMatchId(), turnBasedMatch.getCreatorId(), Long.valueOf(turnBasedMatch.getCreationTimestamp()), turnBasedMatch.getLastUpdaterId(), Long.valueOf(turnBasedMatch.getLastUpdatedTimestamp()), turnBasedMatch.getPendingParticipantId(), Integer.valueOf(turnBasedMatch.getStatus()), Integer.valueOf(turnBasedMatch.getTurnStatus()), turnBasedMatch.getDescription(), Integer.valueOf(turnBasedMatch.getVariant()), Integer.valueOf(turnBasedMatch.getVersion()), turnBasedMatch.getParticipants(), turnBasedMatch.getRematchId(), Integer.valueOf(turnBasedMatch.getMatchNumber()), Integer.valueOf(com.google.android.gms.games.internal.zzc.zza(turnBasedMatch.getAutoMatchCriteria())), Integer.valueOf(turnBasedMatch.getAvailableAutoMatchSlots()), Boolean.valueOf(turnBasedMatch.isLocallyModified()));
    }

    static int zza(TurnBasedMatch turnBasedMatch, String str) {
        ArrayList<Participant> participants = turnBasedMatch.getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = participants.get(i);
            if (participant.getParticipantId().equals(str)) {
                return participant.getStatus();
            }
        }
        String matchId = turnBasedMatch.getMatchId();
        throw new IllegalStateException(new StringBuilder(String.valueOf(str).length() + 29 + String.valueOf(matchId).length()).append("Participant ").append(str).append(" is not in match ").append(matchId).toString());
    }

    static boolean zza(TurnBasedMatch turnBasedMatch, Object obj) {
        if (!(obj instanceof TurnBasedMatch)) {
            return false;
        }
        if (turnBasedMatch == obj) {
            return true;
        }
        TurnBasedMatch turnBasedMatch2 = (TurnBasedMatch) obj;
        return Objects.equal(turnBasedMatch2.getGame(), turnBasedMatch.getGame()) && Objects.equal(turnBasedMatch2.getMatchId(), turnBasedMatch.getMatchId()) && Objects.equal(turnBasedMatch2.getCreatorId(), turnBasedMatch.getCreatorId()) && Objects.equal(Long.valueOf(turnBasedMatch2.getCreationTimestamp()), Long.valueOf(turnBasedMatch.getCreationTimestamp())) && Objects.equal(turnBasedMatch2.getLastUpdaterId(), turnBasedMatch.getLastUpdaterId()) && Objects.equal(Long.valueOf(turnBasedMatch2.getLastUpdatedTimestamp()), Long.valueOf(turnBasedMatch.getLastUpdatedTimestamp())) && Objects.equal(turnBasedMatch2.getPendingParticipantId(), turnBasedMatch.getPendingParticipantId()) && Objects.equal(Integer.valueOf(turnBasedMatch2.getStatus()), Integer.valueOf(turnBasedMatch.getStatus())) && Objects.equal(Integer.valueOf(turnBasedMatch2.getTurnStatus()), Integer.valueOf(turnBasedMatch.getTurnStatus())) && Objects.equal(turnBasedMatch2.getDescription(), turnBasedMatch.getDescription()) && Objects.equal(Integer.valueOf(turnBasedMatch2.getVariant()), Integer.valueOf(turnBasedMatch.getVariant())) && Objects.equal(Integer.valueOf(turnBasedMatch2.getVersion()), Integer.valueOf(turnBasedMatch.getVersion())) && Objects.equal(turnBasedMatch2.getParticipants(), turnBasedMatch.getParticipants()) && Objects.equal(turnBasedMatch2.getRematchId(), turnBasedMatch.getRematchId()) && Objects.equal(Integer.valueOf(turnBasedMatch2.getMatchNumber()), Integer.valueOf(turnBasedMatch.getMatchNumber())) && com.google.android.gms.games.internal.zzc.zza(turnBasedMatch2.getAutoMatchCriteria(), turnBasedMatch.getAutoMatchCriteria()) && Objects.equal(Integer.valueOf(turnBasedMatch2.getAvailableAutoMatchSlots()), Integer.valueOf(turnBasedMatch.getAvailableAutoMatchSlots())) && Objects.equal(Boolean.valueOf(turnBasedMatch2.isLocallyModified()), Boolean.valueOf(turnBasedMatch.isLocallyModified()));
    }

    static String zzb(TurnBasedMatch turnBasedMatch) {
        return Objects.toStringHelper(turnBasedMatch).add("Game", turnBasedMatch.getGame()).add("MatchId", turnBasedMatch.getMatchId()).add("CreatorId", turnBasedMatch.getCreatorId()).add("CreationTimestamp", Long.valueOf(turnBasedMatch.getCreationTimestamp())).add("LastUpdaterId", turnBasedMatch.getLastUpdaterId()).add("LastUpdatedTimestamp", Long.valueOf(turnBasedMatch.getLastUpdatedTimestamp())).add("PendingParticipantId", turnBasedMatch.getPendingParticipantId()).add("MatchStatus", Integer.valueOf(turnBasedMatch.getStatus())).add("TurnStatus", Integer.valueOf(turnBasedMatch.getTurnStatus())).add("Description", turnBasedMatch.getDescription()).add("Variant", Integer.valueOf(turnBasedMatch.getVariant())).add("Data", turnBasedMatch.getData()).add("Version", Integer.valueOf(turnBasedMatch.getVersion())).add("Participants", turnBasedMatch.getParticipants()).add("RematchId", turnBasedMatch.getRematchId()).add("PreviousData", turnBasedMatch.getPreviousMatchData()).add("MatchNumber", Integer.valueOf(turnBasedMatch.getMatchNumber())).add("AutoMatchCriteria", turnBasedMatch.getAutoMatchCriteria()).add("AvailableAutoMatchSlots", Integer.valueOf(turnBasedMatch.getAvailableAutoMatchSlots())).add("LocallyModified", Boolean.valueOf(turnBasedMatch.isLocallyModified())).add("DescriptionParticipantId", turnBasedMatch.getDescriptionParticipantId()).toString();
    }

    static String zzb(TurnBasedMatch turnBasedMatch, String str) {
        ArrayList<Participant> participants = turnBasedMatch.getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = participants.get(i);
            Player player = participant.getPlayer();
            if (player != null && player.getPlayerId().equals(str)) {
                return participant.getParticipantId();
            }
        }
        return null;
    }

    static Participant zzc(TurnBasedMatch turnBasedMatch, String str) {
        ArrayList<Participant> participants = turnBasedMatch.getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = participants.get(i);
            if (participant.getParticipantId().equals(str)) {
                return participant;
            }
        }
        String matchId = turnBasedMatch.getMatchId();
        throw new IllegalStateException(new StringBuilder(String.valueOf(str).length() + 29 + String.valueOf(matchId).length()).append("Participant ").append(str).append(" is not in match ").append(matchId).toString());
    }

    static ArrayList<String> zzc(TurnBasedMatch turnBasedMatch) {
        ArrayList<Participant> participants = turnBasedMatch.getParticipants();
        int size = participants.size();
        ArrayList<String> arrayList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(participants.get(i).getParticipantId());
        }
        return arrayList;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final boolean canRematch() {
        return this.zzpn == 2 && this.zzpo == null;
    }

    public final boolean equals(Object obj) {
        return zza(this, obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.common.data.Freezable
    public final TurnBasedMatch freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final Bundle getAutoMatchCriteria() {
        return this.zzoz;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final int getAvailableAutoMatchSlots() {
        Bundle bundle = this.zzoz;
        if (bundle == null) {
            return 0;
        }
        return bundle.getInt(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS);
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final long getCreationTimestamp() {
        return this.zzoa;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String getCreatorId() {
        return this.zzpc;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final byte[] getData() {
        return this.data;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String getDescription() {
        return this.description;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final void getDescription(CharArrayBuffer charArrayBuffer) {
        DataUtils.copyStringToBuffer(this.description, charArrayBuffer);
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final Participant getDescriptionParticipant() {
        String descriptionParticipantId = getDescriptionParticipantId();
        if (descriptionParticipantId == null) {
            return null;
        }
        return getParticipant(descriptionParticipantId);
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String getDescriptionParticipantId() {
        return this.zzpt;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final Game getGame() {
        return this.zzky;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final long getLastUpdatedTimestamp() {
        return this.zzfk;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String getLastUpdaterId() {
        return this.zzpl;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String getMatchId() {
        return this.zzhg;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final int getMatchNumber() {
        return this.zzpq;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final Participant getParticipant(String str) {
        return zzc(this, str);
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String getParticipantId(String str) {
        return zzb(this, str);
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final ArrayList<String> getParticipantIds() {
        return zzc(this);
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final int getParticipantStatus(String str) {
        return zza((TurnBasedMatch) this, str);
    }

    @Override // com.google.android.gms.games.multiplayer.Participatable
    public final ArrayList<Participant> getParticipants() {
        return new ArrayList<>(this.zzod);
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String getPendingParticipantId() {
        return this.zzpm;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final byte[] getPreviousMatchData() {
        return this.zzpp;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final String getRematchId() {
        return this.zzpo;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final int getStatus() {
        return this.zzpn;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final int getTurnStatus() {
        return this.zzpr;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final int getVariant() {
        return this.zzoe;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final int getVersion() {
        return this.version;
    }

    public final int hashCode() {
        return zza(this);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final boolean isDataValid() {
        return true;
    }

    @Override // com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch
    public final boolean isLocallyModified() {
        return this.zzps;
    }

    public final String toString() {
        return zzb(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getGame(), i, false);
        SafeParcelWriter.writeString(parcel, 2, getMatchId(), false);
        SafeParcelWriter.writeString(parcel, 3, getCreatorId(), false);
        SafeParcelWriter.writeLong(parcel, 4, getCreationTimestamp());
        SafeParcelWriter.writeString(parcel, 5, getLastUpdaterId(), false);
        SafeParcelWriter.writeLong(parcel, 6, getLastUpdatedTimestamp());
        SafeParcelWriter.writeString(parcel, 7, getPendingParticipantId(), false);
        SafeParcelWriter.writeInt(parcel, 8, getStatus());
        SafeParcelWriter.writeInt(parcel, 10, getVariant());
        SafeParcelWriter.writeInt(parcel, 11, getVersion());
        SafeParcelWriter.writeByteArray(parcel, 12, getData(), false);
        SafeParcelWriter.writeTypedList(parcel, 13, getParticipants(), false);
        SafeParcelWriter.writeString(parcel, 14, getRematchId(), false);
        SafeParcelWriter.writeByteArray(parcel, 15, getPreviousMatchData(), false);
        SafeParcelWriter.writeInt(parcel, 16, getMatchNumber());
        SafeParcelWriter.writeBundle(parcel, 17, getAutoMatchCriteria(), false);
        SafeParcelWriter.writeInt(parcel, 18, getTurnStatus());
        SafeParcelWriter.writeBoolean(parcel, 19, isLocallyModified());
        SafeParcelWriter.writeString(parcel, 20, getDescription(), false);
        SafeParcelWriter.writeString(parcel, 21, getDescriptionParticipantId(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
