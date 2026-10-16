package com.tapsdk.antiaddiction.reactor.schedulers;

import com.google.android.gms.common.api.internal.zal$$ExternalSyntheticBackportWithForwarding0;
import com.tapsdk.antiaddiction.reactor.Subscription;
import com.tapsdk.antiaddiction.reactor.functions.Action0;
import com.tapsdk.antiaddiction.reactor.internal.schedulers.NewThreadWorker;
import com.tapsdk.antiaddiction.reactor.subscriptions.CompositeSubscription;
import com.tapsdk.antiaddiction.reactor.subscriptions.Subscriptions;
import com.tapsdk.antiaddiction.reactor.util.RxThreadFactory;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class CachedThreadScheduler extends Scheduler implements SchedulerLifecycle {
    private static final long KEEP_ALIVE_TIME;
    private static final TimeUnit KEEP_ALIVE_UNIT = TimeUnit.SECONDS;
    static final CachedWorkerPool NONE;
    static final ThreadWorker SHUTDOWN_THREADWORKER;
    final AtomicReference<CachedWorkerPool> pool = new AtomicReference<>(NONE);
    final ThreadFactory threadFactory;

    static {
        ThreadWorker threadWorker = new ThreadWorker(RxThreadFactory.NONE);
        SHUTDOWN_THREADWORKER = threadWorker;
        threadWorker.unsubscribe();
        CachedWorkerPool cachedWorkerPool = new CachedWorkerPool(null, 0L, null);
        NONE = cachedWorkerPool;
        cachedWorkerPool.shutdown();
        KEEP_ALIVE_TIME = Integer.getInteger("rx.io-scheduler.keepalive", 60).intValue();
    }

    static final class CachedWorkerPool {
        private final CompositeSubscription allWorkers;
        private final ScheduledExecutorService evictorService;
        private final Future<?> evictorTask;
        private final ConcurrentLinkedQueue<ThreadWorker> expiringWorkerQueue;
        private final long keepAliveTime;
        private final ThreadFactory threadFactory;

        CachedWorkerPool(final ThreadFactory threadFactory, long j, TimeUnit timeUnit) {
            ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool;
            ScheduledFuture<?> scheduledFutureScheduleWithFixedDelay;
            this.threadFactory = threadFactory;
            long nanos = timeUnit != null ? timeUnit.toNanos(j) : 0L;
            this.keepAliveTime = nanos;
            this.expiringWorkerQueue = new ConcurrentLinkedQueue<>();
            this.allWorkers = new CompositeSubscription();
            if (timeUnit != null) {
                scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1, new ThreadFactory() { // from class: com.tapsdk.antiaddiction.reactor.schedulers.CachedThreadScheduler.CachedWorkerPool.1
                    @Override // java.util.concurrent.ThreadFactory
                    public Thread newThread(Runnable runnable) {
                        Thread threadNewThread = threadFactory.newThread(runnable);
                        threadNewThread.setName(threadNewThread.getName() + " (Evictor)");
                        return threadNewThread;
                    }
                });
                NewThreadWorker.tryEnableCancelPolicy(scheduledExecutorServiceNewScheduledThreadPool);
                scheduledFutureScheduleWithFixedDelay = scheduledExecutorServiceNewScheduledThreadPool.scheduleWithFixedDelay(new Runnable() { // from class: com.tapsdk.antiaddiction.reactor.schedulers.CachedThreadScheduler.CachedWorkerPool.2
                    @Override // java.lang.Runnable
                    public void run() {
                        CachedWorkerPool.this.evictExpiredWorkers();
                    }
                }, nanos, nanos, TimeUnit.NANOSECONDS);
            } else {
                scheduledExecutorServiceNewScheduledThreadPool = null;
                scheduledFutureScheduleWithFixedDelay = null;
            }
            this.evictorService = scheduledExecutorServiceNewScheduledThreadPool;
            this.evictorTask = scheduledFutureScheduleWithFixedDelay;
        }

        ThreadWorker get() {
            if (this.allWorkers.isUnsubscribed()) {
                return CachedThreadScheduler.SHUTDOWN_THREADWORKER;
            }
            while (!this.expiringWorkerQueue.isEmpty()) {
                ThreadWorker threadWorkerPoll = this.expiringWorkerQueue.poll();
                if (threadWorkerPoll != null) {
                    return threadWorkerPoll;
                }
            }
            ThreadWorker threadWorker = new ThreadWorker(this.threadFactory);
            this.allWorkers.add(threadWorker);
            return threadWorker;
        }

        void release(ThreadWorker threadWorker) {
            threadWorker.setExpirationTime(now() + this.keepAliveTime);
            this.expiringWorkerQueue.offer(threadWorker);
        }

        void evictExpiredWorkers() {
            if (this.expiringWorkerQueue.isEmpty()) {
                return;
            }
            long jNow = now();
            for (ThreadWorker threadWorker : this.expiringWorkerQueue) {
                if (threadWorker.getExpirationTime() > jNow) {
                    return;
                }
                if (this.expiringWorkerQueue.remove(threadWorker)) {
                    this.allWorkers.remove(threadWorker);
                }
            }
        }

        long now() {
            return System.nanoTime();
        }

        void shutdown() {
            try {
                Future<?> future = this.evictorTask;
                if (future != null) {
                    future.cancel(true);
                }
                ScheduledExecutorService scheduledExecutorService = this.evictorService;
                if (scheduledExecutorService != null) {
                    scheduledExecutorService.shutdownNow();
                }
            } finally {
                this.allWorkers.unsubscribe();
            }
        }
    }

    public CachedThreadScheduler(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        start();
    }

    @Override // com.tapsdk.antiaddiction.reactor.schedulers.SchedulerLifecycle
    public void start() {
        CachedWorkerPool cachedWorkerPool = new CachedWorkerPool(this.threadFactory, KEEP_ALIVE_TIME, KEEP_ALIVE_UNIT);
        if (zal$$ExternalSyntheticBackportWithForwarding0.m(this.pool, NONE, cachedWorkerPool)) {
            return;
        }
        cachedWorkerPool.shutdown();
    }

    @Override // com.tapsdk.antiaddiction.reactor.schedulers.SchedulerLifecycle
    public void shutdown() {
        CachedWorkerPool cachedWorkerPool;
        CachedWorkerPool cachedWorkerPool2;
        do {
            cachedWorkerPool = this.pool.get();
            cachedWorkerPool2 = NONE;
            if (cachedWorkerPool == cachedWorkerPool2) {
                return;
            }
        } while (!zal$$ExternalSyntheticBackportWithForwarding0.m(this.pool, cachedWorkerPool, cachedWorkerPool2));
        cachedWorkerPool.shutdown();
    }

    @Override // com.tapsdk.antiaddiction.reactor.schedulers.Scheduler
    public Scheduler.Worker createWorker() {
        return new EventLoopWorker(this.pool.get());
    }

    static final class EventLoopWorker extends Scheduler.Worker implements Action0 {
        private final CompositeSubscription innerSubscription = new CompositeSubscription();
        final AtomicBoolean once = new AtomicBoolean();
        private final CachedWorkerPool pool;
        private final ThreadWorker threadWorker;

        EventLoopWorker(CachedWorkerPool cachedWorkerPool) {
            this.pool = cachedWorkerPool;
            this.threadWorker = cachedWorkerPool.get();
        }

        @Override // com.tapsdk.antiaddiction.reactor.Subscription
        public void unsubscribe() {
            if (this.once.compareAndSet(false, true)) {
                this.threadWorker.schedule(this);
            }
            this.innerSubscription.unsubscribe();
        }

        @Override // com.tapsdk.antiaddiction.reactor.functions.Action0
        public void call() {
            this.pool.release(this.threadWorker);
        }

        @Override // com.tapsdk.antiaddiction.reactor.Subscription
        public boolean isUnsubscribed() {
            return this.innerSubscription.isUnsubscribed();
        }

        @Override // com.tapsdk.antiaddiction.reactor.schedulers.Scheduler.Worker
        public Subscription schedule(Action0 action0) {
            return schedule(action0, 0L, null);
        }

        @Override // com.tapsdk.antiaddiction.reactor.schedulers.Scheduler.Worker
        public Subscription schedule(final Action0 action0, long j, TimeUnit timeUnit) {
            if (this.innerSubscription.isUnsubscribed()) {
                return Subscriptions.unsubscribed();
            }
            ScheduledAction scheduledActionScheduleActual = this.threadWorker.scheduleActual(new Action0() { // from class: com.tapsdk.antiaddiction.reactor.schedulers.CachedThreadScheduler.EventLoopWorker.1
                @Override // com.tapsdk.antiaddiction.reactor.functions.Action0
                public void call() {
                    if (EventLoopWorker.this.isUnsubscribed()) {
                        return;
                    }
                    action0.call();
                }
            }, j, timeUnit);
            this.innerSubscription.add(scheduledActionScheduleActual);
            scheduledActionScheduleActual.addParent(this.innerSubscription);
            return scheduledActionScheduleActual;
        }
    }

    static final class ThreadWorker extends NewThreadWorker {
        private long expirationTime;

        ThreadWorker(ThreadFactory threadFactory) {
            super(threadFactory);
            this.expirationTime = 0L;
        }

        public long getExpirationTime() {
            return this.expirationTime;
        }

        public void setExpirationTime(long j) {
            this.expirationTime = j;
        }
    }
}
