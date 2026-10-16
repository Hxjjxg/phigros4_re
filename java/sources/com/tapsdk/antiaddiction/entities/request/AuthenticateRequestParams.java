package com.tapsdk.antiaddiction.entities.request;

import com.tds.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class AuthenticateRequestParams implements Serializable {
    public int carrier;

    @SerializedName("game")
    public String gameIdentifier;
    public String token;

    public AuthenticateRequestParams(String str, String str2, int i) {
        this.token = str;
        this.gameIdentifier = str2;
        this.carrier = i;
    }
}
