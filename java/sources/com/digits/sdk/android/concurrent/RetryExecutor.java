package com.digits.sdk.android.concurrent;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class RetryExecutor extends ThreadPoolExecutor {
    private final Backoff backoff;
    private final DelayQueue delayQueue;
    private final RetryPolicy retryPolicy;

    public RetryExecutor(int i, RetryPolicy retryPolicy) {
        this(i, retryPolicy, new ExponentialBackoff(1000L));
    }

    public RetryExecutor(int i, RetryPolicy retryPolicy, Backoff backoff) {
        this(i, retryPolicy, backoff, new DelayQueue());
    }

    RetryExecutor(int i, RetryPolicy retryPolicy, Backoff backoff, DelayQueue delayQueue) {
        super(i, i * 2, 0L, TimeUnit.NANOSECONDS, delayQueue);
        if (retryPolicy == null) {
            throw new IllegalArgumentException("retry policy cannot be null");
        }
        if (backoff == null) {
            throw new IllegalArgumentException("backoff cannot be null");
        }
        this.retryPolicy = retryPolicy;
        this.backoff = backoff;
        this.delayQueue = delayQueue;
    }

    @Override // java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("runnable cannot be null");
        }
        super.execute(new RetryRunnable(this, runnable));
    }

    public RetryPolicy getRetryPolicy() {
        return this.retryPolicy;
    }

    public Backoff getBackoff() {
        return this.backoff;
    }

    class RetryRunnable implements Runnable, Delayed {
        private final Runnable command;
        private final long delay;
        private final int retries;

        public RetryRunnable(RetryExecutor retryExecutor, Runnable runnable) {
            this(retryExecutor, runnable, 0L);
        }

        public RetryRunnable(RetryExecutor retryExecutor, Runnable runnable, long j) {
            this(runnable, j, 0);
        }

        public RetryRunnable(Runnable runnable, long j, int i) {
            this.command = runnable;
            this.delay = System.currentTimeMillis() + j;
            this.retries = i;
        }

        public int getRetries() {
            return this.retries;
        }

        @Override // java.util.concurrent.Delayed
        public long getDelay(TimeUnit timeUnit) {
            return timeUnit.convert(this.delay - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override // java.lang.Comparable
        public int compareTo(Delayed delayed) {
            long delay = delayed.getDelay(TimeUnit.MILLISECONDS);
            long j = this.delay;
            if (j < delay) {
                return -1;
            }
            return j > delay ? 1 : 0;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.command.run();
            } catch (Throwable th) {
                if (RetryExecutor.this.retryPolicy.shouldRetry(this.retries, th)) {
                    RetryExecutor.this.delayQueue.offer(RetryExecutor.this.new RetryRunnable(this.command, RetryExecutor.this.backoff.getDelayMillis(this.retries), this.retries + 1));
                }
            }
        }
    }
}
