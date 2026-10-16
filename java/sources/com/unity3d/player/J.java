package com.unity3d.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.PixelCopy;
import android.view.SurfaceView;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class J extends View implements PixelCopy.OnPixelCopyFinishedListener {
    Bitmap a;

    J(Context context) {
        super(context);
    }

    public final void a(SurfaceView surfaceView) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(surfaceView.getWidth(), surfaceView.getHeight(), Bitmap.Config.ARGB_8888);
        this.a = bitmapCreateBitmap;
        PixelCopy.request(surfaceView, bitmapCreateBitmap, this, new Handler(Looper.getMainLooper()));
    }

    @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
    public final void onPixelCopyFinished(int i) {
        if (i == 0) {
            setBackground(new LayerDrawable(new Drawable[]{new ColorDrawable(-16777216), new BitmapDrawable(getResources(), this.a)}));
        }
    }
}
