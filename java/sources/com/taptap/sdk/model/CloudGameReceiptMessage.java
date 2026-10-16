package com.taptap.sdk.model;

import com.tds.common.entities.AccessToken;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CloudGameReceiptMessage {
    public CloudGameReceiptData data;
    public String messageId;
    public String type;

    public static CloudGameReceiptMessage parseFromJSONObject(JSONObject jSONObject) {
        CloudGameReceiptMessage cloudGameReceiptMessage = new CloudGameReceiptMessage();
        try {
            cloudGameReceiptMessage.type = jSONObject.optString("type");
            cloudGameReceiptMessage.messageId = jSONObject.optString("message_id");
            cloudGameReceiptMessage.data = CloudGameReceiptData.parseFromJSONObject(jSONObject.optJSONObject(AccessToken.ROOT_ELEMENT_NAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloudGameReceiptMessage;
    }
}
