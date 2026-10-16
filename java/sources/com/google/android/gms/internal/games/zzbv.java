package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.quest.Quests;

/* JADX INFO: loaded from: classes.dex */
abstract class zzbv extends Games.zza<Quests.ClaimMilestoneResult> {
    private zzbv(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzbv(GoogleApiClient googleApiClient, zzbp zzbpVar) {
        this(googleApiClient);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public /* synthetic */ Result createFailedResult(Status status) {
        return new zzbw(this, status);
    }
}
