package com.unity3d.player;

/* JADX INFO: renamed from: com.unity3d.player.k0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class RunnableC0048k0 implements Runnable {
    final /* synthetic */ UnityPlayer a;

    RunnableC0048k0(UnityPlayer unityPlayer) {
        this.a = unityPlayer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.nativeResume();
    }
}
