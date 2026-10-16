package com.tapsdk.antiaddiction.entities.request;

import com.tds.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes.dex */
public class PayRequestParams {

    @SerializedName("amount")
    public final long amount;

    public PayRequestParams(long j) {
        this.amount = j;
    }
}
