package com.twitter.sdk.android.core;

import java.util.List;
import retrofit.client.Header;

/* JADX INFO: loaded from: classes.dex */
class TwitterRateLimit {
    private static final String LIMIT_KEY = "x-rate-limit-limit";
    private static final String REMAINING_KEY = "x-rate-limit-remaining";
    private static final String RESET_KEY = "x-rate-limit-reset";
    private int limit;
    private int remainingRequest;
    private long reset;
    private final long timeStamp;

    TwitterRateLimit(List<Header> list) {
        if (list == null) {
            throw new IllegalArgumentException("headers must not be null");
        }
        this.timeStamp = System.currentTimeMillis();
        for (Header header : list) {
            if (LIMIT_KEY.equals(header.getName())) {
                this.limit = Integer.valueOf(header.getValue()).intValue();
            } else if (REMAINING_KEY.equals(header.getName())) {
                this.remainingRequest = Integer.valueOf(header.getValue()).intValue();
            } else if (RESET_KEY.equals(header.getName())) {
                this.reset = Long.valueOf(header.getValue()).longValue();
            }
        }
    }

    public int getLimit() {
        return this.limit;
    }

    public int getRemaining() {
        return this.remainingRequest;
    }

    public long getReset() {
        return this.reset;
    }

    public long getRequestedTime() {
        return this.timeStamp;
    }

    public long getRemainingTime() {
        long j = this.reset;
        long j2 = this.timeStamp;
        if (j > j2) {
            return 0L;
        }
        return j - j2;
    }
}
