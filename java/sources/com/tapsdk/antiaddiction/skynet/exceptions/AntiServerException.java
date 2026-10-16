package com.tapsdk.antiaddiction.skynet.exceptions;

import android.text.TextUtils;
import com.tapsdk.antiaddiction.constants.Constants;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class AntiServerException extends RuntimeException implements Serializable {
    public int errorCode;
    public String errorDescription;
    public String errorMsg;
    public String errorType;
    public int statusCode;

    public AntiServerException(String str) {
        super(str, null);
        this.statusCode = -1;
        this.errorDescription = str;
    }

    public AntiServerException(String str, int i) {
        super(str, null);
        this.errorDescription = str;
        this.statusCode = i;
    }

    public AntiServerException(String str, int i, String str2, int i2, String str3) {
        this(str, i);
        this.errorType = str2;
        this.errorCode = i2;
        this.errorMsg = str3;
    }

    public boolean isTokenExpired() {
        return !TextUtils.isEmpty(this.errorType) && this.errorType.equals(Constants.ServerErrorType.BUSINESS_ERROR) && this.errorCode == 200000;
    }
}
