package com.tapsdk.antiaddiction.entities.request;

import com.tds.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PlayLogTimesRequestParams {

    @SerializedName("server_times")
    public List<List<Long>> serverTimes = new ArrayList();

    @SerializedName("local_times")
    public List<List<Long>> localTimes = new ArrayList();
}
