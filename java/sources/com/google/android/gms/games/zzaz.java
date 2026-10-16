package com.google.android.gms.games;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil;

/* JADX INFO: loaded from: classes.dex */
final class zzaz implements PendingResultUtil.ResultConverter<Players.LoadPlayersResult, Player> {
    zzaz() {
    }

    private static Player zza(Players.LoadPlayersResult loadPlayersResult) {
        if (loadPlayersResult == null) {
            return null;
        }
        PlayerBuffer players = loadPlayersResult.getPlayers();
        if (players != null) {
            try {
                if (players.getCount() > 0) {
                    return ((Player) players.get(0)).freeze();
                }
            } finally {
                if (players != null) {
                    players.release();
                }
            }
        }
        return null;
    }

    @Override // com.google.android.gms.common.internal.PendingResultUtil.ResultConverter
    public final /* synthetic */ Player convert(Result result) {
        return zza((Players.LoadPlayersResult) result);
    }
}
