package com.google.android.gms.games;

/* JADX INFO: loaded from: classes.dex */
final class zzw implements com.google.android.gms.games.internal.zzq<GamesMetadata.LoadGamesResult> {
    zzw() {
    }

    @Override // com.google.android.gms.games.internal.zzq
    public final /* synthetic */ void release(GamesMetadata.LoadGamesResult loadGamesResult) {
        GamesMetadata.LoadGamesResult loadGamesResult2 = loadGamesResult;
        if (loadGamesResult2.getGames() != null) {
            loadGamesResult2.getGames().release();
        }
    }
}
