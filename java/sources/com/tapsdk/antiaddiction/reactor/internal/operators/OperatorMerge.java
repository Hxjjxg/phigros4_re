package com.tapsdk.antiaddiction.reactor.internal.operators;

import com.tapsdk.antiaddiction.reactor.Observable;
import com.tapsdk.antiaddiction.reactor.Producer;
import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.reactor.exceptions.CompositeException;
import com.tapsdk.antiaddiction.reactor.exceptions.Exceptions;
import com.tapsdk.antiaddiction.reactor.exceptions.MissingBackpressureException;
import com.tapsdk.antiaddiction.reactor.exceptions.OnErrorThrowable;
import com.tapsdk.antiaddiction.reactor.internal.util.RxRingBuffer;
import com.tapsdk.antiaddiction.reactor.internal.util.atomic.SpscAtomicArrayQueue;
import com.tapsdk.antiaddiction.reactor.internal.util.atomic.SpscExactAtomicArrayQueue;
import com.tapsdk.antiaddiction.reactor.internal.util.atomic.SpscUnboundedAtomicArrayQueue;
import com.tapsdk.antiaddiction.reactor.internal.util.unsafe.Pow2;
import com.tapsdk.antiaddiction.reactor.operators.BackpressureUtils;
import com.tapsdk.antiaddiction.reactor.operators.NotificationLite;
import com.tapsdk.antiaddiction.reactor.subscriptions.CompositeSubscription;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public final class OperatorMerge<T> implements Observable.Operator<T, Observable<? extends T>> {
    final boolean delayErrors;
    final int maxConcurrent;

    static final class HolderNoDelay {
        static final OperatorMerge<Object> INSTANCE = new OperatorMerge<>(false, Integer.MAX_VALUE);

        HolderNoDelay() {
        }
    }

    static final class HolderDelayErrors {
        static final OperatorMerge<Object> INSTANCE = new OperatorMerge<>(true, Integer.MAX_VALUE);

        HolderDelayErrors() {
        }
    }

    public static <T> OperatorMerge<T> instance(boolean z) {
        if (z) {
            return (OperatorMerge<T>) HolderDelayErrors.INSTANCE;
        }
        return (OperatorMerge<T>) HolderNoDelay.INSTANCE;
    }

    public static <T> OperatorMerge<T> instance(boolean z, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxConcurrent > 0 required but it was " + i);
        }
        if (i == Integer.MAX_VALUE) {
            return instance(z);
        }
        return new OperatorMerge<>(z, i);
    }

    OperatorMerge(boolean z, int i) {
        this.delayErrors = z;
        this.maxConcurrent = i;
    }

    @Override // com.tapsdk.antiaddiction.reactor.functions.Func1
    public Subscriber<Observable<? extends T>> call(Subscriber<? super T> subscriber) {
        MergeSubscriber mergeSubscriber = new MergeSubscriber(subscriber, this.delayErrors, this.maxConcurrent);
        MergeProducer<T> mergeProducer = new MergeProducer<>(mergeSubscriber);
        mergeSubscriber.producer = mergeProducer;
        subscriber.add(mergeSubscriber);
        subscriber.setProducer(mergeProducer);
        return mergeSubscriber;
    }

    static final class MergeProducer<T> extends AtomicLong implements Producer {
        private static final long serialVersionUID = -1214379189873595503L;
        final MergeSubscriber<T> subscriber;

        public MergeProducer(MergeSubscriber<T> mergeSubscriber) {
            this.subscriber = mergeSubscriber;
        }

        @Override // com.tapsdk.antiaddiction.reactor.Producer
        public void request(long j) throws Throwable {
            if (j <= 0) {
                if (j < 0) {
                    throw new IllegalArgumentException("n >= 0 required");
                }
            } else {
                if (get() == Long.MAX_VALUE) {
                    return;
                }
                BackpressureUtils.getAndAddRequest(this, j);
                this.subscriber.emit();
            }
        }

        public long produced(int i) {
            return addAndGet(-i);
        }
    }

    static final class MergeSubscriber<T> extends Subscriber<Observable<? extends T>> {
        static final InnerSubscriber<?>[] EMPTY = new InnerSubscriber[0];
        final Subscriber<? super T> child;
        final boolean delayErrors;
        volatile boolean done;
        boolean emitting;
        volatile ConcurrentLinkedQueue<Throwable> errors;
        final Object innerGuard = new Object();
        volatile InnerSubscriber<?>[] innerSubscribers = EMPTY;
        long lastId;
        int lastIndex;
        final int maxConcurrent;
        boolean missed;
        MergeProducer<T> producer;
        volatile Queue<Object> queue;
        int scalarEmissionCount;
        final int scalarEmissionLimit;
        volatile CompositeSubscription subscriptions;
        long uniqueId;

        public MergeSubscriber(Subscriber<? super T> subscriber, boolean z, int i) {
            this.child = subscriber;
            this.delayErrors = z;
            this.maxConcurrent = i;
            if (i == Integer.MAX_VALUE) {
                this.scalarEmissionLimit = Integer.MAX_VALUE;
                request(Long.MAX_VALUE);
            } else {
                this.scalarEmissionLimit = Math.max(1, i >> 1);
                request(i);
            }
        }

        Queue<Throwable> getOrCreateErrorQueue() {
            ConcurrentLinkedQueue<Throwable> concurrentLinkedQueue = this.errors;
            if (concurrentLinkedQueue == null) {
                synchronized (this) {
                    concurrentLinkedQueue = this.errors;
                    if (concurrentLinkedQueue == null) {
                        concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
                        this.errors = concurrentLinkedQueue;
                    }
                }
            }
            return concurrentLinkedQueue;
        }

        CompositeSubscription getOrCreateComposite() {
            boolean z;
            CompositeSubscription compositeSubscription = this.subscriptions;
            if (compositeSubscription == null) {
                synchronized (this) {
                    compositeSubscription = this.subscriptions;
                    if (compositeSubscription == null) {
                        compositeSubscription = new CompositeSubscription();
                        this.subscriptions = compositeSubscription;
                        z = true;
                    } else {
                        z = false;
                    }
                }
                if (z) {
                    add(compositeSubscription);
                }
            }
            return compositeSubscription;
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onNext(Observable<? extends T> observable) throws Throwable {
            if (observable == null) {
                return;
            }
            if (observable == Observable.empty()) {
                emitEmpty();
                return;
            }
            long j = this.uniqueId;
            this.uniqueId = 1 + j;
            InnerSubscriber<T> innerSubscriber = new InnerSubscriber<>(this, j);
            addInner(innerSubscriber);
            observable.unsafeSubscribe(innerSubscriber);
            emit();
        }

        void emitEmpty() {
            int i = this.scalarEmissionCount + 1;
            if (i == this.scalarEmissionLimit) {
                this.scalarEmissionCount = 0;
                requestMore(i);
            } else {
                this.scalarEmissionCount = i;
            }
        }

        private void reportError() {
            ArrayList arrayList = new ArrayList(this.errors);
            if (arrayList.size() == 1) {
                this.child.onError((Throwable) arrayList.get(0));
            } else {
                this.child.onError(new CompositeException(arrayList));
            }
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onError(Throwable th) throws Throwable {
            getOrCreateErrorQueue().offer(th);
            this.done = true;
            emit();
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onCompleted() throws Throwable {
            this.done = true;
            emit();
        }

        /* JADX WARN: Multi-variable type inference failed */
        void addInner(InnerSubscriber<T> innerSubscriber) {
            getOrCreateComposite().add(innerSubscriber);
            synchronized (this.innerGuard) {
                InnerSubscriber<?>[] innerSubscriberArr = this.innerSubscribers;
                int length = innerSubscriberArr.length;
                InnerSubscriber<?>[] innerSubscriberArr2 = new InnerSubscriber[length + 1];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, length);
                innerSubscriberArr2[length] = innerSubscriber;
                this.innerSubscribers = innerSubscriberArr2;
            }
        }

        void removeInner(InnerSubscriber<T> innerSubscriber) {
            RxRingBuffer rxRingBuffer = innerSubscriber.queue;
            if (rxRingBuffer != null) {
                rxRingBuffer.release();
            }
            this.subscriptions.remove(innerSubscriber);
            synchronized (this.innerGuard) {
                InnerSubscriber<?>[] innerSubscriberArr = this.innerSubscribers;
                int length = innerSubscriberArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        i = -1;
                        break;
                    } else if (innerSubscriber.equals(innerSubscriberArr[i])) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (i < 0) {
                    return;
                }
                if (length == 1) {
                    this.innerSubscribers = EMPTY;
                    return;
                }
                InnerSubscriber<?>[] innerSubscriberArr2 = new InnerSubscriber[length - 1];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, i);
                System.arraycopy(innerSubscriberArr, i + 1, innerSubscriberArr2, i, (length - i) - 1);
                this.innerSubscribers = innerSubscriberArr2;
            }
        }

        void tryEmit(InnerSubscriber<T> innerSubscriber, T t) throws Throwable {
            long j = this.producer.get();
            boolean z = false;
            if (j != 0) {
                synchronized (this) {
                    j = this.producer.get();
                    if (!this.emitting && j != 0) {
                        this.emitting = true;
                        z = true;
                    }
                }
            }
            if (z) {
                RxRingBuffer rxRingBuffer = innerSubscriber.queue;
                if (rxRingBuffer == null || rxRingBuffer.isEmpty()) {
                    emitScalar(innerSubscriber, t, j);
                    return;
                } else {
                    queueScalar(innerSubscriber, t);
                    emitLoop();
                    return;
                }
            }
            queueScalar(innerSubscriber, t);
            emit();
        }

        protected void queueScalar(InnerSubscriber<T> innerSubscriber, T t) throws Throwable {
            RxRingBuffer spscInstance = innerSubscriber.queue;
            if (spscInstance == null) {
                spscInstance = RxRingBuffer.getSpscInstance();
                innerSubscriber.add(spscInstance);
                innerSubscriber.queue = spscInstance;
            }
            try {
                spscInstance.onNext(NotificationLite.next(t));
            } catch (MissingBackpressureException e) {
                innerSubscriber.unsubscribe();
                innerSubscriber.onError(e);
            } catch (IllegalStateException e2) {
                if (innerSubscriber.isUnsubscribed()) {
                    return;
                }
                innerSubscriber.unsubscribe();
                innerSubscriber.onError(e2);
            }
        }

        /* JADX WARN: Code duplicated, block: B:34:0x004a  */
        /* JADX WARN: Code duplicated, block: B:46:0x004b A[EXC_TOP_SPLITTER, SYNTHETIC] */
        protected void emitScalar(InnerSubscriber<T> innerSubscriber, T t, long j) throws Throwable {
            boolean z = true;
            try {
                try {
                    try {
                        this.child.onNext(t);
                    } catch (Throwable th) {
                        th = th;
                        z = false;
                        if (!z) {
                            synchronized (this) {
                                this.emitting = false;
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    if (!this.delayErrors) {
                        Exceptions.throwIfFatal(th2);
                        innerSubscriber.unsubscribe();
                        innerSubscriber.onError(th2);
                        return;
                    }
                    getOrCreateErrorQueue().offer(th2);
                }
                if (j != Long.MAX_VALUE) {
                    this.producer.produced(1);
                }
                innerSubscriber.requestMore(1L);
                synchronized (this) {
                    if (!this.missed) {
                        this.emitting = false;
                    } else {
                        this.missed = false;
                        emitLoop();
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                if (!z) {
                    synchronized (this) {
                        this.emitting = false;
                    }
                }
                throw th;
            }
        }

        public void requestMore(long j) {
            request(j);
        }

        void tryEmit(T t) throws Throwable {
            long j = this.producer.get();
            boolean z = false;
            if (j != 0) {
                synchronized (this) {
                    j = this.producer.get();
                    if (!this.emitting && j != 0) {
                        this.emitting = true;
                        z = true;
                    }
                }
            }
            if (z) {
                Queue<Object> queue = this.queue;
                if (queue == null || queue.isEmpty()) {
                    emitScalar(t, j);
                    return;
                } else {
                    queueScalar(t);
                    emitLoop();
                    return;
                }
            }
            queueScalar(t);
            emit();
        }

        protected void queueScalar(T t) throws Throwable {
            Queue<Object> spscExactAtomicArrayQueue;
            Queue<Object> spscUnboundedAtomicArrayQueue = this.queue;
            if (spscUnboundedAtomicArrayQueue == null) {
                int i = this.maxConcurrent;
                if (i == Integer.MAX_VALUE) {
                    spscUnboundedAtomicArrayQueue = new SpscUnboundedAtomicArrayQueue<>(128);
                } else {
                    if (Pow2.isPowerOfTwo(i)) {
                        spscExactAtomicArrayQueue = new SpscAtomicArrayQueue<>(i);
                    } else {
                        spscExactAtomicArrayQueue = new SpscExactAtomicArrayQueue<>(i);
                    }
                    spscUnboundedAtomicArrayQueue = spscExactAtomicArrayQueue;
                }
                this.queue = spscUnboundedAtomicArrayQueue;
            }
            if (spscUnboundedAtomicArrayQueue.offer(NotificationLite.next(t))) {
                return;
            }
            unsubscribe();
            onError(OnErrorThrowable.addValueAsLastCause(new MissingBackpressureException(), t));
        }

        protected void emitScalar(T t, long j) throws Throwable {
            boolean z = true;
            try {
                try {
                    try {
                        this.child.onNext(t);
                    } catch (Throwable th) {
                        th = th;
                        z = false;
                        if (!z) {
                            synchronized (this) {
                                this.emitting = false;
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    if (!this.delayErrors) {
                        Exceptions.throwIfFatal(th2);
                        unsubscribe();
                        onError(th2);
                        return;
                    }
                    getOrCreateErrorQueue().offer(th2);
                }
                if (j != Long.MAX_VALUE) {
                    this.producer.produced(1);
                }
                int i = this.scalarEmissionCount + 1;
                if (i == this.scalarEmissionLimit) {
                    this.scalarEmissionCount = 0;
                    requestMore(i);
                } else {
                    this.scalarEmissionCount = i;
                }
                synchronized (this) {
                    if (!this.missed) {
                        this.emitting = false;
                    } else {
                        this.missed = false;
                        emitLoop();
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }

        void emit() throws Throwable {
            synchronized (this) {
                if (this.emitting) {
                    this.missed = true;
                } else {
                    this.emitting = true;
                    emitLoop();
                }
            }
        }

        /* JADX WARN: Bottom block not found for handler: all -> 0x01a5 */
        /* JADX WARN: Code duplicated, block: B:159:0x01ab  */
        /* JADX WARN: Code duplicated, block: B:167:0x01ac A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        void emitLoop() throws java.lang.Throwable {
            /*
                Method dump skipped, instruction units count: 436
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tapsdk.antiaddiction.reactor.internal.operators.OperatorMerge.MergeSubscriber.emitLoop():void");
        }

        boolean checkTerminate() {
            if (this.child.isUnsubscribed()) {
                return true;
            }
            ConcurrentLinkedQueue<Throwable> concurrentLinkedQueue = this.errors;
            if (this.delayErrors || concurrentLinkedQueue == null || concurrentLinkedQueue.isEmpty()) {
                return false;
            }
            try {
                reportError();
                return true;
            } finally {
                unsubscribe();
            }
        }
    }

    static final class InnerSubscriber<T> extends Subscriber<T> {
        static final int LIMIT = RxRingBuffer.SIZE / 4;
        volatile boolean done;
        final long id;
        int outstanding;
        final MergeSubscriber<T> parent;
        volatile RxRingBuffer queue;

        public InnerSubscriber(MergeSubscriber<T> mergeSubscriber, long j) {
            this.parent = mergeSubscriber;
            this.id = j;
        }

        @Override // com.tapsdk.antiaddiction.reactor.Subscriber
        public void onStart() {
            this.outstanding = RxRingBuffer.SIZE;
            request(RxRingBuffer.SIZE);
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onNext(T t) throws Throwable {
            this.parent.tryEmit(this, t);
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onError(Throwable th) throws Throwable {
            this.parent.getOrCreateErrorQueue().offer(th);
            this.done = true;
            this.parent.emit();
        }

        @Override // com.tapsdk.antiaddiction.reactor.Observer
        public void onCompleted() throws Throwable {
            this.done = true;
            this.parent.emit();
        }

        public void requestMore(long j) {
            int i = this.outstanding - ((int) j);
            if (i > LIMIT) {
                this.outstanding = i;
                return;
            }
            this.outstanding = RxRingBuffer.SIZE;
            int i2 = RxRingBuffer.SIZE - i;
            if (i2 > 0) {
                request(i2);
            }
        }
    }
}
