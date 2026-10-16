package com.tapsdk.antiaddiction.entities.response;

import com.tapsdk.antiaddiction.constants.Constants;
import com.tds.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CheckPayResult implements Serializable {

    @SerializedName(Constants.MsgExtraParams.DESCRIPTION)
    public String description;

    @SerializedName("allow")
    public boolean status;

    @SerializedName("title")
    public String title;
}
