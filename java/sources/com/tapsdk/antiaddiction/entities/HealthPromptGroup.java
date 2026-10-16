package com.tapsdk.antiaddiction.entities;

import com.tds.gson.annotations.SerializedName;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HealthPromptGroup {

    @SerializedName("account_type")
    public int accountType;

    @SerializedName("tips")
    public List<Prompt> promptList;
}
