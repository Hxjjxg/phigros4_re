package com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.util;

import android.net.Uri;
import com.google.android.gms.games.Player;
import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.AchievementData;
import com.voxelbusters.nativeplugins.features.gameservices.core.datatypes.User;
import com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google.AchievementInfo;

/* JADX INFO: loaded from: classes.dex */
public class DataConversionUtils {
    public static User getUser(Player player) {
        if (player == null) {
            return new User();
        }
        User user = new User();
        user.identifier = player.getPlayerId();
        user.name = player.getDisplayName();
        user.alias = player.getName();
        Uri hiResImageUri = player.getHiResImageUri();
        if (hiResImageUri != null) {
            user.highResImageUrl = hiResImageUri.toString();
        }
        Uri iconImageUri = player.getIconImageUri();
        if (iconImageUri != null) {
            user.iconImageUrl = iconImageUri.toString();
        }
        user.timeStamp = player.getRetrievedTimestamp();
        return user;
    }

    public static AchievementData getAchievementData(AchievementInfo achievementInfo) {
        AchievementData achievementData = new AchievementData();
        achievementData.identifier = achievementInfo.identifier;
        achievementData.name = achievementInfo.name;
        achievementData.unAchievedDescription = achievementInfo.description;
        achievementData.achievedDescription = "";
        achievementData.imagePath = achievementInfo.imagePath;
        achievementData.type = achievementInfo.getTypeForAchievement();
        achievementData.currentSteps = achievementInfo.currentSteps;
        achievementData.totalSteps = achievementInfo.totalSteps;
        achievementData.state = achievementInfo.getStateForAchievement();
        achievementData.lastReportedDate = achievementInfo.lastReportedDate;
        achievementData.isCompleted = achievementInfo.isCompleted;
        return achievementData;
    }
}
