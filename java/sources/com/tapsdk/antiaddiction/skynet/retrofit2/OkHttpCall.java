package com.tapsdk.antiaddiction.skynet.retrofit2;

import com.tapsdk.antiaddiction.skynet.okhttp3.MediaType;
import com.tapsdk.antiaddiction.skynet.okhttp3.Request;
import com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody;
import com.tapsdk.antiaddiction.skynet.okio.Buffer;
import com.tapsdk.antiaddiction.skynet.okio.BufferedSource;
import com.tapsdk.antiaddiction.skynet.okio.ForwardingSource;
import com.tapsdk.antiaddiction.skynet.okio.Okio;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class OkHttpCall<T> implements Call<T> {
    private final Object[] args;
    private volatile boolean canceled;
    private Throwable creationFailure;
    private boolean executed;
    private com.tapsdk.antiaddiction.skynet.okhttp3.Call rawCall;
    private final ServiceMethod<T, ?> serviceMethod;

    OkHttpCall(ServiceMethod<T, ?> serviceMethod, Object[] objArr) {
        this.serviceMethod = serviceMethod;
        this.args = objArr;
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Call
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public OkHttpCall<T> m11clone() {
        return new OkHttpCall<>(this.serviceMethod, this.args);
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Call
    public synchronized Request request() {
        com.tapsdk.antiaddiction.skynet.okhttp3.Call call = this.rawCall;
        if (call != null) {
            return call.request();
        }
        Throwable th = this.creationFailure;
        if (th != null) {
            if (th instanceof IOException) {
                throw new RuntimeException("Unable to create request.", this.creationFailure);
            }
            if (th instanceof RuntimeException) {
                throw ((RuntimeException) th);
            }
            throw ((Error) th);
        }
        try {
            com.tapsdk.antiaddiction.skynet.okhttp3.Call callCreateRawCall = createRawCall();
            this.rawCall = callCreateRawCall;
            return callCreateRawCall.request();
        } catch (IOException e) {
            this.creationFailure = e;
            throw new RuntimeException("Unable to create request.", e);
        } catch (Error e2) {
            e = e2;
            Utils.throwIfFatal(e);
            this.creationFailure = e;
            throw e;
        } catch (RuntimeException e3) {
            e = e3;
            Utils.throwIfFatal(e);
            this.creationFailure = e;
            throw e;
        }
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Call
    public void enqueue(final Callback<T> callback) {
        com.tapsdk.antiaddiction.skynet.okhttp3.Call call;
        Throwable th;
        Utils.checkNotNull(callback, "callback == null");
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already executed.");
            }
            this.executed = true;
            call = this.rawCall;
            th = this.creationFailure;
            if (call == null && th == null) {
                try {
                    com.tapsdk.antiaddiction.skynet.okhttp3.Call callCreateRawCall = createRawCall();
                    this.rawCall = callCreateRawCall;
                    call = callCreateRawCall;
                } catch (Throwable th2) {
                    th = th2;
                    Utils.throwIfFatal(th);
                    this.creationFailure = th;
                }
            }
        }
        if (th != null) {
            callback.onFailure(this, th);
            return;
        }
        if (this.canceled) {
            call.cancel();
        }
        call.enqueue(new com.tapsdk.antiaddiction.skynet.okhttp3.Callback() { // from class: com.tapsdk.antiaddiction.skynet.retrofit2.OkHttpCall.1
            @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Callback
            public void onResponse(com.tapsdk.antiaddiction.skynet.okhttp3.Call call2, com.tapsdk.antiaddiction.skynet.okhttp3.Response response) {
                try {
                    try {
                        callback.onResponse(OkHttpCall.this, OkHttpCall.this.parseResponse(response));
                    } catch (Throwable th3) {
                        th3.printStackTrace();
                    }
                } catch (Throwable th4) {
                    callFailure(th4);
                }
            }

            @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Callback
            public void onFailure(com.tapsdk.antiaddiction.skynet.okhttp3.Call call2, IOException iOException) {
                callFailure(iOException);
            }

            private void callFailure(Throwable th3) {
                try {
                    callback.onFailure(OkHttpCall.this, th3);
                } catch (Throwable th4) {
                    th4.printStackTrace();
                }
            }
        });
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Call
    public synchronized boolean isExecuted() {
        return this.executed;
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Call
    public Response<T> execute() throws IOException {
        com.tapsdk.antiaddiction.skynet.okhttp3.Call callCreateRawCall;
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already executed.");
            }
            this.executed = true;
            Throwable th = this.creationFailure;
            if (th != null) {
                if (th instanceof IOException) {
                    throw ((IOException) th);
                }
                if (th instanceof RuntimeException) {
                    throw ((RuntimeException) th);
                }
                throw ((Error) th);
            }
            callCreateRawCall = this.rawCall;
            if (callCreateRawCall == null) {
                try {
                    callCreateRawCall = createRawCall();
                    this.rawCall = callCreateRawCall;
                } catch (IOException | Error | RuntimeException e) {
                    Utils.throwIfFatal(e);
                    this.creationFailure = e;
                    throw e;
                }
            }
        }
        if (this.canceled) {
            callCreateRawCall.cancel();
        }
        return parseResponse(callCreateRawCall.execute());
    }

    private com.tapsdk.antiaddiction.skynet.okhttp3.Call createRawCall() throws IOException {
        com.tapsdk.antiaddiction.skynet.okhttp3.Call call = this.serviceMethod.toCall(this.args);
        if (call != null) {
            return call;
        }
        throw new NullPointerException("Call.Factory returned null.");
    }

    Response<T> parseResponse(com.tapsdk.antiaddiction.skynet.okhttp3.Response response) throws IOException {
        ResponseBody responseBodyBody = response.body();
        com.tapsdk.antiaddiction.skynet.okhttp3.Response responseBuild = response.newBuilder().body(new NoContentResponseBody(responseBodyBody.contentType(), responseBodyBody.contentLength())).build();
        ExceptionCatchingRequestBody exceptionCatchingRequestBody = new ExceptionCatchingRequestBody(responseBodyBody);
        try {
            T response2 = this.serviceMethod.toResponse(exceptionCatchingRequestBody, responseBuild.code());
            if (responseBuild.code() >= 200 && responseBuild.code() < 300) {
                return Response.success(response2, responseBuild);
            }
            return Response.success(response2, responseBuild);
        } catch (RuntimeException e) {
            exceptionCatchingRequestBody.throwIfCaught();
            throw e;
        }
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Call
    public void cancel() {
        com.tapsdk.antiaddiction.skynet.okhttp3.Call call;
        this.canceled = true;
        synchronized (this) {
            call = this.rawCall;
        }
        if (call != null) {
            call.cancel();
        }
    }

    @Override // com.tapsdk.antiaddiction.skynet.retrofit2.Call
    public boolean isCanceled() {
        boolean z = true;
        if (this.canceled) {
            return true;
        }
        synchronized (this) {
            com.tapsdk.antiaddiction.skynet.okhttp3.Call call = this.rawCall;
            if (call == null || !call.isCanceled()) {
                z = false;
            }
        }
        return z;
    }

    static final class NoContentResponseBody extends ResponseBody {
        private final long contentLength;
        private final MediaType contentType;

        NoContentResponseBody(MediaType mediaType, long j) {
            this.contentType = mediaType;
            this.contentLength = j;
        }

        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody
        public MediaType contentType() {
            return this.contentType;
        }

        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody
        public long contentLength() {
            return this.contentLength;
        }

        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody
        public BufferedSource source() {
            throw new IllegalStateException("Cannot read raw response body of a converted body.");
        }
    }

    static final class ExceptionCatchingRequestBody extends ResponseBody {
        private final ResponseBody delegate;
        IOException thrownException;

        ExceptionCatchingRequestBody(ResponseBody responseBody) {
            this.delegate = responseBody;
        }

        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody
        public MediaType contentType() {
            return this.delegate.contentType();
        }

        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody
        public long contentLength() {
            return this.delegate.contentLength();
        }

        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody
        public BufferedSource source() {
            return Okio.buffer(new ForwardingSource(this.delegate.source()) { // from class: com.tapsdk.antiaddiction.skynet.retrofit2.OkHttpCall.ExceptionCatchingRequestBody.1
                @Override // com.tapsdk.antiaddiction.skynet.okio.ForwardingSource, com.tapsdk.antiaddiction.skynet.okio.Source
                public long read(Buffer buffer, long j) throws IOException {
                    try {
                        return super.read(buffer, j);
                    } catch (IOException e) {
                        ExceptionCatchingRequestBody.this.thrownException = e;
                        throw e;
                    }
                }
            });
        }

        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.ResponseBody, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.delegate.close();
        }

        void throwIfCaught() throws IOException {
            IOException iOException = this.thrownException;
            if (iOException != null) {
                throw iOException;
            }
        }
    }
}
