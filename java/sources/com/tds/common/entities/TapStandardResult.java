package com.tds.common.entities;

import androidx.core.app.NotificationCompat;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapStandardResult {
    public final String msg;

    public TapStandardResult(JSONObject jSONObject) {
        this.msg = jSONObject.optString(NotificationCompat.CATEGORY_MESSAGE, "");
    }
}
