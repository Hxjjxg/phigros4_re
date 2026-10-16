package com.google.android.gms.nearby.messages;

import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public final class SubscribeOptions {
    public static final SubscribeOptions DEFAULT = new Builder().build();
    private final Strategy zzfk;
    private final MessageFilter zzfz;
    private final SubscribeCallback zzga;
    public final boolean zzgb;
    public final int zzgc;

    public static class Builder {
        private Strategy zzfk = Strategy.DEFAULT;
        private MessageFilter zzfz = MessageFilter.INCLUDE_ALL_MY_TYPES;
        private SubscribeCallback zzga;

        public SubscribeOptions build() {
            return new SubscribeOptions(this.zzfk, this.zzfz, this.zzga);
        }

        public Builder setCallback(SubscribeCallback subscribeCallback) {
            this.zzga = (SubscribeCallback) Preconditions.checkNotNull(subscribeCallback);
            return this;
        }

        public Builder setFilter(MessageFilter messageFilter) {
            this.zzfz = messageFilter;
            return this;
        }

        public Builder setStrategy(Strategy strategy) {
            this.zzfk = strategy;
            return this;
        }
    }

    private SubscribeOptions(Strategy strategy, MessageFilter messageFilter, SubscribeCallback subscribeCallback, boolean z, int i) {
        this.zzfk = strategy;
        this.zzfz = messageFilter;
        this.zzga = subscribeCallback;
        this.zzgb = z;
        this.zzgc = i;
    }

    public final SubscribeCallback getCallback() {
        return this.zzga;
    }

    public final MessageFilter getFilter() {
        return this.zzfz;
    }

    public final Strategy getStrategy() {
        return this.zzfk;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzfk);
        String strValueOf2 = String.valueOf(this.zzfz);
        return new StringBuilder(String.valueOf(strValueOf).length() + 36 + String.valueOf(strValueOf2).length()).append("SubscribeOptions{strategy=").append(strValueOf).append(", filter=").append(strValueOf2).append('}').toString();
    }
}
