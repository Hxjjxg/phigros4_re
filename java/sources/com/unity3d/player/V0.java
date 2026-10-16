package com.unity3d.player;

/* JADX INFO: loaded from: classes.dex */
abstract class V0 implements Runnable {
    final /* synthetic */ UnityPlayer a;

    private V0(UnityPlayer unityPlayer) {
        this.a = unityPlayer;
    }

    public abstract void a();

    @Override // java.lang.Runnable
    public final void run() {
        if (this.a.isFinishing()) {
            return;
        }
        a();
    }
}
