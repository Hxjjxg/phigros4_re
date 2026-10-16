package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
final class E0 implements Runnable {
    final /* synthetic */ float a;
    final /* synthetic */ UnityPlayer b;

    E0(UnityPlayer unityPlayer, float f) {
        this.b = unityPlayer;
        this.a = f;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.b.mGlView.a(this.a);
    }
}
