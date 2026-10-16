package com.unity3d.player;

import android.os.SystemClock;
import android.view.KeyEvent;

/* JADX INFO: loaded from: classes.dex */
final class H0 implements Runnable {
    final /* synthetic */ UnityPlayer a;

    H0(UnityPlayer unityPlayer) {
        this.a = unityPlayer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        long jUptimeMillis = SystemClock.uptimeMillis();
        KeyEvent keyEvent = new KeyEvent(jUptimeMillis, jUptimeMillis, 0, 4, 1, 0, -1, 0, 0, 257);
        KeyEvent keyEvent2 = new KeyEvent(jUptimeMillis, jUptimeMillis + 1, 1, 4, 1, 0, -1, 0, 0, 257);
        this.a.mActivity.dispatchKeyEvent(keyEvent);
        this.a.mActivity.dispatchKeyEvent(keyEvent2);
    }
}
