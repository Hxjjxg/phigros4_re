package com.tapsdk.antiaddiction.entities;

import com.tds.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ChildProtectedConfig implements Serializable {

    @SerializedName("child_common_time")
    public int childCommonTime;

    @SerializedName("child_holiday_time")
    public int childHolidayTime;

    @SerializedName("guest_time")
    public int guestTime;

    @SerializedName("night_strict_end")
    public String nightStrictEnd;

    @SerializedName("night_strict_start")
    public String nightStrictStart;

    @SerializedName("night_strict_warn")
    public int nightStrictWarn;

    @SerializedName("no_identify_time")
    public int noIdentifyTime;

    @SerializedName("remain_time_warn")
    public int remainTimeWarn;
}
