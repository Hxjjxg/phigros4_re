package com.google.android.gms.games.snapshot;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataBufferRef;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameRef;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerRef;
import com.tapsdk.antiaddiction.constants.Constants;

/* JADX INFO: loaded from: classes.dex */
public final class SnapshotMetadataRef extends DataBufferRef implements SnapshotMetadata {
    private final Game zzmy;
    private final Player zzrd;

    public SnapshotMetadataRef(DataHolder dataHolder, int i) {
        super(dataHolder, i);
        this.zzmy = new GameRef(dataHolder, i);
        this.zzrd = new PlayerRef(dataHolder, i);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.DataBufferRef
    public final boolean equals(Object obj) {
        return SnapshotMetadataEntity.zza(this, obj);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final /* synthetic */ SnapshotMetadata freeze() {
        return new SnapshotMetadataEntity(this);
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final float getCoverImageAspectRatio() {
        float f = getFloat("cover_icon_image_height");
        float f2 = getFloat("cover_icon_image_width");
        if (f == 0.0f) {
            return 0.0f;
        }
        return f2 / f;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final Uri getCoverImageUri() {
        return parseUri("cover_icon_image_uri");
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getCoverImageUrl() {
        return getString("cover_icon_image_url");
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getDescription() {
        return getString(Constants.MsgExtraParams.DESCRIPTION);
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final void getDescription(CharArrayBuffer charArrayBuffer) {
        copyToBuffer(Constants.MsgExtraParams.DESCRIPTION, charArrayBuffer);
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getDeviceName() {
        return getString("device_name");
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final Game getGame() {
        return this.zzmy;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final long getLastModifiedTimestamp() {
        return getLong("last_modified_timestamp");
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final Player getOwner() {
        return this.zzrd;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final long getPlayedTime() {
        return getLong("duration");
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final long getProgressValue() {
        return getLong("progress_value");
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getSnapshotId() {
        return getString("external_snapshot_id");
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getTitle() {
        return getString("title");
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getUniqueName() {
        return getString("unique_name");
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final boolean hasChangePending() {
        return getInteger("pending_change_count") > 0;
    }

    @Override // com.google.android.gms.common.data.DataBufferRef
    public final int hashCode() {
        return SnapshotMetadataEntity.zza(this);
    }

    public final String toString() {
        return SnapshotMetadataEntity.zzb(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ((SnapshotMetadataEntity) ((SnapshotMetadata) freeze())).writeToParcel(parcel, i);
    }
}
