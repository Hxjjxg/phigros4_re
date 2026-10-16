package com.tapsdk.antiaddiction.entities.request;

import com.digits.sdk.android.DigitsClient;
import com.tds.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class IdentifyRequestParams implements Serializable {

    @SerializedName("id_card")
    public String idCard = "";

    @SerializedName(DigitsClient.EXTRA_USER_ID)
    public String token = "";

    @SerializedName("name")
    public String name = "";
}
