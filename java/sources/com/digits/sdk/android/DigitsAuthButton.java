package com.digits.sdk.android;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/* JADX INFO: loaded from: classes.dex */
public class DigitsAuthButton extends Button implements View.OnClickListener {
    private AuthCallback callback;
    volatile DigitsClient digitsClient;
    private View.OnClickListener onClickListener;

    public DigitsAuthButton(Context context) {
        this(context, null);
    }

    public DigitsAuthButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.buttonStyle);
    }

    public DigitsAuthButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setUpButton();
        super.setOnClickListener(this);
    }

    private void setUpButton() {
        Resources resources = getResources();
        setCompoundDrawablePadding(resources.getDimensionPixelSize(R.dimen.tw__login_btn_drawable_padding));
        setText(R.string.dgts__login_digits_text);
        setTextColor(resources.getColor(R.color.tw__solid_white));
        setTextSize(0, resources.getDimensionPixelSize(R.dimen.tw__login_btn_text_size));
        setTypeface(Typeface.DEFAULT_BOLD);
        setPadding(resources.getDimensionPixelSize(R.dimen.tw__login_btn_right_padding), 0, resources.getDimensionPixelSize(R.dimen.tw__login_btn_right_padding), 0);
        setBackgroundResource(R.drawable.dgts__digits_btn);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.callback == null) {
            throw new IllegalArgumentException("AuthCallback must not be null");
        }
        getDigitsClient().startSignUp(this.callback);
        View.OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    public void setCallback(AuthCallback authCallback) {
        this.callback = authCallback;
    }

    public void setAuthTheme(int i) {
        getDigits().setTheme(i);
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    protected DigitsClient getDigitsClient() {
        if (this.digitsClient == null) {
            synchronized (DigitsClient.class) {
                if (this.digitsClient == null) {
                    this.digitsClient = getDigits().getDigitsClient();
                }
            }
        }
        return this.digitsClient;
    }

    protected Digits getDigits() {
        return Digits.getInstance();
    }
}
