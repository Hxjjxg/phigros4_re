package com.tapsdk.antiaddiction.reactor;

import com.tapsdk.antiaddiction.reactor.exceptions.Exceptions;
import com.tapsdk.antiaddiction.reactor.exceptions.OnErrorFailedException;
import com.tapsdk.antiaddiction.reactor.functions.Action0;
import com.tapsdk.antiaddiction.reactor.functions.Action1;
import com.tapsdk.antiaddiction.reactor.functions.Actions;
import com.tapsdk.antiaddiction.reactor.functions.Func1;
import com.tapsdk.antiaddiction.reactor.internal.operators.EmptyObservableHolder;
import com.tapsdk.antiaddiction.reactor.internal.operators.OnSubscribeLift;
import com.tapsdk.antiaddiction.reactor.internal.operators.OnSubscribeMap;
import com.tapsdk.antiaddiction.reactor.internal.operators.OnSubscribeThrow;
import com.tapsdk.antiaddiction.reactor.internal.operators.OperatorMerge;
import com.tapsdk.antiaddiction.reactor.internal.operators.OperatorObserveOn;
import com.tapsdk.antiaddiction.reactor.internal.operators.OperatorSubscribeOn;
import com.tapsdk.antiaddiction.reactor.observers.SafeSubscriber;
import com.tapsdk.antiaddiction.reactor.operators.OnSubscribeCreate;
import com.tapsdk.antiaddiction.reactor.plugins.RxJavaHooks;
import com.tapsdk.antiaddiction.reactor.schedulers.Scheduler;
import com.tapsdk.antiaddiction.reactor.subscriptions.Subscriptions;
import com.tapsdk.antiaddiction.reactor.util.ActionSubscriber;
import com.tapsdk.antiaddiction.reactor.util.InternalObservableUtils;

/* JADX INFO: loaded from: classes.dex */
public class Observable<T> {
    final OnSubscribe<T> onSubscribe;

    public interface OnSubscribe<T> extends Action1<Subscriber<? super T>> {
    }

    public interface Operator<R, T> extends Func1<Subscriber<? super R>, Subscriber<? super T>> {
    }

    public Observable(OnSubscribe<T> onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe) {
        return new Observable<>(onSubscribe);
    }

    public final Subscription subscribe(Subscriber<? super T> subscriber) {
        return subscribe(subscriber, this);
    }

    static <T> Subscription subscribe(Subscriber<? super T> subscriber, Observable<T> observable) {
        if (subscriber == null) {
            throw new IllegalArgumentException("subscriber can not be null");
        }
        if (observable.onSubscribe == null) {
            throw new IllegalStateException("onSubscribe function can not be null.");
        }
        subscriber.onStart();
        if (!(subscriber instanceof SafeSubscriber)) {
            subscriber = new SafeSubscriber(subscriber);
        }
        try {
            RxJavaHooks.onObservableStart(observable, observable.onSubscribe).call(subscriber);
            return RxJavaHooks.onObservableReturn(subscriber);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (subscriber.isUnsubscribed()) {
                RxJavaHooks.onError(RxJavaHooks.onObservableError(th));
            } else {
                try {
                    subscriber.onError(RxJavaHooks.onObservableError(th));
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    OnErrorFailedException onErrorFailedException = new OnErrorFailedException("Error occurred attempting to subscribe [" + th.getMessage() + "] and then again while trying to pass to onError.", th2);
                    RxJavaHooks.onObservableError(onErrorFailedException);
                    throw onErrorFailedException;
                }
            }
            return Subscriptions.unsubscribed();
        }
    }

    public final void subscribe() {
        subscribe(new ActionSubscriber(Actions.empty(), InternalObservableUtils.ERROR_NOT_IMPLEMENTED, Actions.empty()));
    }

    public final void subscribe(Action1<? super T> action1) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        subscribe(new ActionSubscriber(action1, InternalObservableUtils.ERROR_NOT_IMPLEMENTED, Actions.empty()));
    }

    public final Subscription subscribe(Action1<? super T> action1, Action1<Throwable> action2) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        if (action2 == null) {
            throw new IllegalArgumentException("onError can not be null");
        }
        return subscribe(new ActionSubscriber(action1, action2, Actions.empty()));
    }

    public final void subscribe(Action1<? super T> action1, Action1<Throwable> action2, Action0 action0) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        if (action2 == null) {
            throw new IllegalArgumentException("onError can not be null");
        }
        if (action0 == null) {
            throw new IllegalArgumentException("onComplete can not be null");
        }
        subscribe(new ActionSubscriber(action1, action2, action0));
    }

    public final Observable<T> subscribeOn(Scheduler scheduler) {
        return subscribeOn(scheduler, !(this.onSubscribe instanceof OnSubscribeCreate));
    }

    public final Observable<T> subscribeOn(Scheduler scheduler, boolean z) {
        return unsafeCreate(new OperatorSubscribeOn(this, scheduler, z));
    }

    public static <T> Observable<T> unsafeCreate(OnSubscribe<T> onSubscribe) {
        return new Observable<>(RxJavaHooks.onCreate(onSubscribe));
    }

    public final Subscription unsafeSubscribe(Subscriber<? super T> subscriber) {
        try {
            subscriber.onStart();
            RxJavaHooks.onObservableStart(this, this.onSubscribe).call(subscriber);
            return RxJavaHooks.onObservableReturn(subscriber);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            try {
                subscriber.onError(RxJavaHooks.onObservableError(th));
                return Subscriptions.unsubscribed();
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                OnErrorFailedException onErrorFailedException = new OnErrorFailedException("Error occurred attempting to subscribe [" + th.getMessage() + "] and then again while trying to pass to onError.", th2);
                RxJavaHooks.onObservableError(onErrorFailedException);
                throw onErrorFailedException;
            }
        }
    }

    public Observable<T> observeOn(Scheduler scheduler) {
        return observeOn(scheduler, 128);
    }

    public final Observable<T> observeOn(Scheduler scheduler, int i) {
        return observeOn(scheduler, false, i);
    }

    public final Observable<T> observeOn(Scheduler scheduler, boolean z, int i) {
        return (Observable<T>) lift(new OperatorObserveOn(scheduler, z, i));
    }

    public final <R> Observable<R> lift(Operator<? extends R, ? super T> operator) {
        return unsafeCreate(new OnSubscribeLift(this.onSubscribe, operator));
    }

    public static <T> Observable<T> error(Throwable th) {
        return unsafeCreate(new OnSubscribeThrow(th));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <R> Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends R>> func1) {
        return merge(map(func1));
    }

    public final <R> Observable<R> map(Func1<? super T, ? extends R> func1) {
        return unsafeCreate(new OnSubscribeMap(this, func1));
    }

    public static <T> Observable<T> merge(Observable<? extends Observable<? extends T>> observable) {
        return (Observable<T>) observable.lift(OperatorMerge.instance(false));
    }

    public static <T> Observable<T> empty() {
        return EmptyObservableHolder.instance();
    }
}
