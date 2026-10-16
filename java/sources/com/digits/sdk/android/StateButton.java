package com.digits.sdk.android;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class StateButton extends RelativeLayout {
    int accentColor;
    ButtonThemer buttonThemer;
    CharSequence finishText;
    ImageView imageView;
    ProgressBar progressBar;
    CharSequence progressText;
    CharSequence startText;
    TextView textView;

    public StateButton(Context context) {
        this(context, null);
    }

    public StateButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StateButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.StateButton);
        init(typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
        this.accentColor = ThemeUtils.getAccentColor(getResources(), context.getTheme());
        ButtonThemer buttonThemer = new ButtonThemer(getResources());
        this.buttonThemer = buttonThemer;
        buttonThemer.setBackgroundAccentColor(this, this.accentColor);
        this.buttonThemer.setTextAccentColor(this.textView, this.accentColor);
        setImageAccentColor();
        setSpinnerAccentColor();
    }

    void setImageAccentColor() {
        this.imageView.setColorFilter(getTextColor(), PorterDuff.Mode.SRC_IN);
    }

    void setSpinnerAccentColor() {
        this.progressBar.setIndeterminateDrawable(getProgressDrawable());
    }

    int getTextColor() {
        return this.buttonThemer.getTextColor(this.accentColor);
    }

    Drawable getProgressDrawable() {
        Resources resources;
        int i;
        if (ThemeUtils.isLightColor(this.accentColor)) {
            resources = getResources();
            i = R.drawable.progress_dark;
        } else {
            resources = getResources();
            i = R.drawable.progress_light;
        }
        return resources.getDrawable(i);
    }

    public void setStatesText(int i, int i2, int i3) {
        Context context = getContext();
        this.startText = context.getString(i);
        this.progressText = context.getString(i2);
        this.finishText = context.getString(i3);
    }

    void init(TypedArray typedArray) {
        this.startText = typedArray.getText(R.styleable.StateButton_startStateText);
        this.progressText = typedArray.getText(R.styleable.StateButton_progressStateText);
        this.finishText = typedArray.getText(R.styleable.StateButton_finishStateText);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.dgts__state_button, this);
        this.textView = (TextView) findViewById(R.id.dgts__state_button);
        this.progressBar = (ProgressBar) findViewById(R.id.dgts__state_progress);
        this.imageView = (ImageView) findViewById(R.id.dgts__state_success);
        showStart();
    }

    public void showProgress() {
        this.textView.setText(this.progressText);
        this.progressBar.setVisibility(0);
        this.imageView.setVisibility(8);
    }

    public void showFinish() {
        this.textView.setText(this.finishText);
        this.progressBar.setVisibility(8);
        this.imageView.setVisibility(0);
    }

    public void showError() {
        showStart();
    }

    public void showStart() {
        this.textView.setText(this.startText);
        this.progressBar.setVisibility(8);
        this.imageView.setVisibility(8);
    }
}
