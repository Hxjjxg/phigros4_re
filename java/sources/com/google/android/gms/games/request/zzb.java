package com.google.android.gms.games.request;

import android.os.Parcel;
import com.google.android.gms.common.data.DataBufferRef;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerRef;
import com.tds.common.entities.AccessToken;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class zzb extends DataBufferRef implements GameRequest {
    private final int zzmz;

    public zzb(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.zzmz = i2;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.DataBufferRef
    public final boolean equals(Object obj) {
        return GameRequestEntity.zza(this, obj);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final /* synthetic */ GameRequest freeze() {
        return new GameRequestEntity(this);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final long getCreationTimestamp() {
        return getLong("creation_timestamp");
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final byte[] getData() {
        return getByteArray(AccessToken.ROOT_ELEMENT_NAME);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final long getExpirationTimestamp() {
        return getLong("expiration_timestamp");
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final Game getGame() {
        return new GameRef(this.mDataHolder, this.mDataRow);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final int getRecipientStatus(String str) {
        for (int i = this.mDataRow; i < this.mDataRow + this.zzmz; i++) {
            int windowIndex = this.mDataHolder.getWindowIndex(i);
            if (this.mDataHolder.getString("recipient_external_player_id", i, windowIndex).equals(str)) {
                return this.mDataHolder.getInteger("recipient_status", i, windowIndex);
            }
        }
        return -1;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final List<Player> getRecipients() {
        ArrayList arrayList = new ArrayList(this.zzmz);
        for (int i = 0; i < this.zzmz; i++) {
            arrayList.add(new PlayerRef(this.mDataHolder, this.mDataRow + i, "recipient_"));
        }
        return arrayList;
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final String getRequestId() {
        return getString("external_request_id");
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final Player getSender() {
        return new PlayerRef(this.mDataHolder, getDataRow(), "sender_");
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final int getStatus() {
        return getInteger("status");
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final int getType() {
        return getInteger("type");
    }

    @Override // com.google.android.gms.common.data.DataBufferRef
    public final int hashCode() {
        return GameRequestEntity.zza(this);
    }

    @Override // com.google.android.gms.games.request.GameRequest
    public final boolean isConsumed(String str) {
        return getRecipientStatus(str) == 1;
    }

    public final String toString() {
        return GameRequestEntity.zzc(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ((GameRequestEntity) ((GameRequest) freeze())).writeToParcel(parcel, i);
    }
}
