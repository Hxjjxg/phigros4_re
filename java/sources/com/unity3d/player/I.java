package com.unity3d.player;

import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
final class I implements Runnable {
    private IPermissionRequestCallbacks a;
    private String b;
    private int c;
    private boolean d;

    I(IPermissionRequestCallbacks iPermissionRequestCallbacks, String str, int i, boolean z) {
        this.a = iPermissionRequestCallbacks;
        this.b = str;
        this.c = i;
        this.d = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.c;
        if (i != -1) {
            if (i == 0) {
                this.a.onPermissionGranted(this.b);
            }
        } else if (Build.VERSION.SDK_INT >= 30 || this.d) {
            this.a.onPermissionDenied(this.b);
        } else {
            this.a.onPermissionDeniedAndDontAskAgain(this.b);
        }
    }
}
