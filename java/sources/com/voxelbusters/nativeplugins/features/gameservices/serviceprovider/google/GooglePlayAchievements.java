package com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.achievement.Achievement;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements;
import com.voxelbusters.nativeplugins.defines.CommonDefines;
import com.voxelbusters.nativeplugins.defines.Keys;
import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.AchievementData;
import com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener;
import com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.util.DataConversionUtils;
import com.voxelbusters.nativeplugins.utilities.Debug;
import com.voxelbusters.nativeplugins.utilities.JSONUtility;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class GooglePlayAchievements {
    GoogleApiClient apiService;
    IGameServicesPlayerListener listener;
    String TAG = "GooglePlayAchievements";
    HashMap<String, AchievementInfo> achievementsData = new HashMap<>();
    String instanceId = null;

    public GooglePlayAchievements(GoogleApiClient googleApiClient) {
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

    public void reportProgress(String str, String str2, int i, boolean z) {
        AchievementInfo achievementInfoGetAchievement = GetAchievement(str2);
        if (achievementInfoGetAchievement != null) {
            if (achievementInfoGetAchievement.type == 1) {
                Debug.log(this.TAG, "Achievement Type : " + achievementInfoGetAchievement.type);
                if (i >= 0 && achievementInfoGetAchievement.state == 2) {
                    Debug.log(this.TAG, "Revel the achievement now!");
                    revealAchievement(str, str2, false);
                }
                int i2 = achievementInfoGetAchievement.totalSteps;
                int i3 = achievementInfoGetAchievement.currentSteps;
                Debug.log(this.TAG, "totalSteps : " + i2 + " completedSteps : " + i3);
                if (i < i3) {
                    Debug.error(this.TAG, "Reported negative progress!!!");
                    IGameServicesPlayerListener iGameServicesPlayerListener = this.listener;
                    if (iGameServicesPlayerListener != null) {
                        iGameServicesPlayerListener.onReportProgress(str, DataConversionUtils.getAchievementData(achievementInfoGetAchievement), "Reported wrong progress value!");
                    }
                } else {
                    int i4 = i - i3;
                    Debug.log("Report", "incrementedSteps : " + i4);
                    if (i4 != 0) {
                        incrementAchievement(str, str2, i4, z);
                    } else {
                        IGameServicesPlayerListener iGameServicesPlayerListener2 = this.listener;
                        if (iGameServicesPlayerListener2 != null) {
                            iGameServicesPlayerListener2.onReportProgress(str, DataConversionUtils.getAchievementData(achievementInfoGetAchievement), null);
                        }
                    }
                }
            } else if (i == 0) {
                Debug.log(this.TAG, "This is not an incremental achievement. So just trying to reveal it as 100% progress was not sent as progress.");
                revealAchievement(str, str2, z);
            } else if (i > 0) {
                Debug.log(this.TAG, "Unlocking Achievement");
                unlockAchievement(str, str2, z);
            } else {
                IGameServicesPlayerListener iGameServicesPlayerListener3 = this.listener;
                if (iGameServicesPlayerListener3 != null) {
                    iGameServicesPlayerListener3.onReportProgress(str, DataConversionUtils.getAchievementData(achievementInfoGetAchievement), "Reported wrong progress value. Cannot be negative!");
                }
            }
            if (z) {
                return;
            }
            loadAchievementsDetails(false, false);
            return;
        }
        this.listener.onReportProgress(str, new AchievementData(), "Achievements Data doesn't contain this achievement id. Did you load Achievement Descriptions ahead? ID : " + str2);
    }

    public void revealAchievement(String str, String str2, boolean z) {
        if (!z) {
            Games.Achievements.reveal(this.apiService, str2);
        } else {
            setAchievementProgressCallback(str, str2, Games.Achievements.revealImmediate(this.apiService, str2));
        }
    }

    public void unlockAchievement(String str, String str2, boolean z) {
        if (!z) {
            Games.Achievements.unlock(this.apiService, str2);
        } else {
            setAchievementProgressCallback(str, str2, Games.Achievements.unlockImmediate(this.apiService, str2));
        }
        GetAchievement(str2).SetCurrentSteps(1);
    }

    public void incrementAchievement(String str, String str2, int i, boolean z) {
        if (!z) {
            Games.Achievements.increment(this.apiService, str2, i);
        } else {
            setAchievementProgressCallback(str, str2, Games.Achievements.incrementImmediate(this.apiService, str2, i));
        }
        AchievementInfo achievementInfoGetAchievement = GetAchievement(str2);
        achievementInfoGetAchievement.SetCurrentSteps(achievementInfoGetAchievement.currentSteps + i);
    }

    private void setAchievementProgressCallback(final String str, final String str2, PendingResult<Achievements.UpdateAchievementResult> pendingResult) {
        pendingResult.setResultCallback(new ResultCallback<Achievements.UpdateAchievementResult>() { // from class: com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.GooglePlayAchievements.1
            @Override // com.google.android.gms.common.api.ResultCallback
            public void onResult(Achievements.UpdateAchievementResult updateAchievementResult) {
                int statusCode = updateAchievementResult.getStatus().getStatusCode();
                if (statusCode == 0) {
                    if (GooglePlayAchievements.this.listener != null) {
                        GooglePlayAchievements.this.listener.onReportProgress(str, GooglePlayAchievements.this.getAchievementData(updateAchievementResult.getAchievementId()), null);
                    }
                } else {
                    Debug.log(CommonDefines.GAME_SERVICES_TAG, "Report Progress successful : " + GamesStatusCodes.getStatusString(statusCode));
                    if (GooglePlayAchievements.this.listener != null) {
                        GooglePlayAchievements.this.listener.onReportProgress(str, GooglePlayAchievements.this.getAchievementData(str2), GamesStatusCodes.getStatusString(statusCode));
                    }
                }
                GooglePlayAchievements.this.loadAchievementsDetails(false, false);
            }
        });
    }

    public void loadAchievementsDetails(boolean z) {
        loadAchievementsDetails(z, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadAchievementsDetails(final boolean z, final boolean z2) {
        Games.Achievements.load(this.apiService, z).setResultCallback(new ResultCallback<Achievements.LoadAchievementsResult>() { // from class: com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.GooglePlayAchievements.2
            @Override // com.google.android.gms.common.api.ResultCallback
            public void onResult(Achievements.LoadAchievementsResult loadAchievementsResult) {
                String statusString;
                int statusCode = loadAchievementsResult.getStatus().getStatusCode();
                ArrayList<AchievementData> arrayList = null;
                if (statusCode == 0 || statusCode == 3) {
                    ArrayList<AchievementData> arrayList2 = new ArrayList<>();
                    AchievementBuffer achievements = loadAchievementsResult.getAchievements();
                    for (int i = 0; i < achievements.getCount(); i++) {
                        Achievement achievement = achievements.get(i);
                        AchievementInfo achievementInfo = new AchievementInfo(achievement);
                        GooglePlayAchievements.this.achievementsData.put(achievement.getAchievementId().trim(), achievementInfo);
                        String str = GooglePlayAchievements.this.TAG;
                        StringBuilder sbAppend = new StringBuilder("Added ").append(achievement.getAchievementId()).append("to achievementsData : ").append(GooglePlayAchievements.this.achievementsData.size()).append("   ");
                        GooglePlayAchievements googlePlayAchievements = GooglePlayAchievements.this;
                        Debug.log(str, sbAppend.append(googlePlayAchievements.getJSONString(googlePlayAchievements.achievementsData)).toString());
                        if (z || achievementInfo.lastReportedDate != -1) {
                            arrayList2.add(DataConversionUtils.getAchievementData(achievementInfo));
                        }
                    }
                    achievements.release();
                    statusString = null;
                    arrayList = arrayList2;
                } else {
                    statusString = GamesStatusCodes.getStatusString(statusCode);
                    Debug.error(CommonDefines.GAME_SERVICES_TAG, "Error loading achievements info " + statusString);
                }
                if (z2) {
                    if (z) {
                        if (GooglePlayAchievements.this.listener != null) {
                            GooglePlayAchievements.this.listener.onLoadAchievementDetails(arrayList, statusString);
                        }
                    } else if (GooglePlayAchievements.this.listener != null) {
                        GooglePlayAchievements.this.listener.onLoadUserAchievements(arrayList, statusString);
                    }
                }
            }
        });
    }

    public void showUI(Context context) {
        Intent intent = new Intent(context, (Class<?>) GooglePlayGameUIActivity.class);
        intent.putExtra("type", Keys.GameServices.SHOW_ACHIEVEMENTS);
        context.startActivity(intent);
    }

    public AchievementData getAchievementData(String str) {
        AchievementInfo achievementInfoGetAchievement = GetAchievement(str);
        if (achievementInfoGetAchievement != null) {
            return DataConversionUtils.getAchievementData(achievementInfoGetAchievement);
        }
        return null;
    }

    AchievementInfo GetAchievement(String str) {
        String strTrim = str.trim();
        AchievementInfo achievementInfo = this.achievementsData.get(strTrim);
        if (achievementInfo == null) {
            Debug.error(this.TAG, "AchievementsData ID : " + strTrim + " Contains Key : " + this.achievementsData.containsKey(strTrim));
            Debug.error(this.TAG, "AchievementsData : " + getJSONString(this.achievementsData));
            Debug.error(this.TAG, "Achievements Data doesn't contain this achievement id. Did you load Achievement Descriptions ahead?  ID : " + strTrim);
        } else {
            Debug.log(this.TAG, "Id : " + strTrim + "   " + achievementInfo.toString());
        }
        return achievementInfo;
    }

    String getJSONString(HashMap map) {
        return JSONUtility.getJSONString(map);
    }
}
