package com.voxelbusters.nativeplugins.features.gameservices.core.datatypes;

import com.voxelbusters.nativeplugins.defines.Keys;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Score {
    public long date;
    public String formattedValue;
    public String identifier;
    public long rank;
    public User user;
    public long value;

    public JSONObject getHashMap() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("identifier", this.identifier);
            User user = this.user;
            if (user != null) {
                jSONObject.put(Keys.GameServices.SCORE_USER, user.getHashMap());
            }
            jSONObject.put(Keys.GameServices.SCORE_VALUE, this.value);
            jSONObject.put(Keys.GameServices.SCORE_DATE, this.date);
            jSONObject.put(Keys.GameServices.SCORE_FORMATTED_VALUE, this.formattedValue);
            jSONObject.put(Keys.GameServices.SCORE_RANK, this.rank);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
