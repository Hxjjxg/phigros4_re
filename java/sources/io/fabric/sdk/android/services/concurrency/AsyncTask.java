package io.fabric.sdk.android.services.concurrency;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public abstract class AsyncTask<Params, Progress, Result> {
    private static final int CORE_POOL_SIZE;
    private static final int CPU_COUNT;
    private static final int KEEP_ALIVE = 1;
    private static final String LOG_TAG = "AsyncTask";
    private static final int MAXIMUM_POOL_SIZE;
    private static final int MESSAGE_POST_PROGRESS = 2;
    private static final int MESSAGE_POST_RESULT = 1;
    public static final Executor SERIAL_EXECUTOR;
    public static final Executor THREAD_POOL_EXECUTOR;
    private static volatile Executor defaultExecutor;
    private static final InternalHandler handler;
    private static final BlockingQueue<Runnable> poolWorkQueue;
    private static final ThreadFactory threadFactory;
    private final FutureTask<Result> future;
    private final WorkerRunnable<Params, Result> worker;
    private volatile Status status = Status.PENDING;
    private final AtomicBoolean cancelled = new AtomicBoolean();
    private final AtomicBoolean taskInvoked = new AtomicBoolean();

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    protected abstract Result doInBackground(Params... paramsArr);

    protected void onCancelled() {
    }

    protected void onPostExecute(Result result) {
    }

    protected void onPreExecute() {
    }

    protected void onProgressUpdate(Progress... progressArr) {
    }

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        CPU_COUNT = iAvailableProcessors;
        int i = iAvailableProcessors + 1;
        CORE_POOL_SIZE = i;
        int i2 = (iAvailableProcessors * 2) + 1;
        MAXIMUM_POOL_SIZE = i2;
        ThreadFactory threadFactory2 = new ThreadFactory() { // from class: io.fabric.sdk.android.services.concurrency.AsyncTask.1
            private final AtomicInteger count = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "AsyncTask #" + this.count.getAndIncrement());
            }
        };
        threadFactory = threadFactory2;
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(128);
        poolWorkQueue = linkedBlockingQueue;
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(i, i2, 1L, TimeUnit.SECONDS, linkedBlockingQueue, threadFactory2);
        SerialExecutor serialExecutor = new SerialExecutor();
        SERIAL_EXECUTOR = serialExecutor;
        handler = new InternalHandler();
        defaultExecutor = serialExecutor;
    }

    private static class SerialExecutor implements Executor {
        Runnable active;
        final LinkedList<Runnable> tasks;

        private SerialExecutor() {
            this.tasks = new LinkedList<>();
        }

        @Override // java.util.concurrent.Executor
        public synchronized void execute(final Runnable runnable) {
            this.tasks.offer(new Runnable() { // from class: io.fabric.sdk.android.services.concurrency.AsyncTask.SerialExecutor.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        SerialExecutor.this.scheduleNext();
                    }
                }
            });
            if (this.active == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            Runnable runnablePoll = this.tasks.poll();
            this.active = runnablePoll;
            if (runnablePoll != null) {
                AsyncTask.THREAD_POOL_EXECUTOR.execute(this.active);
            }
        }
    }

    public static void init() {
        handler.getLooper();
    }

    public static void setDefaultExecutor(Executor executor) {
        defaultExecutor = executor;
    }

    public AsyncTask() {
        WorkerRunnable<Params, Result> workerRunnable = new WorkerRunnable<Params, Result>() { // from class: io.fabric.sdk.android.services.concurrency.AsyncTask.2
            @Override // java.util.concurrent.Callable
            public Result call() throws Exception {
                AsyncTask.this.taskInvoked.set(true);
                Process.setThreadPriority(10);
                AsyncTask asyncTask = AsyncTask.this;
                return (Result) asyncTask.postResult(asyncTask.doInBackground(this.params));
            }
        };
        this.worker = workerRunnable;
        this.future = new FutureTask<Result>(workerRunnable) { // from class: io.fabric.sdk.android.services.concurrency.AsyncTask.3
            @Override // java.util.concurrent.FutureTask
            protected void done() {
                try {
                    AsyncTask.this.postResultIfNotInvoked(get());
                } catch (InterruptedException e) {
                    Log.w(AsyncTask.LOG_TAG, e);
                } catch (CancellationException unused) {
                    AsyncTask.this.postResultIfNotInvoked(null);
                } catch (ExecutionException e2) {
                    throw new RuntimeException("An error occured while executing doInBackground()", e2.getCause());
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postResultIfNotInvoked(Result result) {
        if (this.taskInvoked.get()) {
            return;
        }
        postResult(result);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Result postResult(Result result) {
        handler.obtainMessage(1, new AsyncTaskResult(this, result)).sendToTarget();
        return result;
    }

    public final Status getStatus() {
        return this.status;
    }

    protected void onCancelled(Result result) {
        onCancelled();
    }

    public final boolean isCancelled() {
        return this.cancelled.get();
    }

    public final boolean cancel(boolean z) {
        this.cancelled.set(true);
        return this.future.cancel(z);
    }

    public final Result get() throws ExecutionException, InterruptedException {
        return this.future.get();
    }

    public final Result get(long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        return this.future.get(j, timeUnit);
    }

    public final AsyncTask<Params, Progress, Result> execute(Params... paramsArr) {
        return executeOnExecutor(defaultExecutor, paramsArr);
    }

    /* JADX INFO: renamed from: io.fabric.sdk.android.services.concurrency.AsyncTask$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$io$fabric$sdk$android$services$concurrency$AsyncTask$Status;

        static {
            int[] iArr = new int[Status.values().length];
            $SwitchMap$io$fabric$sdk$android$services$concurrency$AsyncTask$Status = iArr;
            try {
                iArr[Status.RUNNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$fabric$sdk$android$services$concurrency$AsyncTask$Status[Status.FINISHED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public final AsyncTask<Params, Progress, Result> executeOnExecutor(Executor executor, Params... paramsArr) {
        if (this.status != Status.PENDING) {
            int i = AnonymousClass4.$SwitchMap$io$fabric$sdk$android$services$concurrency$AsyncTask$Status[this.status.ordinal()];
            if (i == 1) {
                throw new IllegalStateException("Cannot execute task: the task is already running.");
            }
            if (i == 2) {
                throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.status = Status.RUNNING;
        onPreExecute();
        this.worker.params = paramsArr;
        executor.execute(this.future);
        return this;
    }

    public static void execute(Runnable runnable) {
        defaultExecutor.execute(runnable);
    }

    protected final void publishProgress(Progress... progressArr) {
        if (isCancelled()) {
            return;
        }
        handler.obtainMessage(2, new AsyncTaskResult(this, progressArr)).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish(Result result) {
        if (isCancelled()) {
            onCancelled(result);
        } else {
            onPostExecute(result);
        }
        this.status = Status.FINISHED;
    }

    private static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            AsyncTaskResult asyncTaskResult = (AsyncTaskResult) message.obj;
            int i = message.what;
            if (i == 1) {
                asyncTaskResult.task.finish(asyncTaskResult.data[0]);
            } else {
                if (i != 2) {
                    return;
                }
                asyncTaskResult.task.onProgressUpdate(asyncTaskResult.data);
            }
        }
    }

    private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] params;

        private WorkerRunnable() {
        }
    }

    private static class AsyncTaskResult<Data> {
        final Data[] data;
        final AsyncTask task;

        AsyncTaskResult(AsyncTask asyncTask, Data... dataArr) {
            this.task = asyncTask;
            this.data = dataArr;
        }
    }
}
