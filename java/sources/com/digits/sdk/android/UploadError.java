package com.digits.sdk.android;

import com.google.gson.annotations.SerializedName;
import com.tds.common.tracker.model.NetworkStateModel;

/* JADX INFO: loaded from: classes.dex */
class UploadError {

    @SerializedName(NetworkStateModel.PARAM_CODE)
    final int code;

    @SerializedName("item")
    final int item;

    @SerializedName("message")
    final String message;

    UploadError(int i, String str, int i2) {
        this.code = i;
        this.message = str;
        this.item = i2;
    }
}
