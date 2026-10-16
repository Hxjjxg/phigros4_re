package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
final class K0 implements Runnable {
    final /* synthetic */ UnityPlayer a;

    K0(UnityPlayer unityPlayer) {
        this.a = unityPlayer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.nativeSendSurfaceChangedEvent();
    }
}
