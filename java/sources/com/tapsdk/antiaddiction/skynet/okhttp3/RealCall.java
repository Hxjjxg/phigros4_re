package com.tapsdk.antiaddiction.skynet.okhttp3;

import androidx.core.app.NotificationCompat;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.NamedRunnable;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.Util;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.cache.CacheInterceptor;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.connection.ConnectInterceptor;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.connection.StreamAllocation;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.http.BridgeInterceptor;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.http.CallServerInterceptor;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.http.RealInterceptorChain;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.http.RetryAndFollowUpInterceptor;
import com.tapsdk.antiaddiction.skynet.okhttp3.internal.platform.Platform;
import com.tapsdk.antiaddiction.skynet.okio.AsyncTimeout;
import com.tapsdk.antiaddiction.skynet.okio.Timeout;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
final class RealCall implements Call {
    final OkHttpClient client;
    private EventListener eventListener;
    private boolean executed;
    final boolean forWebSocket;
    final Request originalRequest;
    final RetryAndFollowUpInterceptor retryAndFollowUpInterceptor;
    final AsyncTimeout timeout;

    private RealCall(OkHttpClient okHttpClient, Request request, boolean z) {
        this.client = okHttpClient;
        this.originalRequest = request;
        this.forWebSocket = z;
        this.retryAndFollowUpInterceptor = new RetryAndFollowUpInterceptor(okHttpClient, z);
        AsyncTimeout asyncTimeout = new AsyncTimeout() { // from class: com.tapsdk.antiaddiction.skynet.okhttp3.RealCall.1
            @Override // com.tapsdk.antiaddiction.skynet.okio.AsyncTimeout
            protected void timedOut() {
                RealCall.this.cancel();
            }
        };
        this.timeout = asyncTimeout;
        asyncTimeout.timeout(okHttpClient.callTimeoutMillis(), TimeUnit.MILLISECONDS);
    }

    static RealCall newRealCall(OkHttpClient okHttpClient, Request request, boolean z) {
        RealCall realCall = new RealCall(okHttpClient, request, z);
        realCall.eventListener = okHttpClient.eventListenerFactory().create(realCall);
        return realCall;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Call
    public Request request() {
        return this.originalRequest;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Call
    public Response execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        captureCallStackTrace();
        this.timeout.enter();
        this.eventListener.callStart(this);
        try {
            try {
                this.client.dispatcher().executed(this);
                Response responseWithInterceptorChain = getResponseWithInterceptorChain();
                if (responseWithInterceptorChain == null) {
                    throw new IOException("Canceled");
                }
                this.client.dispatcher().finished(this);
                return responseWithInterceptorChain;
            } catch (IOException e) {
                IOException iOExceptionTimeoutExit = timeoutExit(e);
                this.eventListener.callFailed(this, iOExceptionTimeoutExit);
                throw iOExceptionTimeoutExit;
            }
        } catch (Throwable th) {
            this.client.dispatcher().finished(this);
            throw th;
        }
        this.client.dispatcher().finished(this);
        throw th;
    }

    IOException timeoutExit(IOException iOException) {
        if (!this.timeout.exit()) {
            return iOException;
        }
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    private void captureCallStackTrace() {
        this.retryAndFollowUpInterceptor.setCallStackTrace(Platform.get().getStackTraceForCloseable("response.body().close()"));
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Call
    public void enqueue(Callback callback) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        captureCallStackTrace();
        this.eventListener.callStart(this);
        this.client.dispatcher().enqueue(new AsyncCall(callback));
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Call
    public void cancel() {
        this.retryAndFollowUpInterceptor.cancel();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Call
    public Timeout timeout() {
        return this.timeout;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Call
    public synchronized boolean isExecuted() {
        return this.executed;
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Call
    public boolean isCanceled() {
        return this.retryAndFollowUpInterceptor.isCanceled();
    }

    @Override // com.tapsdk.antiaddiction.skynet.okhttp3.Call
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public RealCall m8clone() {
        return newRealCall(this.client, this.originalRequest, this.forWebSocket);
    }

    StreamAllocation streamAllocation() {
        return this.retryAndFollowUpInterceptor.streamAllocation();
    }

    final class AsyncCall extends NamedRunnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final Callback responseCallback;

        AsyncCall(Callback callback) {
            super("OkHttp %s", RealCall.this.redactedUrl());
            this.responseCallback = callback;
        }

        String host() {
            return RealCall.this.originalRequest.url().host();
        }

        Request request() {
            return RealCall.this.originalRequest;
        }

        RealCall get() {
            return RealCall.this;
        }

        void executeOn(ExecutorService executorService) {
            try {
                try {
                    executorService.execute(this);
                } catch (RejectedExecutionException e) {
                    InterruptedIOException interruptedIOException = new InterruptedIOException("executor rejected");
                    interruptedIOException.initCause(e);
                    RealCall.this.eventListener.callFailed(RealCall.this, interruptedIOException);
                    this.responseCallback.onFailure(RealCall.this, interruptedIOException);
                    RealCall.this.client.dispatcher().finished(this);
                }
            } catch (Throwable th) {
                RealCall.this.client.dispatcher().finished(this);
                throw th;
            }
        }

        @Override // com.tapsdk.antiaddiction.skynet.okhttp3.internal.NamedRunnable
        protected void execute() {
            RealCall.this.timeout.enter();
            boolean z = false;
            try {
                try {
                    try {
                        this.responseCallback.onResponse(RealCall.this, RealCall.this.getResponseWithInterceptorChain());
                    } catch (IOException e) {
                        e = e;
                        z = true;
                        IOException iOExceptionTimeoutExit = RealCall.this.timeoutExit(e);
                        if (!z) {
                            RealCall.this.eventListener.callFailed(RealCall.this, iOExceptionTimeoutExit);
                            this.responseCallback.onFailure(RealCall.this, iOExceptionTimeoutExit);
                        } else {
                            Platform.get().log(4, "Callback failure for " + RealCall.this.toLoggableString(), iOExceptionTimeoutExit);
                        }
                    } catch (Throwable th) {
                        th = th;
                        z = true;
                        RealCall.this.cancel();
                        if (!z) {
                            this.responseCallback.onFailure(RealCall.this, new IOException("canceled due to " + th));
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    RealCall.this.client.dispatcher().finished(this);
                    throw th2;
                }
            } catch (IOException e2) {
                e = e2;
            } catch (Throwable th3) {
                th = th3;
            }
            RealCall.this.client.dispatcher().finished(this);
        }
    }

    String toLoggableString() {
        return (isCanceled() ? "canceled " : "") + (this.forWebSocket ? "web socket" : NotificationCompat.CATEGORY_CALL) + " to " + redactedUrl();
    }

    String redactedUrl() {
        return this.originalRequest.url().redact();
    }

    Response getResponseWithInterceptorChain() throws IOException {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.client.interceptors());
        arrayList.add(this.retryAndFollowUpInterceptor);
        arrayList.add(new BridgeInterceptor(this.client.cookieJar()));
        arrayList.add(new CacheInterceptor(this.client.internalCache()));
        arrayList.add(new ConnectInterceptor(this.client));
        if (!this.forWebSocket) {
            arrayList.addAll(this.client.networkInterceptors());
        }
        arrayList.add(new CallServerInterceptor(this.forWebSocket));
        Response responseProceed = new RealInterceptorChain(arrayList, null, null, null, 0, this.originalRequest, this, this.eventListener, this.client.connectTimeoutMillis(), this.client.readTimeoutMillis(), this.client.writeTimeoutMillis()).proceed(this.originalRequest);
        if (!this.retryAndFollowUpInterceptor.isCanceled()) {
            return responseProceed;
        }
        Util.closeQuietly(responseProceed);
        throw new IOException("Canceled");
    }
}
