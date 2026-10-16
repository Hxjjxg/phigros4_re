package com.unity3d.player;

import android.hardware.camera2.CameraDevice;

/* JADX INFO: renamed from: com.unity3d.player.t, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0065t extends CameraDevice.StateCallback {
    final /* synthetic */ C0071w a;

    C0065t(C0071w c0071w) {
        this.a = c0071w;
    }

    @Override // android.hardware.camera2.CameraDevice.StateCallback
    public final void onClosed(CameraDevice cameraDevice) {
        C0071w.D.release();
    }

    @Override // android.hardware.camera2.CameraDevice.StateCallback
    public final void onDisconnected(CameraDevice cameraDevice) {
        AbstractC0077z.Log(5, "Camera2: CameraDevice disconnected.");
        this.a.a(cameraDevice);
        C0071w.D.release();
    }

    @Override // android.hardware.camera2.CameraDevice.StateCallback
    public final void onError(CameraDevice cameraDevice, int i) {
        AbstractC0077z.Log(6, "Camera2: Error opeining CameraDevice " + i);
        this.a.a(cameraDevice);
        C0071w.D.release();
    }

    @Override // android.hardware.camera2.CameraDevice.StateCallback
    public final void onOpened(CameraDevice cameraDevice) {
        this.a.b = cameraDevice;
        C0071w.D.release();
    }
}
