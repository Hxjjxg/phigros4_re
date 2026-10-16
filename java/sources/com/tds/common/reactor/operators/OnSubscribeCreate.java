package com.tds.common.reactor.operators;

import com.tds.common.reactor.Emitter;
import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Producer;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.Subscription;
import com.tds.common.reactor.exceptions.MissingBackpressureException;
import com.tds.common.reactor.functions.Action1;
import com.tds.common.reactor.internal.util.atomic.SpscUnboundedAtomicArrayQueue;
import com.tds.common.reactor.plugins.RxJavaHooks;
import com.tds.common.reactor.subscriptions.SerialSubscription;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class OnSubscribeCreate<T> implements Observable.OnSubscribe<T> {
    final Action1<Emitter<T>> Emitter;
    final Emitter.BackpressureMode backpressure;

    public OnSubscribeCreate(Action1<Emitter<T>> action1, Emitter.BackpressureMode backpressureMode) {
        this.Emitter = action1;
        this.backpressure = backpressureMode;
    }

    /* JADX INFO: renamed from: com.tds.common.reactor.operators.OnSubscribeCreate$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$tds$common$reactor$Emitter$BackpressureMode;

        static {
            int[] iArr = new int[Emitter.BackpressureMode.values().length];
            $SwitchMap$com$tds$common$reactor$Emitter$BackpressureMode = iArr;
            try {
                iArr[Emitter.BackpressureMode.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$tds$common$reactor$Emitter$BackpressureMode[Emitter.BackpressureMode.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$tds$common$reactor$Emitter$BackpressureMode[Emitter.BackpressureMode.DROP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$tds$common$reactor$Emitter$BackpressureMode[Emitter.BackpressureMode.LATEST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
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
    @Override // com.tds.common.reactor.functions.Action1
    public void call(Subscriber<? super T> subscriber) {
        BaseEmitter noneEmitter;
        int i = AnonymousClass1.$SwitchMap$com$tds$common$reactor$Emitter$BackpressureMode[this.backpressure.ordinal()];
        if (i == 1) {
            noneEmitter = new NoneEmitter(subscriber);
        } else if (i == 2) {
            noneEmitter = new ErrorEmitter(subscriber);
        } else if (i == 3) {
            noneEmitter = new DropEmitter(subscriber);
        } else if (i == 4) {
            noneEmitter = new LatestEmitter(subscriber);
        } else {
            noneEmitter = new BufferEmitter(subscriber, 128);
        }
        subscriber.add(noneEmitter);
        subscriber.setProducer(noneEmitter);
        this.Emitter.call(noneEmitter);
    }

    static abstract class BaseEmitter<T> extends AtomicLong implements Emitter<T>, Producer, Subscription {
        private static final long serialVersionUID = 7326289992464377023L;
        final Subscriber<? super T> actual;
        final SerialSubscription serial = new SerialSubscription();

        void onRequested() {
        }

        void onUnsubscribed() {
        }

        public BaseEmitter(Subscriber<? super T> subscriber) {
            this.actual = subscriber;
        }

        @Override // com.tds.common.reactor.Observer
        public void onCompleted() {
            if (this.actual.isUnsubscribed()) {
                return;
            }
            try {
                this.actual.onCompleted();
            } finally {
                this.serial.unsubscribe();
            }
        }

        @Override // com.tds.common.reactor.Observer
        public void onError(Throwable th) {
            if (this.actual.isUnsubscribed()) {
                return;
            }
            try {
                this.actual.onError(th);
            } finally {
                this.serial.unsubscribe();
            }
        }

        @Override // com.tds.common.reactor.Subscription
        public final void unsubscribe() {
            this.serial.unsubscribe();
            onUnsubscribed();
        }

        @Override // com.tds.common.reactor.Subscription
        public final boolean isUnsubscribed() {
            return this.serial.isUnsubscribed();
        }

        @Override // com.tds.common.reactor.Producer
        public final void request(long j) {
            if (BackpressureUtils.validate(j)) {
                BackpressureUtils.getAndAddRequest(this, j);
                onRequested();
            }
        }

        @Override // com.tds.common.reactor.Emitter
        public final void setSubscription(Subscription subscription) {
            this.serial.set(subscription);
        }

        @Override // com.tds.common.reactor.Emitter
        public final long requested() {
            return get();
        }
    }

    static final class NoneEmitter<T> extends BaseEmitter<T> {
        private static final long serialVersionUID = 3776720187248809713L;

        public NoneEmitter(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        @Override // com.tds.common.reactor.Observer
        public void onNext(T t) {
            long j;
            if (this.actual.isUnsubscribed()) {
                return;
            }
            this.actual.onNext(t);
            do {
                j = get();
                if (j == 0) {
                    return;
                }
            } while (!compareAndSet(j, j - 1));
        }
    }

    static abstract class NoOverflowBaseEmitter<T> extends BaseEmitter<T> {
        private static final long serialVersionUID = 4127754106204442833L;

        abstract void onOverflow();

        public NoOverflowBaseEmitter(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        public void onNext(T t) {
            if (this.actual.isUnsubscribed()) {
                return;
            }
            if (get() != 0) {
                this.actual.onNext(t);
                BackpressureUtils.produced(this, 1L);
            } else {
                onOverflow();
            }
        }
    }

    static final class DropEmitter<T> extends NoOverflowBaseEmitter<T> {
        private static final long serialVersionUID = 8360058422307496563L;

        @Override // com.tds.common.reactor.operators.OnSubscribeCreate.NoOverflowBaseEmitter
        void onOverflow() {
        }

        public DropEmitter(Subscriber<? super T> subscriber) {
            super(subscriber);
        }
    }

    static final class ErrorEmitter<T> extends NoOverflowBaseEmitter<T> {
        private static final long serialVersionUID = 338953216916120960L;
        private boolean done;

        public ErrorEmitter(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        @Override // com.tds.common.reactor.operators.OnSubscribeCreate.NoOverflowBaseEmitter, com.tds.common.reactor.Observer
        public void onNext(T t) {
            if (this.done) {
                return;
            }
            super.onNext(t);
        }

        @Override // com.tds.common.reactor.operators.OnSubscribeCreate.BaseEmitter, com.tds.common.reactor.Observer
        public void onCompleted() {
            if (this.done) {
                return;
            }
            this.done = true;
            super.onCompleted();
        }

        @Override // com.tds.common.reactor.operators.OnSubscribeCreate.BaseEmitter, com.tds.common.reactor.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaHooks.onError(th);
            } else {
                this.done = true;
                super.onError(th);
            }
        }

        @Override // com.tds.common.reactor.operators.OnSubscribeCreate.NoOverflowBaseEmitter
        void onOverflow() {
            onError(new MissingBackpressureException("create: could not emit value due to lack of requests"));
        }
    }

    static final class BufferEmitter<T> extends BaseEmitter<T> {
        private static final long serialVersionUID = 2427151001689639875L;
        volatile boolean done;
        Throwable error;
        final Queue<Object> queue;
        final AtomicInteger wip;

        public BufferEmitter(Subscriber<? super T> subscriber, int i) {
            super(subscriber);
            this.queue = new SpscUnboundedAtomicArrayQueue(i);
            this.wip = new AtomicInteger();
        }

        @Override // com.tds.common.reactor.Observer
        public void onNext(T t) {
            this.queue.offer(NotificationLite.next(t));
            drain();
        }

        @Override // com.tds.common.reactor.operators.OnSubscribeCreate.BaseEmitter, com.tds.common.reactor.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            drain();
        }

        @Override // com.tds.common.reactor.operators.OnSubscribeCreate.BaseEmitter, com.tds.common.reactor.Observer
        public void onCompleted() {
            this.done = true;
            drain();
        }

        @Override // com.tds.common.reactor.operators.OnSubscribeCreate.BaseEmitter
        void onRequested() {
            drain();
        }

        @Override // com.tds.common.reactor.operators.OnSubscribeCreate.BaseEmitter
        void onUnsubscribed() {
            if (this.wip.getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        void drain() {
            if (this.wip.getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super T> subscriber = this.actual;
            Queue<Object> queue = this.queue;
            int iAddAndGet = 1;
            do {
                long j = get();
                long j2 = 0;
                while (j2 != j) {
                    if (subscriber.isUnsubscribed()) {
                        queue.clear();
                        return;
                    }
                    boolean z = this.done;
                    Object objPoll = queue.poll();
                    boolean z2 = objPoll == null;
                    if (z && z2) {
                        Throwable th = this.error;
                        if (th != null) {
                            super.onError(th);
                            return;
                        } else {
                            super.onCompleted();
                            return;
                        }
                    }
                    if (z2) {
                        break;
                    }
                    subscriber.onNext((Object) NotificationLite.getValue(objPoll));
                    j2++;
                }
                if (j2 == j) {
                    if (subscriber.isUnsubscribed()) {
                        queue.clear();
                        return;
                    }
                    boolean z3 = this.done;
                    boolean zIsEmpty = queue.isEmpty();
                    if (z3 && zIsEmpty) {
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            super.onError(th2);
                            return;
                        } else {
                            super.onCompleted();
                            return;
                        }
                    }
                }
                if (j2 != 0) {
                    BackpressureUtils.produced(this, j2);
                }
                iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }
    }

    static final class LatestEmitter<T> extends BaseEmitter<T> {
        private static final long serialVersionUID = 4023437720691792495L;
        volatile boolean done;
        Throwable error;
        final AtomicReference<Object> queue;
        final AtomicInteger wip;

        public LatestEmitter(Subscriber<? super T> subscriber) {
            super(subscriber);
            this.queue = new AtomicReference<>();
            this.wip = new AtomicInteger();
        }

        @Override // com.tds.common.reactor.Observer
        public void onNext(T t) {
            this.queue.set(NotificationLite.next(t));
            drain();
        }

        @Override // com.tds.common.reactor.operators.OnSubscribeCreate.BaseEmitter, com.tds.common.reactor.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            drain();
        }

        @Override // com.tds.common.reactor.operators.OnSubscribeCreate.BaseEmitter, com.tds.common.reactor.Observer
        public void onCompleted() {
            this.done = true;
            drain();
        }

        @Override // com.tds.common.reactor.operators.OnSubscribeCreate.BaseEmitter
        void onRequested() {
            drain();
        }

        @Override // com.tds.common.reactor.operators.OnSubscribeCreate.BaseEmitter
        void onUnsubscribed() {
            if (this.wip.getAndIncrement() == 0) {
                this.queue.lazySet(null);
            }
        }

        void drain() {
            if (this.wip.getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super T> subscriber = this.actual;
            AtomicReference<Object> atomicReference = this.queue;
            int iAddAndGet = 1;
            do {
                long j = get();
                long j2 = 0;
                while (true) {
                    if (j2 == j) {
                        break;
                    }
                    if (subscriber.isUnsubscribed()) {
                        atomicReference.lazySet(null);
                        return;
                    }
                    boolean z = this.done;
                    Object andSet = atomicReference.getAndSet(null);
                    boolean z2 = andSet == null;
                    if (z && z2) {
                        Throwable th = this.error;
                        if (th != null) {
                            super.onError(th);
                            return;
                        } else {
                            super.onCompleted();
                            return;
                        }
                    }
                    if (z2) {
                        break;
                    }
                    subscriber.onNext((Object) NotificationLite.getValue(andSet));
                    j2++;
                }
                if (j2 == j) {
                    if (subscriber.isUnsubscribed()) {
                        atomicReference.lazySet(null);
                        return;
                    }
                    boolean z3 = this.done;
                    boolean z4 = atomicReference.get() == null;
                    if (z3 && z4) {
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            super.onError(th2);
                            return;
                        } else {
                            super.onCompleted();
                            return;
                        }
                    }
                }
                if (j2 != 0) {
                    BackpressureUtils.produced(this, j2);
                }
                iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }
    }
}
