package com.tapsdk.antiaddiction.entities.request;

import com.tds.common.tracker.model.NetworkStateModel;
import com.tds.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SessionRequestParam implements Serializable {

    @SerializedName(NetworkStateModel.PARAM_SESSION_ID)
    public String sessionId;
}
