package com.taptap.sdk.model;

import com.tds.common.entities.AccessToken;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CloudGameLoginResponse {
    public CloudGameLoginResData data;
    public String messageId;
    public String type;

    public static CloudGameLoginResponse parseFromJSONObject(JSONObject jSONObject) {
        CloudGameLoginResponse cloudGameLoginResponse = new CloudGameLoginResponse();
        try {
            cloudGameLoginResponse.type = jSONObject.optString("type");
            cloudGameLoginResponse.messageId = jSONObject.optString("message_id");
            cloudGameLoginResponse.data = CloudGameLoginResData.parseFromJSONObject(jSONObject.optJSONObject(AccessToken.ROOT_ELEMENT_NAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloudGameLoginResponse;
    }
}
