package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
final class TargetAction extends Action<Target> {
    TargetAction(Picasso picasso, Target target, Request request, boolean z, int i, Drawable drawable, String str) {
        super(picasso, target, request, z, false, i, drawable, str);
    }

    @Override // com.squareup.picasso.Action
    void complete(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        if (bitmap == null) {
            throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", this));
        }
        Target target = getTarget();
        if (target != null) {
            target.onBitmapLoaded(bitmap, loadedFrom);
            if (bitmap.isRecycled()) {
                throw new IllegalStateException("Target callback must not recycle bitmap!");
            }
        }
    }

    @Override // com.squareup.picasso.Action
    void error() {
        Target target = getTarget();
        if (target != null) {
            if (this.errorResId != 0) {
                target.onBitmapFailed(this.picasso.context.getResources().getDrawable(this.errorResId));
            } else {
                target.onBitmapFailed(this.errorDrawable);
            }
        }
    }
}
