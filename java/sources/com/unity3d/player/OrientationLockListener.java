package com.unity3d.player;

import android.content.Context;
import android.provider.Settings;

/* JADX INFO: loaded from: classes.dex */
public class OrientationLockListener implements InterfaceC0034d0 {
    private C0038f0 a;
    private Context b;

    OrientationLockListener(Context context) {
        this.b = context;
        this.a = new C0038f0(context);
        nativeUpdateOrientationLockState(Settings.System.getInt(context.getContentResolver(), "accelerometer_rotation", 0));
        this.a.a(this);
    }

    public final void a() {
        this.a.a();
        this.a = null;
    }

    public final void b() {
        nativeUpdateOrientationLockState(Settings.System.getInt(this.b.getContentResolver(), "accelerometer_rotation", 0));
    }

    public final native void nativeUpdateOrientationLockState(int i);
}
