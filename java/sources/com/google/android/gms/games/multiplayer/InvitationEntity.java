package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.internal.GamesDowngradeableSafeParcel;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class InvitationEntity extends GamesDowngradeableSafeParcel implements Invitation {
    public static final Parcelable.Creator<InvitationEntity> CREATOR = new zza();
    private final String zzgr;
    private final GameEntity zzky;
    private final long zzoa;
    private final int zzob;
    private final ParticipantEntity zzoc;
    private final ArrayList<ParticipantEntity> zzod;
    private final int zzoe;
    private final int zzof;

    static final class zza extends com.google.android.gms.games.multiplayer.zza {
        zza() {
        }

        @Override // com.google.android.gms.games.multiplayer.zza, android.os.Parcelable.Creator
        public final /* synthetic */ InvitationEntity createFromParcel(Parcel parcel) {
            return createFromParcel(parcel);
        }

        @Override // com.google.android.gms.games.multiplayer.zza
        /* JADX INFO: renamed from: zzd */
        public final InvitationEntity createFromParcel(Parcel parcel) {
            if (InvitationEntity.zzb(InvitationEntity.getUnparcelClientVersion()) || InvitationEntity.canUnparcelSafely(InvitationEntity.class.getCanonicalName())) {
                return super.createFromParcel(parcel);
            }
            GameEntity gameEntityCreateFromParcel = GameEntity.CREATOR.createFromParcel(parcel);
            String string = parcel.readString();
            long j = parcel.readLong();
            int i = parcel.readInt();
            ParticipantEntity participantEntityCreateFromParcel = ParticipantEntity.CREATOR.createFromParcel(parcel);
            int i2 = parcel.readInt();
            ArrayList arrayList = new ArrayList(i2);
            for (int i3 = 0; i3 < i2; i3++) {
                arrayList.add(ParticipantEntity.CREATOR.createFromParcel(parcel));
            }
            return new InvitationEntity(gameEntityCreateFromParcel, string, j, i, participantEntityCreateFromParcel, arrayList, -1, 0);
        }
    }

    InvitationEntity(GameEntity gameEntity, String str, long j, int i, ParticipantEntity participantEntity, ArrayList<ParticipantEntity> arrayList, int i2, int i3) {
        this.zzky = gameEntity;
        this.zzgr = str;
        this.zzoa = j;
        this.zzob = i;
        this.zzoc = participantEntity;
        this.zzod = arrayList;
        this.zzoe = i2;
        this.zzof = i3;
    }

    InvitationEntity(Invitation invitation) {
        this.zzky = new GameEntity(invitation.getGame());
        this.zzgr = invitation.getInvitationId();
        this.zzoa = invitation.getCreationTimestamp();
        this.zzob = invitation.getInvitationType();
        this.zzoe = invitation.getVariant();
        this.zzof = invitation.getAvailableAutoMatchSlots();
        String participantId = invitation.getInviter().getParticipantId();
        ArrayList<Participant> participants = invitation.getParticipants();
        int size = participants.size();
        this.zzod = new ArrayList<>(size);
        Participant participant = null;
        for (int i = 0; i < size; i++) {
            Participant participant2 = participants.get(i);
            if (participant2.getParticipantId().equals(participantId)) {
                participant = participant2;
            }
            this.zzod.add((ParticipantEntity) participant2.freeze());
        }
        Preconditions.checkNotNull(participant, "Must have a valid inviter!");
        this.zzoc = (ParticipantEntity) participant.freeze();
    }

    static int zza(Invitation invitation) {
        return Objects.hashCode(invitation.getGame(), invitation.getInvitationId(), Long.valueOf(invitation.getCreationTimestamp()), Integer.valueOf(invitation.getInvitationType()), invitation.getInviter(), invitation.getParticipants(), Integer.valueOf(invitation.getVariant()), Integer.valueOf(invitation.getAvailableAutoMatchSlots()));
    }

    static boolean zza(Invitation invitation, Object obj) {
        if (!(obj instanceof Invitation)) {
            return false;
        }
        if (invitation == obj) {
            return true;
        }
        Invitation invitation2 = (Invitation) obj;
        return Objects.equal(invitation2.getGame(), invitation.getGame()) && Objects.equal(invitation2.getInvitationId(), invitation.getInvitationId()) && Objects.equal(Long.valueOf(invitation2.getCreationTimestamp()), Long.valueOf(invitation.getCreationTimestamp())) && Objects.equal(Integer.valueOf(invitation2.getInvitationType()), Integer.valueOf(invitation.getInvitationType())) && Objects.equal(invitation2.getInviter(), invitation.getInviter()) && Objects.equal(invitation2.getParticipants(), invitation.getParticipants()) && Objects.equal(Integer.valueOf(invitation2.getVariant()), Integer.valueOf(invitation.getVariant())) && Objects.equal(Integer.valueOf(invitation2.getAvailableAutoMatchSlots()), Integer.valueOf(invitation.getAvailableAutoMatchSlots()));
    }

    static String zzb(Invitation invitation) {
        return Objects.toStringHelper(invitation).add("Game", invitation.getGame()).add("InvitationId", invitation.getInvitationId()).add("CreationTimestamp", Long.valueOf(invitation.getCreationTimestamp())).add("InvitationType", Integer.valueOf(invitation.getInvitationType())).add("Inviter", invitation.getInviter()).add("Participants", invitation.getParticipants()).add("Variant", Integer.valueOf(invitation.getVariant())).add("AvailableAutoMatchSlots", Integer.valueOf(invitation.getAvailableAutoMatchSlots())).toString();
    }

    public final boolean equals(Object obj) {
        return zza(this, obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.common.data.Freezable
    public final Invitation freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final int getAvailableAutoMatchSlots() {
        return this.zzof;
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final long getCreationTimestamp() {
        return this.zzoa;
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final Game getGame() {
        return this.zzky;
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final String getInvitationId() {
        return this.zzgr;
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final int getInvitationType() {
        return this.zzob;
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
    public final Participant getInviter() {
        return this.zzoc;
    }

    @Override // com.google.android.gms.games.multiplayer.Participatable
    public final ArrayList<Participant> getParticipants() {
        return new ArrayList<>(this.zzod);
    }

    @Override // com.google.android.gms.games.multiplayer.Invitation
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
        this.zzky.setShouldDowngrade(z);
        this.zzoc.setShouldDowngrade(z);
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
        if (shouldDowngrade()) {
            this.zzky.writeToParcel(parcel, i);
            parcel.writeString(this.zzgr);
            parcel.writeLong(this.zzoa);
            parcel.writeInt(this.zzob);
            this.zzoc.writeToParcel(parcel, i);
            int size = this.zzod.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                this.zzod.get(i2).writeToParcel(parcel, i);
            }
            return;
        }
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getGame(), i, false);
        SafeParcelWriter.writeString(parcel, 2, getInvitationId(), false);
        SafeParcelWriter.writeLong(parcel, 3, getCreationTimestamp());
        SafeParcelWriter.writeInt(parcel, 4, getInvitationType());
        SafeParcelWriter.writeParcelable(parcel, 5, getInviter(), i, false);
        SafeParcelWriter.writeTypedList(parcel, 6, getParticipants(), false);
        SafeParcelWriter.writeInt(parcel, 7, getVariant());
        SafeParcelWriter.writeInt(parcel, 8, getAvailableAutoMatchSlots());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
