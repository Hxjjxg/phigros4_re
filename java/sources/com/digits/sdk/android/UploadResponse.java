package com.digits.sdk.android;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.internal.TwitterApiConstants;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class UploadResponse {

    @SerializedName(TwitterApiConstants.Errors.ERRORS)
    final List<UploadError> errors;

    UploadResponse(List<UploadError> list) {
        this.errors = list;
    }
}
