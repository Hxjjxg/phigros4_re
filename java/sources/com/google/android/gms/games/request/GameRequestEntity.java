package com.google.android.gms.games.request;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.internal.zzd;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class GameRequestEntity extends zzd implements GameRequest {
    public static final Parcelable.Creator<GameRequestEntity> CREATOR = new zza();
    private final byte[] data;
    private final int status;
    private final int type;
    private final String zzht;
    private final GameEntity zzky;
    private final long zzoa;
    private final PlayerEntity zzqh;
    private final ArrayList<PlayerEntity> zzqi;
    private final long zzqj;
    private final Bundle zzqk;

    GameRequestEntity(GameEntity gameEntity, PlayerEntity playerEntity, byte[] bArr, String str, ArrayList<PlayerEntity> arrayList, int i, long j, long j2, Bundle bundle, int i2) {
        this.zzky = gameEntity;
        this.zzqh = playerEntity;
        this.data = bArr;
        this.zzht = str;
        this.zzqi = arrayList;
        this.type = i;
        this.zzoa = j;
        this.zzqj = j2;
        this.zzqk = bundle;
        this.status = i2;
    }

    public GameRequestEntity(GameRequest gameRequest) {
        this.zzky = new GameEntity(gameRequest.getGame());
        this.zzqh = new PlayerEntity(gameRequest.getSender());
        this.zzht = gameRequest.getRequestId();
        this.type = gameRequest.getType();
        this.zzoa = gameRequest.getCreationTimestamp();
        this.zzqj = gameRequest.getExpirationTimestamp();
        this.status = gameRequest.getStatus();
        byte[] data = gameRequest.getData();
        if (data == null) {
            this.data = null;
        } else {
            byte[] bArr = new byte[data.length];
            this.data = bArr;
            System.arraycopy(data, 0, bArr, 0, data.length);
        }
        List<Player> recipients = gameRequest.getRecipients();
        int size = recipients.size();
        this.zzqi = new ArrayList<>(size);
        this.zzqk = new Bundle();
        for (int i = 0; i < size; i++) {
            Player playerFreeze = recipients.get(i).freeze();
            String playerId = playerFreeze.getPlayerId();
            this.zzqi.add((PlayerEntity) playerFreeze);
            this.zzqk.putInt(playerId, gameRequest.getRecipientStatus(playerId));
        }
    }

    static int zza(GameRequest gameRequest) {
        return (Arrays.hashCode(zzb(gameRequest)) * 31) + Objects.hashCode(gameRequest.getGame(), gameRequest.getRecipients(), gameRequest.getRequestId(), gameRequest.getSender(), Integer.valueOf(gameRequest.getType()), Long.valueOf(gameRequest.getCreationTimestamp()), Long.valueOf(gameRequest.getExpirationTimestamp()));
    }

    static boolean zza(GameRequest gameRequest, Object obj) {
        if (!(obj instanceof GameRequest)) {
            return false;
        }
        if (gameRequest == obj) {
            return true;
        }
        GameRequest gameRequest2 = (GameRequest) obj;
        return Objects.equal(gameRequest2.getGame(), gameRequest.getGame()) && Objects.equal(gameRequest2.getRecipients(), gameRequest.getRecipients()) && Objects.equal(gameRequest2.getRequestId(), gameRequest.getRequestId()) && Objects.equal(gameRequest2.getSender(), gameRequest.getSender()) && Arrays.equals(zzb(gameRequest2), zzb(gameRequest)) && Objects.equal(Integer.valueOf(gameRequest2.getType()), Integer.valueOf(gameRequest.getType())) && Objects.equal(Long.valueOf(gameRequest2.getCreationTimestamp()), Long.valueOf(gameRequest.getCreationTimestamp())) && Objects.equal(Long.valueOf(gameRequest2.getExpirationTimestamp()), Long.valueOf(gameRequest.getExpirationTimestamp()));
    }

    private static int[] zzb(GameRequest gameRequest) {
        List<Player> recipients = gameRequest.getRecipients();
        int size = recipients.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = gameRequest.getRecipientStatus(recipients.get(i).getPlayerId());
        }
        return iArr;
    }

    static String zzc(GameRequest gameRequest) {
        return Objects.toStringHelper(gameRequest).add("Game", gameRequest.getGame()).add("Sender", gameRequest.getSender()).add("Recipients", gameRequest.getRecipients()).add("Data", gameRequest.getData()).add("RequestId", gameRequest.getRequestId()).add("Type", Integer.valueOf(gameRequest.getType())).add("CreationTimestamp", Long.valueOf(gameRequest.getCreationTimestamp())).add("ExpirationTimestamp", Long.valueOf(gameRequest.getExpirationTimestamp())).toString();
    }

    public final boolean equals(Object obj) {
        return zza(this, obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.common.data.Freezable
    public final GameRequest freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final long getCreationTimestamp() {
        return this.zzoa;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final byte[] getData() {
        return this.data;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final long getExpirationTimestamp() {
        return this.zzqj;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final Game getGame() {
        return this.zzky;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final int getRecipientStatus(String str) {
        return this.zzqk.getInt(str, 0);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final List<Player> getRecipients() {
        return new ArrayList(this.zzqi);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final String getRequestId() {
        return this.zzht;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final Player getSender() {
        return this.zzqh;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final int getStatus() {
        return this.status;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final int getType() {
        return this.type;
    }

    public final int hashCode() {
        return zza(this);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final boolean isConsumed(String str) {
        return getRecipientStatus(str) == 1;
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final boolean isDataValid() {
        return true;
    }

    public final String toString() {
        return zzc(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getGame(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 2, getSender(), i, false);
        SafeParcelWriter.writeByteArray(parcel, 3, getData(), false);
        SafeParcelWriter.writeString(parcel, 4, getRequestId(), false);
        SafeParcelWriter.writeTypedList(parcel, 5, getRecipients(), false);
        SafeParcelWriter.writeInt(parcel, 7, getType());
        SafeParcelWriter.writeLong(parcel, 9, getCreationTimestamp());
        SafeParcelWriter.writeLong(parcel, 10, getExpirationTimestamp());
        SafeParcelWriter.writeBundle(parcel, 11, this.zzqk, false);
        SafeParcelWriter.writeInt(parcel, 12, getStatus());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
