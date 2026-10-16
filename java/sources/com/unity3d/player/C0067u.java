package com.unity3d.player;

import android.media.Image;
import android.media.ImageReader;

/* JADX INFO: renamed from: com.unity3d.player.u, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0067u implements ImageReader.OnImageAvailableListener {
    final /* synthetic */ C0071w a;

    C0067u(C0071w c0071w) {
        this.a = c0071w;
    }

    @Override // android.media.ImageReader.OnImageAvailableListener
    public final void onImageAvailable(ImageReader imageReader) {
        if (C0071w.D.tryAcquire()) {
            Image imageAcquireNextImage = imageReader.acquireNextImage();
            if (imageAcquireNextImage != null) {
                Image.Plane[] planes = imageAcquireNextImage.getPlanes();
                if (imageAcquireNextImage.getFormat() == 35 && planes != null && planes.length == 3) {
                    ((Camera2Wrapper) this.a.a).a(planes[0].getBuffer(), planes[1].getBuffer(), planes[2].getBuffer(), planes[0].getRowStride(), planes[1].getRowStride(), planes[1].getPixelStride());
                } else {
                    AbstractC0077z.Log(6, "Camera2: Wrong image format.");
                }
                Image image = this.a.p;
                if (image != null) {
                    image.close();
                }
                this.a.p = imageAcquireNextImage;
            }
            C0071w.D.release();
        }
    }
}
