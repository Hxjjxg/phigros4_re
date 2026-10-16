package com.digits.sdk.android;

import android.content.res.Resources;
import android.util.SparseIntArray;
import com.twitter.sdk.android.core.TwitterApiErrorConstants;

/* JADX INFO: loaded from: classes.dex */
class DigitsErrorCodes implements ErrorCodes {
    private static final int INITIAL_CAPACITY = 10;
    protected final SparseIntArray codeIdMap;
    private final Resources resources;

    DigitsErrorCodes(Resources resources) {
        SparseIntArray sparseIntArray = new SparseIntArray(10);
        this.codeIdMap = sparseIntArray;
        sparseIntArray.put(88, R.string.dgts__confirmation_error_alternative);
        sparseIntArray.put(TwitterApiErrorConstants.REGISTRATION_GENERAL_ERROR, R.string.dgts__network_error);
        sparseIntArray.put(TwitterApiErrorConstants.REGISTRATION_OPERATION_FAILED, R.string.dgts__network_error);
        sparseIntArray.put(TwitterApiErrorConstants.SPAMMER, R.string.dgts__network_error);
        sparseIntArray.put(87, R.string.dgts__network_error);
        this.resources = resources;
    }

    @Override // com.digits.sdk.android.ErrorCodes
    public String getMessage(int i) {
        int i2 = this.codeIdMap.get(i, -1);
        return i2 == -1 ? getDefaultMessage() : this.resources.getString(i2);
    }

    @Override // com.digits.sdk.android.ErrorCodes
    public String getDefaultMessage() {
        return this.resources.getString(R.string.dgts__try_again);
    }

    @Override // com.digits.sdk.android.ErrorCodes
    public String getNetworkError() {
        return this.resources.getString(R.string.dgts__network_error);
    }
}
