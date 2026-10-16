package com.tapsdk.antiaddiction.reactor.schedulers;

import com.tapsdk.antiaddiction.reactor.Subscription;
import com.tapsdk.antiaddiction.reactor.exceptions.OnErrorNotImplementedException;
import com.tapsdk.antiaddiction.reactor.functions.Action0;
import com.tapsdk.antiaddiction.reactor.plugins.RxJavaHooks;
import com.tapsdk.antiaddiction.reactor.subscriptions.CompositeSubscription;
import com.tapsdk.antiaddiction.reactor.util.SubscriptionList;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class ScheduledAction extends AtomicReference<Thread> implements Runnable, Subscription {
    private static final long serialVersionUID = -3962399486978279857L;
    final Action0 action;
    final SubscriptionList cancel;

    public ScheduledAction(Action0 action0) {
        this.action = action0;
        this.cancel = new SubscriptionList();
    }

    public ScheduledAction(Action0 action0, CompositeSubscription compositeSubscription) {
        this.action = action0;
        this.cancel = new SubscriptionList(new Remover(this, compositeSubscription));
    }

    public ScheduledAction(Action0 action0, SubscriptionList subscriptionList) {
        this.action = action0;
        this.cancel = new SubscriptionList(new Remover2(this, subscriptionList));
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            try {
                lazySet(Thread.currentThread());
                this.action.call();
            } catch (Throwable th) {
                unsubscribe();
                throw th;
            }
        } catch (OnErrorNotImplementedException e) {
            signalError(new IllegalStateException("Exception thrown on Scheduler.Worker thread. Add `onError` handling.", e));
        } catch (Throwable th2) {
            signalError(new IllegalStateException("Fatal Exception thrown on Scheduler.Worker thread.", th2));
        }
        unsubscribe();
    }

    void signalError(Throwable th) {
        RxJavaHooks.onError(th);
        Thread threadCurrentThread = Thread.currentThread();
        threadCurrentThread.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread, th);
    }

    @Override // com.tapsdk.antiaddiction.reactor.Subscription
    public boolean isUnsubscribed() {
        return this.cancel.isUnsubscribed();
    }

    @Override // com.tapsdk.antiaddiction.reactor.Subscription
    public void unsubscribe() {
        if (this.cancel.isUnsubscribed()) {
            return;
        }
        this.cancel.unsubscribe();
    }

    static final class Remover extends AtomicBoolean implements Subscription {
        private static final long serialVersionUID = 247232374289553518L;
        final CompositeSubscription parent;
        final ScheduledAction s;

        public Remover(ScheduledAction scheduledAction, CompositeSubscription compositeSubscription) {
            this.s = scheduledAction;
            this.parent = compositeSubscription;
        }

        @Override // com.tapsdk.antiaddiction.reactor.Subscription
        public boolean isUnsubscribed() {
            return this.s.isUnsubscribed();
        }

        @Override // com.tapsdk.antiaddiction.reactor.Subscription
        public void unsubscribe() {
            if (compareAndSet(false, true)) {
                this.parent.remove(this.s);
            }
        }
    }

    public void add(Subscription subscription) {
        this.cancel.add(subscription);
    }

    public void add(Future<?> future) {
        this.cancel.add(new FutureCompleter(future));
    }

    public void addParent(SubscriptionList subscriptionList) {
        this.cancel.add(new Remover2(this, subscriptionList));
    }

    public void addParent(CompositeSubscription compositeSubscription) {
        this.cancel.add(new Remover(this, compositeSubscription));
    }

    final class FutureCompleter implements Subscription {
        private final Future<?> f;

        FutureCompleter(Future<?> future) {
            this.f = future;
        }

        @Override // com.tapsdk.antiaddiction.reactor.Subscription
        public void unsubscribe() {
            if (ScheduledAction.this.get() != Thread.currentThread()) {
                this.f.cancel(true);
            } else {
                this.f.cancel(false);
            }
        }

        @Override // com.tapsdk.antiaddiction.reactor.Subscription
        public boolean isUnsubscribed() {
            return this.f.isCancelled();
        }
    }

    static final class Remover2 extends AtomicBoolean implements Subscription {
        private static final long serialVersionUID = 247232374289553518L;
        final SubscriptionList parent;
        final ScheduledAction s;

        public Remover2(ScheduledAction scheduledAction, SubscriptionList subscriptionList) {
            this.s = scheduledAction;
            this.parent = subscriptionList;
        }

        @Override // com.tapsdk.antiaddiction.reactor.Subscription
        public boolean isUnsubscribed() {
            return this.s.isUnsubscribed();
        }

        @Override // com.tapsdk.antiaddiction.reactor.Subscription
        public void unsubscribe() {
            if (compareAndSet(false, true)) {
                this.parent.remove(this.s);
            }
        }
    }
}
