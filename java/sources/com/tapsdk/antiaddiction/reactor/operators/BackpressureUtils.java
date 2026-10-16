package com.tapsdk.antiaddiction.reactor.operators;

import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.reactor.functions.Func1;
import com.tapsdk.antiaddiction.reactor.internal.util.UtilityFunctions;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public final class BackpressureUtils {
    static final long COMPLETED_MASK = Long.MIN_VALUE;
    static final long REQUESTED_MASK = Long.MAX_VALUE;

    public static long addCap(long j, long j2) {
        long j3 = j + j2;
        return j3 < 0 ? REQUESTED_MASK : j3;
    }

    private BackpressureUtils() {
        throw new IllegalStateException("No instances!");
    }

    public static long getAndAddRequest(AtomicLong atomicLong, long j) {
        long j2;
        do {
            j2 = atomicLong.get();
        } while (!atomicLong.compareAndSet(j2, addCap(j2, j)));
        return j2;
    }

    public static long multiplyCap(long j, long j2) {
        long j3 = j * j2;
        return (((j | j2) >>> 31) == 0 || j2 == 0 || j3 / j2 == j) ? j3 : REQUESTED_MASK;
    }

    public static <T> void postCompleteDone(AtomicLong atomicLong, Queue<T> queue, Subscriber<? super T> subscriber) {
        postCompleteDone(atomicLong, queue, subscriber, UtilityFunctions.identity());
    }

    public static <T> boolean postCompleteRequest(AtomicLong atomicLong, long j, Queue<T> queue, Subscriber<? super T> subscriber) {
        return postCompleteRequest(atomicLong, j, queue, subscriber, UtilityFunctions.identity());
    }

    public static <T, R> void postCompleteDone(AtomicLong atomicLong, Queue<T> queue, Subscriber<? super R> subscriber, Func1<? super T, ? extends R> func1) {
        long j;
        do {
            j = atomicLong.get();
            if ((j & COMPLETED_MASK) != 0) {
                return;
            }
        } while (!atomicLong.compareAndSet(j, COMPLETED_MASK | j));
        if (j != 0) {
            postCompleteDrain(atomicLong, queue, subscriber, func1);
        }
    }

    public static <T, R> boolean postCompleteRequest(AtomicLong atomicLong, long j, Queue<T> queue, Subscriber<? super R> subscriber, Func1<? super T, ? extends R> func1) {
        long j2;
        long j3;
        if (j < 0) {
            throw new IllegalArgumentException("n >= 0 required but it was " + j);
        }
        if (j == 0) {
            return (atomicLong.get() & COMPLETED_MASK) == 0;
        }
        do {
            j2 = atomicLong.get();
            j3 = j2 & COMPLETED_MASK;
        } while (!atomicLong.compareAndSet(j2, addCap(REQUESTED_MASK & j2, j) | j3));
        if (j2 != COMPLETED_MASK) {
            return j3 == 0;
        }
        postCompleteDrain(atomicLong, queue, subscriber, func1);
        return false;
    }

    static <T, R> void postCompleteDrain(AtomicLong atomicLong, Queue<T> queue, Subscriber<? super R> subscriber, Func1<? super T, ? extends R> func1) {
        long jAddAndGet = atomicLong.get();
        if (jAddAndGet == REQUESTED_MASK) {
            while (!subscriber.isUnsubscribed()) {
                Object objPoll = queue.poll();
                if (objPoll == null) {
                    subscriber.onCompleted();
                    return;
                }
                subscriber.onNext(func1.call(objPoll));
            }
            return;
        }
        do {
            long j = Long.MIN_VALUE;
            while (true) {
                if (j != jAddAndGet) {
                    if (subscriber.isUnsubscribed()) {
                        return;
                    }
                    Object objPoll2 = queue.poll();
                    if (objPoll2 == null) {
                        subscriber.onCompleted();
                        return;
                    } else {
                        subscriber.onNext(func1.call(objPoll2));
                        j++;
                    }
                } else {
                    if (j == jAddAndGet) {
                        if (subscriber.isUnsubscribed()) {
                            return;
                        }
                        if (queue.isEmpty()) {
                            subscriber.onCompleted();
                            return;
                        }
                    }
                    jAddAndGet = atomicLong.get();
                    if (jAddAndGet == j) {
                        break;
                    }
                }
            }
            jAddAndGet = atomicLong.addAndGet(-(j & REQUESTED_MASK));
        } while (jAddAndGet != COMPLETED_MASK);
    }

    public static long produced(AtomicLong atomicLong, long j) {
        long j2;
        long j3;
        do {
            j2 = atomicLong.get();
            if (j2 == REQUESTED_MASK) {
                return REQUESTED_MASK;
            }
            j3 = j2 - j;
            if (j3 < 0) {
                throw new IllegalStateException("More produced than requested: " + j3);
            }
        } while (!atomicLong.compareAndSet(j2, j3));
        return j3;
    }

    public static boolean validate(long j) {
        if (j >= 0) {
            return j != 0;
        }
        throw new IllegalArgumentException("n >= 0 required but it was " + j);
    }
}
