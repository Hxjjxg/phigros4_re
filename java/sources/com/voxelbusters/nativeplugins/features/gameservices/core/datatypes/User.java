package com.voxelbusters.nativeplugins.features.gameservices.core.datatypes;

import com.voxelbusters.nativeplugins.defines.Keys;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class User {
    public String alias;
    public String highResImageUrl;
    public String iconImageUrl;
    public String identifier;
    public String name;
    public long timeStamp;

    public JSONObject getHashMap() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("identifier", this.identifier);
            jSONObject.put("name", this.name);
            jSONObject.put(Keys.GameServices.USER_ALIAS, this.alias);
            jSONObject.put(Keys.GameServices.USER_HIGH_RES_IMAGE_URL, this.highResImageUrl);
            jSONObject.put(Keys.GameServices.USER_ICON_IMAGE_URL, this.iconImageUrl);
            jSONObject.put("timestamp", this.timeStamp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
