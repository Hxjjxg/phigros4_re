package com.unity3d.player;

import java.util.concurrent.Semaphore;

/* JADX INFO: loaded from: classes.dex */
final class P0 implements Runnable {
    final /* synthetic */ Semaphore a;
    final /* synthetic */ UnityPlayer b;

    P0(UnityPlayer unityPlayer, Semaphore semaphore) {
        this.b = unityPlayer;
        this.a = semaphore;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.b.nativePause()) {
            UnityPlayer unityPlayer = this.b;
            unityPlayer.mQuitting = true;
            unityPlayer.shutdown();
            this.b.queueDestroy();
        }
        this.a.release();
    }
}
