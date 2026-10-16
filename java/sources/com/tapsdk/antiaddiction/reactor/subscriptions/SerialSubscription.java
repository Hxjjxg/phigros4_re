package com.tapsdk.antiaddiction.reactor.subscriptions;

import com.tapsdk.antiaddiction.reactor.Subscription;

/* JADX INFO: loaded from: classes.dex */
public final class SerialSubscription implements Subscription {
    final SequentialSubscription state = new SequentialSubscription();

    @Override // com.tapsdk.antiaddiction.reactor.Subscription
    public boolean isUnsubscribed() {
        return this.state.isUnsubscribed();
    }

    @Override // com.tapsdk.antiaddiction.reactor.Subscription
    public void unsubscribe() {
        this.state.unsubscribe();
    }

    public void set(Subscription subscription) {
        if (subscription == null) {
            throw new IllegalArgumentException("Subscription can not be null");
        }
        this.state.update(subscription);
    }

    public Subscription get() {
        return this.state.current();
    }
}
