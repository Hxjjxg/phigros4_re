package com.unity3d.player;

import android.app.Activity;
import android.app.Dialog;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

/* JADX INFO: renamed from: com.unity3d.player.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
abstract class AbstractC0035e {
    public static OnBackInvokedDispatcher a(Object obj) {
        if (obj instanceof Activity) {
            return ((Activity) obj).getOnBackInvokedDispatcher();
        }
        if (obj instanceof Dialog) {
            return ((Dialog) obj).getOnBackInvokedDispatcher();
        }
        throw new IllegalArgumentException("Unsupported context type when getting '" + obj.getClass().getName() + "' OnBackInvokedDispatcher");
    }

    public static void a(OnBackInvokedDispatcher onBackInvokedDispatcher, int i, OnBackInvokedCallback onBackInvokedCallback) {
        onBackInvokedDispatcher.registerOnBackInvokedCallback(i, onBackInvokedCallback);
    }

    public static void a(OnBackInvokedDispatcher onBackInvokedDispatcher, OnBackInvokedCallback onBackInvokedCallback) {
        onBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback);
    }
}
