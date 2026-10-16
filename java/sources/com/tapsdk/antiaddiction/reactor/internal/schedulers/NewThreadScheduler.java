package com.tapsdk.antiaddiction.reactor.internal.schedulers;

import com.tapsdk.antiaddiction.reactor.schedulers.Scheduler;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: classes.dex */
public final class NewThreadScheduler extends Scheduler {
    private final ThreadFactory threadFactory;

    public NewThreadScheduler(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    @Override // com.tapsdk.antiaddiction.reactor.schedulers.Scheduler
    public Scheduler.Worker createWorker() {
        return new NewThreadWorker(this.threadFactory);
    }
}
