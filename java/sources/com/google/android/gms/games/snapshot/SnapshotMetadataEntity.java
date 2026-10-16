package com.google.android.gms.games.snapshot;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;

/* JADX INFO: loaded from: classes.dex */
public final class SnapshotMetadataEntity extends com.google.android.gms.games.internal.zzd implements SnapshotMetadata {
    public static final Parcelable.Creator<SnapshotMetadataEntity> CREATOR = new zzf();
    private final String description;
    private final String deviceName;
    private final String zzcc;
    private final String zzgo;
    private final GameEntity zzky;
    private final Uri zzqs;
    private final PlayerEntity zzqv;
    private final String zzqw;
    private final long zzqx;
    private final long zzqy;
    private final float zzqz;
    private final String zzra;
    private final boolean zzrb;
    private final long zzrc;

    SnapshotMetadataEntity(GameEntity gameEntity, PlayerEntity playerEntity, String str, Uri uri, String str2, String str3, String str4, long j, long j2, float f, String str5, boolean z, long j3, String str6) {
        this.zzky = gameEntity;
        this.zzqv = playerEntity;
        this.zzgo = str;
        this.zzqs = uri;
        this.zzqw = str2;
        this.zzqz = f;
        this.zzcc = str3;
        this.description = str4;
        this.zzqx = j;
        this.zzqy = j2;
        this.zzra = str5;
        this.zzrb = z;
        this.zzrc = j3;
        this.deviceName = str6;
    }

    public SnapshotMetadataEntity(SnapshotMetadata snapshotMetadata) {
        this.zzky = new GameEntity(snapshotMetadata.getGame());
        this.zzqv = new PlayerEntity(snapshotMetadata.getOwner());
        this.zzgo = snapshotMetadata.getSnapshotId();
        this.zzqs = snapshotMetadata.getCoverImageUri();
        this.zzqw = snapshotMetadata.getCoverImageUrl();
        this.zzqz = snapshotMetadata.getCoverImageAspectRatio();
        this.zzcc = snapshotMetadata.getTitle();
        this.description = snapshotMetadata.getDescription();
        this.zzqx = snapshotMetadata.getLastModifiedTimestamp();
        this.zzqy = snapshotMetadata.getPlayedTime();
        this.zzra = snapshotMetadata.getUniqueName();
        this.zzrb = snapshotMetadata.hasChangePending();
        this.zzrc = snapshotMetadata.getProgressValue();
        this.deviceName = snapshotMetadata.getDeviceName();
    }

    static int zza(SnapshotMetadata snapshotMetadata) {
        return Objects.hashCode(snapshotMetadata.getGame(), snapshotMetadata.getOwner(), snapshotMetadata.getSnapshotId(), snapshotMetadata.getCoverImageUri(), Float.valueOf(snapshotMetadata.getCoverImageAspectRatio()), snapshotMetadata.getTitle(), snapshotMetadata.getDescription(), Long.valueOf(snapshotMetadata.getLastModifiedTimestamp()), Long.valueOf(snapshotMetadata.getPlayedTime()), snapshotMetadata.getUniqueName(), Boolean.valueOf(snapshotMetadata.hasChangePending()), Long.valueOf(snapshotMetadata.getProgressValue()), snapshotMetadata.getDeviceName());
    }

    static boolean zza(SnapshotMetadata snapshotMetadata, Object obj) {
        if (!(obj instanceof SnapshotMetadata)) {
            return false;
        }
        if (snapshotMetadata == obj) {
            return true;
        }
        SnapshotMetadata snapshotMetadata2 = (SnapshotMetadata) obj;
        return Objects.equal(snapshotMetadata2.getGame(), snapshotMetadata.getGame()) && Objects.equal(snapshotMetadata2.getOwner(), snapshotMetadata.getOwner()) && Objects.equal(snapshotMetadata2.getSnapshotId(), snapshotMetadata.getSnapshotId()) && Objects.equal(snapshotMetadata2.getCoverImageUri(), snapshotMetadata.getCoverImageUri()) && Objects.equal(Float.valueOf(snapshotMetadata2.getCoverImageAspectRatio()), Float.valueOf(snapshotMetadata.getCoverImageAspectRatio())) && Objects.equal(snapshotMetadata2.getTitle(), snapshotMetadata.getTitle()) && Objects.equal(snapshotMetadata2.getDescription(), snapshotMetadata.getDescription()) && Objects.equal(Long.valueOf(snapshotMetadata2.getLastModifiedTimestamp()), Long.valueOf(snapshotMetadata.getLastModifiedTimestamp())) && Objects.equal(Long.valueOf(snapshotMetadata2.getPlayedTime()), Long.valueOf(snapshotMetadata.getPlayedTime())) && Objects.equal(snapshotMetadata2.getUniqueName(), snapshotMetadata.getUniqueName()) && Objects.equal(Boolean.valueOf(snapshotMetadata2.hasChangePending()), Boolean.valueOf(snapshotMetadata.hasChangePending())) && Objects.equal(Long.valueOf(snapshotMetadata2.getProgressValue()), Long.valueOf(snapshotMetadata.getProgressValue())) && Objects.equal(snapshotMetadata2.getDeviceName(), snapshotMetadata.getDeviceName());
    }

    static String zzb(SnapshotMetadata snapshotMetadata) {
        return Objects.toStringHelper(snapshotMetadata).add("Game", snapshotMetadata.getGame()).add("Owner", snapshotMetadata.getOwner()).add("SnapshotId", snapshotMetadata.getSnapshotId()).add("CoverImageUri", snapshotMetadata.getCoverImageUri()).add("CoverImageUrl", snapshotMetadata.getCoverImageUrl()).add("CoverImageAspectRatio", Float.valueOf(snapshotMetadata.getCoverImageAspectRatio())).add("Description", snapshotMetadata.getDescription()).add("LastModifiedTimestamp", Long.valueOf(snapshotMetadata.getLastModifiedTimestamp())).add("PlayedTime", Long.valueOf(snapshotMetadata.getPlayedTime())).add("UniqueName", snapshotMetadata.getUniqueName()).add("ChangePending", Boolean.valueOf(snapshotMetadata.hasChangePending())).add("ProgressValue", Long.valueOf(snapshotMetadata.getProgressValue())).add("DeviceName", snapshotMetadata.getDeviceName()).toString();
    }

    public final boolean equals(Object obj) {
        return zza(this, obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.common.data.Freezable
    public final SnapshotMetadata freeze() {
        return this;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final float getCoverImageAspectRatio() {
        return this.zzqz;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final Uri getCoverImageUri() {
        return this.zzqs;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getCoverImageUrl() {
        return this.zzqw;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getDescription() {
        return this.description;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final void getDescription(CharArrayBuffer charArrayBuffer) {
        DataUtils.copyStringToBuffer(this.description, charArrayBuffer);
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getDeviceName() {
        return this.deviceName;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final Game getGame() {
        return this.zzky;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final long getLastModifiedTimestamp() {
        return this.zzqx;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final Player getOwner() {
        return this.zzqv;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final long getPlayedTime() {
        return this.zzqy;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final long getProgressValue() {
        return this.zzrc;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getSnapshotId() {
        return this.zzgo;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getTitle() {
        return this.zzcc;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final String getUniqueName() {
        return this.zzra;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotMetadata
    public final boolean hasChangePending() {
        return this.zzrb;
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
        SafeParcelWriter.writeParcelable(parcel, 1, getGame(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 2, getOwner(), i, false);
        SafeParcelWriter.writeString(parcel, 3, getSnapshotId(), false);
        SafeParcelWriter.writeParcelable(parcel, 5, getCoverImageUri(), i, false);
        SafeParcelWriter.writeString(parcel, 6, getCoverImageUrl(), false);
        SafeParcelWriter.writeString(parcel, 7, this.zzcc, false);
        SafeParcelWriter.writeString(parcel, 8, getDescription(), false);
        SafeParcelWriter.writeLong(parcel, 9, getLastModifiedTimestamp());
        SafeParcelWriter.writeLong(parcel, 10, getPlayedTime());
        SafeParcelWriter.writeFloat(parcel, 11, getCoverImageAspectRatio());
        SafeParcelWriter.writeString(parcel, 12, getUniqueName(), false);
        SafeParcelWriter.writeBoolean(parcel, 13, hasChangePending());
        SafeParcelWriter.writeLong(parcel, 14, getProgressValue());
        SafeParcelWriter.writeString(parcel, 15, getDeviceName(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
