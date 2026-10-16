package com.unity3d.player;

/* JADX INFO: renamed from: com.unity3d.player.t0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class RunnableC0066t0 implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ UnityPlayer b;

    RunnableC0066t0(UnityPlayer unityPlayer, boolean z) {
        this.b = unityPlayer;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        S s = this.b.mSoftInput;
        if (s != null) {
            s.a(this.a);
        }
    }
}
