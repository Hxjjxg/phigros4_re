package com.digits.sdk.android;

import android.R;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
class ButtonThemer {
    final Resources resources;

    public ButtonThemer(Resources resources) {
        this.resources = resources;
    }

    void setBackgroundAccentColor(View view, int i) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        float fApplyDimension = TypedValue.applyDimension(1, 5.0f, this.resources.getDisplayMetrics());
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(fApplyDimension);
        gradientDrawable.setColor(getPressedColor(i));
        stateListDrawable.addState(new int[]{R.attr.state_focused, R.attr.state_pressed}, gradientDrawable);
        stateListDrawable.addState(new int[]{-16842908, R.attr.state_pressed}, gradientDrawable);
        stateListDrawable.addState(new int[]{R.attr.state_focused}, gradientDrawable);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor(i);
        gradientDrawable2.setCornerRadius(fApplyDimension);
        stateListDrawable.addState(StateSet.WILD_CARD, gradientDrawable2);
        view.setBackground(stateListDrawable);
    }

    void setBackgroundAccentColorInverse(View view, int i) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        float fApplyDimension = TypedValue.applyDimension(1, 5.0f, this.resources.getDisplayMetrics());
        float fApplyDimension2 = TypedValue.applyDimension(1, 2.0f, this.resources.getDisplayMetrics());
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(fApplyDimension);
        int i2 = (int) fApplyDimension2;
        gradientDrawable.setStroke(i2, getPressedColor(i));
        stateListDrawable.addState(new int[]{R.attr.state_focused, R.attr.state_pressed}, gradientDrawable);
        stateListDrawable.addState(new int[]{-16842908, R.attr.state_pressed}, gradientDrawable);
        stateListDrawable.addState(new int[]{R.attr.state_focused}, gradientDrawable);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setCornerRadius(fApplyDimension);
        gradientDrawable2.setStroke(i2, i);
        stateListDrawable.addState(StateSet.WILD_CARD, gradientDrawable2);
        view.setBackground(stateListDrawable);
    }

    int getPressedColor(int i) {
        if (ThemeUtils.isLightColor(i)) {
            return ThemeUtils.calculateOpacityTransform(0.2d, -16777216, i);
        }
        return ThemeUtils.calculateOpacityTransform(0.2d, -1, i);
    }

    void setTextAccentColor(TextView textView, int i) {
        textView.setTextColor(getTextColor(i));
    }

    void setTextAccentColorInverse(TextView textView, int i) {
        int pressedColor = getPressedColor(i);
        textView.setTextColor(new ColorStateList(new int[][]{new int[]{R.attr.state_focused, -16842919}, new int[]{R.attr.state_focused, R.attr.state_pressed}, new int[]{-16842908, R.attr.state_pressed}, StateSet.WILD_CARD}, new int[]{i, pressedColor, pressedColor, i}));
    }

    int getTextColor(int i) {
        Resources resources;
        int i2;
        if (ThemeUtils.isLightColor(i)) {
            resources = this.resources;
            i2 = R.color.dgts__text_dark;
        } else {
            resources = this.resources;
            i2 = R.color.dgts__text_light;
        }
        return resources.getColor(i2);
    }

    void disableDropShadow(View view) {
        view.setStateListAnimator(null);
        view.setElevation(0.0f);
    }
}
