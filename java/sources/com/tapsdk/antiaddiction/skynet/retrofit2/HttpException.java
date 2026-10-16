package com.tapsdk.antiaddiction.skynet.retrofit2;

/* JADX INFO: loaded from: classes.dex */
public class HttpException extends RuntimeException {
    private final int code;
    private final String message;
    public final transient com.tapsdk.antiaddiction.skynet.okhttp3.Response rawResponse;
    private final transient Response<?> response;

    private static String getMessage(Response<?> response) {
        Utils.checkNotNull(response, "response == null");
        return "HTTP " + response.code() + " " + response.message();
    }

    public HttpException(Response<?> response) {
        super(getMessage(response));
        this.code = response.code();
        this.message = response.message();
        this.response = response;
        this.rawResponse = null;
    }

    public HttpException(Response<?> response, com.tapsdk.antiaddiction.skynet.okhttp3.Response response2) {
        super(getMessage(response));
        this.code = response.code();
        this.message = response.message();
        this.response = response;
        this.rawResponse = response2;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public Response<?> response() {
        return this.response;
    }
}
