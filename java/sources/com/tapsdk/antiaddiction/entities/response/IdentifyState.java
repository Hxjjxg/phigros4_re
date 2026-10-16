package com.tapsdk.antiaddiction.entities.response;

import com.tds.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class IdentifyState implements Serializable {
    public static final String STATUS_FAILED = "failed";
    public static final String STATUS_PASS = "pass";
    public static final String STATUS_WAITING = "waiting";
    public int ageLimit;

    @SerializedName("anti_addiction_token")
    public String antiAddictionToken;

    @SerializedName("status")
    public String identifyState = STATUS_PASS;

    public IdentifyState(String str) {
        this.antiAddictionToken = str;
    }

    public String toString() {
        return "IdentifyState{identifyState=" + this.identifyState + ", antiAddictionToken='" + this.antiAddictionToken + "'}";
    }
}
