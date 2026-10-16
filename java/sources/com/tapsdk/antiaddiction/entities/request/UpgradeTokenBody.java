package com.tapsdk.antiaddiction.entities.request;

import com.tds.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpgradeTokenBody implements Serializable {

    @SerializedName("anti_addiction_token_v1")
    public String oldToken;
}
