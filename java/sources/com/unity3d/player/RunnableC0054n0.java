package com.unity3d.player;

/* JADX INFO: renamed from: com.unity3d.player.n0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class RunnableC0054n0 implements Runnable {
    final /* synthetic */ UnityPlayer a;

    RunnableC0054n0(UnityPlayer unityPlayer) {
        this.a = unityPlayer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        C0044i0 c0044i0 = this.a.mGlView;
        if (c0044i0 != null) {
            c0044i0.b();
        }
    }
}
