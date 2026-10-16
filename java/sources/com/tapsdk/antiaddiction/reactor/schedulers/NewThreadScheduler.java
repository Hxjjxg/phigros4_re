package com.tapsdk.antiaddiction.reactor.schedulers;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class NewThreadScheduler extends Scheduler {
    @Override // com.tapsdk.antiaddiction.reactor.schedulers.Scheduler
    public Scheduler.Worker createWorker() {
        return null;
    }

    private NewThreadScheduler() {
        throw new IllegalStateException("No instances!");
    }
}
