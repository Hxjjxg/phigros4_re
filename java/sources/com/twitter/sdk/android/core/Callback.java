package com.twitter.sdk.android.core;

import retrofit.RetrofitError;
import retrofit.client.Response;

/* JADX INFO: loaded from: classes.dex */
public abstract class Callback<T> implements retrofit.Callback<T> {
    public abstract void failure(TwitterException twitterException);

    public abstract void success(Result<T> result);

    @Override // retrofit.Callback
    public final void success(T t, Response response) {
        success(new Result<>(t, response));
    }

    @Override // retrofit.Callback
    public final void failure(RetrofitError retrofitError) {
        failure(TwitterApiException.convert(retrofitError));
    }
}
