package com.google.android.gms.internal.games;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotContents;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.Snapshots;

/* JADX INFO: loaded from: classes.dex */
public final class zzci implements Snapshots {
    @Override // com.google.android.gms.games.snapshot.Snapshots
    public final PendingResult<Snapshots.CommitSnapshotResult> commitAndClose(GoogleApiClient googleApiClient, Snapshot snapshot, SnapshotMetadataChange snapshotMetadataChange) {
        return googleApiClient.execute(new zzcl(this, googleApiClient, snapshot, snapshotMetadataChange));
    }

    @Override // com.google.android.gms.games.snapshot.Snapshots
    public final PendingResult<Snapshots.DeleteSnapshotResult> delete(GoogleApiClient googleApiClient, SnapshotMetadata snapshotMetadata) {
        return googleApiClient.execute(new zzcm(this, googleApiClient, snapshotMetadata));
    }

    @Override // com.google.android.gms.games.snapshot.Snapshots
    public final void discardAndClose(GoogleApiClient googleApiClient, Snapshot snapshot) {
        Games.zza(googleApiClient).zzb(snapshot);
    }

    @Override // com.google.android.gms.games.snapshot.Snapshots
    public final int getMaxCoverImageSize(GoogleApiClient googleApiClient) {
        return Games.zza(googleApiClient).zzaw();
    }

    @Override // com.google.android.gms.games.snapshot.Snapshots
    public final int getMaxDataSize(GoogleApiClient googleApiClient) {
        return Games.zza(googleApiClient).zzau();
    }

    @Override // com.google.android.gms.games.snapshot.Snapshots
    public final Intent getSelectSnapshotIntent(GoogleApiClient googleApiClient, String str, boolean z, boolean z2, int i) {
        return Games.zza(googleApiClient).zzb(str, z, z2, i);
    }

    @Override // com.google.android.gms.games.snapshot.Snapshots
    public final SnapshotMetadata getSnapshotFromBundle(Bundle bundle) {
        if (bundle == null || !bundle.containsKey("com.google.android.gms.games.SNAPSHOT_METADATA")) {
            return null;
        }
        return (SnapshotMetadata) bundle.getParcelable("com.google.android.gms.games.SNAPSHOT_METADATA");
    }

    @Override // com.google.android.gms.games.snapshot.Snapshots
    public final PendingResult<Snapshots.LoadSnapshotsResult> load(GoogleApiClient googleApiClient, boolean z) {
        return googleApiClient.enqueue(new zzcj(this, googleApiClient, z));
    }

    @Override // com.google.android.gms.games.snapshot.Snapshots
    public final PendingResult<Snapshots.OpenSnapshotResult> open(GoogleApiClient googleApiClient, SnapshotMetadata snapshotMetadata) {
        return open(googleApiClient, snapshotMetadata.getUniqueName(), false);
    }

    @Override // com.google.android.gms.games.snapshot.Snapshots
    public final PendingResult<Snapshots.OpenSnapshotResult> open(GoogleApiClient googleApiClient, SnapshotMetadata snapshotMetadata, int i) {
        return open(googleApiClient, snapshotMetadata.getUniqueName(), false, i);
    }

    @Override // com.google.android.gms.games.snapshot.Snapshots
    public final PendingResult<Snapshots.OpenSnapshotResult> open(GoogleApiClient googleApiClient, String str, boolean z) {
        return open(googleApiClient, str, z, -1);
    }

    @Override // com.google.android.gms.games.snapshot.Snapshots
    public final PendingResult<Snapshots.OpenSnapshotResult> open(GoogleApiClient googleApiClient, String str, boolean z, int i) {
        return googleApiClient.execute(new zzck(this, googleApiClient, str, z, i));
    }

    @Override // com.google.android.gms.games.snapshot.Snapshots
    public final PendingResult<Snapshots.OpenSnapshotResult> resolveConflict(GoogleApiClient googleApiClient, String str, Snapshot snapshot) {
        SnapshotMetadata metadata = snapshot.getMetadata();
        return resolveConflict(googleApiClient, str, metadata.getSnapshotId(), new SnapshotMetadataChange.Builder().fromMetadata(metadata).build(), snapshot.getSnapshotContents());
    }

    @Override // com.google.android.gms.games.snapshot.Snapshots
    public final PendingResult<Snapshots.OpenSnapshotResult> resolveConflict(GoogleApiClient googleApiClient, String str, String str2, SnapshotMetadataChange snapshotMetadataChange, SnapshotContents snapshotContents) {
        return googleApiClient.execute(new zzcn(this, googleApiClient, str, str2, snapshotMetadataChange, snapshotContents));
    }
}
