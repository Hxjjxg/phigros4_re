package com.unity3d.player;

import java.util.concurrent.Semaphore;

/* JADX INFO: loaded from: classes.dex */
final class O0 implements Runnable {
    final /* synthetic */ Semaphore a;
    final /* synthetic */ UnityPlayer b;

    O0(UnityPlayer unityPlayer, Semaphore semaphore) {
        this.b = unityPlayer;
        this.a = semaphore;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.b.shutdown();
        this.a.release();
    }
}
