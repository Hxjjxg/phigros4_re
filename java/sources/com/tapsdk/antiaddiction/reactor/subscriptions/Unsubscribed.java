package com.tapsdk.antiaddiction.reactor.subscriptions;

import com.tapsdk.antiaddiction.reactor.Subscription;

/* JADX INFO: loaded from: classes.dex */
public enum Unsubscribed implements Subscription {
    INSTANCE;

    @Override // com.tapsdk.antiaddiction.reactor.Subscription
    public boolean isUnsubscribed() {
        return true;
    }

    @Override // com.tapsdk.antiaddiction.reactor.Subscription
    public void unsubscribe() {
    }
}
