package com.tapsdk.antiaddiction.reactor.observers;

import com.tapsdk.antiaddiction.reactor.Subscriber;
import com.tapsdk.antiaddiction.reactor.exceptions.CompositeException;
import com.tapsdk.antiaddiction.reactor.exceptions.Exceptions;
import com.tapsdk.antiaddiction.reactor.exceptions.OnCompletedFailedException;
import com.tapsdk.antiaddiction.reactor.exceptions.OnErrorFailedException;
import com.tapsdk.antiaddiction.reactor.exceptions.OnErrorNotImplementedException;
import com.tapsdk.antiaddiction.reactor.exceptions.UnsubscribeFailedException;
import com.tapsdk.antiaddiction.reactor.plugins.RxJavaHooks;
import com.tapsdk.antiaddiction.reactor.plugins.RxJavaPlugins;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class SafeSubscriber<T> extends Subscriber<T> {
    private final Subscriber<? super T> actual;
    boolean done;

    public SafeSubscriber(Subscriber<? super T> subscriber) {
        super(subscriber);
        this.actual = subscriber;
    }

    @Override // com.tapsdk.antiaddiction.reactor.Observer
    public void onCompleted() {
        if (this.done) {
            return;
        }
        this.done = true;
        try {
            this.actual.onCompleted();
            try {
                unsubscribe();
            } catch (Throwable th) {
                RxJavaHooks.onError(th);
                throw new UnsubscribeFailedException(th.getMessage(), th);
            }
        } catch (Throwable th2) {
            try {
                Exceptions.throwIfFatal(th2);
                RxJavaHooks.onError(th2);
                throw new OnCompletedFailedException(th2.getMessage(), th2);
            } catch (Throwable th3) {
                try {
                    unsubscribe();
                    throw th3;
                } catch (Throwable th4) {
                    RxJavaHooks.onError(th4);
                    throw new UnsubscribeFailedException(th4.getMessage(), th4);
                }
            }
        }
    }

    @Override // com.tapsdk.antiaddiction.reactor.Observer
    public void onError(Throwable th) {
        Exceptions.throwIfFatal(th);
        if (this.done) {
            return;
        }
        this.done = true;
        _onError(th);
    }

    @Override // com.tapsdk.antiaddiction.reactor.Observer
    public void onNext(T t) {
        try {
            if (this.done) {
                return;
            }
            this.actual.onNext(t);
        } catch (Throwable th) {
            Exceptions.throwOrReport(th, this);
        }
    }

    protected void _onError(Throwable th) {
        RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
        try {
            this.actual.onError(th);
            try {
                unsubscribe();
            } catch (Throwable th2) {
                RxJavaHooks.onError(th2);
                throw new OnErrorFailedException(th2);
            }
        } catch (OnErrorNotImplementedException e) {
            try {
                unsubscribe();
                throw e;
            } catch (Throwable th3) {
                RxJavaHooks.onError(th3);
                throw new OnErrorNotImplementedException("Observer.onError not implemented and error while unsubscribing.", new CompositeException(Arrays.asList(th, th3)));
            }
        } catch (Throwable th4) {
            RxJavaHooks.onError(th4);
            try {
                unsubscribe();
                throw new OnErrorFailedException("Error occurred when trying to propagate error to Observer.onError", new CompositeException(Arrays.asList(th, th4)));
            } catch (Throwable th5) {
                RxJavaHooks.onError(th5);
                throw new OnErrorFailedException("Error occurred when trying to propagate error to Observer.onError and during unsubscription.", new CompositeException(Arrays.asList(th, th4, th5)));
            }
        }
    }

    public Subscriber<? super T> getActual() {
        return this.actual;
    }
}
