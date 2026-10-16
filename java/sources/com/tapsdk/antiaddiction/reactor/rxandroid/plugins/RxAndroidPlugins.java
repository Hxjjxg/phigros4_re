package com.tapsdk.antiaddiction.reactor.rxandroid.plugins;

import com.google.android.gms.common.api.internal.zal$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class RxAndroidPlugins {
    private static final RxAndroidPlugins INSTANCE = new RxAndroidPlugins();
    private final AtomicReference<RxAndroidSchedulersHook> schedulersHook = new AtomicReference<>();

    public static RxAndroidPlugins getInstance() {
        return INSTANCE;
    }

    RxAndroidPlugins() {
    }

    public void reset() {
        this.schedulersHook.set(null);
    }

    public RxAndroidSchedulersHook getSchedulersHook() {
        if (this.schedulersHook.get() == null) {
            zal$$ExternalSyntheticBackportWithForwarding0.m(this.schedulersHook, null, RxAndroidSchedulersHook.getDefaultInstance());
        }
        return this.schedulersHook.get();
    }

    public void registerSchedulersHook(RxAndroidSchedulersHook rxAndroidSchedulersHook) {
        if (!zal$$ExternalSyntheticBackportWithForwarding0.m(this.schedulersHook, null, rxAndroidSchedulersHook)) {
            throw new IllegalStateException("Another strategy was already registered: " + this.schedulersHook.get());
        }
    }
}
