package com.tapsdk.antiaddiction.entities.request;

import com.tds.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class IdentifyDataRequestParams implements Serializable {

    @SerializedName("id_card")
    public String idCard;

    @SerializedName("name")
    public String name;

    public String toString() {
        return "IdentifyManuallyRequestParams{data='" + this.name + '\'' + this.idCard + '}';
    }
}
