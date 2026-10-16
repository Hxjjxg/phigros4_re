package com.voxelbusters.nativeplugins.features.gameservices.core.interfaces;

import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.AchievementData;
import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.Score;
import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.User;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IGameServicesPlayerListener {
    void onAchievementsUIClosed();

    void onLeaderboardsUIClosed();

    void onLoadAchievementDetails(ArrayList<AchievementData> arrayList, String str);

    void onLoadLocalUserDetails(User user);

    void onLoadLocalUserFriendsDetails(ArrayList<User> arrayList, String str);

    void onLoadProfilePicture(String str, String str2, String str3);

    void onLoadUserAchievements(ArrayList<AchievementData> arrayList, String str);

    void onLoadUserProfiles(String str, ArrayList<User> arrayList, String str2);

    void onLoadingScores(String str, Score score, ArrayList<Score> arrayList, String str2);

    void onReportProgress(String str, AchievementData achievementData, String str2);

    void onReportScore(String str, Score score, String str2);
}
