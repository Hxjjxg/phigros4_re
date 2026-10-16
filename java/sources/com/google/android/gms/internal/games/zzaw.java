package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.leaderboard.Leaderboards;

/* JADX INFO: loaded from: classes.dex */
abstract class zzaw extends Games.zza<Leaderboards.LoadPlayerScoreResult> {
    private zzaw(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzaw(GoogleApiClient googleApiClient, zzan zzanVar) {
        this(googleApiClient);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public /* synthetic */ Result createFailedResult(Status status) {
        return new zzax(this, status);
    }
}
