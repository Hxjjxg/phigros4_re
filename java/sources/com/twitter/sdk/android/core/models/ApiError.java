package com.twitter.sdk.android.core.models;

import com.google.gson.annotations.SerializedName;
import com.tds.common.tracker.model.NetworkStateModel;

/* JADX INFO: loaded from: classes.dex */
public class ApiError {

    @SerializedName(NetworkStateModel.PARAM_CODE)
    private final int code;

    @SerializedName("message")
    private final String message;

    public ApiError(String str, int i) {
        this.message = str;
        this.code = i;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }
}
