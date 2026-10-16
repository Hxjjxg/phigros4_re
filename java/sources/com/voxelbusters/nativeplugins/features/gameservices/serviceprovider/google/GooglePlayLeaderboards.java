package com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.voxelbusters.nativeplugins.defines.CommonDefines;
import com.voxelbusters.nativeplugins.defines.Enums;
import com.voxelbusters.nativeplugins.defines.Keys;
import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.Score;
import com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener;
import com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.util.DataConversionUtils;
import com.voxelbusters.nativeplugins.utilities.Debug;
import java.util.ArrayList;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes.dex */
public class GooglePlayLeaderboards {
    GoogleApiClient apiService;
    IGameServicesPlayerListener listener;
    LeaderboardScore localUserScore;
    String TAG = "GooglePlayLeaderboards";
    Hashtable<String, LeaderboardScoreBuffer> leaderboardScoreData = new Hashtable<>();
    long minRank = 0;
    long maxRank = 0;
    int emptyPageCount = 0;
    int pageSize = 0;

    public GooglePlayLeaderboards(GoogleApiClient googleApiClient) {
        this.apiService = googleApiClient;
    }

    public void setListener(IGameServicesPlayerListener iGameServicesPlayerListener) {
        this.listener = iGameServicesPlayerListener;
    }

    public void removeListener(IGameServicesPlayerListener iGameServicesPlayerListener) {
        if (iGameServicesPlayerListener == this.listener) {
            this.listener = null;
        }
    }

    public void reportScore(final String str, String str2, long j, boolean z) {
        if (!z) {
            Games.Leaderboards.submitScore(this.apiService, str2, j);
        } else {
            Games.Leaderboards.submitScoreImmediate(this.apiService, str2, j).setResultCallback(new ResultCallback<Leaderboards.SubmitScoreResult>() { // from class: com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.GooglePlayLeaderboards.1
                @Override // com.google.android.gms.common.api.ResultCallback
                public void onResult(Leaderboards.SubmitScoreResult submitScoreResult) {
                    int statusCode = submitScoreResult.getStatus().getStatusCode();
                    if (statusCode == 0) {
                        Debug.log(CommonDefines.GAME_SERVICES_TAG, "Score Submitted! - " + submitScoreResult.getScoreData().toString());
                        Score score = new Score();
                        score.value = submitScoreResult.getScoreData().getScoreResult(2).rawScore;
                        score.date = System.currentTimeMillis();
                        score.user = DataConversionUtils.getUser(Games.Players.getCurrentPlayer(GooglePlayLeaderboards.this.apiService));
                        if (GooglePlayLeaderboards.this.listener != null) {
                            GooglePlayLeaderboards.this.listener.onReportScore(str, score, null);
                            return;
                        }
                        return;
                    }
                    String statusString = GamesStatusCodes.getStatusString(statusCode);
                    Debug.error(CommonDefines.GAME_SERVICES_TAG, "Error Submitting Score : " + statusString);
                    if (GooglePlayLeaderboards.this.listener != null) {
                        GooglePlayLeaderboards.this.listener.onReportScore(str, null, statusString);
                    }
                }
            });
        }
    }

    public void showUI(Context context, String str, int i) {
        Intent intent = new Intent(context, (Class<?>) GooglePlayGameUIActivity.class);
        intent.putExtra("type", Keys.GameServices.SHOW_LEADERBOARDS);
        intent.putExtra(Keys.GameServices.LEADERBOARD_ID, str);
        intent.putExtra(Keys.GameServices.TIME_SPAN, "" + i);
        context.startActivity(intent);
    }

    public void loadPlayerCenteredScores(String str, String str2, int i, int i2, int i3) {
        PendingResult<Leaderboards.LoadScoresResult> pendingResultLoadPlayerCenteredScores = Games.Leaderboards.loadPlayerCenteredScores(this.apiService, str2, i, i2, i3);
        this.localUserScore = getLocalUserScore(str2, i, i2);
        loadScoresAsync(str, Enums.eLoadScoresType.PlayerCentered, -1, i3, pendingResultLoadPlayerCenteredScores);
    }

    public void loadTopScores(String str, String str2, int i, int i2, int i3) {
        PendingResult<Leaderboards.LoadScoresResult> pendingResultLoadTopScores = Games.Leaderboards.loadTopScores(this.apiService, str2, i, i2, i3);
        this.localUserScore = getLocalUserScore(str2, i, i2);
        loadScoresAsync(str, Enums.eLoadScoresType.Top, -1, i3, pendingResultLoadTopScores);
    }

    public void loadMoreScores(String str, String str2, int i, int i2) {
        LeaderboardScoreBuffer leaderboardScoreBuffer = this.leaderboardScoreData.get(str2);
        if (leaderboardScoreBuffer == null) {
            PendingResult<Leaderboards.LoadScoresResult> pendingResultLoadTopScores = Games.Leaderboards.loadTopScores(this.apiService, str2, 2, 0, i2);
            this.localUserScore = getLocalUserScore(str2, 2, 0);
            loadScoresAsync(str, Enums.eLoadScoresType.More, -1, i2, pendingResultLoadTopScores);
        } else {
            if (this.minRank <= 1 && i == 1) {
                this.maxRank = 0L;
                this.minRank = 0L;
                IGameServicesPlayerListener iGameServicesPlayerListener = this.listener;
                if (iGameServicesPlayerListener != null) {
                    iGameServicesPlayerListener.onLoadingScores(str, null, null, "No score pages available.");
                    return;
                }
                return;
            }
            loadScoresAsync(str, Enums.eLoadScoresType.More, i, i2, Games.Leaderboards.loadMoreScores(this.apiService, leaderboardScoreBuffer, this.pageSize, i != 0 ? 1 : 0));
        }
    }

    void loadScoresAsync(final String str, final Enums.eLoadScoresType eloadscorestype, final int i, final int i2, PendingResult<Leaderboards.LoadScoresResult> pendingResult) {
        if (eloadscorestype != Enums.eLoadScoresType.More) {
            this.pageSize = i2;
        }
        pendingResult.setResultCallback(new ResultCallback<Leaderboards.LoadScoresResult>() { // from class: com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.GooglePlayLeaderboards.2
            @Override // com.google.android.gms.common.api.ResultCallback
            public void onResult(Leaderboards.LoadScoresResult loadScoresResult) {
                int i3;
                int statusCode = loadScoresResult.getStatus().getStatusCode();
                if (statusCode == 0 || statusCode == 3) {
                    String leaderboardId = loadScoresResult.getLeaderboard().getLeaderboardId();
                    LeaderboardScoreBuffer scores = loadScoresResult.getScores();
                    ArrayList<Score> arrayList = new ArrayList<>();
                    synchronized (GooglePlayLeaderboards.this.leaderboardScoreData) {
                        LeaderboardScoreBuffer leaderboardScoreBuffer = GooglePlayLeaderboards.this.leaderboardScoreData.get(leaderboardId);
                        if (leaderboardScoreBuffer != null) {
                            leaderboardScoreBuffer.release();
                        }
                        GooglePlayLeaderboards.this.leaderboardScoreData.put(leaderboardId, scores);
                        long j = GooglePlayLeaderboards.this.minRank;
                        long j2 = GooglePlayLeaderboards.this.maxRank;
                        GooglePlayLeaderboards.this.calculateRankRange(scores, i, i2);
                        int count = scores.getCount();
                        int i4 = 0;
                        while (i4 < count) {
                            LeaderboardScore leaderboardScore = scores.get(i4);
                            if (i != -1) {
                                i3 = i4;
                                if (leaderboardScore.getRank() >= GooglePlayLeaderboards.this.minRank && leaderboardScore.getRank() <= GooglePlayLeaderboards.this.maxRank) {
                                }
                                i4 = i3 + 1;
                            } else {
                                i3 = i4;
                            }
                            arrayList.add(GooglePlayLeaderboards.this.getScore(leaderboardId, leaderboardScore));
                            i4 = i3 + 1;
                        }
                        if (arrayList.size() == 0) {
                            if (GooglePlayLeaderboards.this.emptyPageCount > 0) {
                                GooglePlayLeaderboards.this.minRank = j;
                                GooglePlayLeaderboards.this.maxRank = j2;
                            } else {
                                GooglePlayLeaderboards.this.emptyPageCount++;
                            }
                        } else {
                            GooglePlayLeaderboards.this.emptyPageCount = 0;
                        }
                    }
                    if (GooglePlayLeaderboards.this.listener != null) {
                        IGameServicesPlayerListener iGameServicesPlayerListener = GooglePlayLeaderboards.this.listener;
                        String str2 = str;
                        GooglePlayLeaderboards googlePlayLeaderboards = GooglePlayLeaderboards.this;
                        iGameServicesPlayerListener.onLoadingScores(str2, googlePlayLeaderboards.getScore(leaderboardId, googlePlayLeaderboards.localUserScore), arrayList, null);
                        return;
                    }
                    return;
                }
                String statusString = GamesStatusCodes.getStatusString(statusCode);
                Debug.error(CommonDefines.GAME_SERVICES_TAG, "Failed loading " + eloadscorestype.toString() + " scores with error code : " + statusCode + " Message: " + statusString);
                if (GooglePlayLeaderboards.this.listener != null) {
                    GooglePlayLeaderboards.this.listener.onLoadingScores(str, null, null, statusString);
                }
            }
        });
    }

    LeaderboardScore getLocalUserScore(String str, int i, int i2) {
        return ((Leaderboards.LoadPlayerScoreResult) Games.Leaderboards.loadCurrentPlayerLeaderboardScore(this.apiService, str, i, i2).await()).getScore();
    }

    void calculateRankRange(LeaderboardScoreBuffer leaderboardScoreBuffer, int i, int i2) {
        if (i == -1) {
            int count = leaderboardScoreBuffer.getCount();
            if (count > 0) {
                this.minRank = leaderboardScoreBuffer.get(0).getRank();
                this.maxRank = leaderboardScoreBuffer.get(count - 1).getRank();
                return;
            }
            return;
        }
        if (i == 0) {
            long j = this.maxRank + 1;
            this.minRank = j;
            this.maxRank = (j + ((long) i2)) - 1;
        } else if (i == 1) {
            long j2 = i2;
            long jMax = Math.max(1L, this.minRank - j2);
            this.minRank = jMax;
            this.maxRank = Math.max(1L, (jMax + j2) - 1);
        }
    }

    Score getScore(String str, LeaderboardScore leaderboardScore) {
        Score score = new Score();
        score.identifier = str;
        if (leaderboardScore != null) {
            score.user = DataConversionUtils.getUser(leaderboardScore.getScoreHolder());
            score.date = leaderboardScore.getTimestampMillis();
            score.value = leaderboardScore.getRawScore();
            score.formattedValue = leaderboardScore.getDisplayScore();
            score.rank = leaderboardScore.getRank();
        }
        return score;
    }
}
