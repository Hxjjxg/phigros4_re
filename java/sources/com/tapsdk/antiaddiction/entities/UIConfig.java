package com.tapsdk.antiaddiction.entities;

import com.tds.gson.annotations.SerializedName;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class UIConfig {

    @SerializedName("auth_identify_words")
    public List<Prompt> authIdentifyPromptList;

    @SerializedName("health_reminder_words")
    public List<HealthPromptGroup> healthPromptGroups;
}
