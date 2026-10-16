package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;

/* JADX INFO: loaded from: classes.dex */
abstract class zzdq extends Games.zza<TurnBasedMultiplayer.LeaveMatchResult> {
    private zzdq(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzdq(GoogleApiClient googleApiClient, zzdc zzdcVar) {
        this(googleApiClient);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public /* synthetic */ Result createFailedResult(Status status) {
        return new zzdr(this, status);
    }
}
