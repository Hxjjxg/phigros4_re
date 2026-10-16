package retrofit;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
abstract class CallbackRunnable<T> implements Runnable {
    private final Callback<T> callback;
    private final Executor callbackExecutor;
    private final ErrorHandler errorHandler;

    public abstract ResponseWrapper obtainResponse();

    CallbackRunnable(Callback<T> callback, Executor executor, ErrorHandler errorHandler) {
        this.callback = callback;
        this.callbackExecutor = executor;
        this.errorHandler = errorHandler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            final ResponseWrapper responseWrapperObtainResponse = obtainResponse();
            this.callbackExecutor.execute(new Runnable() { // from class: retrofit.CallbackRunnable.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public void run() {
                    CallbackRunnable.this.callback.success(responseWrapperObtainResponse.responseBody, responseWrapperObtainResponse.response);
                }
            });
        } catch (RetrofitError e) {
            e = e;
            Throwable thHandleError = this.errorHandler.handleError(e);
            if (thHandleError != e) {
                e = RetrofitError.unexpectedError(e.getUrl(), thHandleError);
            }
            this.callbackExecutor.execute(new Runnable() { // from class: retrofit.CallbackRunnable.2
                @Override // java.lang.Runnable
                public void run() {
                    CallbackRunnable.this.callback.failure(e);
                }
            });
        }
    }
}
