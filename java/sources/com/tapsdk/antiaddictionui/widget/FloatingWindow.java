package com.tapsdk.antiaddictionui.widget;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import com.tds.common.utils.UIUtils;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class FloatingWindow {
    private final int ATTACH_STATE_ATTACHED;
    private final int ATTACH_STATE_ATTACHING;
    private final int ATTACH_STATE_DETACHED;
    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks;
    private volatile int attachState;
    private WeakReference<Activity> currentActivity;
    private WeakReference<Activity> destActivity;
    private View windowView;

    private FloatingWindow() {
        this.ATTACH_STATE_ATTACHING = 0;
        this.ATTACH_STATE_ATTACHED = 1;
        this.ATTACH_STATE_DETACHED = -1;
        this.attachState = -1;
    }

    public static FloatingWindow getInstance() {
        return Single.INSTANCE.floatingWindow;
    }

    public void showFloatingWindow(Activity activity, final String str) {
        if (activity == null || TextUtils.isEmpty(str) || this.attachState > -1) {
            return;
        }
        attachWindow(activity, str);
        if (this.activityLifecycleCallbacks == null) {
            this.activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() { // from class: com.tapsdk.antiaddictionui.widget.FloatingWindow.1
                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityCreated(Activity activity2, Bundle bundle) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityDestroyed(Activity activity2) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivitySaveInstanceState(Activity activity2, Bundle bundle) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStarted(Activity activity2) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStopped(Activity activity2) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityResumed(Activity activity2) {
                    FloatingWindow.this.attachWindow(activity2, str);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityPaused(Activity activity2) {
                    FloatingWindow.this.detachWindow(activity2);
                }
            };
        }
        activity.getApplication().registerActivityLifecycleCallbacks(this.activityLifecycleCallbacks);
    }

    public void removeWindow() {
        WeakReference<Activity> weakReference = this.currentActivity;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        if (this.activityLifecycleCallbacks != null) {
            this.currentActivity.get().getApplication().unregisterActivityLifecycleCallbacks(this.activityLifecycleCallbacks);
        }
        detachWindow(this.currentActivity.get());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attachWindow(final Activity activity, final String str) {
        if (activity == null || TextUtils.isEmpty(str) || this.attachState > -1) {
            return;
        }
        WeakReference<Activity> weakReference = this.currentActivity;
        if (weakReference == null || weakReference.get() == null || this.currentActivity.get() != activity) {
            this.attachState = 0;
            this.destActivity = new WeakReference<>(activity);
            activity.getWindow().getDecorView().post(new Runnable() { // from class: com.tapsdk.antiaddictionui.widget.FloatingWindow.2
                @Override // java.lang.Runnable
                public void run() {
                    if (FloatingWindow.this.attachState != 0) {
                        return;
                    }
                    if (FloatingWindow.this.currentActivity == null || FloatingWindow.this.currentActivity.get() == null || FloatingWindow.this.currentActivity.get() != activity) {
                        if (FloatingWindow.this.destActivity == null || FloatingWindow.this.destActivity.get() == null || FloatingWindow.this.destActivity.get() == activity) {
                            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                            layoutParams.flags = 58;
                            layoutParams.gravity = 8388661;
                            layoutParams.dimAmount = 0.0f;
                            layoutParams.format = -2;
                            layoutParams.type = 1003;
                            layoutParams.token = activity.getWindow().getDecorView().getWindowToken();
                            layoutParams.width = UIUtils.dp2px(activity, 100.0f);
                            layoutParams.height = UIUtils.dp2px(activity, 50.0f);
                            if (FloatingWindow.this.windowView == null) {
                                TextView textView = new TextView(activity);
                                textView.setTextColor(SupportMenu.CATEGORY_MASK);
                                textView.setText(str);
                                textView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                                FloatingWindow.this.windowView = textView;
                            }
                            activity.getWindowManager().addView(FloatingWindow.this.windowView, layoutParams);
                            FloatingWindow.this.currentActivity = new WeakReference(activity);
                            FloatingWindow.this.attachState = 1;
                            FloatingWindow.this.destActivity = null;
                        }
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void detachWindow(Activity activity) {
        if (activity == null || this.windowView == null || this.attachState < 0 || activity.isDestroyed()) {
            return;
        }
        this.attachState = -1;
        WeakReference<Activity> weakReference = this.currentActivity;
        if (weakReference != null && weakReference.get() != null && this.currentActivity.get() == activity) {
            activity.getWindowManager().removeViewImmediate(this.windowView);
        }
        this.currentActivity = null;
        this.destActivity = null;
    }

    enum Single {
        INSTANCE;

        final FloatingWindow floatingWindow = new FloatingWindow();

        Single() {
        }
    }
}
