package com.unity3d.player;

import android.view.SurfaceHolder;

/* JADX INFO: renamed from: com.unity3d.player.h0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class SurfaceHolderCallbackC0042h0 implements SurfaceHolder.Callback {
    final /* synthetic */ C0044i0 a;

    SurfaceHolderCallbackC0042h0(C0044i0 c0044i0) {
        this.a = c0044i0;
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.a.b.updateGLDisplay(0, surfaceHolder.getSurface());
        this.a.b.sendSurfaceChangedEvent();
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.a.b.updateGLDisplay(0, surfaceHolder.getSurface());
        C0044i0 c0044i0 = this.a;
        K k = c0044i0.c;
        UnityPlayer unityPlayer = c0044i0.b;
        J j = k.b;
        if (j == null || j.getParent() != null) {
            return;
        }
        unityPlayer.addView(k.b);
        unityPlayer.bringChildToFront(k.b);
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        C0044i0 c0044i0 = this.a;
        K k = c0044i0.c;
        C0037f c0037f = c0044i0.a;
        k.getClass();
        if (PlatformSupport.NOUGAT_SUPPORT && k.a != null) {
            if (k.b == null) {
                k.b = new J(k.a);
            }
            k.b.a(c0037f);
        }
        this.a.b.updateGLDisplay(0, null);
    }
}
