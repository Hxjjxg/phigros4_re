package com.google.android.gms.games;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;
import com.google.android.gms.tasks.Task;

/* JADX INFO: loaded from: classes.dex */
public class LeaderboardsClient extends com.google.android.gms.internal.games.zzu {
    private static final PendingResultUtil.ResultConverter<Leaderboards.LeaderboardMetadataResult, LeaderboardBuffer> zzbj = new zzal();
    private static final PendingResultUtil.ResultConverter<Leaderboards.LeaderboardMetadataResult, Leaderboard> zzbk = new zzam();
    private static final com.google.android.gms.games.internal.zzq<Leaderboards.LeaderboardMetadataResult> zzbl = new zzan();
    private static final PendingResultUtil.ResultConverter<Leaderboards.LoadPlayerScoreResult, LeaderboardScore> zzbm = new zzac();
    private static final com.google.android.gms.games.internal.zzr zzbn = new zzad();
    private static final PendingResultUtil.ResultConverter<Leaderboards.SubmitScoreResult, ScoreSubmissionData> zzbo = new zzae();
    private static final PendingResultUtil.ResultConverter<Leaderboards.LoadScoresResult, LeaderboardScores> zzbp = new zzaf();

    public static class LeaderboardScores implements Releasable {
        private final Leaderboard zzbv;
        private final LeaderboardScoreBuffer zzbw;

        LeaderboardScores(Leaderboard leaderboard, LeaderboardScoreBuffer leaderboardScoreBuffer) {
            this.zzbv = leaderboard;
            this.zzbw = leaderboardScoreBuffer;
        }

        public Leaderboard getLeaderboard() {
            return this.zzbv;
        }

        public LeaderboardScoreBuffer getScores() {
            return this.zzbw;
        }

        @Override // com.google.android.gms.common.api.Releasable
        public void release() {
            LeaderboardScoreBuffer leaderboardScoreBuffer = this.zzbw;
            if (leaderboardScoreBuffer != null) {
                leaderboardScoreBuffer.release();
            }
        }
    }

    LeaderboardsClient(Activity activity, Games.GamesOptions gamesOptions) {
        super(activity, gamesOptions);
    }

    LeaderboardsClient(Context context, Games.GamesOptions gamesOptions) {
        super(context, gamesOptions);
    }

    public Task<Intent> getAllLeaderboardsIntent() {
        return doRead(new zzab(this));
    }

    public Task<Intent> getLeaderboardIntent(String str) {
        return doRead(new zzag(this, str));
    }

    public Task<Intent> getLeaderboardIntent(String str, int i) {
        return doRead(new zzah(this, str, i));
    }

    public Task<Intent> getLeaderboardIntent(String str, int i, int i2) {
        return doRead(new zzai(this, str, i, i2));
    }

    public Task<AnnotatedData<LeaderboardScore>> loadCurrentPlayerLeaderboardScore(String str, int i, int i2) {
        return com.google.android.gms.games.internal.zzi.zza(Games.Leaderboards.loadCurrentPlayerLeaderboardScore(asGoogleApiClient(), str, i, i2), zzbm);
    }

    public Task<AnnotatedData<Leaderboard>> loadLeaderboardMetadata(String str, boolean z) {
        return com.google.android.gms.games.internal.zzi.zza(Games.Leaderboards.loadLeaderboardMetadata(asGoogleApiClient(), str, z), zzbk, zzbl);
    }

    public Task<AnnotatedData<LeaderboardBuffer>> loadLeaderboardMetadata(boolean z) {
        return com.google.android.gms.games.internal.zzi.zzb(Games.Leaderboards.loadLeaderboardMetadata(asGoogleApiClient(), z), zzbj);
    }

    public Task<AnnotatedData<LeaderboardScores>> loadMoreScores(LeaderboardScoreBuffer leaderboardScoreBuffer, int i, int i2) {
        return com.google.android.gms.games.internal.zzi.zzb(Games.Leaderboards.loadMoreScores(asGoogleApiClient(), leaderboardScoreBuffer, i, i2), zzbp);
    }

    public Task<AnnotatedData<LeaderboardScores>> loadPlayerCenteredScores(String str, int i, int i2, int i3) {
        return com.google.android.gms.games.internal.zzi.zzb(Games.Leaderboards.loadPlayerCenteredScores(asGoogleApiClient(), str, i, i2, i3), zzbp);
    }

    public Task<AnnotatedData<LeaderboardScores>> loadPlayerCenteredScores(String str, int i, int i2, int i3, boolean z) {
        return com.google.android.gms.games.internal.zzi.zzb(Games.Leaderboards.loadPlayerCenteredScores(asGoogleApiClient(), str, i, i2, i3, z), zzbp);
    }

    public Task<AnnotatedData<LeaderboardScores>> loadTopScores(String str, int i, int i2, int i3) {
        return com.google.android.gms.games.internal.zzi.zzb(Games.Leaderboards.loadTopScores(asGoogleApiClient(), str, i, i2, i3), zzbp);
    }

    public Task<AnnotatedData<LeaderboardScores>> loadTopScores(String str, int i, int i2, int i3, boolean z) {
        return com.google.android.gms.games.internal.zzi.zzb(Games.Leaderboards.loadTopScores(asGoogleApiClient(), str, i, i2, i3, z), zzbp);
    }

    public void submitScore(String str, long j) {
        doWrite(new zzaj(this, str, j));
    }

    public void submitScore(String str, long j, String str2) {
        doWrite(new zzak(this, str, j, str2));
    }

    public Task<ScoreSubmissionData> submitScoreImmediate(String str, long j) {
        return com.google.android.gms.games.internal.zzi.zza(Games.Leaderboards.submitScoreImmediate(asGoogleApiClient(), str, j), zzbn, zzbo);
    }

    public Task<ScoreSubmissionData> submitScoreImmediate(String str, long j, String str2) {
        return com.google.android.gms.games.internal.zzi.zza(Games.Leaderboards.submitScoreImmediate(asGoogleApiClient(), str, j, str2), zzbn, zzbo);
    }
}
