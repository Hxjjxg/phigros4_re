package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;

/* JADX INFO: loaded from: classes.dex */
abstract class zzdm extends Games.zza<TurnBasedMultiplayer.CancelMatchResult> {
    private final String zzji;

    public zzdm(String str, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzji = str;
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public /* synthetic */ Result createFailedResult(Status status) {
        return new zzdn(this, status);
    }
}
