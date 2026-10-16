package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Fabric;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public final class ExecutorUtils {
    private static final long DEFAULT_TERMINATION_TIMEOUT = 2;

    private ExecutorUtils() {
    }

    public static ExecutorService buildSingleThreadExecutorService(String str) {
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor(getNamedThreadFactory(str));
        addDelayedShutdownHook(str, executorServiceNewSingleThreadExecutor);
        return executorServiceNewSingleThreadExecutor;
    }

    public static ScheduledExecutorService buildSingleThreadScheduledExecutorService(String str) {
        ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(getNamedThreadFactory(str));
        addDelayedShutdownHook(str, scheduledExecutorServiceNewSingleThreadScheduledExecutor);
        return scheduledExecutorServiceNewSingleThreadScheduledExecutor;
    }

    public static final ThreadFactory getNamedThreadFactory(final String str) {
        final AtomicLong atomicLong = new AtomicLong(1L);
        return new ThreadFactory() { // from class: io.fabric.sdk.android.services.common.ExecutorUtils.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(final Runnable runnable) {
                Thread threadNewThread = Executors.defaultThreadFactory().newThread(new BackgroundPriorityRunnable() { // from class: io.fabric.sdk.android.services.common.ExecutorUtils.1.1
                    @Override // io.fabric.sdk.android.services.common.BackgroundPriorityRunnable
                    public void onRun() {
                        runnable.run();
                    }
                });
                threadNewThread.setName(str + atomicLong.getAndIncrement());
                return threadNewThread;
            }
        };
    }

    private static final void addDelayedShutdownHook(String str, ExecutorService executorService) {
        addDelayedShutdownHook(str, executorService, 2L, TimeUnit.SECONDS);
    }

    public static final void addDelayedShutdownHook(final String str, final ExecutorService executorService, final long j, final TimeUnit timeUnit) {
        Runtime.getRuntime().addShutdownHook(new Thread(new BackgroundPriorityRunnable() { // from class: io.fabric.sdk.android.services.common.ExecutorUtils.2
            @Override // io.fabric.sdk.android.services.common.BackgroundPriorityRunnable
            public void onRun() {
                try {
                    Fabric.getLogger().d(Fabric.TAG, "Executing shutdown hook for " + str);
                    executorService.shutdown();
                    if (executorService.awaitTermination(j, timeUnit)) {
                        return;
                    }
                    Fabric.getLogger().d(Fabric.TAG, str + " did not shut down in the allocated time. Requesting immediate shutdown.");
                    executorService.shutdownNow();
                } catch (InterruptedException unused) {
                    Fabric.getLogger().d(Fabric.TAG, String.format(Locale.US, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", str));
                    executorService.shutdownNow();
                }
            }
        }, "Crashlytics Shutdown Hook for " + str));
    }
}
