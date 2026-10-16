package com.google.android.gms.games.multiplayer.realtime;

import android.database.CharArrayBuffer;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class RoomEntity extends GamesDowngradeableSafeParcel implements Room {
    public static final Parcelable.Creator<RoomEntity> CREATOR = new zza();
    private final String description;
    private final String zzgt;
    private final long zzoa;
    private final ArrayList<ParticipantEntity> zzod;
    private final int zzoe;
    private final Bundle zzoz;
    private final String zzpc;
    private final int zzpd;
    private final int zzpe;

    static final class zza extends zze {
        zza() {
        }

        @Override // com.google.android.gms.games.multiplayer.realtime.zze, android.os.Parcelable.Creator
        public final /* synthetic */ RoomEntity createFromParcel(Parcel parcel) {
            return createFromParcel(parcel);
        }

        @Override // com.google.android.gms.games.multiplayer.realtime.zze
        /* JADX INFO: renamed from: zzf */
        public final RoomEntity createFromParcel(Parcel parcel) {
            if (RoomEntity.zzb(RoomEntity.getUnparcelClientVersion()) || RoomEntity.canUnparcelSafely(RoomEntity.class.getCanonicalName())) {
                return super.createFromParcel(parcel);
            }
            String string = parcel.readString();
            String string2 = parcel.readString();
            long j = parcel.readLong();
            int i = parcel.readInt();
            String string3 = parcel.readString();
            int i2 = parcel.readInt();
            Bundle bundle = parcel.readBundle();
            int i3 = parcel.readInt();
            ArrayList arrayList = new ArrayList(i3);
            for (int i4 = 0; i4 < i3; i4++) {
                arrayList.add(ParticipantEntity.CREATOR.createFromParcel(parcel));
            }
            return new RoomEntity(string, string2, j, i, string3, i2, bundle, arrayList, -1);
        }
    }

    public RoomEntity(Room room) {
        this.zzgt = room.getRoomId();
        this.zzpc = room.getCreatorId();
        this.zzoa = room.getCreationTimestamp();
        this.zzpd = room.getStatus();
        this.description = room.getDescription();
        this.zzoe = room.getVariant();
        this.zzoz = room.getAutoMatchCriteria();
        ArrayList<Participant> participants = room.getParticipants();
        int size = participants.size();
        this.zzod = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.zzod.add((ParticipantEntity) participants.get(i).freeze());
        }
        this.zzpe = room.getAutoMatchWaitEstimateSeconds();
    }

    RoomEntity(String str, String str2, long j, int i, String str3, int i2, Bundle bundle, ArrayList<ParticipantEntity> arrayList, int i3) {
        this.zzgt = str;
        this.zzpc = str2;
        this.zzoa = j;
        this.zzpd = i;
        this.description = str3;
        this.zzoe = i2;
        this.zzoz = bundle;
        this.zzod = arrayList;
        this.zzpe = i3;
    }

    static int zza(Room room) {
        return Objects.hashCode(room.getRoomId(), room.getCreatorId(), Long.valueOf(room.getCreationTimestamp()), Integer.valueOf(room.getStatus()), room.getDescription(), Integer.valueOf(room.getVariant()), Integer.valueOf(com.google.android.gms.games.internal.zzc.zza(room.getAutoMatchCriteria())), room.getParticipants(), Integer.valueOf(room.getAutoMatchWaitEstimateSeconds()));
    }

    static int zza(Room room, String str) {
        ArrayList<Participant> participants = room.getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = participants.get(i);
            if (participant.getParticipantId().equals(str)) {
                return participant.getStatus();
            }
        }
        String roomId = room.getRoomId();
        throw new IllegalStateException(new StringBuilder(String.valueOf(str).length() + 28 + String.valueOf(roomId).length()).append("Participant ").append(str).append(" is not in room ").append(roomId).toString());
    }

    static boolean zza(Room room, Object obj) {
        if (!(obj instanceof Room)) {
            return false;
        }
        if (room == obj) {
            return true;
        }
        Room room2 = (Room) obj;
        return Objects.equal(room2.getRoomId(), room.getRoomId()) && Objects.equal(room2.getCreatorId(), room.getCreatorId()) && Objects.equal(Long.valueOf(room2.getCreationTimestamp()), Long.valueOf(room.getCreationTimestamp())) && Objects.equal(Integer.valueOf(room2.getStatus()), Integer.valueOf(room.getStatus())) && Objects.equal(room2.getDescription(), room.getDescription()) && Objects.equal(Integer.valueOf(room2.getVariant()), Integer.valueOf(room.getVariant())) && com.google.android.gms.games.internal.zzc.zza(room2.getAutoMatchCriteria(), room.getAutoMatchCriteria()) && Objects.equal(room2.getParticipants(), room.getParticipants()) && Objects.equal(Integer.valueOf(room2.getAutoMatchWaitEstimateSeconds()), Integer.valueOf(room.getAutoMatchWaitEstimateSeconds()));
    }

    static String zzb(Room room) {
        return Objects.toStringHelper(room).add("RoomId", room.getRoomId()).add("CreatorId", room.getCreatorId()).add("CreationTimestamp", Long.valueOf(room.getCreationTimestamp())).add("RoomStatus", Integer.valueOf(room.getStatus())).add("Description", room.getDescription()).add("Variant", Integer.valueOf(room.getVariant())).add("AutoMatchCriteria", room.getAutoMatchCriteria()).add("Participants", room.getParticipants()).add("AutoMatchWaitEstimateSeconds", Integer.valueOf(room.getAutoMatchWaitEstimateSeconds())).toString();
    }

    static String zzb(Room room, String str) {
        ArrayList<Participant> participants = room.getParticipants();
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

    static Participant zzc(Room room, String str) {
        ArrayList<Participant> participants = room.getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = participants.get(i);
            if (participant.getParticipantId().equals(str)) {
                return participant;
            }
        }
        String roomId = room.getRoomId();
        throw new IllegalStateException(new StringBuilder(String.valueOf(str).length() + 29 + String.valueOf(roomId).length()).append("Participant ").append(str).append(" is not in match ").append(roomId).toString());
    }

    static ArrayList<String> zzc(Room room) {
        ArrayList<Participant> participants = room.getParticipants();
        int size = participants.size();
        ArrayList<String> arrayList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(participants.get(i).getParticipantId());
        }
        return arrayList;
    }

    public final boolean equals(Object obj) {
        return zza(this, obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.common.data.Freezable
    public final Room freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final Bundle getAutoMatchCriteria() {
        return this.zzoz;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final int getAutoMatchWaitEstimateSeconds() {
        return this.zzpe;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final long getCreationTimestamp() {
        return this.zzoa;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final String getCreatorId() {
        return this.zzpc;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final String getDescription() {
        return this.description;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final void getDescription(CharArrayBuffer charArrayBuffer) {
        DataUtils.copyStringToBuffer(this.description, charArrayBuffer);
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final Participant getParticipant(String str) {
        return zzc(this, str);
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final String getParticipantId(String str) {
        return zzb(this, str);
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final ArrayList<String> getParticipantIds() {
        return zzc(this);
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final int getParticipantStatus(String str) {
        return zza((Room) this, str);
    }

    @Override // com.google.android.gms.games.multiplayer.Participatable
    public final ArrayList<Participant> getParticipants() {
        return new ArrayList<>(this.zzod);
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final String getRoomId() {
        return this.zzgt;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final int getStatus() {
        return this.zzpd;
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.Room
    public final int getVariant() {
        return this.zzoe;
    }

    public final int hashCode() {
        return zza(this);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final boolean isDataValid() {
        return true;
    }

    @Override // com.google.android.gms.common.internal.DowngradeableSafeParcel
    public final void setShouldDowngrade(boolean z) {
        super.setShouldDowngrade(z);
        int size = this.zzod.size();
        for (int i = 0; i < size; i++) {
            this.zzod.get(i).setShouldDowngrade(z);
        }
    }

    public final String toString() {
        return zzb(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        if (!shouldDowngrade()) {
            int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeString(parcel, 1, getRoomId(), false);
            SafeParcelWriter.writeString(parcel, 2, getCreatorId(), false);
            SafeParcelWriter.writeLong(parcel, 3, getCreationTimestamp());
            SafeParcelWriter.writeInt(parcel, 4, getStatus());
            SafeParcelWriter.writeString(parcel, 5, getDescription(), false);
            SafeParcelWriter.writeInt(parcel, 6, getVariant());
            SafeParcelWriter.writeBundle(parcel, 7, getAutoMatchCriteria(), false);
            SafeParcelWriter.writeTypedList(parcel, 8, getParticipants(), false);
            SafeParcelWriter.writeInt(parcel, 9, getAutoMatchWaitEstimateSeconds());
            SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
            return;
        }
        parcel.writeString(this.zzgt);
        parcel.writeString(this.zzpc);
        parcel.writeLong(this.zzoa);
        parcel.writeInt(this.zzpd);
        parcel.writeString(this.description);
        parcel.writeInt(this.zzoe);
        parcel.writeBundle(this.zzoz);
        int size = this.zzod.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            this.zzod.get(i2).writeToParcel(parcel, i);
        }
    }
}
