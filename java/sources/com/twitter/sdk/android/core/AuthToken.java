package com.twitter.sdk.android.core;

import com.twitter.sdk.android.core.internal.oauth.AuthHeaders;

/* JADX INFO: loaded from: classes.dex */
public abstract class AuthToken implements AuthHeaders {
    protected final long createdAt;

    public abstract boolean isExpired();

    public AuthToken() {
        this.createdAt = System.currentTimeMillis();
    }

    protected AuthToken(long j) {
        this.createdAt = j;
    }
}
