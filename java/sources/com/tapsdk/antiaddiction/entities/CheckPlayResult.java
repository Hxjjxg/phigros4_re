package com.tapsdk.antiaddiction.entities;

import com.tapsdk.antiaddiction.constants.Constants;
import com.tds.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes.dex */
public class CheckPlayResult {

    @SerializedName("remain_time")
    public int remainTime;

    @SerializedName("title")
    public String title = "";

    @SerializedName(Constants.MsgExtraParams.DESCRIPTION)
    public String description = "";
}
