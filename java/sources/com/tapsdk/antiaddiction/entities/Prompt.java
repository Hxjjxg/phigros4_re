package com.tapsdk.antiaddiction.entities;

import com.tapsdk.antiaddiction.constants.Constants;
import com.tds.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes.dex */
public class Prompt {

    @SerializedName(Constants.MsgExtraParams.DESCRIPTION)
    public String description;

    @SerializedName("negative_button")
    public String negativeButton;

    @SerializedName("positive_button")
    public String positiveButton;

    @SerializedName("title")
    public String title;
}
