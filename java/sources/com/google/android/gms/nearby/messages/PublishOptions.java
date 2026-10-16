package com.google.android.gms.nearby.messages;

import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public final class PublishOptions {
    public static final PublishOptions DEFAULT = new Builder().build();
    private final Strategy zzfk;
    private final PublishCallback zzfl;

    public static class Builder {
        private Strategy zzfk = Strategy.DEFAULT;
        private PublishCallback zzfl;

        public PublishOptions build() {
            return new PublishOptions(this.zzfk, this.zzfl);
        }

        public Builder setCallback(PublishCallback publishCallback) {
            this.zzfl = (PublishCallback) Preconditions.checkNotNull(publishCallback);
            return this;
        }

        public Builder setStrategy(Strategy strategy) {
            this.zzfk = (Strategy) Preconditions.checkNotNull(strategy);
            return this;
        }
    }

    private PublishOptions(Strategy strategy, PublishCallback publishCallback) {
        this.zzfk = strategy;
        this.zzfl = publishCallback;
    }

    public final PublishCallback getCallback() {
        return this.zzfl;
    }

    public final Strategy getStrategy() {
        return this.zzfk;
    }
}
