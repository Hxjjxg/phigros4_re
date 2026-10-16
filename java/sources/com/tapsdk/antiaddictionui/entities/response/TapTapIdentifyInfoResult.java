package com.tapsdk.antiaddictionui.entities.response;

import com.tds.common.tracker.model.NetworkStateModel;
import com.tds.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes.dex */
public class TapTapIdentifyInfoResult {

    @SerializedName(NetworkStateModel.PARAM_CODE)
    public String code;

    @SerializedName("idcard")
    public String idCard;

    @SerializedName("name")
    public String name;
}
