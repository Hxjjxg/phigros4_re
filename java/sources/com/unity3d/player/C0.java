package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
final class C0 implements Runnable {
    final /* synthetic */ UnityPlayer a;

    C0(UnityPlayer unityPlayer) {
        this.a = unityPlayer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.setupUnityToBePaused();
        this.a.windowFocusChanged(false);
        this.a.m_UnityPlayerLifecycleEvents.onUnityPlayerUnloaded();
    }
}
