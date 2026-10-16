package com.tapsdk.antiaddiction.skynet.retrofit2;

import com.tapsdk.antiaddiction.skynet.exceptions.AntiServerException;
import com.tapsdk.antiaddiction.skynet.okhttp3.Headers;
import com.tapsdk.antiaddiction.skynet.okhttp3.Protocol;
import com.tapsdk.antiaddiction.skynet.okhttp3.Request;
import com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody;
import tds.androidx.recyclerview.widget.ItemTouchHelper;

/* JADX INFO: loaded from: classes.dex */
public final class Response<T> {
    private final T body;
    private final ResponseBody errorBody;
    private final com.tapsdk.antiaddiction.skynet.okhttp3.Response rawResponse;

    public static <T> Response<T> success(T t) {
        return success(t, new com.tapsdk.antiaddiction.skynet.okhttp3.Response.Builder().code(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION).message("OK").protocol(Protocol.HTTP_1_1).request(new Request.Builder().url("http://localhost/").build()).build());
    }

    public static <T> Response<T> success(T t, Headers headers) {
        Utils.checkNotNull(headers, "headers == null");
        return success(t, new com.tapsdk.antiaddiction.skynet.okhttp3.Response.Builder().code(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION).message("OK").protocol(Protocol.HTTP_1_1).headers(headers).request(new Request.Builder().url("http://localhost/").build()).build());
    }

    public static <T> Response<T> success(T t, com.tapsdk.antiaddiction.skynet.okhttp3.Response response) {
        Utils.checkNotNull(response, "rawResponse == null");
        if (!response.isSuccessful()) {
            throw new AntiServerException("rawResponse must be successful response", response.code());
        }
        return new Response<>(response, t, null);
    }

    public static <T> Response<T> error(int i, ResponseBody responseBody) {
        if (i < 400) {
            throw new IllegalArgumentException("code < 400: " + i);
        }
        return error(responseBody, new com.tapsdk.antiaddiction.skynet.okhttp3.Response.Builder().code(i).message("Response.error()").protocol(Protocol.HTTP_1_1).request(new Request.Builder().url("http://localhost/").build()).build());
    }

    public static <T> Response<T> error(ResponseBody responseBody, com.tapsdk.antiaddiction.skynet.okhttp3.Response response) {
        Utils.checkNotNull(responseBody, "body == null");
        Utils.checkNotNull(response, "rawResponse == null");
        if (response.isSuccessful()) {
            throw new IllegalArgumentException("rawResponse should not be successful response");
        }
        return new Response<>(response, null, responseBody);
    }

    private Response(com.tapsdk.antiaddiction.skynet.okhttp3.Response response, T t, ResponseBody responseBody) {
        this.rawResponse = response;
        this.body = t;
        this.errorBody = responseBody;
    }

    public com.tapsdk.antiaddiction.skynet.okhttp3.Response raw() {
        return this.rawResponse;
    }

    public int code() {
        return this.rawResponse.code();
    }

    public String message() {
        return this.rawResponse.message();
    }

    public Headers headers() {
        return this.rawResponse.headers();
    }

    public boolean isSuccessful() {
        return this.rawResponse.isSuccessful();
    }

    public T body() {
        return this.body;
    }

    public ResponseBody errorBody() {
        return this.errorBody;
    }

    public String toString() {
        return this.rawResponse.toString();
    }
}
