package com.digits.sdk.android;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class LinkTextView extends TextView {
    public LinkTextView(Context context) {
        super(context);
        init(context);
    }

    public LinkTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public LinkTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        setTextColor(getLinkColor(context));
    }

    private int getLinkColor(Context context) {
        TypedValue typedValueColor = ThemeUtils.getTypedValueColor(context.getTheme(), R.attr.textColorLink);
        if (typedValueColor == null) {
            return ThemeUtils.getAccentColor(context.getResources(), context.getTheme());
        }
        return typedValueColor.data;
    }
}
