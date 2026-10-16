package com.unity3d.player;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;

/* JADX INFO: renamed from: com.unity3d.player.s, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0063s extends CameraCaptureSession.StateCallback {
    final /* synthetic */ C0071w a;

    C0063s(C0071w c0071w) {
        this.a = c0071w;
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    public final void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
        AbstractC0077z.Log(6, "Camera2: CaptureSession configuration failed.");
    }

    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
    public void onConfigured(CameraCaptureSession cameraCaptureSession) {
        StringBuilder sbAppend;
        C0071w c0071w = this.a;
        if (c0071w.b == null) {
            return;
        }
        synchronized (c0071w.s) {
            C0071w c0071w2 = this.a;
            c0071w2.r = cameraCaptureSession;
            try {
                try {
                    c0071w2.q = c0071w2.b.createCaptureRequest(1);
                    C0071w c0071w3 = this.a;
                    c0071w3.q.addTarget(c0071w3.v);
                    C0071w c0071w4 = this.a;
                    c0071w4.q.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, c0071w4.n);
                    this.a.g();
                } catch (IllegalStateException e) {
                    sbAppend = new StringBuilder("Camera2: IllegalStateException ").append(e);
                    AbstractC0077z.Log(6, sbAppend.toString());
                }
            } catch (CameraAccessException e2) {
                sbAppend = new StringBuilder("Camera2: CameraAccessException ").append(e2);
                AbstractC0077z.Log(6, sbAppend.toString());
            }
        }
    }
}
