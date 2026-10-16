package com.voxelbusters.nativeplugins.features.gameservices.serviceprovider.google;

import com.google.android.gms.games.achievement.Achievement;
import com.voxelbusters.nativeplugins.defines.Keys;
import com.voxelbusters.nativeplugins.utilities.StringUtility;

/* JADX INFO: loaded from: classes.dex */
public class AchievementInfo {
    public int currentSteps;
    public String description;
    public String identifier;
    public String imagePath;
    public boolean isCompleted;
    public long lastReportedDate;
    public String name;
    public int state;
    public int totalSteps;
    public int type;

    AchievementInfo(Achievement achievement) {
        this.identifier = achievement.getAchievementId();
        this.name = achievement.getName();
        this.description = achievement.getDescription();
        this.imagePath = getImagePathForAchievement(achievement);
        this.type = achievement.getType();
        int state = achievement.getState();
        this.state = state;
        if (this.type == 1) {
            this.currentSteps = achievement.getCurrentSteps();
            this.totalSteps = achievement.getTotalSteps();
        } else {
            this.currentSteps = state == 0 ? 1 : 0;
            this.totalSteps = 1;
        }
        this.lastReportedDate = achievement.getLastUpdatedTimestamp();
        this.isCompleted = achievement.getState() == 0;
    }

    void SetCurrentSteps(int i) {
        this.currentSteps = i;
    }

    public String getImagePathForAchievement(Achievement achievement) {
        String unlockedImageUrl = achievement.getUnlockedImageUrl();
        String revealedImageUrl = achievement.getRevealedImageUrl();
        if (StringUtility.isNullOrEmpty(unlockedImageUrl)) {
            return !StringUtility.isNullOrEmpty(revealedImageUrl) ? revealedImageUrl : "";
        }
        return unlockedImageUrl;
    }

    public String getStateForAchievement() {
        int i = this.state;
        if (i == 0) {
            return Keys.GameServices.STATE_UNLOCKED;
        }
        return i == 1 ? Keys.GameServices.STATE_REVEALED : Keys.GameServices.STATE_HIDDEN;
    }

    public String getTypeForAchievement() {
        int i = this.type;
        return (i != 0 && i == 1) ? Keys.GameServices.TYPE_INCREMENTAL : Keys.GameServices.TYPE_STANDARD;
    }

    public String toString() {
        return "identifier : " + this.identifier + "   name : " + this.name + "   description : " + this.description + "   imagePath : " + this.imagePath + "   type : " + this.type + "   currentSteps : " + this.currentSteps + "   totalSteps : " + this.totalSteps + "   state : " + this.state + "   lastReportedDate : " + this.lastReportedDate + "   isCompleted : " + this.isCompleted + "   ";
    }
}
