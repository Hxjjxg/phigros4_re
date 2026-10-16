package com.tapsdk.antiaddiction.skynet.retrofit2.adapter.rxjava;

import com.tapsdk.antiaddiction.reactor.Producer;
import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.skynet.retrofit2.Call;
import com.tapsdk.antiaddiction.skynet.retrofit2.Response;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class CallArbiter<T> extends AtomicInteger implements Producer {
    private static final int STATE_HAS_RESPONSE = 2;
    private static final int STATE_REQUESTED = 1;
    private static final int STATE_TERMINATED = 3;
    private static final int STATE_WAITING = 0;
    private final Call<T> call;
    private volatile Response<T> response;
    private final Subscriber<? super Response<T>> subscriber;
    private volatile boolean unsubscribed;

    CallArbiter(Call<T> call, Subscriber<? super Response<T>> subscriber) {
        this.call = call;
        this.subscriber = subscriber;
    }

    void emitResponse(Response<T> response) {
        while (true) {
            int i = get();
            if (i == 0) {
                this.response = response;
                if (compareAndSet(0, 2)) {
                    return;
                }
            } else {
                if (i != 1) {
                    if (i == 2 || i == 3) {
                        throw new AssertionError();
                    }
                    throw new IllegalStateException("Unknown state: " + i);
                }
                if (compareAndSet(1, 3)) {
                    deliverResponse(response);
                    return;
                }
            }
        }
    }

    private void deliverResponse(Response<T> response) {
        try {
            this.subscriber.onNext(response);
            this.subscriber.onCompleted();
        } catch (Exception unused) {
        }
    }

    void emitError(Throwable th) {
        set(3);
        try {
            this.subscriber.onError(th);
        } catch (Exception unused) {
        }
    }

    @Override // com.tapsdk.antiaddiction.reactor.Producer
    public void request(long j) {
        if (j == 0) {
            return;
        }
        while (true) {
            int i = get();
            if (i != 0) {
                if (i == 1) {
                    return;
                }
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalStateException("Unknown state: " + i);
                    }
                    return;
                } else if (compareAndSet(2, 3)) {
                    deliverResponse(this.response);
                    return;
                }
            } else if (compareAndSet(0, 1)) {
                return;
            }
        }
    }
}
