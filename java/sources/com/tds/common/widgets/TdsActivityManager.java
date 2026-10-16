package com.tds.common.widgets;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TdsActivityManager {
    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks;
    private List<AppLifecycleCallback> appLifecycleCallbackList;
    private WeakReference<Activity> rootActivityReference;
    private static final List<WeakReference<Activity>> aliveActivityList = new ArrayList();
    private static final Object activityLock = new Object();
    private static volatile boolean isBackground = false;

    public interface AppLifecycleCallback {
        void onEnterBackground();

        void onEnterForeground();
    }

    public static TdsActivityManager getInstance() {
        return Single.INSTANCE.manager;
    }

    public Activity getCurrentActivity() {
        WeakReference<Activity> weakReference;
        List<WeakReference<Activity>> list = aliveActivityList;
        if (list.size() > 0 && (weakReference = list.get(list.size() - 1)) != null && weakReference.get() != null && !weakReference.get().isDestroyed()) {
            return weakReference.get();
        }
        WeakReference<Activity> weakReference2 = this.rootActivityReference;
        if (weakReference2 != null) {
            return weakReference2.get();
        }
        return null;
    }

    public void setRootActivity(Activity activity) {
        WeakReference<Activity> weakReference = this.rootActivityReference;
        if ((weakReference != null && weakReference.get() != null && !this.rootActivityReference.get().isDestroyed()) || activity == null || activity.isDestroyed()) {
            return;
        }
        this.rootActivityReference = new WeakReference<>(activity);
        registerCallback(activity.getApplication());
    }

    private void registerCallback(Application application) {
        if (this.activityLifecycleCallbacks == null) {
            Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() { // from class: com.tds.common.widgets.TdsActivityManager.1
                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityCreated(Activity activity, Bundle bundle) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityDestroyed(Activity activity) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStarted(Activity activity) {
                    TdsActivityManager.this.handleAppActive(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityResumed(Activity activity) {
                    TdsActivityManager.this.handleAppActive(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityPaused(Activity activity) {
                    TdsActivityManager.this.handleAppActive(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStopped(Activity activity) {
                    if (TdsActivityManager.checkActivityExist(activity, true) && TdsActivityManager.aliveActivityList.size() == 0 && !TdsActivityManager.isBackground) {
                        boolean unused = TdsActivityManager.isBackground = true;
                        TdsActivityManager.this.notifyAppLifecycleCallback();
                    }
                }
            };
            this.activityLifecycleCallbacks = activityLifecycleCallbacks;
            application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Bottom block not found for handler: all -> 0x002b */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x002f, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void handleAppActive(android.app.Activity r5) {
        /*
            r4 = this;
            if (r5 != 0) goto L3
            return
        L3:
            java.lang.Object r0 = com.tds.common.widgets.TdsActivityManager.activityLock     // Catch: java.lang.Throwable -> L2b
            monitor-enter(r0)     // Catch: java.lang.Throwable -> L2b
            java.util.List<java.lang.ref.WeakReference<android.app.Activity>> r1 = com.tds.common.widgets.TdsActivityManager.aliveActivityList     // Catch: java.lang.Throwable -> L28
            int r2 = r1.size()     // Catch: java.lang.Throwable -> L28
            r3 = 0
            if (r2 != 0) goto L18
            boolean r2 = com.tds.common.widgets.TdsActivityManager.isBackground     // Catch: java.lang.Throwable -> L28
            if (r2 == 0) goto L18
            com.tds.common.widgets.TdsActivityManager.isBackground = r3     // Catch: java.lang.Throwable -> L28
            r4.notifyAppLifecycleCallback()     // Catch: java.lang.Throwable -> L28
        L18:
            boolean r2 = checkActivityExist(r5, r3)     // Catch: java.lang.Throwable -> L28
            if (r2 != 0) goto L26
            java.lang.ref.WeakReference r2 = new java.lang.ref.WeakReference     // Catch: java.lang.Throwable -> L28
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L28
            r1.add(r2)     // Catch: java.lang.Throwable -> L28
        L26:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L28
            goto L2f
        L28:
            r5 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L28
            throw r5     // Catch: java.lang.Throwable -> L2b
        L2b:
            r5 = move-exception
            r5.printStackTrace()
        L2f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.common.widgets.TdsActivityManager.handleAppActive(android.app.Activity):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean checkActivityExist(Activity activity, boolean z) {
        try {
            synchronized (activityLock) {
                Iterator<WeakReference<Activity>> it = aliveActivityList.iterator();
                while (it.hasNext()) {
                    if (it.next().get() == activity) {
                        if (z) {
                            it.remove();
                        }
                        return true;
                    }
                }
                return false;
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public void registerAppLifecycleCallback(AppLifecycleCallback appLifecycleCallback) {
        if (this.appLifecycleCallbackList == null) {
            this.appLifecycleCallbackList = new ArrayList();
        }
        this.appLifecycleCallbackList.add(appLifecycleCallback);
    }

    public void unregisterAppLifecycleCallback(AppLifecycleCallback appLifecycleCallback) {
        List<AppLifecycleCallback> list = this.appLifecycleCallbackList;
        if (list != null) {
            list.remove(appLifecycleCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyAppLifecycleCallback() {
        List<AppLifecycleCallback> list = this.appLifecycleCallbackList;
        if (list != null) {
            for (AppLifecycleCallback appLifecycleCallback : list) {
                if (isBackground) {
                    appLifecycleCallback.onEnterBackground();
                } else {
                    appLifecycleCallback.onEnterForeground();
                }
            }
        }
    }

    enum Single {
        INSTANCE;

        TdsActivityManager manager = new TdsActivityManager();

        Single() {
        }
    }
}
