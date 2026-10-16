package com.digits.sdk.android;

import android.R;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
class ThemeUtils {
    public static final int DEFAULT_THEME = 0;
    public static final String THEME_RESOURCE_ID = "THEME_RESOURCE_ID";

    private ThemeUtils() {
    }

    static TypedValue getTypedValueColor(Resources.Theme theme, int i) {
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(i, typedValue, true);
        if (typedValue.type < 28 || typedValue.type > 31) {
            return null;
        }
        return typedValue;
    }

    static int getAccentColor(Resources resources, Resources.Theme theme) {
        TypedValue typedValueColor = getTypedValueColor(theme, R.attr.dgts__accentColor);
        if (typedValueColor != null) {
            return typedValueColor.data;
        }
        TypedValue typedValueColor2 = getTypedValueColor(theme, R.attr.colorAccent);
        if (typedValueColor2 != null) {
            return typedValueColor2.data;
        }
        try {
            Field declaredField = R.attr.class.getDeclaredField("colorAccent");
            TypedValue typedValueColor3 = getTypedValueColor(theme, declaredField.getInt(declaredField.getType()));
            if (typedValueColor3 != null) {
                return typedValueColor3.data;
            }
        } catch (Exception unused) {
        }
        return resources.getColor(R.color.dgts__default_accent);
    }

    static boolean isLightColor(int i) {
        return ((((double) Color.red(i)) * 0.21d) + (((double) Color.green(i)) * 0.72d)) + (((double) Color.blue(i)) * 0.07d) > 170.0d;
    }

    static int calculateOpacityTransform(double d, int i, int i2) {
        double d2 = 1.0d - d;
        return Color.rgb((int) ((((double) Color.red(i2)) * d2) + (((double) Color.red(i)) * d)), (int) ((((double) Color.green(i2)) * d2) + (((double) Color.green(i)) * d)), (int) ((d2 * ((double) Color.blue(i2))) + (d * ((double) Color.blue(i)))));
    }
}
