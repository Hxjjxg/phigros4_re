package com.tds.common.reactor.internal.operators;

import android.R;
import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Producer;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.operators.BackpressureUtils;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public final class OnSubscribeFromArray<T> implements Observable.OnSubscribe<T> {
    final T[] array;

    public OnSubscribeFromArray(T[] tArr) {
        this.array = tArr;
    }

    @Override // com.tds.common.reactor.functions.Action1
    public void call(Subscriber<? super T> subscriber) {
        subscriber.setProducer(new FromArrayProducer(subscriber, this.array));
    }

    static final class FromArrayProducer<T> extends AtomicLong implements Producer {
        private static final long serialVersionUID = 3534218984725836979L;
        final T[] array;
        final Subscriber<? super T> child;
        int index;

        public FromArrayProducer(Subscriber<? super T> subscriber, T[] tArr) {
            this.child = subscriber;
            this.array = tArr;
        }

        @Override // com.tds.common.reactor.Producer
        public void request(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("n >= 0 required but it was " + j);
            }
            if (j == Long.MAX_VALUE) {
                if (BackpressureUtils.getAndAddRequest(this, j) == 0) {
                    fastPath();
                }
            } else {
                if (j == 0 || BackpressureUtils.getAndAddRequest(this, j) != 0) {
                    return;
                }
                slowPath(j);
            }
        }

        void fastPath() {
            Subscriber<? super T> subscriber = this.child;
            for (R.color colorVar : this.array) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                subscriber.onNext(colorVar);
            }
            if (subscriber.isUnsubscribed()) {
                return;
            }
            subscriber.onCompleted();
        }

        void slowPath(long j) {
            Subscriber<? super T> subscriber = this.child;
            T[] tArr = this.array;
            int length = tArr.length;
            int i = this.index;
            do {
                long j2 = 0;
                while (true) {
                    if (j != 0 && i != length) {
                        if (subscriber.isUnsubscribed()) {
                            return;
                        }
                        subscriber.onNext(tArr[i]);
                        i++;
                        if (i == length) {
                            if (subscriber.isUnsubscribed()) {
                                return;
                            }
                            subscriber.onCompleted();
                            return;
                        }
                        j--;
                        j2--;
                    } else {
                        j = get() + j2;
                        if (j == 0) {
                            break;
                        }
                    }
                }
                this.index = i;
                j = addAndGet(j2);
            } while (j != 0);
        }
    }
}
