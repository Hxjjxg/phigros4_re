package com.digits.sdk.android;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/* JADX INFO: loaded from: classes.dex */
public class InvertedAccentButton extends Button {
    public InvertedAccentButton(Context context) {
        this(context, null);
    }

    public InvertedAccentButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.buttonStyle);
    }

    public InvertedAccentButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    void init() {
        int accentColor = ThemeUtils.getAccentColor(getResources(), getContext().getTheme());
        ButtonThemer buttonThemer = new ButtonThemer(getResources());
        buttonThemer.disableDropShadow(this);
        buttonThemer.setBackgroundAccentColorInverse(this, accentColor);
        buttonThemer.setTextAccentColorInverse(this, accentColor);
    }
}
