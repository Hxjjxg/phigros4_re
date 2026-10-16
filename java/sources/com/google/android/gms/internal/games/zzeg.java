package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.video.Videos;

/* JADX INFO: loaded from: classes.dex */
abstract class zzeg extends Games.zza<Videos.CaptureStateResult> {
    private zzeg(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzeg(GoogleApiClient googleApiClient, zzdz zzdzVar) {
        this(googleApiClient);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public /* synthetic */ Result createFailedResult(Status status) {
        return new zzeh(this, status);
    }
}
