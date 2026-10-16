package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesMetadata;

/* JADX INFO: loaded from: classes.dex */
abstract class zzaf extends Games.zza<GamesMetadata.LoadGamesResult> {
    private zzaf(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzaf(GoogleApiClient googleApiClient, zzae zzaeVar) {
        this(googleApiClient);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public /* synthetic */ Result createFailedResult(Status status) {
        return new zzag(this, status);
    }
}
