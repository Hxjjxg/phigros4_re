package com.twitter.sdk.android.tweetui;

import android.graphics.Color;

/* JADX INFO: loaded from: classes.dex */
final class ColorUtils {
    private ColorUtils() {
    }

    static int calculateOpacityTransform(double d, int i, int i2) {
        double d2 = 1.0d - d;
        return Color.rgb((int) ((((double) Color.red(i2)) * d2) + (((double) Color.red(i)) * d)), (int) ((((double) Color.green(i2)) * d2) + (((double) Color.green(i)) * d)), (int) ((d2 * ((double) Color.blue(i2))) + (d * ((double) Color.blue(i)))));
    }

    static boolean isLightColor(int i) {
        return ((((double) Color.red(i)) * 0.21d) + (((double) Color.green(i)) * 0.72d)) + (((double) Color.blue(i)) * 0.07d) > 128.0d;
    }
}
