package com.tapsdk.antiaddiction.entities;

import com.tds.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class IdentificationInfo implements Serializable {

    @SerializedName("identify_state")
    public int authState;

    @SerializedName("id_card")
    public String idCard = "";
    public String name = "";
    public String phoneNumber = "";

    @SerializedName("anti_addiction_token")
    public String antiAddictionToken = "";

    public String toString() {
        return "IdentificationInfo{authState=" + this.authState + ", idCard='" + this.idCard + "', name='" + this.name + "', phoneNumber='" + this.phoneNumber + "', antiAddictionToken='" + this.antiAddictionToken + "'}";
    }
}
