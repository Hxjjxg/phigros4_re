package com.unity3d.player;

import android.graphics.Rect;
import java.util.concurrent.Semaphore;

/* JADX INFO: renamed from: com.unity3d.player.q0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class RunnableC0060q0 implements Runnable {
    boolean a = false;
    boolean b = false;
    final /* synthetic */ Semaphore c;
    final /* synthetic */ UnityPlayer d;
    final /* synthetic */ UnityPlayer e;

    RunnableC0060q0(UnityPlayer unityPlayer, Semaphore semaphore, UnityPlayer unityPlayer2) {
        this.e = unityPlayer;
        this.c = semaphore;
        this.d = unityPlayer2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.e.reportSoftInputArea(new Rect());
        this.e.reportSoftInputIsVisible(false);
        if (this.e.mSoftInput != null) {
            this.d.setOnHandleFocusListener(new C0056o0(this));
            UnityPlayer unityPlayer = this.e;
            unityPlayer.mSoftInput.g = new C0058p0(this);
            unityPlayer.dismissSoftInput();
        }
    }
}
