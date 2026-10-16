package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
final class J0 implements Runnable {
    final /* synthetic */ UnityPlayer a;

    J0(UnityPlayer unityPlayer) {
        this.a = unityPlayer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        UnityPlayer unityPlayer = this.a;
        unityPlayer.removeView(unityPlayer.m_SplashScreen);
        this.a.m_SplashScreen = null;
    }
}
