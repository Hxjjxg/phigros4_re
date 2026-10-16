package com.tapsdk.antiaddictionui.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/* JADX INFO: loaded from: classes.dex */
public class UIUtil {
    public static int dp2pxWithScale(float f, float f2) {
        return (int) (f2 * f);
    }

    public static int dp2px(Context context, float f) {
        return (int) (f * context.getResources().getDisplayMetrics().density);
    }

    public static int sp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int[] getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }
}
