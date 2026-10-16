package com.twitter.sdk.android.tweetui.internal.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.twitter.sdk.android.tweetui.R;

/* JADX INFO: loaded from: classes.dex */
public class AspectRatioImageView extends ImageView {
    static final int ADJUST_DIMENSION_HEIGHT = 0;
    static final int ADJUST_DIMENSION_WIDTH = 1;
    private static final int DEFAULT_ADJUST_DIMENSION = 0;
    private static final float DEFAULT_ASPECT_RATIO = 1.0f;
    private float aspectRatio;
    private int dimensionToAdjust;

    public AspectRatioImageView(Context context) {
        this(context, null);
    }

    public AspectRatioImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.tw__AspectRatioImageView);
        try {
            this.aspectRatio = typedArrayObtainStyledAttributes.getFloat(R.styleable.tw__AspectRatioImageView_tw__image_aspect_ratio, DEFAULT_ASPECT_RATIO);
            this.dimensionToAdjust = typedArrayObtainStyledAttributes.getInt(R.styleable.tw__AspectRatioImageView_tw__image_dimension_to_adjust, 0);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (this.dimensionToAdjust == 0) {
            measuredHeight = (int) (measuredWidth / this.aspectRatio);
        } else {
            measuredWidth = (int) (measuredHeight * this.aspectRatio);
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    public float getAspectRatio() {
        return this.aspectRatio;
    }

    public int getDimensionToAdjust() {
        return this.dimensionToAdjust;
    }
}
