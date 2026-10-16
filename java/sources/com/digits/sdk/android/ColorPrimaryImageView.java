package com.digits.sdk.android;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
public class ColorPrimaryImageView extends ImageView {
    public ColorPrimaryImageView(Context context) {
        super(context);
        init(context);
    }

    public ColorPrimaryImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ColorPrimaryImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        setColorFilter(getTextColorPrimary(context), PorterDuff.Mode.SRC_IN);
    }

    private int getTextColorPrimary(Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.textColorPrimary});
        int color = typedArrayObtainStyledAttributes.getColor(0, getResources().getColor(R.color.dgts__default_logo_name));
        typedArrayObtainStyledAttributes.recycle();
        return color;
    }
}
