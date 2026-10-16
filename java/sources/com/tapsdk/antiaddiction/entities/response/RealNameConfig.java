package com.tapsdk.antiaddiction.entities.response;

import com.tapsdk.antiaddiction.entities.Prompt;
import com.tds.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes.dex */
public class RealNameConfig {

    @SerializedName("real_name_text")
    public RealNameText realNameText;

    @SerializedName("manual_auth_enable")
    public boolean useManual = true;

    public static class RealNameText {

        @SerializedName("auth_waiting")
        public Prompt authWaitingTip;

        @SerializedName("auth_failed")
        public Prompt manualAuthFailedTip;

        @SerializedName("manual_auth")
        public Prompt manualAuthTip;

        @SerializedName("taptap_auth")
        public Prompt tapAuthTip;
    }
}
