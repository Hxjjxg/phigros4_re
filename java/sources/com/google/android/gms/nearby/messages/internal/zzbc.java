package com.google.android.gms.nearby.messages.internal;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
final class zzbc implements Application.ActivityLifecycleCallbacks {
    private final Activity zzhm;
    private final zzak zzig;

    private zzbc(Activity activity, zzak zzakVar) {
        this.zzhm = activity;
        this.zzig = zzakVar;
    }

    /* synthetic */ zzbc(Activity activity, zzak zzakVar, zzau zzauVar) {
        this(activity, zzakVar);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        if (activity == this.zzhm) {
            Log.v("NearbyMessages", String.format("Unregistering ClientLifecycleSafetyNet's ActivityLifecycleCallbacks for %s", activity.getPackageName()));
            activity.getApplication().unregisterActivityLifecycleCallbacks(this);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
        if (activity == this.zzhm) {
            this.zzig.zzf(1);
        }
    }
}
