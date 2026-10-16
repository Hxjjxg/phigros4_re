package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Notifications;

/* JADX INFO: loaded from: classes.dex */
public final class zzbd implements Notifications {
    @Override // com.google.android.gms.games.Notifications
    public final void clear(GoogleApiClient googleApiClient, int i) {
        com.google.android.gms.games.internal.zze zzeVarZza = Games.zza(googleApiClient, false);
        if (zzeVarZza != null) {
            zzeVarZza.zzl(i);
        }
    }

    @Override // com.google.android.gms.games.Notifications
    public final void clearAll(GoogleApiClient googleApiClient) {
        clear(googleApiClient, 63);
    }
}
