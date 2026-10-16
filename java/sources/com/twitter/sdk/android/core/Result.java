package com.twitter.sdk.android.core;

import retrofit.client.Response;

/* JADX INFO: loaded from: classes.dex */
public class Result<T> {
    public final T data;
    public final Response response;

    public Result(T t, Response response) {
        this.data = t;
        this.response = response;
    }
}
