package com.taptap.sdk.model;

import com.voxelbusters.nativeplugins.defines.Keys;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CloudGameReceiptData {
    public String messageId;
    public boolean success;

    public static CloudGameReceiptData parseFromJSONObject(JSONObject jSONObject) {
        CloudGameReceiptData cloudGameReceiptData = new CloudGameReceiptData();
        try {
            cloudGameReceiptData.success = jSONObject.optBoolean(Keys.Billing.Validation.SUCCESS);
            cloudGameReceiptData.messageId = jSONObject.optString("message_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloudGameReceiptData;
    }
}
