package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
class ImageViewAction extends Action<ImageView> {
    Callback callback;

    ImageViewAction(Picasso picasso, ImageView imageView, Request request, boolean z, boolean z2, int i, Drawable drawable, String str, Callback callback) {
        super(picasso, imageView, request, z, z2, i, drawable, str);
        this.callback = callback;
    }

    @Override // com.squareup.picasso.Action
    public void complete(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        if (bitmap == null) {
            throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", this));
        }
        ImageView imageView = (ImageView) this.target.get();
        if (imageView == null) {
            return;
        }
        PicassoDrawable.setBitmap(imageView, this.picasso.context, bitmap, loadedFrom, this.noFade, this.picasso.indicatorsEnabled);
        Callback callback = this.callback;
        if (callback != null) {
            callback.onSuccess();
        }
    }

    @Override // com.squareup.picasso.Action
    public void error() {
        ImageView imageView = (ImageView) this.target.get();
        if (imageView == null) {
            return;
        }
        if (this.errorResId != 0) {
            imageView.setImageResource(this.errorResId);
        } else if (this.errorDrawable != null) {
            imageView.setImageDrawable(this.errorDrawable);
        }
        Callback callback = this.callback;
        if (callback != null) {
            callback.onError();
        }
    }

    @Override // com.squareup.picasso.Action
    void cancel() {
        super.cancel();
        if (this.callback != null) {
            this.callback = null;
        }
    }
}
