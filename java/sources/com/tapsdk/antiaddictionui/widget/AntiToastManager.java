package com.tapsdk.antiaddictionui.widget;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.tapsdk.antiaddiction.utils.AntiAddictionLogger;
import com.tapsdk.antiaddictionui.R;
import com.tapsdk.antiaddictionui.utils.ActivityUtils;

/* JADX INFO: loaded from: classes.dex */
public enum AntiToastManager {
    INSTANCE;

    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;
    private static final String TAG = "AntiToastManager";
    private static final int TIME_OUT = 15000;
    private static Handler handler;
    private static HandlerThread handlerThread;
    private AntiAddictionProgressToast mToast;
    private volatile boolean show;

    public static AntiToastManager instance() {
        synchronized (AntiToastManager.class) {
            initHandler();
        }
        return INSTANCE;
    }

    private static void initHandler() {
        if (handlerThread == null) {
            HandlerThread handlerThread2 = new HandlerThread(TAG, -4);
            handlerThread = handlerThread2;
            handlerThread2.start();
            handler = new Handler(handlerThread.getLooper());
        }
    }

    public void showLoading(Activity activity) {
        if (ActivityUtils.isActivityNotAlive(activity)) {
            return;
        }
        showLoadingActually(activity, TIME_OUT);
    }

    public void showLoading(Activity activity, int i) {
        if (ActivityUtils.isActivityNotAlive(activity)) {
            return;
        }
        showLoadingActually(activity, i);
    }

    void resetToastReference(final AntiAddictionProgressToast antiAddictionProgressToast) {
        handler.post(new Runnable() { // from class: com.tapsdk.antiaddictionui.widget.AntiToastManager.1
            @Override // java.lang.Runnable
            public void run() {
                AntiAddictionProgressToast antiAddictionProgressToast2 = antiAddictionProgressToast;
                if (antiAddictionProgressToast2 != null) {
                    AntiToastManager.this.mToast = antiAddictionProgressToast2;
                }
            }
        });
    }

    private void showLoadingActually(final Activity activity, int i) {
        if (activity == null) {
            return;
        }
        handler.post(new Runnable() { // from class: com.tapsdk.antiaddictionui.widget.AntiToastManager.2
            @Override // java.lang.Runnable
            public void run() {
                if (AntiToastManager.this.show && AntiToastManager.this.mToast != null) {
                    AntiToastManager.this.mToast.dismissAllowingStateLoss();
                }
                AntiToastManager.this.mToast = AntiAddictionProgressToast.newInstance();
                try {
                    AntiToastManager.this.mToast.show(activity.getFragmentManager(), AntiAddictionProgressToast.TAG);
                } catch (Exception e) {
                    AntiAddictionLogger.e(e.getMessage());
                }
                AntiToastManager.this.show = true;
            }
        });
        if (i != Integer.MAX_VALUE) {
            handler.postDelayed(new Runnable() { // from class: com.tapsdk.antiaddictionui.widget.AntiToastManager.3
                @Override // java.lang.Runnable
                public void run() {
                    AntiToastManager.this.dismissLoading();
                }
            }, i);
        }
    }

    public void show(Activity activity, String str, int i) {
        Toast toast = new Toast(activity);
        View viewInflate = LayoutInflater.from(activity).inflate(R.layout.antiaddictionui_view_toast_message, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.tv_toast_message)).setText(str);
        toast.setView(viewInflate);
        if (i == 0) {
            toast.setDuration(0);
        } else if (i == 1) {
            toast.setDuration(1);
        }
        toast.setGravity(17, 0, 0);
        toast.show();
    }

    public void dismissLoading() {
        Handler handler2 = handler;
        if (handler2 == null) {
            return;
        }
        handler2.post(new Runnable() { // from class: com.tapsdk.antiaddictionui.widget.AntiToastManager.4
            @Override // java.lang.Runnable
            public void run() {
                if (AntiToastManager.this.show) {
                    try {
                        AntiToastManager.this.mToast.dismissAllowingStateLoss();
                        AntiToastManager.this.mToast = null;
                    } catch (Exception e) {
                        AntiAddictionLogger.e(e.getMessage());
                    }
                }
                AntiToastManager.this.show = false;
            }
        });
    }
}
