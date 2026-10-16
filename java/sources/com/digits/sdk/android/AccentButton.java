package com.digits.sdk.android;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/* JADX INFO: loaded from: classes.dex */
public class AccentButton extends Button {
    public AccentButton(Context context) {
        this(context, null);
    }

    public AccentButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.buttonStyle);
    }

    public AccentButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    void init() {
        int accentColor = ThemeUtils.getAccentColor(getResources(), getContext().getTheme());
        ButtonThemer buttonThemer = new ButtonThemer(getResources());
        buttonThemer.disableDropShadow(this);
        buttonThemer.setBackgroundAccentColor(this, accentColor);
        buttonThemer.setTextAccentColor(this, accentColor);
    }
}
