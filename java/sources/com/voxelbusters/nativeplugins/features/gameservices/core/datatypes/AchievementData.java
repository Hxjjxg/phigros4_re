package com.voxelbusters.nativeplugins.features.gameservices.core.datatypes;

import com.voxelbusters.nativeplugins.defines.Keys;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AchievementData {
    public String achievedDescription;
    public int currentSteps;
    public String identifier;
    public String imagePath;
    public boolean isCompleted;
    public long lastReportedDate;
    public String name;
    public String state;
    public int totalSteps;
    public String type;
    public String unAchievedDescription;

    public JSONObject getHashMap() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("identifier", this.identifier);
            jSONObject.put("title", this.name);
            jSONObject.put(Keys.GameServices.UNACHIEVED_DESCRIPTION, this.unAchievedDescription);
            jSONObject.put(Keys.GameServices.ACHIEVED_DESCRIPTION, this.achievedDescription);
            jSONObject.put("image-path", this.imagePath);
            jSONObject.put(Keys.GameServices.POINTS_SCORED, this.currentSteps);
            jSONObject.put(Keys.GameServices.MAXIMUM_POINTS, this.totalSteps);
            jSONObject.put(Keys.GameServices.LAST_REPORT_DATE, this.lastReportedDate);
            jSONObject.put(Keys.GameServices.IS_COMPLETED, this.isCompleted ? "true" : "false");
            jSONObject.put(Keys.GameServices.STATE, this.state);
            jSONObject.put("type", this.type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
