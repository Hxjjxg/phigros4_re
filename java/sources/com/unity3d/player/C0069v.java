package com.unity3d.player;

import android.graphics.SurfaceTexture;

/* JADX INFO: renamed from: com.unity3d.player.v, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0069v implements SurfaceTexture.OnFrameAvailableListener {
    final /* synthetic */ C0071w a;

    C0069v(C0071w c0071w) {
        this.a = c0071w;
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
        ((Camera2Wrapper) this.a.a).a(surfaceTexture);
    }
}
