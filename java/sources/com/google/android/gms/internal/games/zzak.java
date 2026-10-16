package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.Invitations;

/* JADX INFO: loaded from: classes.dex */
abstract class zzak extends Games.zza<Invitations.LoadInvitationsResult> {
    private zzak(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzak(GoogleApiClient googleApiClient, zzaj zzajVar) {
        this(googleApiClient);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public /* synthetic */ Result createFailedResult(Status status) {
        return new zzal(this, status);
    }
}
