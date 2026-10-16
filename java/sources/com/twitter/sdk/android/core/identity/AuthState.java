package com.twitter.sdk.android.core.identity;

import android.app.Activity;
import com.google.android.gms.common.api.internal.zal$$ExternalSyntheticBackportWithForwarding0;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
class AuthState {
    final AtomicReference<AuthHandler> authHandlerRef = new AtomicReference<>(null);

    AuthState() {
    }

    public boolean beginAuthorize(Activity activity, AuthHandler authHandler) {
        if (isAuthorizeInProgress()) {
            Fabric.getLogger().w(TwitterCore.TAG, "Authorize already in progress");
        } else if (authHandler.authorize(activity)) {
            boolean zM = zal$$ExternalSyntheticBackportWithForwarding0.m(this.authHandlerRef, null, authHandler);
            if (zM) {
                return zM;
            }
            Fabric.getLogger().w(TwitterCore.TAG, "Failed to update authHandler, authorize already in progress.");
            return zM;
        }
        return false;
    }

    public void endAuthorize() {
        this.authHandlerRef.set(null);
    }

    public boolean isAuthorizeInProgress() {
        return this.authHandlerRef.get() != null;
    }

    public AuthHandler getAuthHandler() {
        return this.authHandlerRef.get();
    }
}
