package com.digits.sdk.android;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
public class AccentImageView extends ImageView {
    public AccentImageView(Context context) {
        super(context);
        init(context);
    }

    public AccentImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AccentImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        setColorFilter(ThemeUtils.getAccentColor(getResources(), context.getTheme()), PorterDuff.Mode.SRC_IN);
    }
}
