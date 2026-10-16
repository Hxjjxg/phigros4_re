package com.tapsdk.antiaddiction.entities.response;

import com.tapsdk.antiaddiction.constants.Constants;
import com.tapsdk.antiaddiction.settings.AntiAddictionSettings;
import com.tds.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class UserAntiConfig implements Serializable {

    @SerializedName("content_rating_check")
    public AgeCheckResult ageCheckResult;

    @SerializedName("local")
    public Local localConfig;

    @SerializedName("anti_addiction")
    public Policy policy;

    @SerializedName("real_name")
    public UserState userState;

    public static class AgeCheckResult {

        @SerializedName("allow")
        public boolean isAllow;
    }

    public static class Local {

        @SerializedName(AntiAddictionSettings.TIME_RANGE_MODE)
        public TimeRangeConfig timeRangeConfig;
    }

    public static class Policy {

        @SerializedName("policy_active")
        public String active;

        @SerializedName("policy_heartbeat_interval")
        public int heartbeatInterval;

        @SerializedName("policy_model")
        public String model;
    }

    public static class TimeRangeConfig {

        @SerializedName("holidays")
        public List<String> holidays;

        @SerializedName("time_end")
        public String timeEnd;

        @SerializedName("time_start")
        public String timeStart;

        @SerializedName("text")
        public UITipText uiTipText;
    }

    public static class TipDesc {

        @SerializedName(Constants.MsgExtraParams.DESCRIPTION)
        public String tipDescription;

        @SerializedName("title")
        public String tipTitle;
    }

    public static class UITipText {

        @SerializedName("allow")
        public TipDesc allow;

        @SerializedName("reject")
        public TipDesc reject;
    }

    public static class UserState {

        @SerializedName(Constants.CacheData.AGE_LIMIT)
        public int ageLimit;

        @SerializedName("is_adult")
        public boolean isAdult;
    }
}
