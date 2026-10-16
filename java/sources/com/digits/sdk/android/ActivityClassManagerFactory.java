package com.digits.sdk.android;

import android.content.Context;
import android.content.res.TypedArray;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
class ActivityClassManagerFactory {
    ActivityClassManagerFactory() {
    }

    ActivityClassManager createActivityClassManager(Context context, int i) {
        try {
            Class.forName("androidx.appcompat.app.ActionBarActivity");
            if (isAppCompatTheme(context, i, new ThemeAttributes())) {
                return new AppCompatClassManagerImp();
            }
            return new ActivityClassManagerImp();
        } catch (Exception unused) {
            return new ActivityClassManagerImp();
        }
    }

    boolean isAppCompatTheme(Context context, int i, ThemeAttributes themeAttributes) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(i, themeAttributes.styleableTheme);
        boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(themeAttributes.styleableThemeWindowActionBar);
        typedArrayObtainStyledAttributes.recycle();
        return zHasValue;
    }

    static class ThemeAttributes {
        private static final String CLASS_NAME = "androidx.appcompat.R$styleable";
        private final int[] styleableTheme;
        private final int styleableThemeWindowActionBar;

        public ThemeAttributes() throws Exception {
            Class<?> cls = Class.forName(CLASS_NAME);
            Field field = cls.getField("Theme");
            this.styleableTheme = (int[]) field.get(field.getType());
            Field field2 = cls.getField("Theme_windowActionBar");
            this.styleableThemeWindowActionBar = ((Integer) field2.get(field2.getType())).intValue();
        }
    }
}
