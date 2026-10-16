package com.tds.common.widgets.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
public class NetworkImageView extends ImageView {
    private int mDefaultImageId;
    private int mErrorImageId;
    private ImageTarget mImageTarget;
    private LoadBuilder mLoadBuilder;
    private TdsImage mTdsImage;
    private String mUrl;

    public NetworkImageView(Context context) {
        this(context, null);
    }

    public NetworkImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NetworkImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setImageUrl(String str) {
        String str2 = this.mUrl;
        if (str2 == null || !str2.equals(str)) {
            this.mUrl = str;
            if (str != null) {
                TdsImage tdsImage = TdsImage.get(getContext());
                this.mTdsImage = tdsImage;
                this.mLoadBuilder = tdsImage.load(str);
            }
            loadImageIfNecessary();
        }
    }

    public void setDefaultImageResId(int i) {
        this.mDefaultImageId = i;
    }

    public void setErrorImageResId(int i) {
        this.mErrorImageId = i;
    }

    void loadImageIfNecessary() {
        boolean z;
        boolean z2;
        TdsImage tdsImage;
        int width = getWidth();
        int height = getHeight();
        if (getLayoutParams() != null) {
            z = getLayoutParams().width == -2;
            z2 = getLayoutParams().height == -2;
        } else {
            z = false;
            z2 = false;
        }
        boolean z3 = z && z2;
        if (width == 0 && height == 0 && !z3) {
            return;
        }
        if (TextUtils.isEmpty(this.mUrl)) {
            ImageTarget imageTarget = this.mImageTarget;
            if (imageTarget != null && (tdsImage = this.mTdsImage) != null) {
                tdsImage.cancelRequest(imageTarget);
            }
            setDefaultImageOrNull();
            return;
        }
        initImageTarget();
        LoadBuilder loadBuilder = this.mLoadBuilder;
        if (loadBuilder != null) {
            loadBuilder.error(this.mErrorImageId).placeholder(this.mDefaultImageId).scale(width, height, getScaleType()).into(this.mImageTarget);
        }
    }

    private void resetBitmapSize(Bitmap bitmap) {
        boolean z;
        boolean z2;
        if (bitmap == null) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        if (getLayoutParams() != null) {
            z = getLayoutParams().width == -2;
            z2 = getLayoutParams().height == -2;
        } else {
            z = false;
            z2 = false;
        }
        if (z) {
            width = 0;
        }
        if (z2) {
            height = 0;
        }
        if (width == 0 && height == 0) {
            setImageBitmapInMain(bitmap);
            return;
        }
        int width2 = bitmap.getWidth();
        int height2 = bitmap.getHeight();
        setImageBitmapInMain(Bitmap.createScaledBitmap(bitmap, getResizedDimension(width, height, width2, height2, getScaleType()), getResizedDimension(height, width, height2, width2, getScaleType()), true));
    }

    private void setDefaultImageOrNull() {
        int i = this.mDefaultImageId;
        if (i != 0) {
            setImageResource(i);
        } else {
            setImageBitmap(null);
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        loadImageIfNecessary();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        TdsImage tdsImage = this.mTdsImage;
        if (tdsImage != null && this.mLoadBuilder != null) {
            tdsImage.cancelRequest(this);
            setImageBitmap(null);
        }
        super.onDetachedFromWindow();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    private void initImageTarget() {
        if (this.mImageTarget == null) {
            this.mImageTarget = new ImageTarget() { // from class: com.tds.common.widgets.image.NetworkImageView.1
                @Override // com.tds.common.widgets.image.ImageTarget
                public void onSuccess(Bitmap bitmap) {
                    if (bitmap != null) {
                        NetworkImageView.this.setImageBitmapInMain(bitmap);
                    } else {
                        onFailure(new RuntimeException("get bitmap from net error"));
                    }
                }

                @Override // com.tds.common.widgets.image.ImageTarget
                public void onFailure(Throwable th) {
                    if (NetworkImageView.this.mErrorImageId > 0) {
                        NetworkImageView networkImageView = NetworkImageView.this;
                        networkImageView.setImageResource(networkImageView.mErrorImageId);
                    }
                }
            };
        }
    }

    private int getResizedDimension(int i, int i2, int i3, int i4, ImageView.ScaleType scaleType) {
        if (i == 0 && i2 == 0) {
            return i3;
        }
        if (scaleType == ImageView.ScaleType.FIT_XY) {
            return i == 0 ? i3 : i;
        }
        if (i == 0) {
            return (int) (((double) i3) * (((double) i2) / ((double) i4)));
        }
        if (i2 == 0) {
            return i;
        }
        double d = ((double) i4) / ((double) i3);
        if (scaleType == ImageView.ScaleType.CENTER_CROP) {
            double d2 = i2;
            return ((double) i) * d < d2 ? (int) (d2 / d) : i;
        }
        double d3 = i2;
        return ((double) i) * d > d3 ? (int) (d3 / d) : i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImageBitmapInMain(final Bitmap bitmap) {
        if (bitmap != null) {
            post(new Runnable() { // from class: com.tds.common.widgets.image.NetworkImageView.2
                @Override // java.lang.Runnable
                public void run() {
                    NetworkImageView.this.setImageBitmap(bitmap);
                }
            });
        }
    }
}
