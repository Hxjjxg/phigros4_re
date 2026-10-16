package com.voxelbusters.nativeplugins.features.gameservices;

import android.content.Context;
import android.content.SharedPreferences;
import com.voxelbusters.nativeplugins.NativePluginHelper;
import com.voxelbusters.nativeplugins.defines.CommonDefines;
import com.voxelbusters.nativeplugins.defines.Keys;
import com.voxelbusters.nativeplugins.defines.UnityDefines;
import com.voxelbusters.nativeplugins.features.gameservices.core.BasicGameService;
import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.AchievementData;
import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.Score;
import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.User;
import com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServices;
import com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesAuthListener;
import com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener;
import com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.GooglePlayGameService;
import com.voxelbusters.nativeplugins.utilities.ApplicationUtility;
import com.voxelbusters.nativeplugins.utilities.Debug;
import com.voxelbusters.nativeplugins.utilities.StringUtility;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class GameServicesHandler implements IGameServicesPlayerListener, IGameServicesAuthListener {
    private static GameServicesHandler INSTANCE;
    private boolean autoSignInUser;
    HashMap<String, Integer> keyMap = new HashMap<>();
    private final IGameServices service;
    private boolean showDefaultErrorDialogs;

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener
    public void onLoadLocalUserDetails(User user) {
    }

    public static GameServicesHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameServicesHandler();
        }
        return INSTANCE;
    }

    private GameServicesHandler() {
        Context currentContext = NativePluginHelper.getCurrentContext();
        if (!ApplicationUtility.isAmazonPlatform(currentContext)) {
            this.service = GooglePlayGameService.getInstance(currentContext);
        } else {
            this.service = new BasicGameService(currentContext);
        }
        this.keyMap.put(Keys.GameServices.TIME_SCOPE_ALL_TIME, 2);
        this.keyMap.put(Keys.GameServices.TIME_SCOPE_WEEK, 1);
        this.keyMap.put(Keys.GameServices.TIME_SCOPE_TODAY, 0);
        this.keyMap.put(Keys.GameServices.USER_SCOPE_FRIENDS, 1);
        this.keyMap.put(Keys.GameServices.USER_SCOPE_GLOBAL, 0);
    }

    public void setShowDefaultErrorDialogs(boolean z) {
        this.showDefaultErrorDialogs = z;
    }

    public boolean isServiceAvailable() {
        boolean zIsAvailable = this.service.isAvailable();
        if (!zIsAvailable) {
            Debug.warning(CommonDefines.GAME_SERVICES_TAG, "Service not functional. Either not avaialble or update required.");
        }
        return zIsAvailable;
    }

    public void register(boolean z) {
        register(z, true);
    }

    public void register(boolean z, boolean z2) {
        this.service.addListener(this, this);
        this.service.register(z);
        if (z2 && getAutoSignInStatus()) {
            autoSignInUser();
        }
    }

    public boolean isSignedIn() {
        return this.service.isSignedIn();
    }

    public void loadAchievementDescriptions() {
        this.service.loadAllAchievements();
    }

    public void loadAchievements() {
        this.service.loadUserAchievements();
    }

    public String getAchievement(String str) {
        return this.service.getAchievementData(str).getHashMap().toString();
    }

    public void reportProgress(String str, String str2, int i, boolean z) {
        this.service.reportProgress(str, str2, i, z);
    }

    public void showAchievementsUi() {
        this.service.showAchievementsUi();
    }

    public void loadTopScores(final String str, final String str2, final String str3, final String str4, final int i) {
        new Thread() { // from class: com.voxelbusters.nativeplugins.features.gameservices.GameServicesHandler.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                GameServicesHandler.this.service.loadTopScores(str, str2, GameServicesHandler.this.keyMap.get(str3).intValue(), GameServicesHandler.this.keyMap.get(str4).intValue(), i);
            }
        }.start();
    }

    public void loadPlayerCenteredScores(final String str, final String str2, final String str3, final String str4, final int i) {
        new Thread() { // from class: com.voxelbusters.nativeplugins.features.gameservices.GameServicesHandler.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                GameServicesHandler.this.service.loadPlayerCenteredScores(str, str2, GameServicesHandler.this.keyMap.get(str3).intValue(), GameServicesHandler.this.keyMap.get(str4).intValue(), i);
            }
        }.start();
    }

    public void loadMoreScores(final String str, final String str2, final int i, final int i2) {
        new Thread() { // from class: com.voxelbusters.nativeplugins.features.gameservices.GameServicesHandler.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                GameServicesHandler.this.service.loadMoreScrores(str, str2, i, i2);
            }
        }.start();
    }

    public void reportScore(String str, String str2, long j, boolean z) {
        this.service.reportScore(str, str2, j, z);
    }

    public void showLeaderboardsUi(String str, String str2) {
        this.service.showLeaderboardsUi(str, this.keyMap.get(str2).intValue());
    }

    public void loadUsers(String str, String str2) {
        this.service.loadUsers(str, StringUtility.convertJsonStringToStringArray(str2));
    }

    public void loadLocalUserFriends(boolean z) {
        this.service.loadLocalPlayerFriends(z);
    }

    public void authenticateLocalUser() {
        this.autoSignInUser = false;
        this.service.signIn();
    }

    private void autoSignInUser() {
        this.autoSignInUser = true;
        this.service.signIn();
    }

    public void signOut() {
        this.service.signOut();
        SaveAutoSignInStatus(false);
    }

    public void loadProfilePicture(final String str, final String str2) {
        NativePluginHelper.executeOnUIThread(new Runnable() { // from class: com.voxelbusters.nativeplugins.features.gameservices.GameServicesHandler.4
            @Override // java.lang.Runnable
            public void run() {
                GameServicesHandler.this.service.loadProfileImage(str, str2);
            }
        });
    }

    public void loadExternalAuthenticationDetails(String str) {
        this.service.requestAuthToken(str);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesAuthListener
    public void onConnected(User user, String str) {
        HashMap map = new HashMap();
        if (str != null) {
            map.put("error", str);
        }
        map.put(Keys.GameServices.LOCAL_USER_INFO, user.getHashMap());
        NativePluginHelper.sendMessage(UnityDefines.GameServices.AUTHENTICATION_FINISHED, map);
        SaveAutoSignInStatus(true);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesAuthListener
    public void onDisConnected() {
        HashMap map = new HashMap();
        map.put("error", "Disconnected!");
        NativePluginHelper.sendMessage(UnityDefines.GameServices.AUTHENTICATION_FINISHED, map);
        SaveAutoSignInStatus(false);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesAuthListener
    public void onConnectionSuspended() {
        HashMap map = new HashMap();
        map.put("error", "Connection Suspended!");
        NativePluginHelper.sendMessage(UnityDefines.GameServices.AUTHENTICATION_FINISHED, map);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesAuthListener
    public void onConnectionFailure() {
        HashMap map = new HashMap();
        map.put("error", "Connection Failure while connecting!");
        NativePluginHelper.sendMessage(UnityDefines.GameServices.AUTHENTICATION_FINISHED, map);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener
    public void onLoadingScores(String str, Score score, ArrayList<Score> arrayList, String str2) {
        HashMap map = new HashMap();
        map.put(Keys.GameServices.INSTANCE_ID, str);
        if (str2 != null) {
            map.put("error", str2);
        } else {
            HashMap map2 = new HashMap();
            map2.put(Keys.GameServices.LEADERBOARD_LOCAL_SCORE, score == null ? null : score.getHashMap());
            map2.put(Keys.GameServices.LEADERBOARD_SCORES_LIST, arrayList != null ? getConvertedScoreList(arrayList) : null);
            map.put(Keys.GameServices.LEADERBOARD_INFO, map2);
        }
        NativePluginHelper.sendMessage(UnityDefines.GameServices.RECEIVED_SCORES, map);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener
    public void onLoadAchievementDetails(ArrayList<AchievementData> arrayList, String str) {
        HashMap map = new HashMap();
        if (!StringUtility.isNullOrEmpty(str)) {
            map.put("error", str);
        }
        map.put(Keys.GameServices.ACHIEVEMENTS_LIST, getConvertedAchievementsList(arrayList));
        NativePluginHelper.sendMessage(UnityDefines.GameServices.RECEIVED_ACHIEVEMENT_DESCRIPTIONS, map);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener
    public void onLoadUserAchievements(ArrayList<AchievementData> arrayList, String str) {
        HashMap map = new HashMap();
        if (!StringUtility.isNullOrEmpty(str)) {
            map.put("error", str);
        }
        map.put(Keys.GameServices.ACHIEVEMENTS_LIST, getConvertedAchievementsList(arrayList));
        NativePluginHelper.sendMessage(UnityDefines.GameServices.RECEIVED_ACHIEVEMENTS, map);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener
    public void onLoadLocalUserFriendsDetails(ArrayList<User> arrayList, String str) {
        HashMap map = new HashMap();
        if (str != null) {
            map.put("error", str);
        }
        map.put(Keys.GameServices.LOCAL_USER_FRIENDS, arrayList == null ? null : getConvertedUserList(arrayList));
        NativePluginHelper.sendMessage(UnityDefines.GameServices.RECEIVED_LOCAL_USER_FRIENDS_REQUEST, map);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener
    public void onLoadUserProfiles(String str, ArrayList<User> arrayList, String str2) {
        HashMap map = new HashMap();
        if (str2 != null) {
            map.put("error", str2);
        }
        map.put(Keys.GameServices.INSTANCE_ID, str);
        map.put(Keys.GameServices.USERS_LIST, arrayList == null ? null : getConvertedUserList(arrayList));
        NativePluginHelper.sendMessage(UnityDefines.GameServices.RECEIVED_USER_PROFILES_LIST, map);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener
    public void onLoadProfilePicture(String str, String str2, String str3) {
        HashMap map = new HashMap();
        if (str3 != null) {
            map.put("error", str3);
        }
        map.put(Keys.GameServices.INSTANCE_ID, str);
        map.put(Keys.GameServices.IMAGE_FILE_PATH, str2);
        NativePluginHelper.sendMessage(UnityDefines.GameServices.RECEIVED_PICTURE_LOAD_REQUEST, map);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener
    public void onReportProgress(String str, AchievementData achievementData, String str2) {
        HashMap map = new HashMap();
        map.put(Keys.GameServices.INSTANCE_ID, str);
        if (achievementData != null) {
            map.put(Keys.GameServices.ACHIEVEMENT_INFO, achievementData.getHashMap());
        }
        if (str2 != null) {
            map.put("error", str2);
        }
        NativePluginHelper.sendMessage(UnityDefines.GameServices.RECEIVED_REPORT_PROGRESS, map);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener
    public void onReportScore(String str, Score score, String str2) {
        HashMap map = new HashMap();
        map.put(Keys.GameServices.INSTANCE_ID, str);
        if (score != null) {
            map.put(Keys.GameServices.SCORE_INFO, score.getHashMap());
        }
        if (str2 != null) {
            map.put("error", str2);
        }
        NativePluginHelper.sendMessage(UnityDefines.GameServices.RECEIVED_REPORT_SCORE, map);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener
    public void onAchievementsUIClosed() {
        NativePluginHelper.sendMessage(UnityDefines.GameServices.RECEIVED_ACHIEVEMENTS_UI_CLOSED);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesPlayerListener
    public void onLeaderboardsUIClosed() {
        NativePluginHelper.sendMessage(UnityDefines.GameServices.RECEIVED_LEADERBOARDS_UI_CLOSED);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesAuthListener
    public void onSignOut(String str) {
        HashMap map = new HashMap();
        if (!StringUtility.isNullOrEmpty(str)) {
            map.put("error", str);
        }
        NativePluginHelper.sendMessage(UnityDefines.GameServices.RECEIVED_SIGN_OUT_STATUS, map);
    }

    @Override // com.voxelbusters.nativeplugins.features.gameservices.core.interfaces.IGameServicesAuthListener
    public void onReceivingExternalAuthenticationDetails(String str, String str2) {
        HashMap map = new HashMap();
        if (!StringUtility.isNullOrEmpty(str2)) {
            map.put("error", str2);
        }
        HashMap map2 = new HashMap();
        map2.put(Keys.GameServices.SERVER_AUTH_CODE, str);
        map.put(Keys.GameServices.EXTERNAL_CREDENTIALS_DATA, map2);
        NativePluginHelper.sendMessage(UnityDefines.GameServices.RECEIVED_EXTERNAL_AUTHENTICATION_DETAILS, map);
    }

    ArrayList<JSONObject> getConvertedUserList(ArrayList<User> arrayList) {
        ArrayList<JSONObject> arrayList2 = new ArrayList<>();
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList2.add(arrayList.get(i).getHashMap());
            }
        }
        return arrayList2;
    }

    ArrayList<JSONObject> getConvertedScoreList(ArrayList<Score> arrayList) {
        ArrayList<JSONObject> arrayList2 = new ArrayList<>();
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList2.add(arrayList.get(i).getHashMap());
            }
        }
        return arrayList2;
    }

    ArrayList<JSONObject> getConvertedAchievementsList(ArrayList<AchievementData> arrayList) {
        ArrayList<JSONObject> arrayList2 = new ArrayList<>();
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList2.add(arrayList.get(i).getHashMap());
            }
        }
        return arrayList2;
    }

    public boolean getShowDefaultErrorDialogs() {
        return this.showDefaultErrorDialogs;
    }

    public boolean isAutoLogin() {
        return this.autoSignInUser;
    }

    private boolean getAutoSignInStatus() {
        return NativePluginHelper.getCurrentContext().getSharedPreferences(Keys.GameServices.SAVED_KEYS_FILE, 0).getBoolean(Keys.GameServices.SAVED_AUTO_SIGN_IN_STATUS, false);
    }

    private void SaveAutoSignInStatus(boolean z) {
        SharedPreferences.Editor editorEdit = NativePluginHelper.getCurrentContext().getSharedPreferences(Keys.GameServices.SAVED_KEYS_FILE, 0).edit();
        editorEdit.putBoolean(Keys.GameServices.SAVED_AUTO_SIGN_IN_STATUS, z);
        editorEdit.commit();
    }
}
