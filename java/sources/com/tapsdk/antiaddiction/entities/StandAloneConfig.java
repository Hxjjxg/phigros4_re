package com.tapsdk.antiaddiction.entities;

import com.tds.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes.dex */
public class StandAloneConfig {

    @SerializedName("stand_alone_mode")
    public int standAloneMode = 1;

    @SerializedName("enable_local_auth_identifier")
    public int useLocalAuthIdentifier = 1;
}
