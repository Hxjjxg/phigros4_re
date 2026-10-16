package com.google.android.gms.nearby.messages.internal;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
final class zzaj implements Application.ActivityLifecycleCallbacks {
    private final Activity zzhm;
    private final zzah zzhn;

    private zzaj(Activity activity, zzah zzahVar) {
        this.zzhm = activity;
        this.zzhn = zzahVar;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        if (activity == this.zzhm) {
            if (Log.isLoggable("NearbyMessagesClient", 2)) {
                Log.v("NearbyMessagesClient", String.format("Unregistering ClientLifecycleSafetyNet's ActivityLifecycleCallbacks for %s", activity.getPackageName()));
            }
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
            try {
                this.zzhn.zzf(1);
            } catch (RemoteException e) {
                if (Log.isLoggable("NearbyMessagesClient", 2)) {
                    Log.v("NearbyMessagesClient", String.format("Failed to emit ACTIVITY_STOPPED from ClientLifecycleSafetyNet for Activity %s: %s", activity.getPackageName(), e));
                }
            }
        }
    }
}
