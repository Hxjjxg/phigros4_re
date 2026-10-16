package com.voxelbusters.nativeplugins.features.gameservices.core.interfaces;

import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.AchievementData;

/* JADX INFO: loaded from: classes.dex */
public interface IGameServices {
    void addListener(IGameServicesAuthListener iGameServicesAuthListener, IGameServicesPlayerListener iGameServicesPlayerListener);

    AchievementData getAchievementData(String str);

    boolean isAvailable();

    boolean isSignedIn();

    void loadAllAchievements();

    void loadLocalPlayerFriends(boolean z);

    void loadMoreScrores(String str, String str2, int i, int i2);

    void loadPlayerCenteredScores(String str, String str2, int i, int i2, int i3);

    void loadProfileImage(String str, String str2);

    void loadTopScores(String str, String str2, int i, int i2, int i3);

    void loadUserAchievements();

    void loadUsers(String str, String[] strArr);

    void register(boolean z);

    void removeListener(IGameServicesAuthListener iGameServicesAuthListener, IGameServicesPlayerListener iGameServicesPlayerListener);

    void reportProgress(String str, String str2, int i, boolean z);

    void reportScore(String str, String str2, long j, boolean z);

    void requestAuthToken(String str);

    void requestLocalPlayerDetails();

    void showAchievementsUi();

    void showLeaderboardsUi(String str, int i);

    void signIn();

    void signOut();
}
