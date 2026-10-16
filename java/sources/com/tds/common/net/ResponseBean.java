package com.tds.common.net;

import com.tds.common.entities.AccessToken;
import com.tds.common.net.json.JsonUtil;
import com.voxelbusters.nativeplugins.defines.Keys;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class ResponseBean<T> {
    public final T data;
    public final boolean success;

    ResponseBean(JSONObject jSONObject, Class<T> cls) throws JSONException {
        this.success = jSONObject.optBoolean(Keys.Billing.Validation.SUCCESS);
        if (jSONObject.has(AccessToken.ROOT_ELEMENT_NAME)) {
            this.data = (T) JsonUtil.parse(jSONObject.getJSONObject(AccessToken.ROOT_ELEMENT_NAME), cls);
        } else {
            this.data = null;
        }
    }
}
