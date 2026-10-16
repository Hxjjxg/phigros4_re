package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.event.Events;

/* JADX INFO: loaded from: classes.dex */
abstract class zzz extends Games.zza<Events.LoadEventsResult> {
    private zzz(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzz(GoogleApiClient googleApiClient, zzw zzwVar) {
        this(googleApiClient);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public /* synthetic */ Result createFailedResult(Status status) {
        return new zzaa(this, status);
    }
}
